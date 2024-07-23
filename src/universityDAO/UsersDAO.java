package universityDAO;

import java.sql.*;
import java.util.*;
import universityCore.Users;

public class UsersDAO {
    private Connection con;

    public UsersDAO() throws Exception {
        // establish connection
        con = ConnectionManager.getConnection();
    }

    /* ---- SQL Operations ---- */

    // get all users
    public List<Users> getAllUsers() throws Exception {
        List<Users> list = new ArrayList<>(); // list to receive Users object

        // initialize statements and result set
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from users");

            // loop to get all users objects into the list
            while (rs.next()) {
                Users tempUsers = rowToUsers(rs);
                list.add(tempUsers);
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

    public int searchUserId(String username) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        int iduser = 0;

        try {
            st = con.prepareStatement("select * from users where username = ?");
            st.setString(1, username);
            rs = st.executeQuery();
            if(rs.next()) {
                Users tempUsers = rowToUsers(rs);
                iduser = tempUsers.getId();
            }
            return iduser;
        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    // get username by id
    public String getUsername(int id) throws Exception {

        // initialize statements and resultset
        PreparedStatement st = null;
        ResultSet rs = null;
        String username = "none";
        
        try {
            // sql statement
            st = con.prepareStatement("select users.username from users where idUser = ?");
            // parameter
            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()) {
                username = rs.getString("username");
            }
            return username;
        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    // get username by id
    public String getU_name(int id) throws Exception {

        // initialize statements and resultset
        PreparedStatement st = null;
        ResultSet rs = null;
        String username = "none";
        
        try {
            // sql statement
            st = con.prepareStatement("select users.u_name from users where idUser = ?");
            // parameter
            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()) {
                username = rs.getString("u_name");
            }
            return username;
        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    // get user password by id
    public String getUserpassword(int id) throws Exception {
        // initialize statements and resultset
        PreparedStatement st = null;
        ResultSet rs = null;
        String password = "";

        try {
            // sql statement
            st = con.prepareStatement("select * from users where idUser = ?");
            // parameter
            st.setInt(1, id);

            rs = st.executeQuery();
            while (rs.next()) {
                Users tempUsers = rowToUsers(rs);
                password = tempUsers.getPassword();
            }
            return password;
        } finally {
            if (st != null) {
                st.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    // add new user
    public void addUser(Users theUser) throws Exception {
        // initializing statements and resultset
        PreparedStatement st = null;
        try {
            // sql statement
            st = con.prepareStatement("insert into users (username, password, email, u_name, admin) values (?, ?, ?, ?, ?)");
            // set parameters
            st.setString(1, theUser.getUsername());
            st.setString(2, theUser.getPassword());
            st.setString(3, theUser.getEmail());
            st.setString(4, theUser.getU_name());
            st.setBoolean(5, theUser.isAdmin());

            st.executeUpdate();
            System.out.println("User " + theUser.getU_name() + " added successfully.");
            st.close();
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    // delete user
    public void deleteUser(int id) throws Exception {
        // initializing statement
        PreparedStatement st = null;

        try {
            // sql statement
            st = con.prepareStatement("delete from users where idUser = ?");
            st.setInt(1, id);

            st.executeUpdate();
            System.out.println("User '" + id + "' deleted successfully.");
            st.close();
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    // helper method to convert rows into Users objects
    private Users rowToUsers(ResultSet rs) throws SQLException {
        // assigning row values into variables
        int id = rs.getInt("iduser");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String u_name = rs.getString("u_name");
        boolean admin = rs.getBoolean("admin");

        return new Users(id, username, password, email, u_name, admin);
    }

    public static void main(String[] args) throws Exception {
        UsersDAO dao = new UsersDAO();
        //Users users = new Users(0, "usertest", "123", "usertest@email.com", "User Test", false);
        //dao.addUser(users);
        //dao.deleteUser(4);
        System.out.println(dao.getUserpassword(2));
    }
}
