package VotingSystem;

import java.util.Scanner;

public class Main {

	// This is the starting point of the java program
    public static void main(String[] args) {

    	// create a scanner object to take the input from users
        Scanner scanner = new Scanner(System.in);

        // create an object of the class that is voting service and also 
        // when this line executes the voting service constructor also runs
        VotingService votingService = new VotingService();

        // this variable stores the students who is currently logged in
        User loggedInUser = null; // null means no student is logged in yet

        // this stores the number selected from the menu like 1 to 8 
        int choice = 0;
        
        // print 
        System.out.println( " Online Voting System " );
        System.out.println(  "================================" );


        // do starts a loop. because we want the menu to appear again and again until the user logout
        do {

            System.out.println( "\n----------- MENU -----------" );

            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Candidates");
            System.out.println("4. Cast Vote");
            System.out.println("5. View Total Votes");
            System.out.println("6. View Election Results");
            System.out.println("7. Logout");
            System.out.println("8. Exit");
            System.out.print( "Enter your choice: " );
            
            // reads whatever the user type
            String input = scanner.nextLine();

            // by using try and catch to handle the errors 
            try {
            	// converts the String into an integer
                choice = Integer.parseInt(input);
                
                // java cannot convert it to an integer
            } catch (NumberFormatException e) {

            	// then print 
                System.out.println(  "Please enter a number from 1 to 8." );
                // skip the remaining code in the current loop and shows the menu again
                continue;
            }


            // switch checks which menu option the user selected
            switch (choice) {


                // REGISTER
                case 1:

                    System.out.println( "\n--- STUDENT REGISTRATION ---" );
                    System.out.print( "Enter Student ID: " );
                    String studentId = scanner.nextLine();
                    System.out.print( "Enter Name: " );

                    String name =  scanner.nextLine();

                    System.out.print( "Create Password: " );
                     
                    // stores the password
                    String password =  scanner.nextLine();


                    // by using try and catch to login 
                    try {

                    	// send the students details to voting service 
                        votingService.registerUser( studentId, name, password );

                    } catch ( IllegalArgumentException e) {

                        System.out.println( "Registration failed: " + e.getMessage()  );
                    }

                    // stop the program goes back to the menu
                    break;


                // Login
                case 2:

                    System.out.println( "\n--- STUDENT LOGIN ---" );
                    System.out.print(  "Enter Student ID: " );

                    String loginId = scanner.nextLine();

                    System.out.print( "Enter Password: " );

                    String loginPassword = scanner.nextLine();


                    try {

                        loggedInUser = votingService.loginUser( loginId, loginPassword );

                        System.out.println( "Login successful!" );
                        System.out.println( "Welcome, " + loggedInUser.getName()  );

                    } catch ( IllegalArgumentException e) {

                        System.out.println( "Login failed: " + e.getMessage() );
                    }

                    break;


                // View candidate
                case 3:
                	// calls the medhod in voting service
                    votingService.displayCandidates();
                    break;


                // cast vote
                case 4:

                	// checks whether someone is looged in 
                    if (loggedInUser == null) {

                        System.out.println( "Please login before voting." );

                        break;
                    }

                    // checks whether the logged-in student has already voted or not
                    if (votingService.hasStudentVoted( loggedInUser.getStudentId())) {

                        System.out.println( "You have already voted." );
                        break;
                    }


                    // candidates are displayed automatically
                    votingService.displayCandidates();
                    System.out.print( "Enter Candidate ID to vote: " );

                    String candidateId =  scanner.nextLine();

                    try {

                        votingService.castVote( loggedInUser.getStudentId(), candidateId);

                    } catch ( IllegalArgumentException | IllegalStateException e) {

                        System.out.println( "Voting failed: "  + e.getMessage());
                    }

                    break;


                // total votes 
                case 5:
                	// display votes count
                    votingService.displayVoteCount();
                    break;


                // Result
                case 6:

                    votingService.displayResults();
                    break;


                // Logout
                case 7:
                    // checks whether someone is currently logged in
                    if (loggedInUser != null) {

                        System.out.println( loggedInUser.getName() + " logged out." );

                        // this line removes the current looged-in user from the session.now system considers nobody logged in
                        loggedInUser = null;

                    } else {

                        System.out.println( "No user is currently logged in." );
                    }

                    break;


                // Exit
                case 8:

                    System.out.println(  "Thank you for using the Online Voting System!" );
                    break;


                default:

                    System.out.println( "Invalid choice. Enter 1 to 8." );
            }

        } while (choice != 8);


        scanner.close();
    }
}