package universityDAO;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;
import universityCore.Students;
import universityCore.Users;
import universityCore.logs.StudentsLog;

public class StudentsLogDAO {
    // connection 
    Connection con;

    public StudentsLogDAO() throws Exception {
        // establish connection
        con = ConnectionManager.getConnection();
    }

    // get logs
    public List<StudentsLog> getLogs() throws Exception {
        List<StudentsLog> list = new ArrayList<>(); // the Logs objects

        // initializing Statements and ResultSet
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // sql statement
            st = con.prepareStatement("select * from auditlog_students");
            
            rs = st.executeQuery();
            // loop to get all rows into the list
            while (rs.next()) {
                StudentsLog tempLog = rowToLogs(rs);
                list.add(tempLog);
            }
            return list;
        } finally {
            // closing statements and resultset
            st.close();
            rs.close();
        }
    }

    // add new log
    public void addLog(int userId, int studentId, String action) throws Exception {
        // initializing Statement
        PreparedStatement st = null;

        try {
            // sql statement
            st = con.prepareStatement("insert into auditlog_students (_user, _st_matr, action) " +
                "values (?, ?, ?)");
            // parameters
            st.setInt(1, userId);
            st.setInt(2, studentId);
            st.setString(3, action);
            st.executeUpdate();

        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, "Error:\n" + exc, "Error creating log", JOptionPane.ERROR_MESSAGE);
        }
    }

    private StudentsLog rowToLogs(ResultSet rs) throws SQLException {
        // assigning row values into variables
        int idlog_st = rs.getInt("idlog_st");
        int userid = rs.getInt("_user");
        int studentid = rs.getInt("_st_matr");
        String action = rs.getString("action");
        Timestamp action_date = rs.getTimestamp("action_date");

        Users users = new Users();
        users.setId(userid);
        Students students = new Students();
        students.setMatr(studentid);

        return new StudentsLog(idlog_st, action, action_date, students, users);
    }
}
