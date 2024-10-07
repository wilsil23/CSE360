package UserLinkedList;

import java.util.ArrayList;
import java.util.List;

public class UserNode {
    public String email;
    public String username;
    public String password;
    public String name;
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
