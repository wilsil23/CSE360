package UserLinkedList;

public class linkedList{
	public UserNode head;	//Start of linked list
	
	// Method to add to linked list
	public void addUser(String email, String username, String password, String name, String role) {
		UserNode newUser = new UserNode(email, username, password, name, role);
		if (head == null) {
			head = newUser;
			newUser.role = "admin";	// First user is admin
		} else {
			UserNode current = head;
			while(current.next != null) {	// Recursive function
				current = head.next;
			}
			current.next = newUser;	// Add user to end of list
		}
	}
	
	// Method to print out all users by user name
	public void printUsers() {
		UserNode current = head;	//Start at beginning of list
		while (current != null) {	//Recursion function
			System.out.println("Username: " + current.username);	//Prints all user names
			current = current.next;
		}
	}
	
	// Method to remove users by user name (Only can be used by admin)
	public void removeByUser(String username) {
		// Checks if list is empty
		if (head == null) {
			System.out.println("No users in list");
		}
		// Checks if head is requested user
		if (head.username == username) {
			head = head.next;
			System.out.println("Account with username: " + username + " has been deleted.");
		}
		UserNode current = head;
		UserNode temp = null;
		while (current.next != null && current.username != username) {
			temp = current;
			current = current.next;
		}
		if (current.next == null && current.username != username) {
			System.out.println("User not found.");
		}
		if (current.username == username) {
			temp.next = current.next;	// Cuts the user out of list, deleting it
			System.out.println("Account with username: " + username + " has been deleted.");
		}
	}
}
