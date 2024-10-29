package adminPage;

import java.util.Optional;

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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ROLESGUI extends Application {
    public static void main(String[] args) {
        launch(args);
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
        linkedlist userList = new linkedlist();
    	adminPage instructorPage = new adminPage();
        secStage.setTitle("Instructor");
        Button logoutButton = new Button("Logout");

        // Logout action that returns to the login page
        logoutButton.setOnAction(event -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(secStage); // Redirect to login page
        });
 
        // Buttons for the various article functions
        Button addArticleButton = new Button("Add Article");
        Button deleteArticleButton = new Button("Delete Article");
        Button listArticlesButton = new Button("List Articles");
        Button backupArticlesButton = new Button("Backup Articles");
        Button restoreArticlesButton = new Button("Restore Articles");
        Button btnLogout = new Button("Logout");

        btnLogout.setOnAction(e -> {
            // Confirm logout action
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Logout Confirmation");
            confirmationAlert.setHeaderText("Are you sure you want to log out?");
            confirmationAlert.setContentText("Click 'OK' to log out or 'Cancel' to stay.");

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Close the current window
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                stage.close(); // Close the admin page

                // Launch the LoginPage
                LoginPage loginPage = new LoginPage();
                try {
                    loginPage.start(new Stage()); // Open the login page in a new stage
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        // Button actions
        addArticleButton.setOnAction(e -> {
            try {
                instructorPage.addArticle();
            } catch (Exception ex) {
            	instructorPage.showAlert1("Error", "Failed to Add Article", ex.getMessage());
            }
        });
        deleteArticleButton.setOnAction(e -> {
            try {
                instructorPage.deleteArticle();
            } catch (Exception ex) {
            	instructorPage.showAlert1("Error", "Failed to Delete Article", ex.getMessage());
            }
        });
        listArticlesButton.setOnAction(e -> {
            try {
                instructorPage.listArticles();
            } catch (Exception ex) {
                instructorPage.showAlert1("Error", "Failed to List Articles", ex.getMessage());
            }
        });
        backupArticlesButton.setOnAction(e -> {
            try {
            	instructorPage.backupArticles();
            } catch (Exception ex) {
            	instructorPage.showAlert1("Error", "Failed to Backup Articles", ex.getMessage());
            }
        });
        restoreArticlesButton.setOnAction(e -> {
            try {
            	instructorPage.restoreArticles();
            } catch (Exception ex) {
            	instructorPage.showAlert1("Error", "Failed to Restore Articles", ex.getMessage());
            }
        });
        Button updateButton = new Button("Update Article");

     // Set the action for the button
     updateButton.setOnAction(event -> {
         try {
             // Call the updateArticle method when the button is clicked
             instructorPage.updateArticle();
         } catch (Exception e) {
             instructorPage.showAlert("Error", "Update Failed", "An error occurred while updating the article: " + e.getMessage());
         }
     });

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(btnLogout, addArticleButton, deleteArticleButton, updateButton, listArticlesButton, backupArticlesButton, restoreArticlesButton);

        // Set up and show the stage
        Scene scene = new Scene(layout, 400, 300);
        secStage.setScene(scene);
        secStage.show();
    }

    public void studentBTN(Stage secStage) {
        secStage.setTitle("Student");
        Button logoutButton = new Button("Logout");

        // Logout action that returns to the login page
        logoutButton.setOnAction(event -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(secStage); // Redirect to login page
        });

        StackPane root = new StackPane();
        root.getChildren().add(logoutButton);
        secStage.setScene(new Scene(root, 500, 450));
        secStage.show();
    }

    public void backBTN() {
        System.out.println("Back button works");
    }

    public void logoutBTN() {
        System.out.println("Logout LOGOUT");
    }
}
