package universityCore;

public abstract class UniversityEntity {
    private int matr;
    private String name;
    private String address;
    private String phone;
    private String ssn;

    public UniversityEntity(int matr, String name, String address, String phone, String ssn) {
        this.matr = matr;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.ssn = ssn;
    }

    public UniversityEntity() { // empty constructor for building generic objects
    }

    public abstract String toString();

    public int getMatr() {
        return matr;
    }

    public void setMatr(int matr) {
        this.matr = matr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
