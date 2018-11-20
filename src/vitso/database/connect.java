package vitso.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class connect {

	
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    final private String host = "192.168.0.240";
    final private String user = "admin";
    final private String passwd = "vitso102796015";
    final private String database = "HomeAutomation";
    
    
    public Connection connectToDB() throws Exception {
        try {
                // This will load the MySQL driver, each DB has its own driver
                Class.forName("com.mysql.jdbc.Driver");

                // Setup the connection with the DB
                return DriverManager.getConnection("jdbc:mysql://" + host + "/"
                                + database + "?" + "user=" + user + "&password=" + passwd);

        } catch (Exception e) {
                throw e;
        }
	}



	// You need to close the resultSet
	public void close(Connection con,Statement stat,ResultSet rs) {
	        try {
	                if (rs != null) {
	                	rs.close();
	                }
	
	                if (stat != null) {
	                	stat.close();
	                }
	
	                if (con != null) {
	                	con.close();
	                }
	        } catch (Exception e) {
	
	        }
	}
	
	
}
