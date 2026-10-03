package HospitalManagement;

//LocalDate is used for storing appointment date
import java.time.LocalDate;
//LocalTime is used for storing appointment time
import java.time.LocalTime;

public class appointment {


    // store Unique appointment ID
    private int appointmentId;
    // store Patient object associated with this appointment
    private Patient patient;
    //store Doctor object associated with this appointment
    private doctor doctor;
    // store Date of appointment
    private LocalDate date;
    //store Time of appointment
    private LocalTime time;

    // Constructor is used to create an Appointment object
    public appointment( int appointmentId, Patient patient, doctor doctor, LocalDate date, LocalTime time) {

    	// Store appointment information
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    // Getter for appointment ID
    public int getAppointmentId() {
        return appointmentId;
    }

    // Getter for patient
    public Patient getPatient() {
        return patient;
    }

    // Getter for doctor
    public doctor getDoctor() {
        return doctor;
    }

    // Getter for date
    public LocalDate getDate() {
        return date;
    }

    // Getter for time
    public LocalTime getTime() {
        return time;
    }

    // Display appointment information
    @Override
    public String toString() {

        return "Appointment ID: " + appointmentId
                + ", Patient: " + patient.getName()
                + ", Doctor: Dr. " + doctor.getName()
                + ", Specialization: "
                + doctor.getSpecialization()
                + ", Date: " + date
                + ", Time: " + time;
    }
}