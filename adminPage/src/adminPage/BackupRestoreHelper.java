package adminPage;

// Importing necessary classes for input/output and collection manipulation
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/***
<p>  Backup and restore class</p>
<p> Description: This class provides methods to back up and restore a list of articles to and from a specified file using object serialization. </p>
<p> Copyright: Ivan Bustamante © 2024 </p>
@author Ivan Bustamante Campana
@version 5.00    2024-10-20 Updated for use at ASU
*/



// Class for handling backup and restore operations for articles
public class BackupRestoreHelper {

    // Method to back up a list of articles to a specified file
    public void backupArticles(List<Article> articles, String filename) throws IOException {
        // Creating an ObjectOutputStream to write objects to the specified file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Writing the list of articles to the file
            oos.writeObject(articles);
        }
        // The stream is automatically closed at the end of the try-with-resources statement
    }

    // Method to restore a list of articles from a specified backup file
    public List<Article> restoreArticles(String filename) throws IOException, ClassNotFoundException {
        // Creating an ObjectInputStream to read objects from the specified file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            // Reading the list of articles from the file and casting it to List<Article>
            return (List<Article>) ois.readObject();
        }
        // The stream is automatically closed at the end of the try-with-resources statement
    }
}