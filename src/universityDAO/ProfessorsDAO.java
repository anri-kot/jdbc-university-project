package universityDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import universityCore.Courses;
import universityCore.Professors;

public class ProfessorsDAO {
    private Connection con;

    public ProfessorsDAO() throws Exception {
        // connection
        con = ConnectionManager.getConnection();
    }

    /* ---- SQL Operations ---- */
    
    // returns all professors
    public List<Professors> getAllProfessors() throws Exception {
        List<Professors> list = new ArrayList<>(); // receives Professors object

        // initializing statements and resultset
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from professors"); // sql query

            // loop to get all rows from db
            while (rs.next()) {
                Professors tempProfessors = rowToProfessors(rs);
                list.add(tempProfessors);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // search professors by id
    public List<Professors> searchProfessorsId(int id) throws Exception {
        List<Professors> list = new ArrayList<>(); // will receibe the professors object

        // initializing statements and resultset
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement("select * from professors where pr_matr = ?"); // sql statement
            // setting parameters
            st.setInt(1, id);

            rs = st.executeQuery();
            // loop to get all rows from the db
            while (rs.next()) {
                Professors tempProfessors = rowToProfessors(rs);
                list.add(tempProfessors);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // add new professor
    public void addProfessors(Professors theProfessors) throws Exception {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("insert into professors (pr_name, pr_address, pr_phone, pr_ssn, salary, Courses_idCourse) values (?, ?, ?, ?, ?, ?)");

            st.setString(1, theProfessors.getName());
            st.setString(2, theProfessors.getAddress());
            st.setString(3, theProfessors.getPhone());
            st.setString(4, theProfessors.getSsn());
            st.setDouble(5, theProfessors.getSalary());
            st.setInt(6, theProfessors.getCourse().getIdCourse());

            st.executeUpdate(); // execute insert/update

            System.out.println("professor added successfully");
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    // update professors
    public void updateProfessors(Professors theProfessor) throws Exception {
        // initializing statements
        PreparedStatement st = null;

        try { 
            // sql statements
            st = con.prepareStatement("update professors set pr_name = ?, pr_address = ?, pr_phone = ?, pr_ssn = ?, salary = ?, Courses_idCourse = ?" +
                " where pr_matr = ?");
            st.setString(1, theProfessor.getName());
            st.setString(2, theProfessor.getAddress());
            st.setString(3, theProfessor.getPhone());
            st.setString(4, theProfessor.getSsn());
            st.setDouble(5, theProfessor.getSalary());
            st.setInt(6, theProfessor.getCourse().getIdCourse());
            st.setInt(7, theProfessor.getMatr());

            st.executeUpdate();
            System.out.println("Professor " + theProfessor.getName() + " updated successfully.");
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    // delete professors
    public void deleteProfessors(int id) throws Exception {
        // initializing statement
        PreparedStatement st = null;

        try {
            // sql statement
            st = con.prepareStatement("delete professors where pr_matr = ?");
            st.setInt(1, id); // parameters

            st.executeUpdate();
            System.out.println("Professor " + id + " deleted successfully.");
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    /* ---- Helper Methods */

    // helper method to convert rows into professor objects
    private Professors rowToProfessors(ResultSet rs) throws SQLException {
        int matr = rs.getInt("pr_matr");
        String name = rs.getString("pr_name");
        String address = rs.getString("pr_address");
        String phone = rs.getString("pr_phone");
        String ssn = rs.getString("pr_ssn");
        double salary = rs.getDouble("salary");
        int courseId = rs.getInt("Courses_idCourse");

        Courses tempCourse = new Courses();
        tempCourse.setIdCourse(courseId);

        return new Professors(matr, name, address, phone, ssn, salary, tempCourse);

    }

    // helper method for closing ResultSet and Statements
    private void close(Statement st, ResultSet rs) {
        try {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception exc) {
            System.out.println(exc);
        }

    }

    public static void main(String[] args) throws Exception {
        ProfessorsDAO p = new ProfessorsDAO();
        Courses c = new Courses();
        c.setIdCourse(3);
        p.updateProfessors(new Professors(4, "Ed Sheeran", "Canada", "77885599441", "77445522115", 5150.50, c));
        System.out.println(p.getAllProfessors());
    }
}
