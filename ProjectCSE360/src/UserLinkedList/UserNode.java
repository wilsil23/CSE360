package UserLinkedList;

public class UserNode {
	String email;	// User email
	String username;	// User's user name
	String password;	//User password
	String name;	// Individual's name
	String role;	// User defined role
	UserNode next;

	// Constructor
	public UserNode(String email, String username, String password, String name, String role) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
		this.next = null;
	}
}
