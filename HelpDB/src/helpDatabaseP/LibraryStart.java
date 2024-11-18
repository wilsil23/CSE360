package helpDatabaseP;

import java.sql.SQLException;
import java.util.Scanner;

/*******
* <p> LibraryStart Class </p>
* 
* <p> Description: A Java file that acts as a helper for the newly created article database </p>
* 
* <p> Copyright: Carson Williams © 2024 </p>
* 
* @author Carson Williams
* 
*/

public class LibraryStart {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/

	private static HelpDatabase databaseHelper;	// define new database
	private static final Scanner scanner = new Scanner(System.in);	// Define scanner

	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/
	
	public static void main( String[] args ) throws Exception
	{
		databaseHelper = new HelpDatabase();
		try { 
			
			databaseHelper.connectToDatabase();  // Connect to the database
			if(databaseHelper.isDatabaseEmpty() == true) {
				databaseHelper.registerArticle("Hello", "Help", "lalalala", "This is a test", "Help, Test", "N/A");
			}
			
			System.out.println( "If you wish to list all articles then select L\nIf you wih to view an article then select V\nIf you wish to search then select S\nEnter your choice:  " );
			String role = scanner.nextLine();

			switch (role) {
			case "L":
				listFlow();		// Automatically lists the articles
				break;
			case "V":
				viewFlow();
				break;
			case "S":
				searchFlow();
			default:
				System.out.println("Invalid choice. Please select 'l', 'v', 's'");
				databaseHelper.closeConnection();	// Close connection if invalid choice
			}

		} catch (SQLException e) {
			System.err.println("Database error: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			System.out.println("Good Bye!!");
			databaseHelper.closeConnection();
		}
	}

	/**********
	 * This is where the user is redirected when they enter an L. The method lists an article 
	 */
	private static void listFlow() throws Exception {
		databaseHelper.displayArticles();	// Method call to delete article
	}

	private static void viewFlow() throws Exception {
		System.out.print("Enter ID#: ");
		String id = scanner.nextLine();
		int numId = Integer.parseInt(id);
		databaseHelper.viewArticleBody(numId);
	}
	
	private static void searchFlow() throws Exception {
		System.out.print("Enter keywords of article: ");
		String keywords = scanner.nextLine();	// Takes user input for title
		databaseHelper.keywordSearch(keywords);
	}
}
