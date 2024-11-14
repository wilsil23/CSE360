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


public class adminPage extends Application {
    linkedlist userList = new linkedlist();
    private final Random random = new Random(); // Random instance for code generation.
    ROLESGUI adminpage = new ROLESGUI();
    
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
        
        AddUser.setOnAction(e -> addUser()); // Call addUser when clicked

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
                adminpage.addArticle();
            } catch (Exception ex) {
            	adminpage.showAlert1("Error", "Failed to Add Article", ex.getMessage());
            }
        });
        deleteArticleButton.setOnAction(e -> {
            try {
            	adminpage.deleteArticle();
            } catch (Exception ex) {
            	adminpage.showAlert1("Error", "Failed to Delete Article", ex.getMessage());
            }
        });
        listArticlesButton.setOnAction(e -> {
            try {
            	adminpage.listArticles();
            } catch (Exception ex) {
            	adminpage.showAlert1("Error", "Failed to List Articles", ex.getMessage());
            }
        });
        backupArticlesButton.setOnAction(e -> {
            try {
            	adminpage.backupArticles();
            } catch (Exception ex) {
            	adminpage.showAlert1("Error", "Failed to Backup Articles", ex.getMessage());
            }
        });
        restoreArticlesButton.setOnAction(e -> {
            try {
            	adminpage.restoreArticles();
            } catch (Exception ex) {
            	adminpage.showAlert1("Error", "Failed to Restore Articles", ex.getMessage());
            }
        });
        Button updateButton = new Button("Update Article");

     // Set the action for the button
     updateButton.setOnAction(event -> {
         try {
             // Call the updateArticle method when the button is clicked
        	 adminpage.updateArticle();
         } catch (Exception e) {
        	 adminpage.showAlert("Error", "Update Failed", "An error occurred while updating the article: " + e.getMessage());
         }
     });

        // Layout setup
        VBox layout = new VBox(10);
        layout.getChildren().addAll(AddUser, DeleteUser, ListUsers, UpdateUser, btnLogout, Generatecode,addArticleButton, deleteArticleButton, updateButton, listArticlesButton, backupArticlesButton, restoreArticlesButton);

        // Set up and show the stage
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
 // Method to generate a random invitation code
    public String generateRandomCode() {
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
            adminpage.showAlert1("Success", "Invitation Code Generated",
                      "Invitation Code: " + invitationCode + "\nRole: " + role.get());

            // Optionally, display the invitation code in the console for debugging
           // System.out.println("Invitation Code: " + invitationCode + " for Role: " + role.get());
        } else {
        	adminpage.showAlert1("Error", "No Role Provided", "Please enter a valid role.");
        }
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
                	adminpage.showAlert1("Error", "Duplicate Username", "A user with the username '" + username.get() + "' already exists.");
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
                            adminpage.showAlert1("Success", "User Added", "User '" + username.get() + "' has been added successfully.");
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
                            adminpage.showAlert1("Success", "Role Added", "The role '" + newRole.get() + "' has been added to user '" + username.get() + "'.");
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
                                adminpage.showAlert1("Success", "Role Removed", "The role '" + roleToRemove.get() + "' has been removed from user '" + username.get() + "'.");
                            } else {
                            	adminpage.showAlert1("Failure", "Role Not Found", "The role '" + roleToRemove.get() + "' was not found for user '" + username.get() + "'.");
                            }
                        }

                    } else {
                        // Cancelled operation
                    	adminpage.showAlert1("Cancelled", "Operation Cancelled", "No role was modified.");
                    }
                }
            } else {
                // User not found
            	adminpage.showAlert1("Failure", "User Not Found", "No user with the username '" + username.get() + "' was found.");
            }
        }
    }
}
