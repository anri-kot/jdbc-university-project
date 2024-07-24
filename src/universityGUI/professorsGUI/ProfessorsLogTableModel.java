package universityGUI.professorsGUI;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import universityCore.logs.ProfessorsLog;
import universityDAO.UsersDAO;

public class ProfessorsLogTableModel extends AbstractTableModel {
    // column indexes
    private static final int ID_COL = 0;
    private static final int USER_COL = 1;
    private static final int PROFESSOR_COL = 2;
    private static final int ACTION_COL = 3;
    private static final int DATE_COL = 4;

    private String[] columnNames = {"Log ID", "User", "Professor ID", "Action", "Date"};
    private List<ProfessorsLog> logs;

    public ProfessorsLogTableModel(List<ProfessorsLog> logs) {
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
        ProfessorsLog tempLog = logs.get(row);

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
            case PROFESSOR_COL:
                return tempLog.getProfessors().getMatr();
            case ACTION_COL:
                return tempLog.getAction();
            case DATE_COL:
                return tempLog.getAction_date();
            default:
                return tempLog.getIdLog();
        }
    }
}
