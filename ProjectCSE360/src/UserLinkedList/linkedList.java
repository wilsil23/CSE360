package UserLinkedList;

public class linkedlist {
    public UserNode head; // Start of linked list

    // Method to add a user to the linked list
    public void addUser(String email, String username, String password, String name, String role) {
        UserNode newUser = new UserNode(email, username, password, name, role);
        if (head == null) {
            head = newUser;
            // The first user is assigned the role through the constructor
        } else {
            UserNode current = head;
            // Traverse to the end of the list
            while (current.next != null) {
                current = current.next; // Move to the next node
            }
            current.next = newUser; // Add user to end of list
        }
    }

    // Method to print out all users by username
    public void printUsers() {
        UserNode current = head; // Start at beginning of list
        System.out.println("List of User Accounts:");
        while (current != null) { // Traverse the list
            System.out.println("Username: " + current.username + 
                               ", Name: " + current.name + 
                               ", Roles: " + current.roles); // Print username, name, and roles
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
        if (head.username.equals(username)) {
            head = head.next;
            System.out.println("Account with username: " + username + " has been deleted.");
            return true; // Indicate success
        }

        UserNode current = head;
        UserNode previous = null;

        // Traverse the list to find the user
        while (current != null && !current.username.equals(username)) {
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
            if (current.username.equals(username)) { // Check if the username matches
                return current; // Return the found user
            }
            current = current.next; // Move to the next node
        }
        return null; // Return null if the user is not found
    }
}
