package universityGUI.studentsGUI;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import universityCore.Enrollments;

public class EnrollmentsTableModel extends AbstractTableModel {
    // column indexes
    private final static int ID_ENROLLMENT_COL = 0;
    private final static int EN_YEAR_COL = 1;
    private final static int ID_STUDENT_COL = 2;
    private final static int ID_CLASS_COL = 3;

    private String[] columnNames = {"ID", "Year", "Student ID", "Class"};
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

        switch (col) {
            case ID_ENROLLMENT_COL:
                return tempEnrollments.getIdEnrollment();
            case EN_YEAR_COL:
                return tempEnrollments.getEn_year();
            case ID_STUDENT_COL:
                return tempEnrollments.getStudents().getMatr();
            case ID_CLASS_COL:
                return tempEnrollments.getClasses().getIdClass();
            default:
                return tempEnrollments;
        }
    }
}
