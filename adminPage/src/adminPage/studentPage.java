package adminPage;

import javafx.application.Application;
import javafx.scene.Node;
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
import javafx.scene.layout.Pane;
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

public class studentPage extends Application {
	linkedlist userList = new linkedlist();
    ROLESGUI studentPage = new ROLESGUI();
    @Override
    public void start(Stage secStage) {
        secStage.setTitle("Student");
        
        Button btnLogout = new Button("Logout");
        btnLogout.setLayoutX(275);
        btnLogout.setLayoutY(300);
        
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
        
        Button btnSearch = new Button("Keyword Search");
        
        btnSearch.setOnAction(e -> {
            // Create a TextInputDialog for entering a search keyword
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Keyword Search");
            dialog.setHeaderText("Enter the keyword you want to search:");
            dialog.setContentText("Keyword:");

            // Show the dialog and capture user input
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(keyword -> {
                // Handle the search logic using the entered keyword
                // For demonstration purposes, we'll just show an alert with the entered keyword
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                try {
					studentPage.Search(keyword);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                alert.setHeaderText(null);
                alert.setContentText("You searched for: " + keyword);
                alert.showAndWait();
            });
        });
        
        btnSearch.setLayoutX(250);
        btnSearch.setLayoutY(200);
        	
        // Layout setup
        Pane pane = new Pane();
        pane.getChildren().addAll(btnLogout, btnSearch);
        // Set up and show the stage
        Scene scene = new Scene(pane, 600, 400);
        secStage.setScene(scene);
        secStage.show();
    }   
}