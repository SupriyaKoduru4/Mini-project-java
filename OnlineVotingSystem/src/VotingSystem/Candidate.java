package VotingSystem;

public class Candidate {

	// stores the unique ID  and name of the candidate
	// private means this variable cannot be accessed directly from outside this class
	private String candidateId;
	private String name;
	
	// It runs automatically when we create a Candidate object
	// It accepts the candidate ID and candidate name as parameters
	public Candidate(String candidateId, String name) {
		
        // Assigns the received candidate ID to this objects candidateId field and name to this name field
		// this refers to the current object
		this.candidateId = candidateId;
		this.name = name;
	}
	
	// Getter method to return the values
	public String getCandidateId() {
		return candidateId;
	}
	
	public String getName() {
		return name;
	}
	
	
	// Indicates that this method overrides the toString() method
    // inherited from Java Object class.
	@Override
    // Defines how a Candidate object should be represented as text
	public String toString() {
		 // Combines the candidate ID and name into one readable string
		return candidateId + " - " + name;
	}
	
	
}
