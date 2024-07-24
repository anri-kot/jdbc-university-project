package universityGUI.coursesGUI;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import universityCore.Courses;

public class CoursesTableModel extends AbstractTableModel {
    private static final int ID_COL = 0;
    private static final int C_NAME_COL = 1;
    private static final int C_DURATION_COL = 2;
    public static final int OBJECT_COL = -1;
    
    private String[] columnNames = {"Course ID", "Name", "Duration (h)"};
    private List<Courses> courses;

    public CoursesTableModel(List<Courses> courses) {
        this.courses = courses;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return courses.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Courses tempCourses = courses.get(row);

        switch (col) {
            case ID_COL:
                return tempCourses.getIdCourse();
            case C_NAME_COL:
                return tempCourses.getC_name();
            case C_DURATION_COL:
                return tempCourses.getC_duration();
            case OBJECT_COL:
                return tempCourses;
            default:
                return tempCourses.getIdCourse();
        }
    }
}
