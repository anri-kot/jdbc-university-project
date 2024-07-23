package universityGUI.studentsGUI;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import universityCore.logs.StudentsLog;
import universityDAO.UsersDAO;

public class LogTableModel extends AbstractTableModel {
    // column indexes
    private static final int ID_COL = 0;
    private static final int USER_COL = 1;
    private static final int STUDENT_COL = 2;
    private static final int ACTION_COL = 3;
    private static final int DATE_COL = 4;

    private String[] columnNames = {"Log ID", "User", "Student ID", "Action", "Date"};
    private List<StudentsLog> logs;

    public LogTableModel(List<StudentsLog> logs) {
        this.logs = logs;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return logs.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        StudentsLog tempLog = logs.get(row);

        switch (col) {
            case ID_COL:
                return tempLog.getIdLog();
            case USER_COL:
                try {
                    UsersDAO usersDAO = new UsersDAO();
                    String user = usersDAO.getUsername(tempLog.getUsers().getId());
                    return user;
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            case STUDENT_COL:
                return tempLog.getStudents().getMatr();
            case ACTION_COL:
                return tempLog.getAction();
            case DATE_COL:
                return tempLog.getAction_date();
            default:
                return tempLog.getIdLog();
        }
    }
}
