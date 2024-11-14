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
			
			// Checks if the database is empty, if so add an article
			if(databaseHelper.isDatabaseEmpty() == true) {
				databaseHelper.registerArticle("Trouble Finding Articles", "To find articles, begin by selecting which level of article you prefer. Finally, select the option to list all articles.");	// Test to add when empty so there is always an article
			}
			
			System.out.println( "If you wish to list all articles then select L\nEnter your choice:  " );
			String role = scanner.nextLine();

			switch (role) {
			case "L":
				listFlow();		// Automatically lists the articles
				break;
			default:
				System.out.println("Invalid choice. Please select 'l'");
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

}
