package universityDAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    protected static ConnectionManager connect; // receives the connection

    // variables to assign properties to
    protected static String user;
    protected static String password;
    protected static String dburl;
    protected static String driver;

    // starts connection if not started
    public static ConnectionManager getInstance() {
        if (connect == null) {
            return connect = new ConnectionManager();
        } else {
            return connect;
        }

    }

    // effectively starts connection
    public static Connection getConnection()
            throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
        Properties prop = new Properties(); // receives properties from .properties file
        prop.load(new FileInputStream("prop.properties"));

        // assigning properties to variables
        user = prop.getProperty("user");
        password = prop.getProperty("password");
        dburl = prop.getProperty("dburl");
        driver = prop.getProperty("driver");

        Class.forName(driver);
        return DriverManager.getConnection(dburl, user, password);
    }

    public static void main(String[] args)
            throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
        Connection con;
        con = ConnectionManager.getConnection();
        System.out.println(con);
    }
}
