package universityGUI.classesGUI;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import universityCore.Classes;
import universityCore.Courses;
import universityDAO.CoursesDAO;

public class ClassesTableModel extends AbstractTableModel {
    private final static int ID_CLASS_COL = 0;
    private final static int SEMESTER_COL = 1;
    private final static int COURSE_COL = 2;
    public static final int OBJECT_COL = -1;

    private String[] columnNames = {"Class ID", "Semester", "Course"};
    private List<Classes> classes;
    
    public ClassesTableModel(List<Classes> classes) {
        this.classes = classes;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return classes.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Classes tempClasses = classes.get(row);

        switch (col) {
            case ID_CLASS_COL:
                return tempClasses.getIdClass();
            case SEMESTER_COL:
                return tempClasses.getSemester();
            case COURSE_COL:
                try {
                    CoursesDAO coursesDAO = new CoursesDAO();
                    int courseId = tempClasses.getCourses().getIdCourse();
                    Courses course = coursesDAO.searchCoursesId(courseId).get(0);
                    return course.getC_name();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            case OBJECT_COL:
                return tempClasses;
            default:
                return tempClasses.getIdClass();
        }
    }
}
