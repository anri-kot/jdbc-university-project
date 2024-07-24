package universityCore;

public class Professors extends UniversityEntity {
    private double salary;
    private Courses course;

    public Professors(){} // empty constructor for creating generic Professors Objects

    public Professors(int matr, String name, String address, String phone, String ssn, double salary, Courses course) {
        super(matr, name, address, phone, ssn);
        this.salary = salary;
        this.course = course;
    }

    public String toString() {
        return "professorId=" + getMatr() + ", name=" + getName() +
                ", address=" + getAddress() + ", phone=" + getPhone() + ", ssn=" + getSsn() +
                ", salary=" + getSalary() + ", courseId=" + getCourse().getIdCourse();
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Courses getCourse() {
        return course;
    }

    public void setCourse(Courses course) {
        this.course = course;
    }
}
