package adminPage;

import javafx.application.Application;
import javafx.scene.control.Label;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/***
<p> instructorPage class </p>
<p> Description: This acts as a GUI for the instructor homepage,
    including adding, retrieving, and deleting articles. </p>
<p> Copyright: William Diamond, Carson Williams, Oscar Nguyen © 2024 </p>
@author William Diamond, Carson Williams, Oscar Nguyen
@version 5.00    2024-10-20 Updated for use at ASU
*/

public class instructorPage extends Application {
	linkedlist userList = new linkedlist();
    ROLESGUI instructorPage = new ROLESGUI();
    @Override
    public void start(Stage secStage) {
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
        // new buttons for phase 3
        Button groupButton = new Button("Group Management");
        
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
        
     // added new public method and buttons for phase 3, new window when creating a new group
        groupButton.setOnAction(e -> GroupManagement(secStage));

        private void GroupManagement(Stage secStage) {
            
            Stage dialog = new Stage();
            dialog.setTitle("Group Management");

            // Buttons for different actions
            Button addStudentButton = new Button("Add Student");
            Button deleteStudentButton = new Button("Delete Student");
            Button listStudentsButton = new Button("View Students");
            Button closeButton = new Button("Exit");
            
         // Add student to group
            addStudentButton.setOnAction(e -> {
                try {
                    instructorPage.addStudent(); 
                } catch (Exception ex) {
                    instructorPage.showAlert1("Error", "Failed to Add Student", ex.getMessage());
                }
            });
         // Delete student from group
            deleteStudentButton.setOnAction(e -> {
                try {
                    instructorPage.deleteStudent(); 
                } catch (Exception ex) {
                    instructorPage.showAlert1("Error", "Failed to Delete Student", ex.getMessage());
                }
            });
         // View list of students
            listStudentsButton.setOnAction(e -> {
                try {
                    instructorPage.listStudents(); 
                } catch (Exception ex) {
                    instructorPage.showAlert1("Error", "Failed to List Students", ex.getMessage());
                }
            });
         // Close button to return to the instructor page
            closeButton.setOnAction(e -> {
                dialog.close(); 
            });

            // Layout setup for dialog
            VBox dialogLayout = new VBox(10);
            dialogLayout.getChildren().addAll(addStudentButton, deleteStudentButton, listStudentsButton, closeButton);

            // Set up scene and show the dialog
            Scene dialogScene = new Scene(dialogLayout, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();
        }
    }
        
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
        layout.getChildren().addAll(btnLogout, addArticleButton, deleteArticleButton, updateButton, listArticlesButton, backupArticlesButton, restoreArticlesButton, groupButton);

        // Set up and show the stage
        Scene scene = new Scene(layout, 400, 300);
        secStage.setScene(scene);
        secStage.show();
    }   
}