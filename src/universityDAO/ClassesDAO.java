package universityDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import universityCore.Classes;
import universityCore.Courses;

public class ClassesDAO {
    private Connection con;

    public ClassesDAO() throws Exception {
        // connection
        con = ConnectionManager.getConnection();
    }

    /* ---- SQL Operations ---- */

    // return all classes
    public List<Classes> getAllClasses() throws Exception {
        List<Classes> list = new ArrayList<>(); // will receive the list of rows from the object

        // initializing resultset and statements
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from classes");

            // loop to get all rows from the query
            while (rs.next()) {
                Classes tempClasses = rowToClasses(rs);
                list.add(tempClasses);
            }
            return list;
        } finally {
            close(st, rs);
        }

    }

    // return classes by id
    public List<Classes> searchClassesId(String id) throws Exception {
        List<Classes> list = new ArrayList<>(); // will receive the list of rows from the object

        // initializing resultset and statements
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement("select * from classes where idClass = ?"); // sql statement
            st.setString(1, id); // setting parameters

            rs = st.executeQuery();
            // loop to get all rows from the query
            while (rs.next()) {
                Classes tempClasses = rowToClasses(rs);
                list.add(tempClasses);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // add new classes
    public void addClass(Classes theClass) throws Exception {
        // initializing statements
        PreparedStatement st = null;

        try {
            // sql statement
            st = con.prepareStatement("insert into classes (idClass, semester, Courses_idCourse) values (?, ?, ?)");
            // setting parameters
            st.setString(1, theClass.getIdClass());
            st.setInt(2, theClass.getSemester());
            st.setInt(3, theClass.getCourses().getIdCourse());

            st.executeUpdate();
            System.out.println("Class " + theClass.getIdClass() + " added successfully");
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    // update classes
    public void updateClass(Classes theClass) throws Exception {
        // initializing Statements
        PreparedStatement st = null;

        try {
            // sql statements
            st = con.prepareStatement("update classes set idCourse = ?, semester = ?, Courses_idCourse = ? where idCourse = ?");
            // setting parameters
            st.setString(1, theClass.getIdClass());
            st.setInt(2, theClass.getSemester());
            st.setInt(3, theClass.getCourses().getIdCourse());

            st.executeUpdate();
            System.out.println("Class " + theClass.getIdClass() + " updated successfully.");
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    // delete classes
    public void deleteClass(Classes theClass) throws Exception {
        // initializing Statements
        PreparedStatement st = null;

        try {
            // sql statements
            st = con.prepareStatement("delete classes where idClass = ?");
            // setting parameters
            st.setString(1, theClass.getIdClass());

            st.executeUpdate();
            System.out.println("Class " + theClass + " deleted successfully");
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    /* ---- Helper Methods ---- */

    // helper method that converts rows into a Classes object
    private Classes rowToClasses(ResultSet rs) throws SQLException {
        String idClass = rs.getString("idClass");
        int semester = rs.getInt("semester");
        Courses course = new Courses();
        course.setIdCourse(rs.getInt("Courses_idCourse"));

        return new Classes(idClass, semester, course);
    }

    // helper method that closes Statements and ResultSets
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
        ClassesDAO dao = new ClassesDAO();
        System.out.println(dao.searchClassesId("2B"));
    }

}
