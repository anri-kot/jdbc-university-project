package universityGUI.studentsGUI;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import universityCore.Students;

public class StudentsTableModel extends AbstractTableModel {
    private final static int ST_MATR_COL = 0;
    private final static int ST_NAME_COL = 1;
    private final static int ST_ADDRESS_COL = 2;
    private final static int ST_PHONE_COL = 3;
    private final static int ST_SSN_COL = 4;
    protected final static int OBJECT_COL = -1; // will be used to return the default object

    private String[] columnNames = { "ID", "Name", "Address", "Phone", "SSN" };
    private List<Students> students;

    public StudentsTableModel(List<Students> theStudents) {
        students = theStudents;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return students.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Students tempStudents = students.get(row);

        switch (col) {
            case ST_MATR_COL:
                return tempStudents.getMatr();
            case ST_NAME_COL:
                return tempStudents.getName();
            case ST_ADDRESS_COL:
                return tempStudents.getAddress();
            case ST_PHONE_COL:
                return tempStudents.getPhone();
            case ST_SSN_COL:
                return tempStudents.getSsn();
            default:
                return tempStudents;
        }
    }
}
