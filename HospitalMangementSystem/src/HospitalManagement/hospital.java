package HospitalManagement;

// used forstoring appointment dates, times, dynamic lists, dat storage, interface, map interface
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hospital {
	
	// HashMap stores patients and doctors. like key value pairs
    private Map<Integer, Patient> patients;
    private Map<Integer, doctor> doctors;
    private Map<String, appointment> appointments;

    // Appointment ID starts from 1001
    private int appointmentCounter = 1001;

 // Constructor of Hospital class
    public hospital() {

    	// create hash map for patients, doctors, and for appointments
        patients = new HashMap<>();
        doctors = new HashMap<>();
        appointments = new HashMap<>();
    }

 
// register patient
    // This method registers a new patient 
    public void registerPatient(Patient patient)
            throws hospitalException {

    	//cantainsKey() checks whether the patient ID already exists in the HashMap.
        if (patients.containsKey(patient.getPatientId())) {

        	 // If ID already exists, throw custom exception
            throw new hospitalException( "Patient ID already exists: " + patient.getPatientId());
        }

        // put() stores the patient object in HashMap.
        patients.put( patient.getPatientId(), patient );

        System.out.println();
        System.out.println( "Patient registered successfully.");
    }


// add doctors
    // This method adds a doctor to the system.
    // Users do not directly call this method from the menu.
    public void addDoctor(doctor doctor)
            throws hospitalException {
    	
        // Check whether doctor ID already exists
        if (doctors.containsKey(doctor.getDoctorId())) {

            // Throw exception if duplicate doctor ID is found
            throw new hospitalException( "Doctor ID already exists: " + doctor.getDoctorId());
        }

        // Add doctor to the HashMap
        doctors.put( doctor.getDoctorId(), doctor );

        System.out.println( "Doctor added successfully.");
    }


// get all patients
    // Returns all registered patients
    public List<Patient> getAllPatients() {

    	// new ArrayList<> creates a list from those objects // patients.values() returns all Patient objects
        return new ArrayList<>(
                patients.values()
        );
    }


// get all doctors
    public List<doctor> getAllDoctors() {

    	// Convert HashMap values into ArrayList
        return new ArrayList<>(
                doctors.values()
        );
    }


// get all patient by id
 // Finds a patient using patient ID
    public Patient getPatient(int patientId)
            throws hospitalException {

    	// Get patient object from HashMap
        Patient patient = patients.get(patientId);

        // If patient ID does not exist, get() returns null.
        if (patient == null) {
            // Throw custom exception
            throw new hospitalException( "Patient not found.");
        }

        return patient;
    }

  
    // get doctor by id
 // Finds a doctor using doctor ID
    public doctor getDoctor(int doctorId)
            throws hospitalException {
    	
    	 // Get doctor object from HashMap
        doctor doctor = doctors.get(doctorId);

        // Check whether doctor exists
        if (doctor == null) {
            throw new hospitalException("Doctor not found.");
        }

        return doctor;
    }


// book appointment
    public void bookAppointment(
            int patientId,
            int doctorId,
            LocalDate date,
            LocalTime time)
            throws hospitalException {

    	  // Get patient object
        Patient patient =  getPatient(patientId);

        // Get doctor object
        doctor doctor = getDoctor(doctorId);

        // Check whether selected date is in the past
        if (date.isBefore(LocalDate.now())) {
            throw new hospitalException("Cannot book appointment for a past date.");
        }

        // Convert time into HH:mm format
        String slot = String.format( "%02d:%02d", time.getHour(), time.getMinute() );

        // Check valid slot
        if (!doctor.getAvailableSlots().contains(slot)) {
            throw new hospitalException( "Invalid time slot.");
        }

        // Create unique key
        String appointmentKey = createAppointmentKey( doctorId, date, time );

        // Check duplicate booking
        if (appointments.containsKey( appointmentKey)) {
            throw new hospitalException( "This time slot is already booked.");
        }

       //  If appointment is for today, check whether the selected time has already passed.
        if (date.equals(LocalDate.now()) && time.isBefore(LocalTime.now())) {
            throw new hospitalException( "Cannot book an appointment "  + "for a past time.");
        }

        // Create appointment
        appointment appointment = new appointment( appointmentCounter++, patient, doctor, date, time );

        // Store appointment in HashMap
        appointments.put( appointmentKey, appointment );

        // Display booking confirmation
        System.out.println();
        System.out.println( "========================================");

        System.out.println("Appointment booked successfully.");
        System.out.println("Appointment ID: " + appointment.getAppointmentId());
        System.out.println("Patient: " + patient.getName());
        System.out.println( "Doctor: Dr. " + doctor.getName());
        System.out.println( "Date: " + date);
        System.out.println( "Time: " + time);

    }

   
    
// cancel appointment
    public void cancelAppointment(
            int appointmentId)
            throws hospitalException {
    	
    	// Variable stores the key of appointment to remove
        String keyToRemove = null;

        //  entrySet() gives both key and value from the appointments HashMap.
        for (Map.Entry<String, appointment> entry
                : appointments.entrySet()) {

        	// Get Appointment object from current entry and compare its ID with requested ID.
            if (entry.getValue().getAppointmentId() == appointmentId) {
            	 // Store the matching key
                keyToRemove = entry.getKey();

                break;
            }
        }

        // If key is still null, appointment was not found
        if (keyToRemove == null) {
            throw new hospitalException( "Appointment not found.");
        }
        // Remove appointment from HashMap
        appointments.remove(keyToRemove);
        // Display confirmation
        System.out.println();
        System.out.println( "Appointment cancelled successfully.");
    }

  
// view available slots
    //  Returns available time slots for a doctor on a particular date.
    public List<String> getAvailableSlots(
            int doctorId,
            LocalDate date)
            throws hospitalException {
    	
    	 // Find doctor using doctor ID
        doctor doctor = getDoctor(doctorId);

        // Create empty list for available slots
        List<String> availableSlots = new ArrayList<>();

        // Loop through all default slots of the selected doctor.
        for (String slot : doctor.getAvailableSlots()) {

        	// Convert String time into LocalTime
            LocalTime time = LocalTime.parse(slot);

            // Create unique appointment key
            String key = createAppointmentKey( doctorId, date, time );

            // If appointment does not exist, add the slot to available list.
            if (!appointments.containsKey(key)) {

                availableSlots.add(slot);
            }
        }

        return availableSlots;
    }


    // view doctors schedule
    //  Returns all appointments of a doctor on a particular date.
   
    public List<appointment> getDoctorSchedule( int doctorId, LocalDate date)
            throws hospitalException {
    	 // First verify that doctor exists
        getDoctor(doctorId);

        // Create empty schedule list
        List<appointment> schedule = new ArrayList<>();

        // Loop through all appointments
        for (appointment appointment :
                appointments.values()) {

        	// Check two conditions -  Appointment belongs to selected doctor
            if (appointment.getDoctor()
                    .getDoctorId()
                    == doctorId
                    && appointment.getDate()
                    .equals(date)) {

                // Add matching appointment
                schedule.add(appointment);
            }
        }

        // Return doctors schedule
        return schedule;
    }


// view patient history
    //  Returns all appointments of a particular patient
    public List<appointment> getPatientHistory( int patientId)
            throws hospitalException {

    	// Verify patient exists
        getPatient(patientId);

        // Create empty history list
        List<appointment> history = new ArrayList<>();

        // Loop through all appointments
        for (appointment appointment :
                appointments.values()) {

        	//  Check whether appointment belongs to the selected patient.
            if (appointment.getPatient()
                    .getPatientId()
                    == patientId) {

                history.add(appointment);
            }
        }

        return history;
    }


// create unique appointment key
    // This method creates a unique key for every appointment.  
    private String createAppointmentKey( int doctorId, LocalDate date, LocalTime time) {

        return doctorId + "-" + date  + "-"  + time;
    }

 
    // Displays all registered patients
    public void displayPatients() {

    	 // Check whether there are no patients
        if (patients.isEmpty()) {
            System.out.println( "No patients registered.");
            return;
        }

        System.out.println();
        System.out.println( "========== REGISTERED PATIENTS ==========");

        // Loop through all patients
        for (Patient patient : patients.values()) {
            System.out.println(patient);
        }
    }

 
// display all doctors
    public void displayDoctors() {

    	 // Check whether doctors are available
        if (doctors.isEmpty()) {
            System.out.println( "No doctors available.");
            return;
        }

        System.out.println();
        System.out.println( "========== AVAILABLE DOCTORS ==========" );

        // Loop through all doctors
        for (doctor doctor : doctors.values()) {
            System.out.println( doctor.getDoctorId() + ". " + doctor);
        }
    }
}