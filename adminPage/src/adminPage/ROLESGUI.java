package adminPage;



import javafx.application.Application;

import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.control.Button;

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



    public void instructorBTN(Stage primaryStage) {
        // Redirect to the InstructorPage
        instructorPage instructorPage = new instructorPage();
        instructorPage.start(primaryStage); // Assuming InstructorPage is another JavaFX application page
    }



    // Action for the student button

    public void studentBTN(Stage secStage) {

        // Create an instance of StudentPage and show it

        studentPage studentPage = new studentPage();

        studentPage.start(secStage); // Redirect to the StudentPage

    }



    public void backBTN() {

        System.out.println("Back button works");

    }



    public void logoutBTN() {

        System.out.println("Logout LOGOUT");

    }

}