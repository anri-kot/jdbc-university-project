package universityDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import universityCore.Enrollments;
import universityCore.Grades;

public class GradesDAO {
    Connection con;

    public GradesDAO () throws Exception {
        // Connection
        con = ConnectionManager.getConnection();
    }

    /* ---- SQL Operations ---- */

    // get all grades
    public List<Grades> getGrades() throws Exception {
        List<Grades> list = new ArrayList<>(); // receives the rows from the db

        // initializing ResultSet and Statement
        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from grades");

            // loop to get all rows
            while (rs.next()) {
                Grades tempGrades = rowToGrades(rs);
                list.add(tempGrades);
            }
            return list;
        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
        }

    }

    // get grade by enrollment
    public List<Grades> searchGrades(int enrollmentId) throws Exception {
        List<Grades> list = new ArrayList<>(); // receives the rows from the db

        // initializing Statements and ResultSet
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // sql statement
            st = con.prepareStatement("select * from grades where _enrollmentId = ?");
            // parameters
            st.setInt(1, enrollmentId);

            rs = st.executeQuery();
            // loop to get all rows
            while (rs.next()) {
                Grades tempGrades = rowToGrades(rs);
                list.add(tempGrades);
            }
            return list;

        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
        } 
    }

    // add new grade
    public void addGrades(Grades theGrades) throws Exception {
        // initializing Statement
        PreparedStatement st = null;
        try {
            // sql statements
            st = con.prepareStatement("insert into grades (grade, _enrollmentId, date_received) values (?, ?, ?)");
            // parameters
            st.setDouble(1, theGrades.getGrade());
            st.setInt(2, theGrades.getEnrollments().getIdEnrollment());
            st.setDate(3, theGrades.getDate_received());

            st.execute();
            JOptionPane.showMessageDialog(null, "Grade added successfully", "Grade Manager", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    // update grade
    public void updateGrades(Grades theGrades) throws Exception {
        // initializing Statement
        PreparedStatement st = null;
        try {
            // sql statement
            st = con.prepareStatement("update grades set grade = ?, _enrollmentId = ?, date_received = ? where idGrade = ?");
            // parameters
            st.setDouble(1, theGrades.getGrade());
            st.setInt(2, theGrades.getEnrollments().getIdEnrollment());
            st.setDate(3, theGrades.getDate_received());
            st.setInt(4, theGrades.getIdGrade());

            st.execute();
            JOptionPane.showMessageDialog(null, "Grade updated successfully", "Grade Manager", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    // delete grades
    public void deleteGrades(int idGrade) throws Exception {
        // initializing Statement
        PreparedStatement st = null;
        try {
            // sql statement
            st = con.prepareStatement("delete from grades where idGrade = ?");
            // param
            st.setInt(1, idGrade);

            st.executeQuery();
            JOptionPane.showMessageDialog(null, "Grade deleted successfully", "Grade Manager", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    /* ------------------- */

    private Grades rowToGrades(ResultSet rs) throws Exception {
        // assigning values into variables
        int idGrade = rs.getInt("idGrade");
        double grade = rs.getDouble("grade");
        int enrollmentId = rs.getInt("_enrollmentId");
        Date date_received = rs.getDate("date_received");

        Enrollments enrollments = new Enrollments(enrollmentId, 0, null, null);
        return new Grades(idGrade, grade, enrollments, date_received);
    }
}
