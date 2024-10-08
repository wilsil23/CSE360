import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import java.awt.Label;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;




public class RolesGUI extends Application {
	// set up the vars
	//private Label label_AdminRole = new Label("Admin");
	//private Label label_InstructorRole = new Label("Instructor");
	//private Label label_StudentRole = new Label("Student");
	
	//features
	//private Label label_LogoutButton = new Label("Logout");
	private Button btn;
	private Button btn2;
	
	
	

		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
			
	}
	
	@Override
	public void start(Stage primaryStage) {
		VBox root = new VBox();
		
		primaryStage.setTitle("What is your Role?"); // the title of the window
				
		Button adminButton = new Button("Admin"); // top button, admin
        Button instructorButton = new Button("Instructor"); // middle button,teacher
        Button studentButton = new Button("Student"); // bottom button, student
        
        Button logoutButton = new Button("Logout"); // logout back to main 100%
        Button backButton = new Button("Back"); // back to main page button
        
        // all actions 
        adminButton.setOnAction(event ->{
        	adminBTN();
        });
        instructorButton.setOnAction(event ->{
        	instructorBTN(primaryStage);
        });
        studentButton.setOnAction(event ->{
        	studentBTN(primaryStage);
        });
        backButton.setOnAction(event ->{
        	backBTN();
        });
        
        //Label label = new Label("Pick your Role"); 
        
       
		// get all the bottons i think
        
		root.getChildren().addAll(adminButton, instructorButton, studentButton);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(root); // all three in a veritcal line 
		borderPane.setTop(logoutButton);
		borderPane.setBottom(backButton); // back btn is bottom left
		
		Scene Scene = new Scene(borderPane, 500, 450);
		primaryStage.setScene(Scene);
        primaryStage.show(); // shows the UI
	}
	// this is what will be called
	public void adminBTN() {
		System.out.println("admin button works");
	}
	
	public void instructorBTN(Stage secStage) {
		secStage.setTitle("instructor");
        btn = new Button();
        btn.setText("Logout'");
           
	        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        secStage.setScene(new Scene(root, 500, 450));
        secStage.show();
	}
	
	public void studentBTN(Stage secStage) {
		secStage.setTitle("Student");
        btn = new Button();
        btn.setText("Logout");
           
	        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        secStage.setScene(new Scene(root, 500, 450));
        secStage.show();
	}
	public void backBTN() {
		System.out.println("back button works");
	}
	public void logoutBTN() {
		System.out.println("logout LOGOUT");
	}
	

}
