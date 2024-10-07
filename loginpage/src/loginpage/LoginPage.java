package loginpage;

//JavaFX imports needed to support the Graphical User Interface
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*******
* <p> PasswordAddressTestbed Class </p>
* 
* <p> Description: A JavaFX demonstration application and baseline for a sequence of projects </p>
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

public class LoginPage extends Application {
	
	/** The width of the pop-up window for the user interface */
	public final static double WINDOW_WIDTH = 500;
	/** The height of the pop-up window for the user interface */
	public final static double WINDOW_HEIGHT = 430;
	
	/** The default constructor */
	public LoginPage() {
	}

	/**********
	 * This is the start method that is called once the application has been loaded into memory and
	 * is ready to get to work.
	 * 
	 * In designing this application I have elected to IGNORE all opportunities for automatic
	 * layout support and instead have elected to manually position each GUI element and its 
	 * properties in order to exercise complete control over the user interface look and feel.
	 * 
	 */
	@Override
	public void start(Stage theStage) throws Exception {
		
		theStage.setTitle("loginpage");			// Label the stage (a window)
		
		Pane theRoot = new Pane();							// Create a pane within the window
		
		
		Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);	// Create the scene
		
		theStage.setScene(theScene);						// Set the scene on the stage
		
		theStage.show();									// Show the stage to the user
		
		Label label_Password = new Label("Enter the password here");
		Label label_Username = new Label("Enter the username here");
		Label label_inviteCode = new Label("Enter invite code");
		TextField text_inviteCode = new TextField();
		TextField text_Password = new TextField();
		TextField text_Username = new TextField();
		
		
		label_Password.setLayoutX(180);
		label_Password.setLayoutY(280);	
		
		label_Username.setLayoutX(180);
		label_Username.setLayoutY(180);	
		
		label_inviteCode.setLayoutX(180);
		label_inviteCode.setLayoutY(80);	
		
		text_inviteCode.setLayoutX(180);
		text_inviteCode.setLayoutY(100);	
		
		text_Password.setLayoutX(180);
		text_Password.setLayoutY(300);	
		
		text_Username.setLayoutX(180);
		text_Username.setLayoutY(200);	
		
		
		
		theRoot.getChildren().addAll(label_Password, label_Username, label_inviteCode, 
				text_Username, text_Password, text_inviteCode);
		// When the stage is shown to the user, the pane within the window is visible.  This means
		// that the labels, fields, and buttons of the Graphical User Interface (GUI) are visible 
		// and it is now possible for the user to select input fields and enter values into them, 
		// click on buttons, and read the labels, the results, and the error messages.
	}
	
	/*********************************************************************************************/

	/**********************************************************************************************
	 * This is the method that launches the JavaFX application
	 * 
	 * @param args	The standard argument list for a Java Mainline
	 * 
	 */
	public static void main(String[] args) {				// This method may not be required
		launch(args);										// for all JavaFX applications using
	}														// other IDEs.
}

