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
				+ "body VARCHAR(255))";
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
	public void registerArticle(String title, String body) throws Exception {
		// Requires encryption
		String encryptedBody = Base64.getEncoder().encodeToString(
				encryptionHelper.encrypt(body.getBytes(), EncryptionUtils.getInitializationVector(title.toCharArray()))
		);
		
		// Inserts the article into the database
		String insertArticle = "INSERT INTO helpLibrary (title, body) VALUES (?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertArticle)) {
			pstmt.setString(1, title);
			pstmt.setString(2, encryptedBody);
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
			String title = rs.getString("title");
			// Requires decryption
			String encryptedBody = rs.getString("body");
			char[] decryptedBody = EncryptionUtils.toCharArray(
					encryptionHelper.decrypt(
							Base64.getDecoder().decode(
									encryptedBody
							), 
							EncryptionUtils.getInitializationVector(title.toCharArray())
					)	
			);

			// Displays the article values in terminal
			System.out.println("ID: " + id);
			System.out.println("Title: " + title);
			System.out.print("Body: ");
			EncryptionUtils.printCharArray(decryptedBody);
			System.out.println();
			System.out.println();
			
			// Fills unneeded attributes
			Arrays.fill(decryptedBody, '0');
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