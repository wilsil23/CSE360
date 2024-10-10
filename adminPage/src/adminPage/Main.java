package adminPage; // Adjust the package name if needed

/***
<p> Main </p>
<p> Description: The Main class serves as the entry point for the JavaFX-based graphical user interface (GUI) application.
 It is responsible for launching the application and initiating the first user interface page, which is the login page.
This class plays a foundational role in initializing the JavaFX lifecycle and directing the user flow within the system.</p>

<p> Copyright: Ivan Bustamante Campana © 2024 </p>
@author Ivan Bustamante Campana
@version 4.00    2017-10-16 The mainline of a JavaFX-based GUI implementation of a User
Interface testbed
@version 5.00    2022-09-22 Updated for use at ASU
*/

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
