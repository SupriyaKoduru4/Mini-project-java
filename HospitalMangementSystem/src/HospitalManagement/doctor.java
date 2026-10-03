package HospitalManagement;

//ArrayList is used to store multiple time slots
import java.util.ArrayList;
//List is the interface used for storing the slots
import java.util.List;

public class doctor {
	
// store the doctor information 
    private int doctorId;
    private String name;
    private String specialization;
    // List stores the doctors available appointment time slots
    private List<String> availableSlots;

    // Constructor is used to create a Doctor object
    public doctor(int doctorId, String name, String specialization) {

    	 // Assign the received values to object variables by using this keyword
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;

        // Create list for storing available time slots
        this.availableSlots = new ArrayList<>();

        //time slots are added for every doctor
        availableSlots.add("10:00");
        availableSlots.add("11:00");
        availableSlots.add("12:00");
        availableSlots.add("14:00");
        availableSlots.add("15:00");
        availableSlots.add("16:00");
    }

    // Getter method returns the doctor details
    public int getDoctorId() {
        return doctorId;
    }

    // Getter for doctor name
    public String getName() {
        return name;
    }

    // Getter for specialization
    public String getSpecialization() {
        return specialization;
    }

    // Getter for available slots
    public List<String> getAvailableSlots() {
        return availableSlots;
    }

    // toString() displays doctor information
    @Override
    public String toString() {
        return "Dr. " + name + "-" + specialization;
    }
}