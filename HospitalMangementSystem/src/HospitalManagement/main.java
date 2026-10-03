package HospitalManagement; 
// Defines the package
import java.time.LocalDate; 
import java.time.LocalTime; 
import java.time.format.DateTimeFormatter; 
import java.util.List; 
import java.util.Scanner; 

public class main { 

	// Creates Scanner
    private static Scanner scanner = new Scanner(System.in); 
    private static hospital hospital = new hospital(); 
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); 

    public static void main(String[] args) { 

    	// Loads doctors
        loadDoctors(); 
        // Stores menu choice
        int choice = -1;

     // Repeats the menu
        do { 
        	 // Shows menu
            displayMenu();

         // Handles errors
            try { 
            	 // Gets user choice
                choice = Integer.parseInt(scanner.nextLine());
                // Checks user choice
                switch (choice) {
                
             // Register patient
                    case 1: 
                        registerPatient(); // calls registration
                        break; 

               // view doctors
                    case 2: 
                        viewDoctors(); // shows doctors
                        break; 

               // book appointment          
                    case 3: 
                        bookAppointment(); // books appointment
                        break; 

               // cancel appointment
                    case 4:
                        cancelAppointment(); // cancels appointment.
                        break;

              // View available slots
                    case 5: 
                        viewAvailableSlots(); // shows available slots
                        break; 

             // View doctor schedule
                    case 6: 
                        viewDoctorSchedule(); // shows doctor schedule
                        break;

              // View patient history
                    case 7: 
                        viewPatientHistory(); // Shows patient history.
                        break;

                    case 8: // View all patients.
                        hospital.displayPatients(); // Shows all patients.
                        break; 

                    case 0: // Exit program.
                        System.out.println(); // Prints blank line.
                        System.out.println("Thank you for using " + "Hospital Appointment " + "Management System."); // Shows exit message.
                        break; // Stops this case.

                    default: // Invalid choice.
                        System.out.println("Invalid choice."); // Shows error.
                }

            } catch (NumberFormatException e) { // Handles wrong number.

                System.out.println(); // Prints blank line.
                System.out.println("Please enter a valid number."); // Shows error.

            } catch (hospitalException e) { // Handles hospital errors.

                System.out.println(); // Prints blank line.
                System.out.println("Error: " + e.getMessage()); // Shows error message.

            } catch (Exception e) { // Handles other errors.

                System.out.println(); // Prints blank line.
                System.out.println("Invalid input: " + e.getMessage()); // Shows error message.
            }

        } while (choice != 0); // Repeats until 0.

        scanner.close(); // Closes Scanner.
    }



    private static void loadDoctors() { // Loads doctor details.

        try { // Handles errors.

            hospital.addDoctor(new doctor(501, "Ravi Kumar", "Cardiologist")); // Adds doctor.
            hospital.addDoctor(new doctor(502, "Priya Sharma", "Dermatologist")); // Adds doctor.
            hospital.addDoctor(new doctor(503, "Anil Kumar", "Neurologist")); // Adds doctor.
            hospital.addDoctor(new doctor(504, "Sneha Reddy", "General Physician")); // Adds doctor.

        } catch (hospitalException e) { // Handles doctor error.
            System.out.println("Error loading doctors: " + e.getMessage()); // Shows error.
        }
    }



    private static void displayMenu() { // Shows main menu.

        System.out.println(); // Prints blank line.
        System.out.println("========================================"); // Prints border.
        System.out.println(" HOSPITAL APPOINTMENT MANAGEMENT SYSTEM"); // Prints title.
        System.out.println("========================================"); // Prints border.
        System.out.println("1. Register Patient"); // Shows option 1.
        System.out.println("2. View Doctors"); // Shows option 2.
        System.out.println("3. Book Appointment"); // Shows option 3.
        System.out.println("4. Cancel Appointment"); // Shows option 4.
        System.out.println("5. View Available Slots"); // Shows option 5.
        System.out.println("6. View Doctor Schedule"); // Shows option 6.
        System.out.println("7. View Patient History"); // Shows option 7.
        System.out.println("8. View All Patients"); // Shows option 8.
        System.out.println("0. Exit"); // Shows exit option.
        System.out.print("Enter your choice: "); // Asks for choice.
    }



    private static void registerPatient() throws hospitalException { // Registers patient.

        System.out.println(); // Prints blank line.
        System.out.println("----- Register Patient -----"); // Shows heading.
        System.out.print("Enter Patient ID: "); // Asks patient ID.
        
        int id = Integer.parseInt(scanner.nextLine()); // Gets patient ID.
        System.out.print("Enter Patient Name: "); // Asks patient name.
        String name = scanner.nextLine(); // Gets patient name.
        System.out.print("Enter Age: "); // Asks age.

        int age = Integer.parseInt(scanner.nextLine()); // Gets age.
        System.out.print("Enter Gender: "); // Asks gender.
        String gender = scanner.nextLine(); // Gets gender.
        System.out.print("Enter Phone: "); // Asks phone.
        String phone = scanner.nextLine(); // Gets phone.

        Patient patient = new Patient(id, name, age, gender, phone); // Creates patient.
        hospital.registerPatient(patient); // Registers patient.
    }

   
    private static void viewDoctors() { // Shows doctors.

        List<doctor> doctors = hospital.getAllDoctors(); // Gets all doctors.

        System.out.println(); // Prints blank line.
        System.out.println("========== AVAILABLE DOCTORS =========="); // Shows heading.

        for (int i = 0; i < doctors.size(); i++) { // Loops through doctors.

            doctor doctor = doctors.get(i); // Gets current doctor.
            System.out.println((i + 1) + ". Dr. " + doctor.getName() + " - " + doctor.getSpecialization()); // Shows doctor details.
        }

        System.out.println("========================================"); // Prints border.
    }



    private static void bookAppointment() throws hospitalException { // Books appointment.

        System.out.println(); // Prints blank line.
        System.out.println("========== BOOK APPOINTMENT =========="); // Shows heading.

        List<Patient> patients = hospital.getAllPatients(); // Gets all patients.
        if (patients.isEmpty()) { // Checks patients.

            System.out.println("No patients registered."); // Shows message.
            System.out.println("Please register a patient first."); // Shows instruction.
            return; // Stops method.
        }

        System.out.println(); // Prints blank line.
        System.out.println("Available Patients:"); // Shows patients.

        for (int i = 0; i < patients.size(); i++) { // Loops through patients.
           Patient patient = patients.get(i); // Gets current patient.
            System.out.println((i + 1) + ". " + patient.getName() + " (ID: " + patient.getPatientId() + ")"); // Shows patient details.
        }

        System.out.print("Select Patient: "); // Asks patient.

        int patientChoice = Integer.parseInt(scanner.nextLine()); // Gets patient choice.
        if (patientChoice < 1 || patientChoice > patients.size()) { // Checks patient choice.

            System.out.println("Invalid patient selection."); // Shows error.
            return; // Stops method.
        }

        Patient selectedPatient = patients.get(patientChoice - 1); // Gets selected patient.


        List<doctor> doctors = hospital.getAllDoctors(); // Gets all doctors.

        System.out.println(); // Prints blank line.
        System.out.println("Available Doctors:"); // Shows doctors.

        for (int i = 0; i < doctors.size(); i++) { // Loops through doctors.
            doctor doctor = doctors.get(i); // Gets current doctor.
            System.out.println((i + 1) + ". Dr. " + doctor.getName() + " - " + doctor.getSpecialization()); // Shows doctor details.
        }

        System.out.print("Select Doctor: "); // Asks doctor.

        int doctorChoice = Integer.parseInt(scanner.nextLine()); // Gets doctor choice.
        if (doctorChoice < 1 || doctorChoice > doctors.size()) { // Checks doctor choice.
            System.out.println("Invalid doctor selection."); // Shows error.
            return; // Stops method.
        }
        
        doctor selectedDoctor = doctors.get(doctorChoice - 1); // Gets selected doctor.
        System.out.println(); // Prints blank line.
        System.out.print("Enter Date (yyyy-MM-dd): "); // Asks date.
        LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormatter); // Converts input to date.
        List<String> slots = hospital.getAvailableSlots(selectedDoctor.getDoctorId(), date); // Gets available slots.

        if (slots.isEmpty()) { // Checks available slots.

            System.out.println(); // Prints blank line.
            System.out.println("No slots available for " + "this doctor on this date."); // Shows message.
            return; // Stops method.
        }

        System.out.println(); // Prints blank line.
        System.out.println("Available Time Slots:"); // Shows time slots.

        for (int i = 0; i < slots.size(); i++) { // Loops through slots.

            System.out.println((i + 1) + ". " + slots.get(i)); // Shows slot.
        }
        System.out.print("Select Time Slot: "); // Asks time slot.

        int slotChoice = Integer.parseInt(scanner.nextLine()); // Gets slot choice.

        if (slotChoice < 1 || slotChoice > slots.size()) { // Checks slot choice.
            System.out.println("Invalid slot selection."); // Shows error.
            return; // Stops method.
        }

        String selectedSlot = slots.get(slotChoice - 1); // Gets selected slot.

        LocalTime selectedTime = LocalTime.parse(selectedSlot, timeFormatter); // Converts slot to time.
        hospital.bookAppointment(selectedPatient.getPatientId(), selectedDoctor.getDoctorId(), date, selectedTime); // Books appointment.
    }


    private static void cancelAppointment() throws hospitalException { // Cancels appointment.

        System.out.println(); // Prints blank line.
        System.out.println("----- Cancel Appointment -----"); // Shows heading.
        System.out.print("Enter Appointment ID: "); // Asks appointment ID.

        int appointmentId = Integer.parseInt(scanner.nextLine()); // Gets appointment ID.
        hospital.cancelAppointment(appointmentId); // Cancels appointment.
    }

   
    private static void viewAvailableSlots() throws hospitalException { // Shows available slots.

        System.out.println(); // Prints blank line.
        System.out.println("----- View Available Slots -----"); // Shows heading.

        // Show doctors
        List<doctor> doctors = hospital.getAllDoctors(); // Gets all doctors.
        System.out.println(); // Prints blank line.
        System.out.println("Available Doctors:"); // Shows doctors.

        for (int i = 0; i < doctors.size(); i++) { // Loops through doctors.

            doctor doctor = doctors.get(i); // Gets current doctor.
            System.out.println((i + 1) + ". Dr. " + doctor.getName() + " - " + doctor.getSpecialization()); // Shows doctor details.
        }

        System.out.print("Select Doctor: "); // Asks doctor.
        int doctorChoice = Integer.parseInt(scanner.nextLine()); // Gets doctor choice.

        if (doctorChoice < 1 || doctorChoice > doctors.size()) { // Checks doctor choice.

            System.out.println("Invalid doctor selection."); // Shows error.
            return; // Stops method.
        }

        doctor selectedDoctor = doctors.get(doctorChoice - 1); // Gets selected doctor.
        System.out.print("Enter Date (yyyy-MM-dd): "); // Asks date.

        LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormatter); // Converts input to date.
        List<String> slots = hospital.getAvailableSlots(selectedDoctor.getDoctorId(), date); // Gets available slots.

        System.out.println(); // Prints blank line.
        System.out.println("Available Slots:"); // Shows slots.

        if (slots.isEmpty()) { // Checks slots.
            System.out.println("No slots available."); // Shows message.

        } else { // Runs when slots exist.
            for (int i = 0; i < slots.size(); i++) { // Loops through slots.
                System.out.println((i + 1) + ". " + slots.get(i)); // Shows slot.
            }
        }
    }

   
    private static void viewDoctorSchedule() throws hospitalException { // Shows doctor schedule.

        System.out.println(); // Prints blank line.
        System.out.println("----- Doctor Schedule -----"); // Shows heading.

        List<doctor> doctors = hospital.getAllDoctors(); // Gets all doctors.

        System.out.println(); // Prints blank line.
        System.out.println("Available Doctors:"); // Shows doctors.

        for (int i = 0; i < doctors.size(); i++) { // Loops through doctors.
            doctor doctor = doctors.get(i); // Gets current doctor.
            System.out.println((i + 1) + ". Dr. " + doctor.getName() + " - " + doctor.getSpecialization()); // Shows doctor details.
        }

        System.out.print("Select Doctor: "); // Asks doctor.

        int doctorChoice = Integer.parseInt(scanner.nextLine()); // Gets doctor choice.

        if (doctorChoice < 1 || doctorChoice > doctors.size()) { // Checks doctor choice.
            System.out.println("Invalid doctor selection."); // Shows error.

            return; // Stops method.
        }

        doctor selectedDoctor = doctors.get(doctorChoice - 1); // Gets selected doctor.

        System.out.print("Enter Date (yyyy-MM-dd): "); // Asks date.
        LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormatter); // Converts input to date.
        List<appointment> schedule = hospital.getDoctorSchedule(selectedDoctor.getDoctorId(), date); // Gets doctor schedule.
        System.out.println(); // Prints blank line.

        if (schedule.isEmpty()) { // Checks appointments.
            System.out.println("No appointments found."); // Shows message.

        } else { // Runs when appointments exist.
            System.out.println("Doctor Schedule:"); // Shows schedule.

            for (appointment appointment : schedule) { // Loops appointments.
                System.out.println(appointment); // Shows appointment.
            }
        }
    }

  
    private static void viewPatientHistory() throws hospitalException { // Shows patient history.
        System.out.println(); // Prints blank line.
        System.out.println("----- Patient History -----"); // Shows heading.

        List<Patient> patients = hospital.getAllPatients(); // Gets all patients.

        if (patients.isEmpty()) { // Checks patients.
            System.out.println("No patients registered."); // Shows message.
            return; // Stops method.
        }

        System.out.println(); // Prints blank line.
        System.out.println("Registered Patients:"); // Shows patients.

        for (int i = 0; i < patients.size(); i++) { // Loops patients.
            Patient patient = patients.get(i); // Gets current patient.
            System.out.println((i + 1) + ". " + patient.getName() + " (ID: " + patient.getPatientId() + ")"); // Shows patient details.
        }
        System.out.print("Select Patient: "); // Asks patient.

        int patientChoice = Integer.parseInt(scanner.nextLine()); // Gets patient choice.

        if (patientChoice < 1 || patientChoice > patients.size()) { // Checks patient choice.
            System.out.println("Invalid patient selection."); // Shows error.
            return; // Stops method.
        }

        Patient selectedPatient = patients.get(patientChoice - 1); // Gets selected patient.
        List<appointment> history = hospital.getPatientHistory(selectedPatient.getPatientId()); // Gets patient history.
        System.out.println(); // Prints blank line.
        if (history.isEmpty()) { // Checks history.
            System.out.println("No appointment history found."); // Shows message.

        } else { // Runs when history exists.
            System.out.println("Patient Appointment History:"); // Shows history.
            for (appointment appointment : history) { // Loops history.
                System.out.println(appointment); // Shows appointment.
            }
        }
    }
}