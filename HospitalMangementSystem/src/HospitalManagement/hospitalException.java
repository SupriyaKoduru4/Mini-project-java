package HospitalManagement;


//Custom exception class
//It is used for hospital-related errors  hospital exception is a child class of Java's built-in Exception class.
public class hospitalException extends Exception {

	 // serialVersionUID is used for serialization compatibility . This is a special variable used when a class that can be serialized needs version identification.
	// This is used for serialization compatibility and avoids serialization-related warnings
    private static final long serialVersionUID = 1L;

    // Constructor
    public hospitalException(String message) {
    	// Send this error message to the constructor of the parent Exception class.
        super(message);
    }
}

// I am creating my own exception called HospitalException. It inherits from Java's Exception class, and it accepts a custom error message.