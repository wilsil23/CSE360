package adminPage;
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
import javafx.geometry.Pos;
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
<p> Admin Page with functionalitys</p>
<p> Description: This class provides methods and also design for our admin page. It has all the functionalities. </p>
<p> Copyright: Ivan Bustamante, Oscar Nguyen © 2024 </p>
@author Ivan Bustamante Campana, Oscar Nguyen
@version 5.00    2024-10-20 Updated for use at ASU
*/





public class adminPage extends Application {
    private static DatabaseHelper databaseHelper; // Database helper for DB operations
    private static BackupRestoreHelper backupRestoreHelper = new BackupRestoreHelper(); // Backup and restore helper
    //private static final Scanner scanner = new Scanner(System.in); // Scanner for console input
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
        primaryStage.setTitle("Admin Page");

        // Buttons for the various article functions
        Button addArticleButton = new Button("Add Article");
        Button deleteArticleButton = new Button("Delete Article");
        Button listArticlesButton = new Button("List Articles");
        Button backupArticlesButton = new Button("Backup Articles");
        Button restoreArticlesButton = new Button("Restore Articles");
        Button AddUser = new Button("Add User");
        Button ListUsers = new Button("List Users");
        Button btnLogout = new Button("Logout");
        Button Generatecode = new Button("Generate Code");
        Button DeleteUser = new Button("Delete Users");
        Button UpdateUser = new Button("Update Role");
        Button openAccessGroupButton = new Button("Open Instructor Access Group");
        AddUser.setOnAction(e -> addUser()); // Call addUser when clicked
        openAccessGroupButton.setOnAction(event -> openAccessGroupPage());
        DeleteUser.setOnAction(e -> {
            // Create a TextInputDialog to prompt for the username
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Delete User");
            dialog.setHeaderText("Enter Username to Delete");
            dialog.setContentText("Username:");

            // Capture the input
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(username -> deleteUser(username)); // Call deleteUser if a username is entered
        });

        // List users when "List Users" button is clicked
        ListUsers.setOnAction(e -> {
            // Fetch users from the linked list
            StringBuilder userListStr = new StringBuilder();
            UserNode current = userList.head; // Assuming userList is your linkedlist instance
            userListStr.append("List of User Accounts:\n"); // Optional header for clarity
            while (current != null) {
                userListStr.append("Username: ")
                           .append(current.username != null ? current.username : "N/A")
                           .append(", Name: ")
                           .append(current.name != null ? current.name : "N/A")
                           .append(", Roles: ")
                           .append(current.roles != null ? current.roles : "N/A")
                           .append("\n");
                current = current.next;
            }

            // Show the users in an Alert dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("List of Users");
            alert.setHeaderText("Current Users:");
            alert.setContentText(userListStr.length() > 0 ? userListStr.toString() : "No users available.");
            alert.showAndWait();
        });

        UpdateUser.setOnAction(e -> modifyUserRole()); // Call modifyUserRole when clicked

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


        Generatecode.setOnAction(e -> createInvitationCode());

        // Button actions
        addArticleButton.setOnAction(e -> {
            try {
                addArticle();
            } catch (Exception ex) {
                showAlert1("Error", "Failed to Add Article", ex.getMessage());
            }
        });
        deleteArticleButton.setOnAction(e -> {
            try {
                deleteArticle();
            } catch (Exception ex) {
                showAlert1("Error", "Failed to Delete Article", ex.getMessage());
            }
        });
        listArticlesButton.setOnAction(e -> {
            try {
                listArticles();
            } catch (Exception ex) {
                showAlert1("Error", "Failed to List Articles", ex.getMessage());
            }
        });
        backupArticlesButton.setOnAction(e -> {
            try {
                backupArticles();
            } catch (Exception ex) {
                showAlert1("Error", "Failed to Backup Articles", ex.getMessage());
            }
        });
        restoreArticlesButton.setOnAction(e -> {
            try {
                restoreArticles();
            } catch (Exception ex) {
                showAlert1("Error", "Failed to Restore Articles", ex.getMessage());
            }
        });
        Button updateButton = new Button("Update Article");

     // Set the action for the button
     updateButton.setOnAction(event -> {
         try {
             // Call the updateArticle method when the button is clicked
             updateArticle();
         } catch (Exception e) {
             showAlert("Error", "Update Failed", "An error occurred while updating the article: " + e.getMessage());
         }
     });

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(AddUser, DeleteUser, ListUsers, UpdateUser, btnLogout, Generatecode,addArticleButton, deleteArticleButton,  listArticlesButton, backupArticlesButton, restoreArticlesButton,openAccessGroupButton);

        // Set up and show the stage
        Scene scene = new Scene(layout, 400, 300);
        layout.setAlignment(Pos.CENTER);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void openAccessGroupPage() {
        AccessGroup accessGroup = new AccessGroup();
        try {
            accessGroup.start(new Stage());  // Open AccessGroup page in a new stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to add an article
    private static void addArticle() {
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
 // Method to generate a random invitation code
    private String generateRandomCode() {
        // Generate a random alphanumeric string
        int length = 8; // Specify the length of the code
        StringBuilder code = new StringBuilder(length);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Alphanumeric characters
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }

    // Method to create invitation code
    public void createInvitationCode() {
        TextInputDialog roleDialog = new TextInputDialog();
        roleDialog.setTitle("Generate Invitation Code");
        roleDialog.setHeaderText("Enter Role");
        roleDialog.setContentText("Role (e.g., Student, Teacher):");

        Optional<String> role = roleDialog.showAndWait();

        if (role.isPresent() && !role.get().isEmpty()) {
            // Generate an invitation code
            String invitationCode = generateRandomCode();

            // Optionally, you can prompt for email and username if needed
            // For simplicity, we'll assume that the admin is generating a code without immediate user details

            // Add a pending invitation to the linked list with null user details
            userList.addUser(null, null, null, null, role.get(), invitationCode);

            // Show confirmation alert with the invitation code and role
            showAlert1("Success", "Invitation Code Generated",
                      "Invitation Code: " + invitationCode + "\nRole: " + role.get());

            // Optionally, display the invitation code in the console for debugging
           // System.out.println("Invitation Code: " + invitationCode + " for Role: " + role.get());
        } else {
            showAlert1("Error", "No Role Provided", "Please enter a valid role.");
        }
    }

    // Method to show alerts
    private static void showAlert1(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to prompt and add a user to the linked list
    public void addUser() {
        // Create dialog boxes for user inputs
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Add User");
        emailDialog.setHeaderText("Enter User Email");
        emailDialog.setContentText("Email:");

        Optional<String> email = emailDialog.showAndWait();

        if (email.isPresent() && !email.get().isEmpty()) {
            TextInputDialog usernameDialog = new TextInputDialog();
            usernameDialog.setTitle("Add User");
            usernameDialog.setHeaderText("Enter Username");
            usernameDialog.setContentText("Username:");

            Optional<String> username = usernameDialog.showAndWait();

            if (username.isPresent() && !username.get().isEmpty()) {
                // Check for duplicate username
                if (userList.findUserByUsername(username.get()) != null) {
                    showAlert1("Error", "Duplicate Username", "A user with the username '" + username.get() + "' already exists.");
                    return; // Exit the method to prevent adding the user
                }

                TextInputDialog passwordDialog = new TextInputDialog();
                passwordDialog.setTitle("Add User");
                passwordDialog.setHeaderText("Enter Password");
                passwordDialog.setContentText("Password:");

                Optional<String> password = passwordDialog.showAndWait();

                if (password.isPresent() && !password.get().isEmpty()) {
                    TextInputDialog nameDialog = new TextInputDialog();
                    nameDialog.setTitle("Add User");
                    nameDialog.setHeaderText("Enter Name");
                    nameDialog.setContentText("Name:");

                    Optional<String> name = nameDialog.showAndWait();

                    if (name.isPresent() && !name.get().isEmpty()) {
                        TextInputDialog roleDialog = new TextInputDialog();
                        roleDialog.setTitle("Add User");
                        roleDialog.setHeaderText("Enter Role (Student, Teacher)");
                        roleDialog.setContentText("Role:");

                        Optional<String> role = roleDialog.showAndWait();

                        if (role.isPresent() && !role.get().isEmpty()) {
                            // Add user to the list with the specified role and no invitation code
                            userList.addUser(email.get(), username.get(), password.get(), name.get(), role.get(), null);
                            System.out.println("User added: " + username.get() + " with role: " + role.get());

                            // Show confirmation alert
                            showAlert1("Success", "User Added", "User '" + username.get() + "' has been added successfully.");
                        }
                    }
                }
            }
        }
    }

    // Method to delete a user by username from the linked list with confirmation
    public void deleteUser(String username) {
        // Create a confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete User");
        confirmationAlert.setHeaderText("Are you sure?");
        confirmationAlert.setContentText("Do you want to delete the user '" + username + "'?");

        // Wait for the user's response
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // If the user confirms by clicking "Yes"
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Proceed with the deletion
            boolean success = userList.removeByUser(username);  // Use the linked list's remove method

            // Show a message based on whether the deletion was successful
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete User");

            if (success) {
                alert.setHeaderText("User Deleted");
                alert.setContentText("The user '" + username + "' was successfully deleted.");
            } else {
                alert.setHeaderText("User Not Found");
                alert.setContentText("No user with the username '" + username + "' was found.");
            }

            alert.showAndWait();
        } else {
            // If the user cancels, show a cancellation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete User");
            alert.setHeaderText("Action Cancelled");
            alert.setContentText("The deletion of the user '" + username + "' was cancelled.");
            alert.showAndWait();
        }
    }

    // Method to modify a user's role
    public void modifyUserRole() {
        // Step 1: Input the username for the user to modify
        TextInputDialog usernameDialog = new TextInputDialog();
        usernameDialog.setTitle("Modify User Role");
        usernameDialog.setHeaderText("Enter the Username");
        usernameDialog.setContentText("Username:");

        Optional<String> username = usernameDialog.showAndWait();

        if (username.isPresent() && !username.get().isEmpty()) {
            // Step 2: Check if the user exists
            UserNode user = userList.findUserByUsername(username.get());

            if (user != null) {
                // Step 3: Ask if the admin wants to add or remove a role
                Alert roleChoiceAlert = new Alert(Alert.AlertType.CONFIRMATION);
                roleChoiceAlert.setTitle("Modify Role");
                roleChoiceAlert.setHeaderText("Add or Remove Role");
                roleChoiceAlert.setContentText("Do you want to add or remove a role from the user '" + username.get() + "'?");

                ButtonType addRole = new ButtonType("Add Role");
                ButtonType removeRole = new ButtonType("Remove Role");
                ButtonType cancel = new ButtonType("Cancel");

                roleChoiceAlert.getButtonTypes().setAll(addRole, removeRole, cancel);

                Optional<ButtonType> result = roleChoiceAlert.showAndWait();

                // Step 4: Handle the selection
                if (result.isPresent()) {
                    if (result.get() == addRole) {
                        // Step 5: Prompt for new role to add
                        TextInputDialog roleDialog = new TextInputDialog();
                        roleDialog.setTitle("Add Role");
                        roleDialog.setHeaderText("Enter New Role to Add");
                        roleDialog.setContentText("Role:");

                        Optional<String> newRole = roleDialog.showAndWait();

                        if (newRole.isPresent() && !newRole.get().isEmpty()) {
                            user.addRole(newRole.get());
                            System.out.println("Role added: " + newRole.get() + " to user " + username.get());
                            showAlert1("Success", "Role Added", "The role '" + newRole.get() + "' has been added to user '" + username.get() + "'.");
                        }

                    } else if (result.get() == removeRole) {
                        // Step 6: Prompt for role to remove
                        TextInputDialog roleDialog = new TextInputDialog();
                        roleDialog.setTitle("Remove Role");
                        roleDialog.setHeaderText("Enter Role to Remove");
                        roleDialog.setContentText("Role:");

                        Optional<String> roleToRemove = roleDialog.showAndWait();

                        if (roleToRemove.isPresent() && !roleToRemove.get().isEmpty()) {
                            boolean removed = user.removeRole(roleToRemove.get());
                            if (removed) {
                                System.out.println("Role removed: " + roleToRemove.get() + " from user " + username.get());
                                showAlert1("Success", "Role Removed", "The role '" + roleToRemove.get() + "' has been removed from user '" + username.get() + "'.");
                            } else {
                                showAlert1("Failure", "Role Not Found", "The role '" + roleToRemove.get() + "' was not found for user '" + username.get() + "'.");
                            }
                        }

                    } else {
                        // Cancelled operation
                        showAlert1("Cancelled", "Operation Cancelled", "No role was modified.");
                    }
                }
            } else {
                // User not found
                showAlert1("Failure", "User Not Found", "No user with the username '" + username.get() + "' was found.");
            }
        }
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
    private static void listArticles() {
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
    private static void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}