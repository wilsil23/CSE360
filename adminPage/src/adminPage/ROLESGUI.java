package adminPage;

/***
<p> ROLESGUI Class </p>
<p> Description: A JavaFX demonstration a choose role application and baseline for a sequence of projects </p>
<p> Copyright: Oscar Nguyen © 2024 </p>
@author Oscar Nguyen
@version 4.00    2017-10-16 The mainline of a JavaFX-based GUI implementation of a User
Interface testbed
@version 5.00    2022-09-22 Updated for use at ASU
*/

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ROLESGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loginPage(primaryStage);
    }

    private void loginPage(Stage primaryStage) {
        VBox root = new VBox();
        primaryStage.setTitle("What is your Role?");

        Button adminButton = new Button("Admin");
        Button instructorButton = new Button("Instructor");
        Button studentButton = new Button("Student");

        // Set action for buttons
        adminButton.setOnAction(event -> adminBTN(primaryStage));
        instructorButton.setOnAction(event -> instructorBTN(primaryStage));
        studentButton.setOnAction(event -> studentBTN(primaryStage));

        root.getChildren().addAll(adminButton, instructorButton, studentButton);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        Scene scene = new Scene(root, 500, 450);
        primaryStage.setScene(scene);
        primaryStage.show(); // Show the role selection UI
    }

    // Action for the admin button
    public void adminBTN(Stage primaryStage) {
        adminPage adminPage = new adminPage();
        adminPage.start(primaryStage); // Redirect to AdminPage
    }

    public void instructorBTN(Stage secStage) {
        secStage.setTitle("Instructor");
        Button logoutButton = new Button("Logout");

        // Logout action that returns to the login page
        logoutButton.setOnAction(event -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(secStage); // Redirect to login page
        });

        StackPane root = new StackPane();
        root.getChildren().add(logoutButton);
        secStage.setScene(new Scene(root, 500, 450));
        secStage.show();
    }

    public void studentBTN(Stage secStage) {
        secStage.setTitle("Student");
        Button logoutButton = new Button("Logout");

        // Logout action that returns to the login page
        logoutButton.setOnAction(event -> {
            LoginPage loginPage = new LoginPage();
            loginPage.start(secStage); // Redirect to login page
        });

        StackPane root = new StackPane();
        root.getChildren().add(logoutButton);
        secStage.setScene(new Scene(root, 500, 450));
        secStage.show();
    }

    public void backBTN() {
        System.out.println("Back button works");
    }

    public void logoutBTN() {
        System.out.println("Logout LOGOUT");
    }
}
