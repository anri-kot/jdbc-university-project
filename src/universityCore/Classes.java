package universityCore;

public class Classes {
    String idClass;
    int semester;
    Courses courses;

    public Classes(String idClass, int semester, Courses courses) {
        this.idClass = idClass;
        this.semester = semester;
        this.courses = courses;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }
}
