package universityCore;

import java.sql.Date;

public class Grades {
    private int idGrade;
    private double grade;
    private Enrollments enrollments;

    private Date date_received;

    public Grades(int idGrade, double grade, Enrollments enrollments, Date date_received) {
        this.idGrade = idGrade;
        this.grade = grade;
        this.enrollments = enrollments;
        this.date_received = date_received;
    }

    public String toString() {
        return "\n[gradeID=" + getIdGrade() +
            ", grade=" + getGrade() +
            ", enrollment=" + getEnrollments().getIdEnrollment() +
            ", date_received=" + getDate_received() +
            "]";
    }

    public Date getDate_received() {
        return date_received;
    }

    public void setDate_received(Date date_received) {
        this.date_received = date_received;
    }

    public int getIdGrade() {
        return idGrade;
    }

    public void setIdGrade(int idGrade) {
        this.idGrade = idGrade;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Enrollments getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Enrollments enrollments) {
        this.enrollments = enrollments;
    }
}
