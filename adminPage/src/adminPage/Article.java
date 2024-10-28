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
        this.difficulty = difficulty;// Assigning the unique ID
        this.title = title;          // Assigning the title
        this.authors = authors;      // Assigning the authors
        this.abstractText = abstractText; // Assigning the abstract text
        this.body = body;            // Assigning the main body of the article
        this.keywords = keywords;    // Assigning the keywords
        this.references = references; // Assigning the references
    }

    // Getter method for retrieving the article ID
    public int getId() {
        return id;
    }
    
    public String getDifficulty() {
    	return difficulty;
    }
    
    // Getter method for retrieving the article title
    public String getTitle() {
        return title;
    }
    
    // Getter method for retrieving the authors of the article
    public String getAuthors() {
        return authors;
    }
    
    // Getter method for retrieving the abstract text of the article
    public String getAbstractText() {
        return abstractText;
    }
    
    // Getter method for retrieving the body of the article
    public String getBody() {
        return body;
    }
    
    // Getter method for retrieving the keywords associated with the article
    public String getKeywords() {
        return keywords;
    }
    
    // Getter method for retrieving the references of the article
    public String getReferences() {
        return references;
    }
    
}
