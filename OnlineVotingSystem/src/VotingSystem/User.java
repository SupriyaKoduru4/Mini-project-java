package VotingSystem;

// create a class 
public class User {
	
	// Private variables to store student details
    // Private means these variables cannot be accessed directly
    // from outside this class.
	
	private String studentId;
	private String name;
	private String password;
	
    // I created a parameterized Constructor to initializes the student details when an object is created
	public User(String studentId, String name, String password) {
		
		// this refers to the current object
		// Assigning constructor parameters to instance variables
		this.studentId = studentId;
		this.name = name;
		this.password = password;
	}
	
	// Getter method to return the values
	public String getStudentId() {
		return studentId;
	}
	
	public String getName() {
		return name;
	}
	
    // This method checks whether the entered password is correct
    // Returns true if passwords match otherwise, returns false
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}



}
