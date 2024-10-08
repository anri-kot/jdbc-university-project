package universityDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import universityCore.Students;

public class StudentsDAO {
    private Connection con;

    public StudentsDAO() throws Exception {
        // connection
        con = ConnectionManager.getConnection();
    }

    // ---- SQL OPERATIONS ----

    // Return all students
    public List<Students> getAllStudents() throws Exception {
        List<Students> list = new ArrayList<>();

        Statement st = null;
        ResultSet rs = null;

        // executing query
        try {
            st = con.createStatement();
            // sql statement
            rs = st.executeQuery("select * from students");

            while (rs.next()) {
                Students tempStudents = rowToStudent(rs);
                list.add(tempStudents);
            }
            return list;

        } finally {
            close(st, rs);
        }
    }

    // Return students by name seach
    public List<Students> searchStudents(String name) throws Exception {
        List<Students> list = new ArrayList<>();

        PreparedStatement st = null;
        ResultSet rs = null;

        // execute query
        try {
            // sql statement
            st = con.prepareStatement("select * from students where st_name like ?");

            st.setString(1, "%" + name + "%");
            rs = st.executeQuery();

            while (rs.next()) {
                Students tempStudents = rowToStudent(rs);
                list.add(tempStudents);
            }
            return list;

        } finally {
            close(st, rs);
        }
    }

    // Return students by id
    public List<Students> searchStudentsId(int matr) throws Exception {
        List<Students> list = new ArrayList<>();

        PreparedStatement st = null;
        ResultSet rs = null;

        // execute query
        try {
            // sql statement
            st = con.prepareStatement("select * from students where st_matr = ?");

            st.setInt(1, matr);
            rs = st.executeQuery();

            while (rs.next()) {
                Students tempStudents = rowToStudent(rs);
                list.add(tempStudents);
            }
            return list;

        } finally {
            close(st, rs);
        }
    }

    // return students with enrollments
    public List<Students> searchStudentsByClass(String idClass) throws Exception {
        List<Students> list = new ArrayList<>(); // will receive the rows

        // initializing statements and resultset
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // sql statement
            st = con.prepareStatement("select Students.* from Students join enrollments on enrollments.Students_st_matr = Students.st_matr " +
                "where Enrollments.Classes_idClass = ?");
            // params
            st.setString(1, idClass);
            
            rs = st.executeQuery();
            // loop to get all rows
            while (rs.next()) {
                Students tempStudents = rowToStudent(rs);
                list.add(tempStudents);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // return students with enrollments
    public List<Students> searchStudentsByNameAndClass(String name, String idClass) throws Exception {
        List<Students> list = new ArrayList<>(); // will receive the rows

        // initializing statements and resultset
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // sql statement
            st = con.prepareStatement("select * from Students join enrollments on enrollments.Students_st_matr = Students.st_matr " +
            "where Students.st_name like ? and Enrollments.Classes_idClass = ?");
            // params
            st.setString(1, "%" + name + "%");
            st.setString(2, idClass);
            
            rs = st.executeQuery();
            // loop to get all rows
            while (rs.next()) {
                Students tempStudents = rowToStudent(rs);
                list.add(tempStudents);
            }
            return list;
        } finally {
            close(st, rs);
        }
    }

    // Add new student
    public void addStudent(Students theStudents, int userid) throws Exception {
        // initializing Statement and ResultSet
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // sql statement
            st = con.prepareStatement(
                    "insert into students (st_name, st_address, st_phone, st_ssn) values (?, ?, ?, ?)");

            // set parameters
            st.setString(1, theStudents.getName());
            st.setString(2, theStudents.getAddress());
            st.setString(3, theStudents.getPhone());
            st.setString(4, theStudents.getSsn());

            // execute insert/update
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Student registered sucessfully.",
                    "Student registered", JOptionPane.INFORMATION_MESSAGE);
            
            /* ---- add to log ---- */

            int newStudentId = 0;

            // sql statement
            st = con.prepareStatement(
                    "select * from students where st_ssn = ?");
            st.setString(1, theStudents.getSsn());
            
            rs = st.executeQuery();
            // getting new result from the queue
            if (rs.next()) {
                Students tempStudents = rowToStudent(rs);
                newStudentId = tempStudents.getMatr();
            }
            StudentsLogDAO logDAO = new StudentsLogDAO();
            logDAO.addLog(userid, newStudentId, "Added new Student");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error",
                    "ERROR: " + e, JOptionPane.ERROR_MESSAGE);
        }
        st.close();
    }

    // Update student
    public void updateStudent(Students theStudents, int userid) throws Exception {
        PreparedStatement st = null;
        try {
            // sql statement
            st = con.prepareStatement(
                    "update students set st_name = ?, st_address = ?, st_phone = ?, st_ssn = ? where st_matr = ?");

            // set parameters
            st.setString(1, theStudents.getName());
            st.setString(2, theStudents.getAddress());
            st.setString(3, theStudents.getPhone());
            st.setString(4, theStudents.getSsn());
            st.setInt(5, theStudents.getMatr());

            // execute insert/update
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Student updated sucessfully.",
                    "Student updated", JOptionPane.INFORMATION_MESSAGE);

            // add to log
            StudentsLogDAO logDAO = new StudentsLogDAO();
            logDAO.addLog(userid, theStudents.getMatr(), "Updated Student");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error",
                    "ERROR: " + e, JOptionPane.ERROR_MESSAGE);
        }
        st.close();
    }

    // delete student
    public void deleteStudent(int matr) throws Exception {
        PreparedStatement st = null;
        try {
            // sql statement
            st = con.prepareStatement("delete from students where st_matr = ?");
            st.setInt(1, matr);

            // execute query
            st.executeUpdate();
            JOptionPane.showMessageDialog(null, "Student deleted sucessfully.",
                    "Student deleted", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e,
                    "ERROR: ", JOptionPane.ERROR_MESSAGE);
        }
        st.close();
    }

    // close statement helper method
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

    // convert row helper method
    private Students rowToStudent(ResultSet rs) throws SQLException {
        int matr = rs.getInt("st_matr");
        String name = rs.getString("st_name");
        String address = rs.getString("st_address");
        String phone = rs.getString("st_phone");
        String ssn = rs.getString("st_ssn");

        return new Students(matr, name, address, phone, ssn);
    }

    public static void main(String[] args) throws Exception {
        StudentsDAO dao = new StudentsDAO();
        System.out.println(dao.searchStudents("Luke"));

        System.out.println(dao.getAllStudents());
    }
}
