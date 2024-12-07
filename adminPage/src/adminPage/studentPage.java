package adminPage; 

import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;





/***
<p> Student page with search functionalitys</p>
<p> Description: This class provides the student page with search functionalities for articles and also able to send messages to instructor/admin </p>
<p> Copyright: Ivan Bustamante, Oscar Nguyen © 2024 </p>
@author Ivan Bustamante Campana, Oscar Nguyen
@version 5.00    2024-10-20 Updated for use at ASU
*/


public class studentPage extends Application {

    private static DatabaseHelper databaseHelper; // Assuming you have a DatabaseHelper for querying the DB
    
    @Override
    public void start(Stage studentStage) {
        studentStage.setTitle("Student Page");

        // Example content for the student page
        Button logoutButton = new Button("Logout");
        Button searchByTitleButton = new Button("Search by Title");
        Button searchByDifficultyButton = new Button("Search by Difficulty");
        Button searchByIdButton = new Button("Search by ID");
        Button sendGenericMessageButton = new Button("Send Generic Message");
        Button sendSpecificMessageButton = new Button("Send Specific Message");
        
        
        // ComboBox to choose search method
        ComboBox<String> searchMethodComboBox = new ComboBox<>();
        searchMethodComboBox.getItems().addAll("Search by Title", "Search by Difficulty", "Search by ID", "List All Articles");
        searchMethodComboBox.setPromptText("Choose search method");

        // Text fields and ComboBox for search inputs (initially hidden)
        TextField titleInput = new TextField();
        titleInput.setPromptText("Enter title to search");
        titleInput.setVisible(false);

        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Beginner", "Intermediate", "Advanced");
        difficultyComboBox.setPromptText("Select difficulty");
        difficultyComboBox.setVisible(false);

        TextField idInput = new TextField();
        idInput.setPromptText("Enter ID to search");
        idInput.setVisible(false);

        // ListView to display search results
        ListView<Article> searchResultsList = new ListView<>();
        searchResultsList.setPrefHeight(200);  // Adjust the height of the ListView for better display

        // TextArea for displaying full article details
        TextArea articleDetails = new TextArea();
        articleDetails.setEditable(false);
        articleDetails.setWrapText(true);
        articleDetails.setPromptText("Article details will appear here...");
        
     // TextArea for generic message
        TextArea genericMessageBox = new TextArea();
        genericMessageBox.setPromptText("Enter your generic message here...");
        genericMessageBox.setVisible(false);
        
        TextArea specificMessageBox = new TextArea();
        specificMessageBox.setPromptText("Enter your specific message here...");
        specificMessageBox.setVisible(false);

        // Button for sending message
        Button sendGenericMessageButtonAction = new Button("Send Message");
        sendGenericMessageButtonAction.setVisible(false);

        Button sendSpecificMessageButtonAction = new Button("Send Message");
        sendSpecificMessageButtonAction.setVisible(false);
        
     // Show generic message box when the button is clicked
        sendGenericMessageButton.setOnAction(event -> {
            genericMessageBox.setVisible(true);
            sendGenericMessageButtonAction.setVisible(true);
        });

        // Show specific message box when the button is clicked
        sendSpecificMessageButton.setOnAction(event -> {
            specificMessageBox.setVisible(true);
            sendSpecificMessageButtonAction.setVisible(true);
        });

        // Action for sending the generic message
        sendGenericMessageButtonAction.setOnAction(event -> {
            String message = genericMessageBox.getText().trim();
            if (!message.isEmpty()) {
                // Here, you can send the message (or display it for now)
                System.out.println("Sending Generic Message: " + message);
                genericMessageBox.clear(); // Clear the message after sending
                genericMessageBox.setVisible(false); // Hide the box
                sendGenericMessageButtonAction.setVisible(false); // Hide the send button
            }
        });

        // Action for sending the specific message
        sendSpecificMessageButtonAction.setOnAction(event -> {
            String message = specificMessageBox.getText().trim();
            if (!message.isEmpty()) {
                // Here, you can send the message (or display it for now)
                System.out.println("Sending Specific Message: " + message);
                specificMessageBox.clear(); // Clear the message after sending
                specificMessageBox.setVisible(false); // Hide the box
                sendSpecificMessageButtonAction.setVisible(false); // Hide the send button
            }
        });


        // Action for the search method selection
        searchMethodComboBox.setOnAction(event -> {
            String selectedMethod = searchMethodComboBox.getValue();

            // Hide all input fields initially
            titleInput.setVisible(false);
            difficultyComboBox.setVisible(false);
            idInput.setVisible(false);

            // Show the relevant input field based on the search method selected
            if (selectedMethod != null) {
                switch (selectedMethod) {
                    case "Search by Title":
                        titleInput.setVisible(true);
                        break;
                    case "Search by Difficulty":
                        difficultyComboBox.setVisible(true);
                        break;
                    case "Search by ID":
                        idInput.setVisible(true);
                        break;
                    case "List All Articles":
                        // Handle listing all articles
                        searchResultsList.getItems().clear();
                        try {
                            List<Article> articles = databaseHelper.getAllArticles();
                            if (!articles.isEmpty()) {
                                searchResultsList.getItems().addAll(articles);
                            } else {
                                searchResultsList.getItems().add(null); // Indicating no articles available
                            }
                        } catch (SQLException e) {
                            System.err.println("Error retrieving all articles: " + e.getMessage());
                        }
                        break;
                }
            }
        });
        

        // Search by title action (Button and Enter key press)
        searchByTitleButton.setOnAction(event -> searchByTitle(titleInput, searchResultsList));
        titleInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchByTitle(titleInput, searchResultsList);
            }
        });
        logoutButton.setOnAction(event -> {
            // Close the current studentPage window
            studentStage.close();

            // Show the LoginPage
            LoginPage loginPage = new LoginPage();
            try {
                loginPage.start(new Stage());  // Open the LoginPage in a new stage (window)
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

       
        
        // Search by difficulty action (Button and Enter key press)
        searchByDifficultyButton.setOnAction(event -> searchByDifficulty(difficultyComboBox, searchResultsList));
        difficultyComboBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchByDifficulty(difficultyComboBox, searchResultsList);
            }
        });

        // Search by ID action (Button and Enter key press)
        searchByIdButton.setOnAction(event -> searchById(idInput, searchResultsList));
        idInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                searchById(idInput, searchResultsList);
            }
        });

        // Add a listener to update the article details when an item is selected in the ListView
        searchResultsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Display the full article details in the TextArea
                articleDetails.setText(formatArticleDetails(newValue));
            }
        });

        // Layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(searchMethodComboBox, titleInput, difficultyComboBox, idInput, searchResultsList, articleDetails, logoutButton, sendGenericMessageButton, sendSpecificMessageButton, genericMessageBox, sendGenericMessageButtonAction,
                specificMessageBox, sendSpecificMessageButtonAction);

        // Set scene and show stage
        studentStage.setScene(new Scene(layout, 600, 600));
        studentStage.show();
    }

    private void searchByTitle(TextField titleInput, ListView<Article> searchResultsList) {
        String title = titleInput.getText().trim();
        searchResultsList.getItems().clear();  // Clear previous results
        if (!title.isEmpty()) {
            try {
                Article article = databaseHelper.getArticleByTitle(title);
                if (article != null) {
                    searchResultsList.getItems().add(article);
                } else {
                    searchResultsList.getItems().add(null); // Indicating no result found
                }
            } catch (SQLException e) {
                System.err.println("Error searching article by title: " + e.getMessage());
            }
        }
    }

    private void searchByDifficulty(ComboBox<String> difficultyComboBox, ListView<Article> searchResultsList) {
        String difficulty = difficultyComboBox.getValue();
        searchResultsList.getItems().clear();  // Clear previous results
        if (difficulty != null && !difficulty.isEmpty()) {
            try {
                List<Article> articles = databaseHelper.getArticlesByDifficulty(difficulty);
                if (!articles.isEmpty()) {
                    searchResultsList.getItems().addAll(articles);
                } else {
                    searchResultsList.getItems().add(null); // Indicating no result found
                }
            } catch (SQLException e) {
                System.err.println("Error searching article by difficulty: " + e.getMessage());
            }
        }
    }

    private void searchById(TextField idInput, ListView<Article> searchResultsList) {
        String idText = idInput.getText().trim();
        searchResultsList.getItems().clear();  // Clear previous results
        if (!idText.isEmpty()) {
            try {
                int id = Integer.parseInt(idText);  // Convert the ID to integer
                Article article = databaseHelper.getArticleById(id);
                if (article != null) {
                    searchResultsList.getItems().add(article);
                } else {
                    searchResultsList.getItems().add(null); // Indicating no result found
                }
            } catch (NumberFormatException e) {
                searchResultsList.getItems().add(null); // Invalid ID input
            } catch (SQLException e) {
                System.err.println("Error searching article by ID: " + e.getMessage());
            }
        }
    }

    // Helper method to format article details for display in the TextArea
    private String formatArticleDetails(Article article) {
        return "Title: " + article.getTitle() + "\n" +
                "Authors: " + article.getAuthors() + "\n" +
                "Abstract: " + article.getAbstractText() + "\n" +
                "Body: " + article.getBody() + "\n" +
                "Keywords: " + article.getKeywords() + "\n" +
                "References: " + article.getReferences();
    }

    public static void main(String[] args) throws Exception {
        databaseHelper = new DatabaseHelper();
        try {
            databaseHelper.connectToDatabase();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }

        // Launch the JavaFX application
        launch(args);

        // Ensure database connection is closed upon application exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> databaseHelper.closeConnection()));
    }
}