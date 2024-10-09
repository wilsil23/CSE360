package loginpage;

// JavaFX imports needed to support the Graphical User Interface
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*******
* <p> LoginPage Class </p>
* 
* <p> Description: A JavaFX demonstration application and baseline for a sequence of projects </p>
* 
* <p> Copyright: William Diamond © 2022 </p>
* 
* @author William Diamond
* 
* @version 4.00	2017-10-16 The mainline of a JavaFX-based GUI implementation of a User 
* 					Interface testbed
* @version 5.00	2022-09-22 Updated for use at ASU
* 
*/

public class LoginPage extends Application {
	
	// Constants defining the width and height of the window (stage)
	public final static double WINDOW_WIDTH = 500;  // Width of the application window
	public final static double WINDOW_HEIGHT = 430; // Height of the application window
	
	// Default constructor (not strictly necessary, as there's no custom logic here)
	public LoginPage() {
	}

	/**********
	 * The start method is the main entry point for JavaFX applications. 
	 * It initializes the primary stage (window) and sets up the user interface elements.
	 * 
	 * @param theStage The primary window for this application
	 */
	@Override
	public void start(Stage theStage) throws Exception {
		
		// Set the title of the stage (window) displayed at the top
		theStage.setTitle("loginpage");	
		
		// Create a Pane to hold all the UI elements
		Pane theRoot = new Pane();
		
		// Create a scene that will be displayed on the stage, with predefined width and height
		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	
		
		// Set the scene on the stage
		theStage.setScene(theScene);
		
		// Display the stage to the user
		theStage.show();	
		
		// Create labels for username, password, and invite code
		Label label_Password = new Label("Enter the password here");
		Label label_Username = new Label("Enter the username here");
		Label label_inviteCode = new Label("Enter invite code");

		// Create text fields for the user to input the username, password, and invite code
		TextField text_inviteCode = new TextField(); // Text field for invite code input
		TextField text_Password = new TextField();   // Text field for password input
		TextField text_Username = new TextField();   // Text field for username input

		// Create a login button that the user can click to submit their details
		Button button_login = new Button();
		button_login.setText("login"); // Set the text displayed on the button
		
		// Manually set the positions of all UI elements (X, Y coordinates)
		// Position the password label and text field
		label_Password.setLayoutX(180);  // Set X position
		label_Password.setLayoutY(280);  // Set Y position
		
		text_Password.setLayoutX(180);   // Set X position
		text_Password.setLayoutY(300);   // Set Y position
		
		// Position the username label and text field
		label_Username.setLayoutX(180);  // Set X position
		label_Username.setLayoutY(180);  // Set Y position
		
		text_Username.setLayoutX(180);   // Set X position
		text_Username.setLayoutY(200);   // Set Y position
		
		// Position the invite code label and text field
		label_inviteCode.setLayoutX(180);  // Set X position
		label_inviteCode.setLayoutY(80);   // Set Y position
		
		text_inviteCode.setLayoutX(180);   // Set X position
		text_inviteCode.setLayoutY(100);   // Set Y position
		
		// Position the login button
		button_login.setLayoutX(220);      // Set X position
		button_login.setLayoutY(400);      // Set Y position
		
		// Add all UI components to the root pane
		theRoot.getChildren().addAll(
			label_Password, label_Username, label_inviteCode, 
			text_Username, text_Password, text_inviteCode, button_login
		);
		
		// When the stage is shown to the user, the pane within the window is visible. 
		// This means that the labels, fields, and buttons are visible, 
		// and it is now possible for the user to interact with the GUI elements.
	}
	
	/*********************************************************************************************/

	/**********************************************************************************************
	 * This is the main method that launches the JavaFX application.
	 * 
	 * @param args	The standard argument list for a Java Mainline
	 */
	public static void main(String[] args) {
		// This method calls the JavaFX launch method to start the application.
		launch(args);
	}
}
