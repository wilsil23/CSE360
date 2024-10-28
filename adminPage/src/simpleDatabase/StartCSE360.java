package simpleDatabase;

// Import statements for handling SQL exceptions, lists, arrays, and user input
import java.sql.SQLException; // Import statement for SQLException
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;



/***
<p> Main class </p>
<p> Description:This is the main application class that serves as the user interface for interacting with articles,
 allowing users to add, delete, list, back up, and restore articles in a database  </p>
<p> Copyright: Ivan Bustamante © 2024 </p>
@author Ivan Bustamante Campana
@version 5.00    2024-10-20 Updated for use at ASU
*/




// Main class for starting the CSE360 application
public class StartCSE360 {

    private static DatabaseHelper databaseHelper; // Database helper instance for DB operations
    private static BackupRestoreHelper backupRestoreHelper = new BackupRestoreHelper(); // Backup and restore helper instance
    private static final Scanner scanner = new Scanner(System.in); // Scanner for user input

    // Main method - Entry point of the application
    public static void main(String[] args) throws Exception {
        // Initialize the DatabaseHelper and connect to the database
        databaseHelper = new DatabaseHelper();
        try {
            databaseHelper.connectToDatabase();  // Connect to the database

            String choice;
            do {
                // Display options to the user
                System.out.println("Do you want to add or delete an article? Press A to Add an Article!, Press D to delete an Article!, Press L to see all Articles, B to Backup Articles, R to Restore Articles, or Q to quit.");
                choice = scanner.nextLine().toUpperCase(); // Read user input and convert to uppercase

                // Handle user choice using a switch statement
                switch (choice) {
                    case "A": // Add article option
                        addArticle();
                        break;
                    case "D": // Delete article option
                        deleteArticle();
                        break;
                    case "L": // List all articles option
                        listArticles();
                        break;
                    case "B": // Backup articles option
                        backupArticles();
                        break;
                    case "R": // Restore articles option
                        restoreArticles();
                        break;
                    case "Q": // Quit option
                        System.out.println("Good Bye!!");
                        break;
                    default: // Handle invalid input
                        System.out.println("Invalid choice. Please select A, D, L, B, R, or Q.");
                }
            } while (!choice.equals("Q")); // Repeat until the user chooses to quit
        } catch (SQLException e) { // Handle SQL exceptions
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            databaseHelper.closeConnection(); // Ensure the database connection is closed
        }
    }

    // Method to add an article
    private static void addArticle() throws Exception {
        System.out.print("Enter article title: ");
        String title = scanner.nextLine(); // Get the article title from the user
        System.out.print("Enter author(s): ");
        String authors = scanner.nextLine(); // Get the authors from the user
        System.out.print("Enter abstract: ");
        String abstractText = scanner.nextLine(); // Get the abstract from the user
        System.out.print("Enter body: ");
        String body = scanner.nextLine(); // Get the body content from the user
        System.out.print("Enter keywords: ");
        String keywords = scanner.nextLine(); // Get the keywords from the user
        System.out.print("Enter references: ");
        String references = scanner.nextLine(); // Get the references from the user

        // Create the article in the database
        databaseHelper.createArticle(title, authors, abstractText, body, keywords, references);
        System.out.println("Article added successfully."); // Confirm article addition
    }

    // Method to delete an article by its ID
    private static void deleteArticle() throws Exception {
        System.out.print("Enter the ID of the article to delete: ");
        int id = Integer.parseInt(scanner.nextLine()); // Read article ID from the user

        // Delete the article from the database
        databaseHelper.deleteArticle(id);
        System.out.println("Article deleted successfully."); // Confirm deletion
    }

    // Method to list all articles
    private static void listArticles() throws Exception {
        System.out.println("Listing all articles:\n" );
        databaseHelper.displayArticles(); // Display articles using the DatabaseHelper
    }

    // Method to backup articles to a specified file
    private static void backupArticles() throws Exception {
        System.out.print("Enter backup filename: ");
        String filename = scanner.nextLine(); // Get the backup filename from the user
        
        // Retrieve all articles and backup to the specified file
        List<Article> articles = databaseHelper.getAllArticles(); // You need to implement this method in DatabaseHelper
        backupRestoreHelper.backupArticles(articles, filename);
        System.out.println("Articles backed up successfully."); // Confirm backup completion
    }

    // Method to restore articles from a specified file
    private static void restoreArticles() throws Exception {
        System.out.print("Enter restore filename: ");
        String filename = scanner.nextLine(); // Get the restore filename from the user
        
        // Clear existing articles before restoring
        databaseHelper.clearArticles();
        
        // Restore articles from the backup file and load them into the database
        List<Article> articles = backupRestoreHelper.restoreArticles(filename);
        databaseHelper.loadArticles(articles);
        System.out.println("Articles restored successfully."); // Confirm restoration completion
    }
}
