import UserLinkedList.linkedList;

public class Main {
    public static void main(String[] args) {
        linkedList userList = new linkedList();
        userList.addUser("test@gmail.com", "test", "password","Test", "Student");
        userList.addUser("carson@gmail.com", "cwill228", "password", "Carson", "Student");
        userList.addUser("test2@gmail.com", "test2", "password","Test", "Student");
        userList.printUsers();
        userList.removeByUser("cwill228");
        userList.printUsers();
    }
}