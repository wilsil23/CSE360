package helpDatabaseP;
import java.sql.*;
import java.util.Base64;

import org.bouncycastle.util.Arrays;

import Encryption.EncryptionHelper;
import Encryption.EncryptionUtils;

/*******
* <p> HelpDatabase Class </p>
* 
* <p> Description: A Java file that creates a database that holds a library of help articles. It also includes its functions. </p>
* 
* <p> Copyright: Carson Williams © 2024 </p>
* 
* @author Carson Williams
* 
*/

class HelpDatabase {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/helpDatabase";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	private Connection connection = null;
	private Statement statement = null; 
	//	PreparedStatement pstmt
	
	private EncryptionHelper encryptionHelper;
	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/
	
	public HelpDatabase() throws Exception {
		encryptionHelper = new EncryptionHelper();
	}
	
	/**********
	 * This method connects to the database
	 */
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
	
	/**********
	 * This method creates the SQL table for the database
	 */
	private void createTables() throws SQLException {
		String helpLib = "CREATE TABLE IF NOT EXISTS helpLibrary ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "title VARCHAR(255) UNIQUE, "
				+ "author VARCHAR(255), "
				+ "ab VARCHAR(255), "
				+ "body VARCHAR(255), "
				+ "keywords VARCHAR(255), "
				+ "references VARCHAR(255))";
		statement.execute(helpLib);
	}
	
	/**********
	 * This method checks if the database is empty
	 */
	public boolean isDatabaseEmpty() throws SQLException {
		String query = "SELECT COUNT(*) AS count FROM helpLibrary";
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()) {
			return resultSet.getInt("count") == 0;
		}
		return true;
	}
	
	/**********
	 * This method adds an article to the database
	 * 
	 * @param title  The title of the article being added
	 * @param body The body of the article being added
	 * 
	 */
	public void registerArticle(String title, String author, String ab, String body, String keywords, String references) throws Exception {
		// Requires encryption
		String encryptedAuthor = Base64.getEncoder().encodeToString(
				encryptionHelper.encrypt(author.getBytes(), EncryptionUtils.getInitializationVector(title.toCharArray()))
		);
		// Requires encryption
		String encryptedAbstract = Base64.getEncoder().encodeToString(
				encryptionHelper.encrypt(ab.getBytes(), EncryptionUtils.getInitializationVector(title.toCharArray()))
		);
		// Requires encryption
		String encryptedBody = Base64.getEncoder().encodeToString(
				encryptionHelper.encrypt(body.getBytes(), EncryptionUtils.getInitializationVector(title.toCharArray()))
		);
		// Requires encryption
		String encryptedKeywords = Base64.getEncoder().encodeToString(
				encryptionHelper.encrypt(keywords.getBytes(), EncryptionUtils.getInitializationVector(title.toCharArray()))
		);
		// Requires encryption
		String encryptedReferences = Base64.getEncoder().encodeToString(
				encryptionHelper.encrypt(references.getBytes(), EncryptionUtils.getInitializationVector(title.toCharArray()))
		);
		
		// Inserts the article into the database
		String insertArticle = "INSERT INTO helpLibrary (title, author, ab, body, keywords, references) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertArticle)) {
			pstmt.setString(1, title);
			pstmt.setString(2, encryptedAuthor);
			pstmt.setString(3, encryptedAbstract);
			pstmt.setString(4, encryptedBody);
			pstmt.setString(5, encryptedKeywords);
			pstmt.setString(6, encryptedReferences);
			pstmt.executeUpdate();
		}
	}
	
	/**********
	 * This method displays the articles in the database
	 */
	public void displayArticles() throws Exception{
		String sql = "SELECT * FROM helpLibrary"; 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql); 

		while(rs.next()) { 
			// Retrieve by column name 
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
	
	/**********
	 * This method effectively views the body of an article
	 */
	public void viewArticleBody() throws Exception{
		String sql = "SELECT * FROM helpLibrary"; 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql); 

		while(rs.next()) { 
			// Retrieve by column name 
			String title = rs.getString("title");
			//Required decryption
			String encryptedBody = rs.getString("body");
			char[] decryptedBody = EncryptionUtils.toCharArray(
					encryptionHelper.decrypt(
							Base64.getDecoder().decode(
									encryptedBody
							), 
							EncryptionUtils.getInitializationVector(title.toCharArray())
					)	
			);
			
			//Display the Body
			EncryptionUtils.printCharArray(decryptedBody);
			System.out.println();
			System.out.println();
			
			Arrays.fill(decryptedBody, '0');
		}
	}
	
	/**********
	 * This method effectively deletes an article
	 */
	public void deleteArticle(int idNum) throws Exception {
	    String del = "DELETE FROM helpLibrary WHERE id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(del)) {
	        
	        pstmt.setInt(1, idNum);
	        pstmt.executeUpdate();
	    }
	}
	
	/**********
	 * This method creates a backup of the database
	 */
	public void backupArticles(String fileName) throws SQLException {
        String sql = "SCRIPT DROP TO '" + fileName + "'";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
	
	/**********
	 * This method restores the database
	 */
	public void restoreArticles(String fileName) throws SQLException {
        String sql = "RUNSCRIPT FROM '" + fileName + "'";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
	
	/**********
	 * This method closes connection to the database
	 */
	public void closeConnection() {
		try{ 
			if(statement!=null) statement.close(); 
		} catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		try { 
			if(connection!=null) connection.close(); 
		} catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}
	
}