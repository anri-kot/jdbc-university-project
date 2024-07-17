package universityDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import universityCore.Enrollments;
import universityCore.Students;
import universityCore.Classes;

public class EnrollmentsDAO {
    Connection con;  // receives the connection

    // Connection
    public EnrollmentsDAO() throws Exception {
        con = ConnectionManager.getConnection(); // establish connection
    }

    /* ---- SQL Operations ---- */

    // returns all enrollments
    public List<Enrollments> getAllEnrollments() throws Exception {
        List<Enrollments> list = new ArrayList<>(); // receives the Enrollments object

        // initializing Statements and ResultSet
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from enrollments");

            // loop for converting objects into list rows
            while (rs.next()) {
                Enrollments tempEnrollments = rowToEnrollments(rs);
                list.add(tempEnrollments);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // search by enrollment id
    public List<Enrollments> searchEnrollmentsId(int id) throws Exception {
        List<Enrollments> list = new ArrayList<>(); // receives the Enrollments object
        // initializing Statements and ResultSet
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            //sql statement
            st = con.prepareStatement("select * from enrollments where idEnrollment = ?"); 
            st.setInt(1, id);

            rs = st.executeQuery();
            // loop for converting object into rows
            while (rs.next()) {
                Enrollments tempEnrollments = rowToEnrollments(rs);
                list.add(tempEnrollments);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // search enrollments by student id
    public List<Enrollments> searchEnrollmentsStId(int matr) throws Exception {
        List<Enrollments> list = new ArrayList<>(); // receives the Enrollments object
        // initializing Statemets and ResultSet
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            // sql statement
            st = con.prepareStatement("select * from Enrollments where Students_st_matr = ?");
            st.setInt(1, matr); // parameter

            rs = st.executeQuery();
            // loop for converting objects into rows
            while (rs.next()) {
                Enrollments tempEnrollments = rowToEnrollments(rs);
                list.add(tempEnrollments);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // search enrollments with course name
    public ArrayList<String> searchEnrollmentsJoinCourse(int matr) throws Exception {
        ArrayList<String> c_name = new ArrayList<>(); // receives the course name
        // initializing Statements and ResultSet
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            // sql statement
            st = con.prepareStatement("select courses.c_name from enrollments " +
                "join classes on enrollments.Classes_idClass = classes.idClass " +
                "join courses on classes.Courses_idCourse = courses.idCourse " +
                "where Students_st_matr = ?");
            st.setInt(1, matr);

            rs = st.executeQuery();
            // loop for getting rows
            while (rs.next()) {
                c_name.add(rs.getString("c_name"));
            }
            return c_name;
        } finally {
            close(st, rs);
        }
    }

    // add new enrollment
    public void addEnrollments(Enrollments theEnrollment) throws Exception {
        // initializing statement
        PreparedStatement st = null;

        try {
            // sql statement
            st = con.prepareStatement("insert into enrollments (en_year, Students_st_matr, Classes_idClass) values (?, ?, ?)");
            // set paramenters
            st.setInt(1, theEnrollment.getEn_year());
            st.setInt(2, theEnrollment.getStudents().getMatr());
            st.setString(3, theEnrollment.getClasses().getIdClass());

            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Enrollment added successfully.", "Enrollments manager", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    // update enrollments
    public void updateEnrollments(Enrollments theEnrollment) throws Exception {
        // initializing statements
        PreparedStatement st = null;

        try {
            // sql statements
            st = con.prepareStatement("update enrollments set en_year = ?, Students_st_matr = ?, Classes_idClass = ? where idEnrollment = ?");
            // setting parameters
            st.setInt(1, theEnrollment.getEn_year());
            st.setInt(2, theEnrollment.getStudents().getMatr());
            st.setString(3, theEnrollment.getClasses().getIdClass());
            st.setInt(4, theEnrollment.getIdEnrollment());

            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Enrollment " + theEnrollment.getIdEnrollment() + " updated successfully.", "Enrollments manager", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    // delete enrollments
    public void deleteEnrollments(int id) throws Exception {
        // initializing Statements
        PreparedStatement st = null;

        try {
            // sql statements
            st = con.prepareStatement("delete from enrollments where idEnrollment = ?");
            // setting parameter
            st.setInt(1, id);

            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Enrollment " + id + " deleted successfully.", "Enrollments manager", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    /* ---- Helper Methods ---- */

    public Enrollments rowToEnrollments(ResultSet rs) throws SQLException {
        int id = rs.getInt("idEnrollment");
        int year = rs.getInt("en_year");
        Students theStudent = new Students();
        theStudent.setMatr(rs.getInt("Students_st_matr"));
        Classes theClass = new Classes();
        theClass.setIdClass(rs.getString("Classes_idClass"));

        return new Enrollments(id, year, theStudent, theClass);
    }

    public void close(Statement st, ResultSet rs) {
        try {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception exc) {
            System.out.println();
        }
    }


    public static void main(String[] args) throws Exception {
        EnrollmentsDAO dao = new EnrollmentsDAO();
        //System.out.println(dao.searchEnrollmentsId(7));
        /* Students std = new Students();
        std.setMatr(5);
        Classes css = new Classes();
        css.setIdClass("2C");
        Enrollments en = new Enrollments(11, 2012, std, css);
        dao.updateEnrollments(en);; */

        System.out.println(dao.searchEnrollmentsJoinCourse(5));
    }
}