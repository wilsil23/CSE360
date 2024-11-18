package simpleDatabase;

// Importing necessary classes for database connectivity and encryption
import java.sql.*;
import java.util.Base64;
import org.bouncycastle.util.Arrays;
import Encryption.EncryptionHelper;
import java.util.ArrayList;
import Encryption.EncryptionUtils;
import java.util.List;

/***
<p> Database class </p>
<p> Description: This class manages database connections and operations,
    including creating tables, adding, retrieving, and deleting articles, while also handling encryption and database credentials. </p>
<p> Copyright: Ivan Bustamante, Carson Williams © 2024 </p>
@author Ivan Bustamante Campana
@version 5.00    2024-10-20 Updated for use at ASU
*/



// Class for handling database operations related to articles
class DatabaseHelper {

    // JDBC driver name and database URL 
    static final String JDBC_DRIVER = "org.h2.Driver";   
    static final String DB_URL = "jdbc:h2:~/firstDatabase";  

    // Database credentials 
    static final String USER = "sa"; 
    static final String PASS = ""; 

    // Connection and statement objects for database interaction
    private Connection connection = null;
    private Statement statement = null; 
    private EncryptionHelper encryptionHelper;

    // Constructor initializes the encryption helper
    public DatabaseHelper() throws Exception {
        encryptionHelper = new EncryptionHelper();
    }

    // Method to establish a connection to the database
    public void connectToDatabase() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER); // Load the JDBC driver
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement(); 
            createTables();  // Create the necessary tables if they don't exist
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
        }
    }

    // Method to clear all articles from the database
    public void clearArticles() throws SQLException {
        String query = "DELETE FROM articles";
        statement.executeUpdate(query);
    }

    // Method to load a list of articles into the database
    public void loadArticles(List<Article> articles) throws SQLException {
        for (Article article : articles) {
            createArticle(article.getTitle(), article.getAuthors(), article.getAbstractText(),
                          article.getKeywords(), article.getBody(), article.getReferences());
        }
    }

    // Method to retrieve all articles from the database
    public List<Article> getAllArticles() throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT * FROM articles";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop through the result set and create Article objects
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String authors = rs.getString("authors");
                String abstractText = rs.getString("abstract");
                String body = rs.getString("body");
                String keywords = rs.getString("keywords");
                String references = rs.getString("references");

                // Create an Article object and add it to the list
                Article article = new Article(id, title, authors, abstractText, body, keywords, references);
                articles.add(article);
            }
        }
        return articles;
    }

    // Private method to create necessary tables in the database
    private void createTables() throws SQLException {
        // SQL for creating the users table
        String userTable = "CREATE TABLE IF NOT EXISTS cse360users (" 
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "email VARCHAR(255) UNIQUE, "
                + "password VARCHAR(255), "
                + "role VARCHAR(20))";

        // SQL for creating the articles table
        String articleTable = "CREATE TABLE IF NOT EXISTS articles (" 
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "title VARCHAR(255), "
                + "authors VARCHAR(255), " 
                + "abstract TEXT, "
                + "body TEXT, "
                + "keywords TEXT, "
                + "references TEXT)";

        // Execute the SQL statements to create the tables
        statement.execute(userTable);
        statement.execute(articleTable);
    }

    // Method to add a new article to the database
    public void createArticle(String title, String authors, String abstractText, String keywords, String body, String references) {
        String insertArticle = "INSERT INTO articles (title, authors, abstract, body, keywords, references) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertArticle)) {
            // Set the parameters for the prepared statement
            pstmt.setString(1, title);
            pstmt.setString(2, authors);
            pstmt.setString(3, abstractText);
            pstmt.setString(4, body);
            pstmt.setString(5, keywords);
            pstmt.setString(6, references);
            // Execute the update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting article: " + e.getMessage());
        }
    }

    // Method to delete an article by its ID
    public void deleteArticle(int id) throws SQLException {
        String query = "DELETE FROM articles WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id); // Set the article ID to delete
            pstmt.executeUpdate();
        }
    }

    // Method to display all articles in the database
    public void displayArticles() throws SQLException {
        String sql = "SELECT * FROM articles";
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            // Output the list of articles
            System.out.println("List of Articles:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String authors = rs.getString("authors");

                // Displaying in the specified format
                System.out.printf("ID: %d%nTitle: %s%nAuthor(s): %s%n%n", id, title, authors);
            }
        } finally {
            // Close resources in the finally block to prevent resource leaks
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

	/**********
	 * This method searches the database by Title
	 * @throws Exception 
	 */
	public void keywordSearch(String input) throws Exception {
		String selected = "SELECT * FROM articles WHERE title LIKE ?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(selected)) {
            // Add '%' around the user input for partial matching
            preparedStatement.setString(1, "%" + input + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	int id  = rs.getInt("id"); 
    			String title = rs.getString("title");	// Doesn't need decryption
    			// Requires decryption
    			String encryptedAuthor = rs.getString("author");
    			char[] decryptedAuthor = EncryptionUtils.toCharArray(
    					encryptionHelper.decrypt(
    							Base64.getDecoder().decode(
    									encryptedAuthor
    							), 
    							EncryptionUtils.getInitializationVector(title.toCharArray())
    					)	
    			);
    			// Requires decryption
    			String encryptedAbstract = rs.getString("ab");
    			char[] decryptedAbstract = EncryptionUtils.toCharArray(
    					encryptionHelper.decrypt(
    							Base64.getDecoder().decode(
    									encryptedAbstract
    							), 
    							EncryptionUtils.getInitializationVector(title.toCharArray())
    					)	
    			);

    			// Displays the article values in terminal
    			System.out.println("ID: " + id);
    			System.out.println("Title: " + title);
    			System.out.print("Author: "); 
    			EncryptionUtils.printCharArray(decryptedAuthor);
    			System.out.println();
    			System.out.print("Abstract: "); 
    			EncryptionUtils.printCharArray(decryptedAbstract);
    			System.out.println();
    			System.out.println();
    			
    			// Fills unneeded attributes
    			Arrays.fill(decryptedAuthor, '0');
    			Arrays.fill(decryptedAbstract, '0');
    		}
		}
	}
    
    // Method to close the database connection
    public void closeConnection() {
        try { 
            if (statement != null) statement.close(); 
        } catch (SQLException se2) { 
            se2.printStackTrace();
        } 
        try { 
            if (connection != null) connection.close(); 
        } catch (SQLException se) { 
            se.printStackTrace(); 
        } 
    }
}
