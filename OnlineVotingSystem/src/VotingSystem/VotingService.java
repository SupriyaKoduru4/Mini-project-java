package VotingSystem;

// Import collection classes
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VotingService {

    // store registered students using student ID as the key
    private final Map<String, User> users = new HashMap<>();

    // store candidates using candidate ID as the key
    private final Map<String, Candidate> candidates = new HashMap<>();

    // track whether each student has voted
    private final Map<String, Boolean> hasVoted = new HashMap<>();

    // store the vote count for each candidate
    private final Map<String, Integer> candidateVotes = new HashMap<>();

    // constructor initialize candidates
    public VotingService() {
        addCandidate(new Candidate("C101", "Rahul"));
        addCandidate(new Candidate("C102", "Priya"));
        addCandidate(new Candidate("C103", "Kiran"));
    }

    // add candidate to the election system 
    private void addCandidate(Candidate candidate) {
    	// stores the value key pairs id and name in the map  and this allows the system to find a candidate using their ID
        candidates.put(candidate.getCandidateId(), candidate);
        // this allows the system to find a candidate using their ID.
        candidateVotes.put(candidate.getCandidateId(), 0);
    }

    // Register student
    public void registerUser(String studentId, String name, String password) {

    	// Validate student details
    	if (studentId == null || studentId.trim().isEmpty()
    	        || name == null || name.trim().isEmpty()
    	        || password == null || password.trim().isEmpty()) {

    	    throw new IllegalArgumentException( "Student ID, name and password cannot be empty.");
    	}
        // Check whether the student ID is already registered
        if (users.containsKey(studentId)) {
            throw new IllegalArgumentException(
                    "Student ID already registered."
            );
        }

        // Create and store the new student
        User user = new User(studentId, name, password);

        users.put(studentId, user);

        // Initially, the student has not voted
        hasVoted.put(studentId, false);

        System.out.println("Registration successful!");
    }

    // Login student
    public User loginUser(String studentId, String password) {

        // Find the student using the student ID
        User user = users.get(studentId);

        // Validate the student ID and password
        if (user == null || !user.checkPassword(password)) {
            throw new IllegalArgumentException( "Invalid Student ID or password." );
        }

        // Return the user if login is successful
        return user;
    }

    // Display candidate list
    public void displayCandidates() {

        System.out.println("\n------ CANDIDATE LIST ------");

        for (Candidate candidate : candidates.values()) {
            System.out.println(candidate);
        }
    }

    // Cast vote
    public void castVote(String studentId, String candidateId) {

        // Check whether the student is registered
        if (!users.containsKey(studentId)) {
            throw new IllegalArgumentException( "Student is not registered." );
        }

        // Prevent the student from voting more than once
        if (Boolean.TRUE.equals(hasVoted.get(studentId))) {
            throw new IllegalStateException( "You have already voted. Multiple votes are not allowed." );
        }

        // Check whether the selected candidate exists
        if (!candidates.containsKey(candidateId)) {
            throw new IllegalArgumentException( "Invalid candidate ID." );
        }

        // Get the candidate's current vote count
        int currentVotes = candidateVotes.get(candidateId);

        // Increase the vote count by one
        candidateVotes.put(candidateId, currentVotes + 1);

        // Mark the student as having voted
        hasVoted.put(studentId, true);

        System.out.println("Vote cast successfully!");
    }

    // Calculate total votes
    public int getTotalVotes() {

        int totalVotes = 0;

        // Add the vote counts of all candidates
        for (int votes : candidateVotes.values()) {
            totalVotes += votes;
        }

        return totalVotes;
    }

    // Display total votes for every candidate
    public void displayVoteCount() {

        System.out.println("\n------ TOTAL VOTES ------");

        for (Candidate candidate : candidates.values()) {

            String candidateId = candidate.getCandidateId();

            int votes = candidateVotes.get(candidateId);

            System.out.println( candidate.getName() + " : " + votes + " votes"  );
        }

        System.out.println("Total Votes Cast: " + getTotalVotes());
    }

    // Display winner and runner-up
    public void displayResults() {

        System.out.println("\n------ ELECTION RESULTS ------");

        // Check whether candidates are available
        if (candidates.isEmpty()) {
            System.out.println("No candidates available.");
            return;
        }

        // Copy candidates into a list so they can be sorted
        List<Candidate> sortedCandidates =
                new ArrayList<>(candidates.values());

        // Sort candidates by vote count in descending order.
        // Candidate ID is used to order candidates with equal votes.
        sortedCandidates.sort((c1, c2) -> {

            int votes1 = candidateVotes.get(c1.getCandidateId());
            int votes2 = candidateVotes.get(c2.getCandidateId());

            int voteComparison = Integer.compare(votes2, votes1);

            if (voteComparison != 0) {
                return voteComparison;
            }

            return c1.getCandidateId().compareTo(c2.getCandidateId());
        });

        // Get the candidate at the top of the sorted list
        Candidate winner = sortedCandidates.get(0);

        int winnerVotes = candidateVotes.get(winner.getCandidateId());

        System.out.println( "Winner / Finalist: " + winner.getName() + " - " + winnerVotes + " votes" );

        // Display runner-up if at least two candidates exist
        if (sortedCandidates.size() > 1) {

            Candidate runnerUp = sortedCandidates.get(1);

            int runnerUpVotes = candidateVotes.get(runnerUp.getCandidateId());

            System.out.println( "Runner-Up: " + runnerUp.getName() + " - " + runnerUpVotes + " votes"
            );

        } else {
            System.out.println("Runner-Up: Not available");
        }

        System.out.println("Total Votes: " + getTotalVotes());
    }

    // Check whether a student has voted
    public boolean hasStudentVoted(String studentId) {
        return Boolean.TRUE.equals(hasVoted.get(studentId));
    }
}