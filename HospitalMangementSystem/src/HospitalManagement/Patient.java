package HospitalManagement;

public class Patient {

	// Private variables store patient information
    // private is used for encapsulation
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;

    // Constructor is used to initialize patient details
    // Constructor name must be same as class name
    public Patient(int patientId, String name, int age, String gender, String phone) {

    	// this refers to the current Patient object
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    // Getter method returns the patient details
    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    public String getGender() {
        return gender;
    }

 
    public String getPhone() {
        return phone;
    }

    // toString() is used to display patient information
    // when we print the Patient object
    @Override
    public String toString() {

        return "Patient ID: " + patientId + ", Name: " + name  + ", Age: " + age + ", Gender: " + gender + ", Phone: " + phone;
    }
}