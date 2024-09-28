package universityDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import universityCore.Courses;

public class CoursesDAO {
    Connection con;

    public CoursesDAO() throws Exception {
        // connection
        con = ConnectionManager.getConnection();
    }

    // ---- sql operations ----

    // get all courses
    public List<Courses> getAllCourses() throws Exception {
        List<Courses> list = new ArrayList<>();

        Statement st = null;
        ResultSet rs = null;

        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from courses");

            while (rs.next()) { // loop to get all rows from the db
                Courses tempCourses = rowToCourses(rs);
                list.add(tempCourses);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // get course by name
    public List<Courses> searchCourses(String name) throws Exception {
        List<Courses> list = new ArrayList<>();

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement("select * from courses where c_name like ?");
            st.setString(1, "%" + name + "%");
            rs = st.executeQuery();

            while (rs.next()) {
                Courses tempCourses = rowToCourses(rs);
                list.add(tempCourses);
            }
            return list;
        } finally {
            close(st, rs);
        }
    
    }
    // get course by exact name
    public Courses searchCoursesName(String name) throws Exception {
        // Courses object
        Courses tempCourses = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement("select * from courses where c_name = ?");
            st.setString(1,name);
            rs = st.executeQuery();

            if (rs.next()) {
                tempCourses = rowToCourses(rs);
            }
            return tempCourses;
        } finally {
            close(st, rs);
        }
    }

    // get course by id
    public List<Courses> searchCoursesId(int id) throws Exception {
        List<Courses> list = new ArrayList<>();

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement("select * from courses where idCourse = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            while (rs.next()) {
                Courses tempCourses = rowToCourses(rs);
                list.add(tempCourses);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // add new course
    public void addCourse(Courses theCourse) throws Exception {
        PreparedStatement st = null;

        try {
            st = con.prepareStatement("insert into courses (c_name, c_duration) values (?, ?)");

            st.setString(1, theCourse.getC_name());
            st.setInt(2, theCourse.getC_duration());
            st.executeUpdate();

            JOptionPane.showMessageDialog(null, "Course added sucessfully.",
                    "Course added", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            st.close();
        }
    }

    // update course
    public void updateCourse(Courses theCourse) throws Exception {
        PreparedStatement st = null;

        try {
            st = con.prepareStatement("update courses set c_name = ?, c_duration = ? where idCourse = ?");

            st.setString(1, theCourse.getC_name());
            st.setInt(2, theCourse.getC_duration());
            st.setInt(3, theCourse.getIdCourse());
            st.executeUpdate();

            JOptionPane.showMessageDialog(null, "Course updated sucessfully.",
                    "Course updated", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exc) {
            System.out.println(exc);
        }
        st.close();
    }

    // delete course
    public void deleteCourse(int id) throws Exception {
        PreparedStatement st = null;

        try {
            st = con.prepareStatement("delete from courses where idCourse = ?");

            st.setInt(1, id);
            st.executeUpdate();

            JOptionPane.showMessageDialog(null, "Course deleted sucessfully.",
                    "Course deleted", JOptionPane.INFORMATION_MESSAGE);
        } finally {
            st.close();
        }
    }

    // close helper method
    private void close(Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // helper method to convert rows into course objects
    private Courses rowToCourses(ResultSet rs) throws SQLException {
        // getting values from rows
        int idCourse = rs.getInt("idCourse");
        String c_name = rs.getString("c_name");
        int c_duration = rs.getInt("c_duration");

        return new Courses(idCourse, c_name, c_duration); // returs course object
    }
}
