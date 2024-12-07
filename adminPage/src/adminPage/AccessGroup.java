package adminPage;

/***
<p>  Access Group Helper</p>
<p> Description: This class provides methods to search, add, delete users from groups </p>
<p> Copyright: Leo masini © 2024 </p>
@author Leo Masini
@version 5.00    2024-10-20 Updated for use at ASU
*/

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccessGroup extends Application {

    @Override
    public void start(Stage groupStage) {
        groupStage.setTitle("Instructor Access Group");

        // Dropdown menu for actions (Add, View, Delete)
        ComboBox<String> actionComboBox = new ComboBox<>();
        actionComboBox.getItems().addAll("Add Student to Group", "Delete Student from Group", "View Student in Group");
        actionComboBox.setPromptText("Select an action");

        // Text field for inputting student username
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("Enter student's username");

        // Text field for inputting group name (always visible)
        TextField groupInput = new TextField();
        groupInput.setPromptText("Enter group name");

        // Buttons for each action
        Button actionButton = new Button("Execute Action");

        // Label to display the selected action
        Label actionLabel = new Label("Please select an action.");

        // Set the action for the combo box (what happens when the user selects an action)
        actionComboBox.setOnAction(event -> {
            String selectedAction = actionComboBox.getValue();
            if (selectedAction != null) {
                actionLabel.setText("Selected Action: " + selectedAction);
            }
        });

        // Execute the selected action when the button is clicked
        actionButton.setOnAction(event -> {
            String selectedAction = actionComboBox.getValue();
            String username = usernameInput.getText().trim();
            String groupName = groupInput.getText().trim();

            if (selectedAction != null && !username.isEmpty() && !groupName.isEmpty()) {
                // Show success popup
                showSuccessPopup(groupStage);

                // You can also process the action (e.g., add, delete, view student here)
                System.out.println(selectedAction + " for student: " + username);
                System.out.println("Group: " + groupName);
            } else {
                // Show error popup if fields are not filled correctly
                showErrorPopup(groupStage, "Please select an action, and fill in both the username and group name.");
            }
        });

        // Layout for the AccessGroup page
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                actionComboBox, 
                usernameInput, 
                groupInput,  // Always show the group input field
                actionButton, 
                actionLabel
        );

        // Set scene and show stage
        groupStage.setScene(new Scene(layout, 400, 300));
        groupStage.show();
    }

    // Method to show success popup and close the window
    private void showSuccessPopup(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("The action was completed successfully!");

        alert.showAndWait();  // Show the popup and wait for user to close it

        // Close the current stage after showing the popup
        stage.close();
    }

    // Method to show error popup with custom message
    private void showErrorPopup(Stage stage, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();  // Show the error popup
    }

    public static void main(String[] args) {
        launch(args);
    }
}
