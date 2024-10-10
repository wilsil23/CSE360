package UserLinkedList;

import java.util.ArrayList;
import java.util.List;

/*******
* <p> UserNode Class </p>
* 
* <p> Description: A Java file consisting of the construction of nodes used for a linked list </p>
* 
* <p> Copyright: Carson Williams, Ivan Bustamante Campana © 2024 </p>
* 
* @author Carson Williams, Ivan Bustamante Campana
* 
*/
public class UserNode {
    public String email;	// User email
    public String username;		// User username
    public String password;		// User password
    public String name;		// Name of user
    public List<String> roles; // List to hold user roles
    public UserNode next; // Reference to the next user node

    public UserNode(String email, String username, String password, String name, String role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.roles = new ArrayList<>(); // Initialize roles list
        this.roles.add(role); // Add initial role
        this.next = null; // Initialize next to null
    }

    // Method to add a role
    public void addRole(String role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    // Method to remove a role
    public boolean removeRole(String role) {
        return roles.remove(role);
    }
}