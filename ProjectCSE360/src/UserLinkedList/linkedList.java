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
			while(current.next != null) {
				current = head.next;
			}
			current.next = newUser;	// Add user to end of list
		}
	}
	
	public void printUsers() {
		UserNode current = head;	//Start at beginning of list
		while (current != null) {	//Recursion function
			System.out.println("Username: " + current.username);	//Prints all usernames
			current = current.next;
		}
	}
}
