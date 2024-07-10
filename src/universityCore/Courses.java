package universityCore;

public class Courses {
    int idCourse;
    String c_name;
    int c_duration;

    public Courses(int idCourse, String c_name, int c_duration) {
        this.idCourse = idCourse;
        this.c_name = c_name;
        this.c_duration = c_duration;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public int getC_duration() {
        return c_duration;
    }

    public void setC_duration(int c_duration) {
        this.c_duration = c_duration;
    }
}
