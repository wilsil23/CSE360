package adminPage;

// Importing Serializable interface for object serialization
import java.io.Serializable;

/***
<p>  article class</p>
<p> Description: This class represents a scholarly article with attributes like title,
    authors, abstract, body, keywords, and references, and it implements Serializable for object serialization.  </p>
<p> Copyright: Ivan Bustamante © 2024 </p>
@author Ivan Bustamante Campana
@version 5.00    2024-10-20 Updated for use at ASU
*/

// Article class implements Serializable to allow instances to be serialized
public class Article implements Serializable {
    // Unique identifier for each article
    private int id;
    
    private String difficulty;
    
    // Title of the article
    private String title;
    
    // Authors of the article, can be multiple authors separated by commas
    private String authors;
    
    // Abstract or summary of the article
    private String abstractText;
    
    // Keywords related to the article for search and categorization
    private String keywords;
    
    // Main body content of the article
    private String body;
    
    // References or bibliography related to the article
    private String references;

    // Constructor to initialize an Article object with all necessary fields
    // Ensure this constructor matches the parameters you're passing
    public Article(int id, String difficulty, String title, String authors, String abstractText, String body, String keywords, String references) {
        this.id = id;          
        this.difficulty = difficulty;
        this.title = title;          
        this.authors = authors;      
        this.abstractText = abstractText;
        this.body = body;            
        this.keywords = keywords;    
        this.references = references; 
    }

    // Getter methods for all fields
    public int getId() {
        return id;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthors() {
        return authors;
    }
    
    public String getAbstractText() {
        return abstractText;
    }
    
    public String getBody() {
        return body;
    }
    
    public String getKeywords() {
        return keywords;
    }
    
    public String getReferences() {
        return references;
    }
    
    // Override the toString() method to display the article title or any other relevant information
    @Override
    public String toString() {
        // Return a formatted string that includes the title, authors, and ID
        return "Title: " + title + ", Authors: " + authors + ", ID: " + id;
    }

}
