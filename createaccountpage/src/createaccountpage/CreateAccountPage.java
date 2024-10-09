package createaccountpage;

// JavaFX imports needed to support the Graphical User Interface
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*******
 * <p> CreateAccountPage Class </p>
 * 
 * <p> Description: A simple JavaFX demonstration application for creating an account. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2022 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 4.00	2017-10-16 The mainline of a JavaFX-based GUI implementation of a User 
 * 					Interface testbed
 * @version 5.00	2022-09-22 Updated for use at ASU
 * 
 */
public class CreateAccountPage extends Application {
	
	// Constants to define the width and height of the window (stage)
	public final static double WINDOW_WIDTH = 500;  // Width of the application window
	public final static double WINDOW_HEIGHT = 430; // Height of the application window
	
	// Default constructor (no custom logic required, but it is a placeholder)
	public CreateAccountPage() {
	}
	
	/**********
	 * The `start` method is the main entry point for JavaFX applications.
	 * It is responsible for setting up and displaying the primary user interface elements.
	 * 
	 * @param theStage The main window (stage) for this application
	 */
	@Override
	public void start(Stage theStage) throws Exception {
		
		// Set the title of the window (stage) that appears in the title bar
		theStage.setTitle("Create Account Page");	
		
		// Create a Pane to act as a container for all UI components
		Pane theRoot = new Pane();
		
		// Create a Scene that holds the root pane, with specified dimensions
		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// Set the Scene on the Stage (so it can be displayed)
		theStage.setScene(theScene);
		
		// Show the stage (make it visible)
		theStage.show();
		
		// Create labels for user input fields (email and name)
		Label label_email = new Label("Enter your email here"); // Label for email input
		Label label_name = new Label("Enter your name here");   // Label for name input
		
		// Create text fields for user input (email and name)
		TextField text_email = new TextField();  // Text field for entering the email
		TextField text_name = new TextField();   // Text field for entering the name
		
		// Set positions for the labels and text fields using manual layout
		// Email label and text field
		label_email.setLayoutX(200);  // Set X position of email label
		label_email.setLayoutY(80);   // Set Y position of email label
		
		text_email.setLayoutX(200);   // Set X position of email text field
		text_email.setLayoutY(100);   // Set Y position of email text field
		
		// Name label and text field
		label_name.setLayoutX(200);   // Set X position of name label
		label_name.setLayoutY(180);   // Set Y position of name label
		
		text_name.setLayoutX(200);    // Set X position of name text field
		text_name.setLayoutY(200);    // Set Y position of name text field
		
		// Add the labels and text fields to the pane so they are visible in the window
		theRoot.getChildren().addAll(label_email, label_name, text_email, text_name);
		
		// At this point, the window will be shown to the user, with the email and name fields
		// ready for input. The user can enter values, but no functionality has been added yet.
	}
	
	/*********************************************************************************************/

	/**********************************************************************************************
	 * This method launches the JavaFX application.
	 * 
	 * @param args The standard argument list for a Java mainline
	 */
	public static void main(String[] args) {				
		launch(args);  // Launch the application (this starts the JavaFX lifecycle)
	}
}
