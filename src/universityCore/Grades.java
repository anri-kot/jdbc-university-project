package universityCore;

public class Grades {
    private int idGrade;
    private double grade;
    private Students students;
    private Classes classes;

    public Grades(int idGrade, double grade, Students students, Classes classes) {
        this.idGrade = idGrade;
        this.grade = grade;
        this.students = students;
        this.classes = classes;
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
