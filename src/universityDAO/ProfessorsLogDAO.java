package universityDAO;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import universityCore.Professors;
import universityCore.Users;
import universityCore.logs.ProfessorsLog;

public class ProfessorsLogDAO {
    // Connection
    Connection con;
    
    public ProfessorsLogDAO() throws Exception {
        // initializing connection
        con = ConnectionManager.getConnection();
    }

    /* ---- SQL OPERATIONS ---- */

    // get all logs
    public List<ProfessorsLog> getLogs() throws Exception {
        List<ProfessorsLog> list = new ArrayList<>(); // list that will receive the objects from the db
        // initializing Statements and ResultSet
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from auditlog_professors");

            // loop to get all rows from the db into the list
            while (rs.next()) {
                ProfessorsLog tempLogs = rowToLogs(rs);
                list.add(tempLogs);
            }
            return list;
        } finally {
            st.close();
            rs.close();
        }
    }

    public void addLogs(int userId, int professorId, String action) throws Exception {
        // initializing Statements
        PreparedStatement st = null;

        try {
            // sql statement
            st = con.prepareStatement("insert into auditlog_professors (_user, _pr_matr, action) values (?, ?, ?)");
            // parameters
            st.setInt(1, userId);
            st.setInt(2, professorId);
            st.setString(3, action);
            
            st.executeUpdate();
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error:\n" + exc, "Error creating log", JOptionPane.ERROR_MESSAGE);
        }
        if (st != null) {
            st.close();;
        }
    }

    private ProfessorsLog rowToLogs(ResultSet rs) throws SQLException {
        // assigning row values into variables
        int idlog_st = rs.getInt("idlog_pr");
        int userid = rs.getInt("_user");
        int professorId = rs.getInt("_pr_matr");
        String action = rs.getString("action");
        Timestamp action_date = rs.getTimestamp("action_date");

        Users users = new Users();
        users.setId(userid);
        Professors professors = new Professors();
        professors.setMatr(professorId);

        return new ProfessorsLog(idlog_st, users, professors, action, action_date);
    }

    public static void main(String[] args) throws Exception {
        ProfessorsLogDAO dao = new ProfessorsLogDAO();
        System.out.println(dao.getLogs());
    }
}
