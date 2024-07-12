package universityDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import universityCore.Students;

public class StudentsDAO {
    private Connection con;

    public StudentsDAO() throws Exception {

        // connection
        con = ConnectionManager.getConnection();
        System.out.println("Successfully connected to the database");
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

    // Add new student
    public void AddStudent(Students theStudents) throws Exception {
        PreparedStatement st = null;
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
        } catch (Exception e) {
            System.out.println(e);
            st.close();
        }
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
