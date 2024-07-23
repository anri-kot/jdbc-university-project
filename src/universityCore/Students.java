package universityCore;

public class Students extends UniversityEntity {

    public Students(){ // empty constructor for building generic objects
        super();
    }

    public Students(int matr, String name, String address, String phone, String ssn) {
        super(matr, name, address, phone, ssn);
    }

    public String toString() {
        return "studentId=" + getMatr() + ", name=" + getName() +
                ", address=" + getAddress() + ", phone=" + getPhone() + ", ssn=" + getSsn();
    }

    /*
     * public String toString() {
     * return "\n Student id: " + getMatr() +
     * "\n Name: " + getName() +
     * "\n Address: " + getAddress() +
     * "\n Phone: " + getPhone() +
     * "\n SSN: " + getSsn();
     * }
     */
}
