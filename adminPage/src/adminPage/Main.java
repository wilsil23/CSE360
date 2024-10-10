package adminPage; // Adjust the package name if needed

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize and show the login page
        LoginPage loginPage = new LoginPage();
        loginPage.start(primaryStage);
    }
}
