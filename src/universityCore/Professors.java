package universityCore;

public class Professors extends People {
    double salary;
    Courses courses;

    public Professors(int matr, String name, String address, String phone, String ssn, double salary, Courses courses) {
        super(matr, name, address, phone, ssn);
        this.salary = salary;
        this.courses = courses;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }
}
