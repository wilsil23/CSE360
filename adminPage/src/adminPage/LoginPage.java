package adminPage;


/*******
* <p> LoginPage Class </p> 
* 
* <p> Description: A JavaFX demonstration application and baseline for a sequence of projects </p>
* 
* <p> Copyright: William Diamond © 2024 </p>
* 
* @author William Diamond
* 
* @version 4.00	2017-10-16 The mainline of a JavaFX-based GUI implementation of a User 
* 					Interface testbed
* @version 5.00	2022-09-22 Updated for use at ASU
* 
*/


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginPage extends Application {

    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 530; // Adjusted height to accommodate new fields

    public LoginPage() {
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Page");

        // Set the scene for the primary stage
        Scene theScene = getScene(primaryStage);
        primaryStage.setScene(theScene);

        // Display the stage
        primaryStage.show();
    }

    public static Scene getScene(Stage primaryStage) {

        Pane theRoot = new Pane();

        // Labels
        Label label_inviteCode = new Label("Enter invite code:");
        Label label_Username = new Label("Enter the username:");
        Label label_Password = new Label("Enter the password:");
        Label label_ConfirmPassword = new Label("Confirm the password:");

        // TextFields for input
        TextField text_inviteCode = new TextField();
        TextField text_Username = new TextField();

        // Password fields
        PasswordField text_Password = new PasswordField();
        PasswordField text_ConfirmPassword = new PasswordField();

        // Button for login/creation
        Button button_login = new Button("Login");

        // Set positions for the labels and fields using manual layout
        label_inviteCode.setLayoutX(180);
        label_inviteCode.setLayoutY(80);
        text_inviteCode.setLayoutX(180);
        text_inviteCode.setLayoutY(100);

        label_Username.setLayoutX(180);
        label_Username.setLayoutY(180);
        text_Username.setLayoutX(180);
        text_Username.setLayoutY(200);

        label_Password.setLayoutX(180);
        label_Password.setLayoutY(280);
        text_Password.setLayoutX(180);
        text_Password.setLayoutY(300);

        label_ConfirmPassword.setLayoutX(180);
        label_ConfirmPassword.setLayoutY(360);
        text_ConfirmPassword.setLayoutX(180);
        text_ConfirmPassword.setLayoutY(380);

        button_login.setLayoutX(220);
        button_login.setLayoutY(450);

        // Add all components to the root pane
        theRoot.getChildren().addAll(
            label_inviteCode, text_inviteCode,
            label_Username, text_Username,
            label_Password, text_Password,
            label_ConfirmPassword, text_ConfirmPassword,
            button_login
        );

        // Add an event handler for the "Login" button
        button_login.setOnAction(event -> {
            // Validate that no field is empty
            if (text_Username.getText().isEmpty() || text_Password.getText().isEmpty() || text_inviteCode.getText().isEmpty() || text_ConfirmPassword.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Incomplete Fields", "Please fill out all fields before proceeding.");
            } 
            // Validate that passwords match
            else if (!text_Password.getText().equals(text_ConfirmPassword.getText())) {
                showAlert(Alert.AlertType.ERROR, "Password Error", "Passwords Do Not Match", "Please ensure both password fields match.");
            } 
            // All validations passed
            else {
                // If all fields are filled and passwords match, navigate to the CreateAccount page
                CreateAccount createAccount = new CreateAccount();
                try {
                    createAccount.start(primaryStage); // Switch to the CreateAccount page
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    // Helper method to show alert messages
    private static void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
