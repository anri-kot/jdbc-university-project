package universityDAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    protected static ConnectionManager connect;

    public static ConnectionManager getInstance() {

        if (connect == null) {

            return connect = new ConnectionManager();

        } else {

            return connect;

        }

    }

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        // get db properties
        Properties prop = new Properties();
        prop.load(new FileInputStream("prop.properties"));

        String user = prop.getProperty("user");
        String password = prop.getProperty("password");
        String dburl = prop.getProperty("dburl");
        String driver = prop.getProperty("driver");

        Class.forName(driver);

        return DriverManager.getConnection(dburl, user, password);

    }

    public static void main(String[] args)
            throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
        System.out.println(ConnectionManager.getConnection());
    }
}
