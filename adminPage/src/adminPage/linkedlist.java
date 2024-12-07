package adminPage;

/*******
* <p> linkedList Class </p>

* 
* <p> Description: A Java file consisting of the construction of a linked list, as well as its functions </p>
* 
* <p> Copyright: Carson Williams, Ivan Bustamante Campana © 2024 </p>
* 
* @author Carson Williams, Ivan Bustamante Campana
* 
*/

import java.time.LocalDateTime;
import java.util.UUID;

public class linkedlist {
    public UserNode head; // Start of linked list

    // Method to add a user to the linked list
    public void addUser(String email, String username, String password, String name, String role, String invitationCode) {
        UserNode newUser = new UserNode(email, username, password, name, role, invitationCode);
        if (head == null) {
            head = newUser;
        } else {
            UserNode current = head;
            // Traverse to the end of the list
            while (current.next != null) {
                current = current.next; // Move to the next node
            }
            current.next = newUser; // Add user to end of list
        }
    }

    // Method to invite a user with a one-time code
    public String inviteUser(String email, String username, String name, String role) {
        String invitationCode = UUID.randomUUID().toString(); // Generate unique code
        addUser(email, username, null, name, role, invitationCode); // Add user with invitation code
        return invitationCode; // Return the invitation code for the admin
    }

    // Method to reset a user's password with a one-time password
    public boolean resetUserPassword(String username, String oneTimePassword, LocalDateTime expiration) {
        UserNode user = findUserByUsername(username);
        if (user != null) {
            user.oneTimePassword = oneTimePassword;
            user.passwordExpiration = expiration;
            return true; // Indicate success
        }
        return false; // User not found
    }

    // Method to print out all users by username
    public void printUsers() {
        UserNode current = head; // Start at beginning of list
        System.out.println("List of User Accounts:");
        while (current != null) { // Traverse the list
            System.out.println("Username: " + (current.username != null ? current.username : "N/A") +
                               ", Name: " + (current.name != null ? current.name : "N/A") +
                               ", Roles: " + (current.roles != null ? current.roles : "N/A"));
            current = current.next; // Move to the next node
        }
    }

    // Method to remove users by username (Only can be used by admin)
    public boolean removeByUser(String username) {
        // Check if list is empty
        if (head == null) {
            System.out.println("No users in the list.");
            return false; // Indicate failure
        }

        // Check if head is the requested user
        if (head.username != null && head.username.equals(username)) {
            head = head.next;
            System.out.println("Account with username: " + username + " has been deleted.");
            return true; // Indicate success
        }

        UserNode current = head;
        UserNode previous = null;

        // Traverse the list to find the user
        while (current != null && (current.username == null || !current.username.equals(username))) {
            previous = current;
            current = current.next; // Move to the next node
        }

        // If user is not found
        if (current == null) {
            System.out.println("User not found.");
            return false; // Indicate failure
        }

        // User found, remove the node
        previous.next = current.next; // Skips over the node to delete it
        System.out.println("Account with username: " + username + " has been deleted.");
        return true; // Indicate success
    }

    // Method to find a user by username
    public UserNode findUserByUsername(String username) {
        UserNode current = head; // Start at the beginning of the list
        while (current != null) { // Traverse until the end of the list
            if (username.equals(current.username)) { // Check if the username matches
                return current; // Return the found user
            }
            current = current.next; // Move to the next node
        }
        return null; // Return null if the user is not found
    }

    // Method to find a user by invitation code
    public UserNode findUserByInvitationCode(String invitationCode) {
        UserNode current = head;
        while (current != null) {
            if (invitationCode.equals(current.invitationCode)) {
                return current;
            }
            current = current.next;
        }
        return null; // Invitation code not found
    }
}
