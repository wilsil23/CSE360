import UserLinkedList.linkedList;
import loginpage.LoginPage;
import adminPage.adminPage;
import createaccountpage.CreateAccountPage;
import REALROLES.ROLESGUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        linkedList userList = new linkedList();
        userList.addUser("test@gmail.com", "test", "password","Test", "Student");
        userList.addUser("carson@gmail.com", "cwill228", "password", "Carson", "Student");
        userList.addUser("test2@gmail.com", "test2", "password","Test", "Student");
        userList.printUsers();
        userList.removeByUser("cwill228");
        userList.printUsers();
        
        
		launch(args); 
        
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		LoginPage loginPage = new LoginPage();
		
		primaryStage.setScene(loginPage.getScene());
		
		primaryStage.show();
		// TODO Auto-generated method stub
		
	}
}

