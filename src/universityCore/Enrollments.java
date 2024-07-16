package universityCore;

public class Enrollments {
    int idEnrollment;
    int en_year;
    Students students;
    Classes classes;

    public Enrollments(int idEnrollment, int en_year, Students students, Classes classes) {
        this.idEnrollment = idEnrollment;
        this.en_year = en_year;
        this.students = students;
        this.classes = classes;
    }

    public String toString() {
        return "\n[idEnrollment=" + getIdEnrollment() +
            ", en_year=" + getEn_year() +
            ", studentId=" + getStudents().getMatr() +
            ", idClass=" + getClasses().getIdClass() + "]";
    }

    public int getIdEnrollment() {
        return idEnrollment;
    }

    public void setIdEnrollment(int idEnrollment) {
        this.idEnrollment = idEnrollment;
    }

    public int getEn_year() {
        return en_year;
    }

    public void setEn_year(int en_year) {
        this.en_year = en_year;
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }
}
