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
}
