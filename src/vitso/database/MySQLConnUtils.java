package vitso.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class MySQLConnUtils {
 
public static Connection getMySQLConnection()
        throws ClassNotFoundException, SQLException {
  
    // Note: Change the connection parameters accordingly.
    String hostName2 = "192.168.1.110";
    String dbName = "HomeAutomation";
    String userName = "admin";
    String password = "vitso102796015";
    return getMySQLConnection(hostName2, dbName, userName, password);
}
 
public static Connection getMySQLConnection(String hostName, String dbName,
        String userName, String password) throws SQLException,
        ClassNotFoundException {
    
    // Declare the class Driver for MySQL DB
    // This is necessary with Java 5 (or older)
    // Java6 (or newer) automatically find the appropriate driver.
    // If you use Java> 5, then this line is not needed.
    Class.forName("com.mysql.jdbc.Driver");
 
 
    // URL Connection for MySQL
    // Example: jdbc:mysql://localhost:3306/simplehr
    String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
 
    Connection conn = DriverManager.getConnection(connectionURL, userName,
            password);
    conn.setAutoCommit(false);
    return conn;
}
}