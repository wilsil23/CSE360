package adminPage;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserNode {
    public String email;
    public String username;
    public String password;
    public String name;
    public List<String> roles; // List to hold user roles
    public UserNode next; // Reference to the next user node
    
    // Additional fields for invitation and password reset
    public String invitationCode; // For storing the invitation code
    public String oneTimePassword; // For password reset
    public LocalDateTime passwordExpiration; // Expiration for one-time password

    // Constructor for new user
    public UserNode(String email, String username, String password, String name, String role, String invitationCode) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.roles = new ArrayList<>(); // Initialize roles list
        this.roles.add(role); // Add initial role
        this.invitationCode = invitationCode; // Set the invitation code
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

    // Method to check if the password reset code is valid
    public boolean isPasswordResetValid(String oneTimePassword, LocalDateTime currentTime) {
        return this.oneTimePassword.equals(oneTimePassword) && currentTime.isBefore(passwordExpiration);
    }
}