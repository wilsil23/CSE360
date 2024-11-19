package adminPage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

/***
<p> ROLESGUI Class </p>
<p> Description: A JavaFX demonstration a choose role application and baseline for a sequence of projects </p>
<p> Copyright: Oscar Nguyen © 2024 </p>
@author Oscar Nguyen
@version 4.00    2017-10-16 The mainline of a JavaFX-based GUI implementation of a User
Interface testbed
@version 5.00    2022-09-22 Updated for use at ASU
*/

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;






public class ROLESGUI extends Application {
    static DatabaseHelper databaseHelper; // Database helper for DB operations
    private static BackupRestoreHelper backupRestoreHelper = new BackupRestoreHelper(); // Backup and restore helper
    private static final Scanner scanner = new Scanner(System.in); // Scanner for console input
    linkedlist userList = new linkedlist();
    private final Random random = new Random(); // Random instance for code generation

    
    
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

    @Override
    public void start(Stage primaryStage) {
        loginPage(primaryStage);
    }

    private void loginPage(Stage primaryStage) {
        VBox root = new VBox();
        primaryStage.setTitle("What is your Role?");

        Button adminButton = new Button("Admin");
        Button instructorButton = new Button("Instructor");
        Button studentButton = new Button("Student");

        // Set action for buttons
        adminButton.setOnAction(event -> adminBTN(primaryStage));
        instructorButton.setOnAction(event -> instructorBTN(primaryStage));
        studentButton.setOnAction(event -> studentBTN(primaryStage));

        root.getChildren().addAll(adminButton, instructorButton, studentButton);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        Scene scene = new Scene(root, 500, 450);
        primaryStage.setScene(scene);
        primaryStage.show(); // Show the role selection UI
    }

    // Action for the admin button
    public void adminBTN(Stage primaryStage) {
        adminPage adminPage = new adminPage();
        adminPage.start(primaryStage); // Redirect to AdminPage
    }

    public void instructorBTN(Stage secStage) {
        instructorPage instructorpage = new instructorPage();
        instructorpage.start(secStage);
    }

    public void studentBTN(Stage secStage) {
        studentPage studentpage = new studentPage();
        studentpage.start(secStage);
    }

    public void backBTN() {
        System.out.println("Back button works");
    }

    public void logoutBTN() {
        System.out.println("Logout LOGOUT");
    }





// Method to search the database

public void Search(String keyword) throws Exception {
	databaseHelper.keywordSearch(keyword);
}





// Method to delete an article by ID
public void deleteArticle() {
    // Create a TextInputDialog to get the article ID from the user
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Delete Article");
    dialog.setHeaderText("Enter the ID of the article to delete:");
    dialog.setContentText("Article ID:");

    // Show the dialog and wait for user input
    Optional<String> result = dialog.showAndWait();

    if (result.isPresent()) {
        try {
            int id = Integer.parseInt(result.get()); // Parse the ID

            // Delete the article from the database
            databaseHelper.deleteArticle(id);
            showAlert1("Success", "Article Deleted", "The article has been deleted successfully.");
        } catch (NumberFormatException e) {
            showAlert1("Error", "Invalid ID", "Please enter a valid numeric ID.");
        } catch (Exception e) {
            showAlert1("Error", "Deletion Failed", e.getMessage());
        }
    }
}

// Method to list all articles
// Method to list all articles
// Method to list all articles
public void listArticles() {
    try {
        // Create a ByteArrayOutputStream to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        // Redirecting System.out to capture the output
        PrintStream originalOut = System.out; // Save the original System.out
        System.setOut(printStream); // Set System.out to the PrintStream

        // Call the displayArticles method
        databaseHelper.displayArticles();

        // Restore the original System.out
        System.out.flush(); // Ensure all data is flushed to the original stream
        System.setOut(originalOut);

        // Get the output from the ByteArrayOutputStream
        String articlesText = outputStream.toString();

        // Show the formatted articles in a JavaFX Alert
        showAlert1("Article List", "All Articles", articlesText);
        
    } catch (Exception e) {
        showAlert1("Error", "Failed to List Articles", e.getMessage());
    }
}




// Method to back up articles to a file
// Method to backup articles to a specified file
public void backupArticles() {
    // Create a TextInputDialog to get the backup filename from the user
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Backup Articles");
    dialog.setHeaderText("Enter the backup filename:");
    dialog.setContentText("Filename (without extension):");

    // Show the dialog and wait for user input
    Optional<String> result = dialog.showAndWait();

    if (result.isPresent() && !result.get().trim().isEmpty()) {
        String filename = result.get().trim(); // Get the entered filename

        try {
            // Retrieve all articles and backup to the specified file
            List<Article> articles = databaseHelper.getAllArticles(); // You need to implement this method in DatabaseHelper
            backupRestoreHelper.backupArticles(articles, filename);
            showAlert1("Success", "Backup Complete", "Articles backed up successfully to " + filename);
        } catch (Exception e) {
            showAlert1("Error", "Backup Failed", e.getMessage());
        }
    } else {
        showAlert1("Error", "Invalid Filename", "Please enter a valid filename.");
    }
}


// Method to update an article by its ID
public void updateArticle() throws Exception {
    // Prompt for the ID of the article to update
    TextInputDialog idDialog = new TextInputDialog();
    idDialog.setTitle("Update Article");
    idDialog.setHeaderText("Enter Article ID");
    idDialog.setContentText("Article ID:");
    String idInput = idDialog.showAndWait().orElse(null);

    if (idInput == null || idInput.isEmpty()) {
        showAlert1("Error", "Input Required", "Article ID is required.");
        return;
    }

    int id;
    try {
        id = Integer.parseInt(idInput);
    } catch (NumberFormatException e) {
        showAlert1("Error", "Invalid Input", "Please enter a valid article ID.");
        return;
    }

    // Fetch the existing article details to pre-fill the dialog
    Article existingArticle = databaseHelper.getArticleById(id);
    if (existingArticle == null) {
        showAlert1("Error", "Article Not Found", "No article found with the provided ID.");
        return;
    }

    // Create a dialog to update article details
    Dialog<Article> dialog = new Dialog<>();
    dialog.setTitle("Update Article");
    dialog.setHeaderText("Update Article Details");

    // Create a form to gather updated information
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    // Create fields for each article attribute
    TextField difficultyField = new TextField(existingArticle.getDifficulty());
    difficultyField.setPromptText("Difficulty");
    TextField titleField = new TextField(existingArticle.getTitle());
    titleField.setPromptText("Title");
    TextField authorsField = new TextField(existingArticle.getAuthors());
    authorsField.setPromptText("Authors");
    TextField abstractField = new TextField(existingArticle.getAbstractText());
    abstractField.setPromptText("Abstract");
    TextField bodyField = new TextField(existingArticle.getBody());
    bodyField.setPromptText("Body");
    TextField keywordsField = new TextField(existingArticle.getKeywords());
    keywordsField.setPromptText("Keywords");
    TextField referencesField = new TextField(existingArticle.getReferences());
    referencesField.setPromptText("References");

    // Add fields to the grid
    grid.add(new Label("Difficulty:"), 0, 0);
    grid.add(difficultyField, 1, 0);
    grid.add(new Label("Title:"), 0, 1);
    grid.add(titleField, 1, 1);
    grid.add(new Label("Authors:"), 0, 2);
    grid.add(authorsField, 1, 2);
    grid.add(new Label("Abstract:"), 0, 3);
    grid.add(abstractField, 1, 3);
    grid.add(new Label("Body:"), 0, 4);
    grid.add(bodyField, 1, 4);
    grid.add(new Label("Keywords:"), 0, 5);
    grid.add(keywordsField, 1, 5);
    grid.add(new Label("References:"), 0, 6);
    grid.add(referencesField, 1, 6);

    dialog.getDialogPane().setContent(grid);

    // Add buttons for confirmation and cancellation
    ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == updateButtonType) {
            // Create and return an updated Article object with all fields, including the ID
            return new Article(id, difficultyField.getText(), titleField.getText(), authorsField.getText(),
                               abstractField.getText(), bodyField.getText(),
                               keywordsField.getText(), referencesField.getText());
        }
        return null;
    });

    // Show the dialog and wait for the result
    Optional<Article> result = dialog.showAndWait();
    result.ifPresent(article -> {
        // Call the database helper to update the article
		databaseHelper.updateArticle(article);
		showAlert("Success", "Article Updated", "The article has been updated successfully.");
    });
}



// Method to restore articles from a file
public void restoreArticles() {
    // Create a TextInputDialog to get the restore filename from the user
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Restore Articles");
    dialog.setHeaderText("Enter the restore filename:");
    dialog.setContentText("Filename (without extension):");

    // Show the dialog and wait for user input
    Optional<String> result = dialog.showAndWait();

    if (result.isPresent() && !result.get().trim().isEmpty()) {
        String filename = result.get().trim(); // Get the entered filename

        try {
            // Clear existing articles before restoring
            databaseHelper.clearArticles();

            // Restore articles from the backup file and load them into the database
            List<Article> articles = backupRestoreHelper.restoreArticles(filename);
            databaseHelper.loadArticles(articles);
            showAlert1("Success", "Restore Complete", "Articles restored successfully from " + filename);
        } catch (Exception e) {
            showAlert1("Error", "Restore Failed", e.getMessage());
        }
    } else {
        showAlert1("Error", "Invalid Filename", "Please enter a valid filename.");
    }
}

// Helper method to show alert messages
	void showAlert(String title, String header, String content) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(header);
	    alert.setContentText(content);
	    alert.showAndWait();
	}
	
	
	// Method to show alerts
	public void showAlert1(String title, String header, String content) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(header);
	    alert.setContentText(content);
	    alert.showAndWait();
	}

    public void addArticle() {
        try {
        	
        	 // Dialog for diffculty
        	// Dialog for difficulty
        	ChoiceDialog<String> difficultyDialog = new ChoiceDialog<>("Beginner", "Beginner", "Intermediate", "Advanced");
        	difficultyDialog.setTitle("Add Article");
        	difficultyDialog.setHeaderText("Select Difficulty");
        	difficultyDialog.setContentText("Difficulty:");

        	String difficulty = difficultyDialog.showAndWait().orElse(null);

        	if (difficulty == null || difficulty.isEmpty()) {
        	    showAlert1("Error", "Input Required", "Difficulty is required.");
        	    return;
        	}
            // Dialog for Title
            TextInputDialog titleDialog = new TextInputDialog();
            titleDialog.setTitle("Add Article");
            titleDialog.setHeaderText("Enter Article Title");
            titleDialog.setContentText("Title:");
            String title = titleDialog.showAndWait().orElse(null);
            
            if (title == null || title.isEmpty()) {
                showAlert1("Error", "Input Required", "Article title is required.");
                return;
            }

            // Dialog for Authors
            TextInputDialog authorsDialog = new TextInputDialog();
            authorsDialog.setTitle("Add Article");
            authorsDialog.setHeaderText("Short Description");
            authorsDialog.setContentText("Description:");
            String authors = authorsDialog.showAndWait().orElse(null);
            
            if (authors == null || authors.isEmpty()) {
                showAlert1("Error", "Input Required", "Description are required.");
                return;
            }

            // Dialog for Abstract
            TextInputDialog abstractDialog = new TextInputDialog();
            abstractDialog.setTitle("Add Article");
            abstractDialog.setHeaderText("Enter Keyword's");
            abstractDialog.setContentText("Keyword's:");
            String abstractText = abstractDialog.showAndWait().orElse(null);
            
            if (abstractText == null || abstractText.isEmpty()) {
                showAlert1("Error", "Input Required", "Keyword's is required.");
                return;
            }

            // Dialog for Body
            TextInputDialog bodyDialog = new TextInputDialog();
            bodyDialog.setTitle("Add Article");
            bodyDialog.setHeaderText("Enter Body");
            bodyDialog.setContentText("Body:");
            String body = bodyDialog.showAndWait().orElse(null);
            
            if (body == null || body.isEmpty()) {
                showAlert1("Error", "Input Required", "Body is required.");
                return;
            }

            // Dialog for Keywords
            TextInputDialog keywordsDialog = new TextInputDialog();
            keywordsDialog.setTitle("Add Article");
            keywordsDialog.setHeaderText("Enter Link's");
            keywordsDialog.setContentText("Link's:");
            String keywords = keywordsDialog.showAndWait().orElse(null);
            
            if (keywords == null || keywords.isEmpty()) {
                showAlert1("Error", "Input Required", "Link's are required.");
                return;
            }

            // Dialog for References
            TextInputDialog referencesDialog = new TextInputDialog();
            referencesDialog.setTitle("Add Article");
            referencesDialog.setHeaderText("Group Information");
            referencesDialog.setContentText("Group:");
            String references = referencesDialog.showAndWait().orElse(null);
            
            if (references == null || references.isEmpty()) {
                showAlert1("Error", "Input Required", "Group information is required.");
                return;
            }

            // Create the article in the database
            databaseHelper.createArticle(difficulty, title, authors, abstractText, body, keywords, references);
            showAlert1("Success", "Article Added", "The article has been added successfully.");
            
        } catch (Exception e) {
            showAlert1("Error", "Failed to Add Article", e.getMessage());
        }
    }

	public void addStudent() {
		// TODO Auto-generated method stub
		
	}

	public void deleteStudent() {
		// TODO Auto-generated method stub
		
	}

	public void listStudents() {
		// TODO Auto-generated method stub
		
	}
	
}
