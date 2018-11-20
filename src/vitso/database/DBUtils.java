package vitso.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vitso.entities.MqttRelais;
import vitso.entities.Relais;
import vitso.entities.UserAccount;
import vitso.modules.*;
 
public class DBUtils {
 
  public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {
 
      String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a "
              + " where a.User_Name = ? and a.password= ?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setString(1, userName);
      pstm.setString(2, password);
      ResultSet rs = pstm.executeQuery();
 
      if (rs.next()) {
          String gender = rs.getString("Gender");
          UserAccount user = new UserAccount();
          user.setUserName(userName);
          user.setPassword(password);
          user.setGender(gender);
          return user;
      }
      return null;
  }
 
  public static UserAccount findUser(Connection conn, String userName) throws SQLException {
 
      String sql = "Select a.User_Name, a.Password, a.Gender from User_Account a " + " where a.User_Name = ? ";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setString(1, userName);
 
      ResultSet rs = pstm.executeQuery();
 
      if (rs.next()) {
          String password = rs.getString("Password");
          String gender = rs.getString("Gender");
          UserAccount user = new UserAccount();
          user.setUserName(userName);
          user.setPassword(password);
          user.setGender(gender);
          return user;
      }
      return null;
  }
 
  public static List<MqttRelais> queryModules(Connection conn) throws SQLException {
	  String sql = "Select * from v_mqttrelais";

      PreparedStatement pstm = conn.prepareStatement(sql);
 
      ResultSet rs = pstm.executeQuery();
      List<MqttRelais> list = new ArrayList<MqttRelais>();
      while (rs.next()) {
    	  int ID = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  int number = rs.getInt("Number");
    	  int port = rs.getInt("Port");
    	  MqttRelais mqtt = new MqttRelais(ID, name, number, port);
          list.add(mqtt);
      }
      return list;
  }
 
  public static MqttRelais findModule(Connection conn, int ID) throws SQLException {
      String sql = "Select * from v_mqttrelais where ID=?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setInt(1, ID);
 
      ResultSet rs = pstm.executeQuery();
 
      while (rs.next()) {
    	  int ID_2 = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  int number = rs.getInt("Number");
    	  int port = rs.getInt("Port");
    	  MqttRelais mqtt = new MqttRelais(ID_2, name, number, port);
    	  mqtt.setRelaisList(queryRelais(conn, ID_2));
          return mqtt;
      }
      return null;
  }
 
  public static MqttRelais findModuleByName(Connection conn, String moduleName) throws SQLException {
      String sql = "Select * from v_mqttrelais where name=?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setString(1, moduleName);
 
      ResultSet rs = pstm.executeQuery();
 
      while (rs.next()) {
    	  int ID_2 = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  int number = rs.getInt("Number");
    	  int port = rs.getInt("Port");
    	  MqttRelais mqtt = new MqttRelais(ID_2, name, number, port);
    	  mqtt.setRelaisList(queryRelais(conn, ID_2));
          return mqtt;
      }
      return null;
  }
  public static void updateModule(Connection conn, MqttRelais module) throws SQLException {
      try {
		String sql = "Update Modules set Name=?, Description=? where ID=? ";
		  PreparedStatement pstm = conn.prepareStatement(sql);
		  pstm.setString(1, module.getName());
		  pstm.setString(2, "");
		  pstm.setInt(3, module.getID());
		  pstm.executeUpdate();

		  sql = "Update MqttRelais set Name=?, Number=?, Port=? where ID=? ";
		  pstm = conn.prepareStatement(sql);
		  pstm.setString(1, module.getName());
		  pstm.setInt(2, module.getNumber());
		  pstm.setInt(3, module.getPort());
		  pstm.setInt(4, module.getID());
		  pstm.executeUpdate();
		  
		  conn.commit();
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
  }
 
  public static void insertModule(Connection conn, MqttRelais module) throws SQLException {
      try {
    	  int last_inserted_id = 0;
    	  
    	  String sql = "Insert into Modules(TypeID, Name) values (?,?)";
 		  PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 		  pstm.setInt(1,1);
		  pstm.setString(2, module.getName());
 		  pstm.executeUpdate();
		  ResultSet rs = pstm.getGeneratedKeys();
		  if(rs.next())
		  {
			  last_inserted_id = rs.getInt(1);
		  }
 		  sql = "Insert into MqttRelais(ID, Name, Number, Port) values (?,?,?,?)";
		  pstm = conn.prepareStatement(sql);
		  pstm.setInt(1, last_inserted_id);
		  pstm.setString(2, module.getName());
		  pstm.setInt(3, module.getNumber());
		  pstm.setInt(4, module.getPort());
		  pstm.executeUpdate();
		  
		  conn.commit();
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
 
  public static void deleteModule(Connection conn, int ID) throws SQLException {
      try {
    	  String sql = "Delete from Modules where ID= ?";
 
		  PreparedStatement pstm = conn.prepareStatement(sql);
 
		  pstm.setInt(1, ID);
 
		  pstm.executeUpdate();
		  
		  
    	  sql = "Delete from MqttRelais where ID= ?";
    	  
		  pstm = conn.prepareStatement(sql);
 
		  pstm.setInt(1, ID);
 
		  pstm.executeUpdate();	
		  
		  conn.commit();
		  
		  
		  
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      
      
      
      
      
      
      
  }
 
  
  
  
  
  
  
  public static List<Relais> queryAllRelais(Connection conn) throws SQLException {
	  String sql = "Select * from v_relais";

      PreparedStatement pstm = conn.prepareStatement(sql);
 
      ResultSet rs = pstm.executeQuery();
      List<Relais> list = new ArrayList<Relais>();
      while (rs.next()) {
    	  int ID = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  int moduleID = rs.getInt("ModuleID");
    	  int number = rs.getInt("Number");
    	  int port = rs.getInt("Port");
    	  String status = rs.getString("Status");
    	  String moduleName = rs.getString("ModuleName");
    	  String moduleDescription = rs.getString("ModuleDescription");
    	  Relais relais = new Relais(ID, name, description, moduleID, number, port, status);
    	  relais.setModuleName(moduleName);
    	  relais.setModuleDescription(moduleDescription);
          list.add(relais);
      }
      return list;
  }
  
  
  public static List<Relais> queryRelais(Connection conn, int ModuleID) throws SQLException {
	  String sql = "Select * from v_relais where ModuleID=?";

      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setInt(1, ModuleID);
 
      ResultSet rs = pstm.executeQuery();
      List<Relais> list = new ArrayList<Relais>();
      while (rs.next()) {
    	  int ID = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  int moduleID = rs.getInt("ModuleID");
    	  int number = rs.getInt("Number");
    	  int port = rs.getInt("Port");
    	  String status = rs.getString("Status");
    	  String moduleName = rs.getString("ModuleName");
    	  String moduleDescription = rs.getString("ModuleDescription");
    	  Relais relais = new Relais(ID, name, description, moduleID, number, port, status);
    	  relais.setModuleName(moduleName);
    	  relais.setModuleDescription(moduleDescription);
          list.add(relais);
      }
      return list;
  }
 
  public static Relais findRelais(Connection conn, int ID) throws SQLException {
      String sql = "Select * from v_relais where ID=?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setInt(1, ID);
 
      ResultSet rs = pstm.executeQuery();
 
      while (rs.next()) {
    	  int ID_2 = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  int moduleID = rs.getInt("ModuleID");
    	  int number = rs.getInt("Number");
    	  int port = rs.getInt("Port");
    	  String status = rs.getString("Status");
    	  String moduleName = rs.getString("ModuleName");
    	  String moduleDescription = rs.getString("ModuleDescription");
    	  Relais relais = new Relais(ID_2, name, description, moduleID, number, port, status);
    	  relais.setModuleName(moduleName);
    	  relais.setModuleDescription(moduleDescription);
    	  return relais;
      }
      return null;
  }
 
  public static void updateRelais(Connection conn, Relais relais) throws SQLException {
      try {
		String sql = "Update Relais set Name=?, Description=?, Number=?, Port=?, Status=? where ID=? ";
		  PreparedStatement pstm = conn.prepareStatement(sql);
		  pstm.setString(1, relais.getName());
		  pstm.setString(2, relais.getDescription());
		  pstm.setInt(3, relais.getNumber());
		  pstm.setInt(4, relais.getPort());
		  pstm.setString(5, relais.getStatus().toString());
		  pstm.setInt(6, relais.getID());
		  pstm.executeUpdate();
		  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
	        System.out.println(sdf.format(now).concat(pstm.toString()));
	        
		  conn.commit();
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
 
  public static void insertRelais(Connection conn, Relais relais) throws SQLException {
      try {
    	  int last_inserted_id = 0;
    	  
    	  String sql = "Insert into Relais(ModuleID, Name, Description, Number, Port, Status) values (?,?,?,?,?,?)";
 		  PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 		  pstm.setInt(1,relais.getModuleID());
		  pstm.setString(2, relais.getName());
		  pstm.setString(3, relais.getDescription());
 		  pstm.setInt(4,relais.getNumber());
 		  pstm.setInt(5,relais.getPort());
		  pstm.setString(6, relais.getStatus().toString());
 		  pstm.executeUpdate();
		  ResultSet rs = pstm.getGeneratedKeys();

		  conn.commit();
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
 
  public static void deleteRelais(Connection conn, int ID) throws SQLException {
      try {
    	  String sql = "Delete from Relais where ID= ?";
 
		  PreparedStatement pstm = conn.prepareStatement(sql);
 
		  pstm.setInt(1, ID);
 
		  pstm.executeUpdate();
		  

		  
		  conn.commit();
		  
		  
		  
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      
      
      
      
      
      
      
  }
 
}