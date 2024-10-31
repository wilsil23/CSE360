package adminPage;

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
            createArticle(article.getDifficulty() , article.getTitle(), article.getAuthors(), article.getAbstractText(),
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
                String difficulty = rs.getString("difficulty");
                String title = rs.getString("title");
                String authors = rs.getString("authors");
                String abstractText = rs.getString("abstract");
                String body = rs.getString("body");
                String keywords = rs.getString("keywords");
                String references = rs.getString("references");

                // Create an Article object and add it to the list
                Article article = new Article(id,difficulty, title, authors, abstractText, body, keywords, references);
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
                + "difficulty VARCHAR(255), "
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
    public void createArticle(String difficulty, String title, String authors, String abstractText, String keywords, String body, String references) {
        String insertArticle = "INSERT INTO articles (difficulty, title, authors, abstract, body, keywords, references) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertArticle)) {
            // Set the parameters for the prepared statement
        	pstmt.setString(1, difficulty);
            pstmt.setString(2, title);
            pstmt.setString(3, authors);
            pstmt.setString(4, abstractText);
            pstmt.setString(5, body);
            pstmt.setString(6, keywords);
            pstmt.setString(7, references);
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
                String difficulty = rs.getString("difficulty");
                String title = rs.getString("title");
                String authors = rs.getString("authors");

                // Displaying in the specified format
                System.out.printf("ID: %d%ndifficulty: %s%nTitle: %s%nAuthor(s): %s%n%n", id, difficulty, title, authors);
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
 // Method to retrieve an article by its ID
    public Article getArticleById(int id) throws SQLException {
        String sql = "SELECT * FROM articles WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id); // Set the ID parameter
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Create an Article object and populate it with data from the ResultSet
                return new Article(
                    rs.getInt("id"),
                    rs.getString("difficulty"),
                    rs.getString("title"),
                    rs.getString("authors"),
                    rs.getString("abstract"),
                    rs.getString("body"),
                    rs.getString("keywords"),
                    rs.getString("references")
                );
            } else {
                return null; // Return null if no article is found with the given ID
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving article: " + e.getMessage());
            throw e; // Re-throw the exception to handle it elsewhere if needed
        }
    }

    
 // Method to update an article by its ID
 // Method to update an article in the database
    public void updateArticle(Article article) {
        String updateArticle = "UPDATE articles SET difficulty = ?, title = ?, authors = ?, abstract = ?, body = ?, keywords = ?, references = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateArticle)) {
            // Set the parameters for the prepared statement
            pstmt.setString(1, article.getDifficulty());
            pstmt.setString(2, article.getTitle());
            pstmt.setString(3, article.getAuthors());
            pstmt.setString(4, article.getAbstractText());
            pstmt.setString(5, article.getBody());
            pstmt.setString(6, article.getKeywords());
            pstmt.setString(7, article.getReferences());
            pstmt.setInt(8, article.getId()); // Assuming Article class has a getId() method

            // Execute the update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating article: " + e.getMessage());
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
