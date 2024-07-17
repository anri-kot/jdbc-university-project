package universityGUI.studentsGUI;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;
import universityCore.Enrollments;
import universityDAO.EnrollmentsDAO;

public class EnrollmentsTableModel extends AbstractTableModel {
    // column indexes
    private final static int ID_ENROLLMENT_COL = 0;
    private final static int EN_YEAR_COL = 1;
    private final static int ID_STUDENT_COL = 2;
    private final static int ID_CLASS_COL = 3;
    private final static int COURSE_NAME_COL = 4;
    protected final static int OBJECT_COL = -1;

    private String[] columnNames = {"ID", "Year", "Student ID", "Class", "Course"};
    private List<Enrollments> enrollments;

    // constructor
    public EnrollmentsTableModel(List<Enrollments> theEnrollments) {
        enrollments = theEnrollments;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return enrollments.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Enrollments tempEnrollments = enrollments.get(row);
        ArrayList<String> courseName = new ArrayList<>();

        switch (col) {
            case ID_ENROLLMENT_COL:
                return tempEnrollments.getIdEnrollment();
            case EN_YEAR_COL:
                return tempEnrollments.getEn_year();
            case ID_STUDENT_COL:
                return tempEnrollments.getStudents().getMatr();
            case ID_CLASS_COL:
                return tempEnrollments.getClasses().getIdClass();
            case COURSE_NAME_COL:
                try {
                    EnrollmentsDAO dao = new EnrollmentsDAO();
                    courseName = dao.searchEnrollmentsJoinCourse(tempEnrollments.getStudents().getMatr());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return courseName.get(row);
            case OBJECT_COL:
                return tempEnrollments;
            default:
                return tempEnrollments.getIdEnrollment();
        }
    }
}
