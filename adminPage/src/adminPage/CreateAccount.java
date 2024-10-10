package adminPage;

// JavaFX imports needed to support the Graphical User Interface
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CreateAccount extends Application {

    public final static double WINDOW_WIDTH = 500;  // Width of the application window
    public final static double WINDOW_HEIGHT = 550; // Adjusted height of the application window

    // Default constructor (no custom logic required, but it is a placeholder)
    public CreateAccount() {
    }

    @Override
    public void start(Stage theStage) throws Exception {

        theStage.setTitle("Finish Setting Up Your Account");

        Pane theRoot = new Pane();
        Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);

        theStage.setScene(theScene);
        theStage.show();

        // Create labels and text fields for name (first, middle, last) and preferred first name
        Label label_email = new Label("Enter your email here:");
        Label label_firstName = new Label("Enter your first name:");
        Label label_middleName = new Label("Enter your middle name:");
        Label label_lastName = new Label("Enter your last name:");
        Label label_preferredFirstName = new Label("Optional: Enter your preferred first name:");

        TextField text_email = new TextField();
        TextField text_firstName = new TextField();
        TextField text_middleName = new TextField();
        TextField text_lastName = new TextField();
        TextField text_preferredFirstName = new TextField();

        Button button_finishSetup = new Button("Finish Setup");

        // Set positions for the labels, text fields, and button using manual layout
        label_email.setLayoutX(200);
        label_email.setLayoutY(40);
        text_email.setLayoutX(200);
        text_email.setLayoutY(60);

        label_firstName.setLayoutX(200);
        label_firstName.setLayoutY(100);
        text_firstName.setLayoutX(200);
        text_firstName.setLayoutY(120);

        label_middleName.setLayoutX(200);
        label_middleName.setLayoutY(160);
        text_middleName.setLayoutX(200);
        text_middleName.setLayoutY(180);

        label_lastName.setLayoutX(200);
        label_lastName.setLayoutY(220);
        text_lastName.setLayoutX(200);
        text_lastName.setLayoutY(240);

        label_preferredFirstName.setLayoutX(200);
        label_preferredFirstName.setLayoutY(280);
        text_preferredFirstName.setLayoutX(200);
        text_preferredFirstName.setLayoutY(300);

        button_finishSetup.setLayoutX(220);
        button_finishSetup.setLayoutY(380);

        // Add all elements to the root pane
        theRoot.getChildren().addAll(
                label_email, text_email,
                label_firstName, text_firstName,
                label_middleName, text_middleName,
                label_lastName, text_lastName,
                label_preferredFirstName, text_preferredFirstName,
                button_finishSetup
        );

        // Add an event handler to the "Finish Setup" button
        button_finishSetup.setOnAction(event -> {
            // Check if all mandatory fields are filled out
            if (text_email.getText().isEmpty() || text_firstName.getText().isEmpty() || text_lastName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Error");
                alert.setHeaderText("Incomplete Fields");
                alert.setContentText("Please fill out the required fields (email, first name, and last name) before finishing the setup.");
                alert.showAndWait();
            } else {
                // Check if a preferred first name is provided; if not, use the regular first name
                String preferredName = text_preferredFirstName.getText().isEmpty() ? text_firstName.getText() : text_preferredFirstName.getText();

                // Show a success message using the preferred name
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Account Setup Completed");
                alert.setHeaderText("Success");
                alert.setContentText("Account setup completed successfully! Welcome, " + preferredName + "!");
                alert.showAndWait();

                // Redirect to ROLESGUI or another page as needed
                try {
                    ROLESGUI rolesGUI = new ROLESGUI(); // Create an instance of ROLESGUI
                    rolesGUI.start(new Stage()); // Start the new stage for ROLESGUI
                    theStage.close(); // Optionally close the current stage
                } catch (Exception e) {
                    e.printStackTrace(); // Handle exceptions if necessary
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);  // Launch the application (this starts the JavaFX lifecycle)
    }
}
