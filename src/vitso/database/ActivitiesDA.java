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

import vitso.entities.*;
 
public class ActivitiesDA {
 

  public static List<Activity> queryActivitys(Connection conn) throws SQLException {
	  String sql = "Select * from v_activities";

      PreparedStatement pstm = conn.prepareStatement(sql);
 
      ResultSet rs = pstm.executeQuery();
      List<Activity> list = new ArrayList<Activity>();
      while (rs.next()) {
    	  int ID = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  Boolean notify =  rs.getBoolean("Notify");
    	  Activity activity = new Activity(ID, name, description, notify);
          list.add(activity);
      }
      return list;
  }
 
  public static Activity findActivity(Connection conn, int ID) throws SQLException {
      String sql = "Select * from v_activities where ID=?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setInt(1, ID);
 
      ResultSet rs = pstm.executeQuery();
 
      while (rs.next()) {
    	  int ID_2 = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  Boolean notify =  rs.getBoolean("Notify");
    	  Activity activity = new Activity(ID_2, name, description, notify);
          return activity;
      }
      return null;
  }
 
  public static Activity findActivityByName(Connection conn, String activityName) throws SQLException {
      String sql = "Select * from v_activities where name=?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setString(1, activityName);
 
      ResultSet rs = pstm.executeQuery();
 
      while (rs.next()) {
    	  int ID_2 = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  Boolean notify =  rs.getBoolean("Notify");
    	  Activity activity = new Activity(ID_2, name, description, notify);
          return activity;
      }
      return null;
  }
  public static void updateActivity(Connection conn, Activity activity) throws SQLException {
      try {
		String sql = "Update Activities set Name=?, Description=?, Notify=? where ID=? ";
		  PreparedStatement pstm = conn.prepareStatement(sql);
		  pstm.setString(1, activity.getName());
		  pstm.setString(2, activity.getDescription());
		  pstm.setBoolean(3, activity.getNotify());
		  pstm.setInt(4, activity.getID());
		  pstm.executeUpdate();
		  conn.commit();
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
 
  public static int insertActivity(Connection conn, Activity activity) throws SQLException {
      try {
    	  int last_inserted_id = 0;
    	  
    	  String sql = "Insert into Activities(Name, Description, Notify) values (?,?,?)";
 		  PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 		  pstm.setString(1, activity.getName());
 		  pstm.setString(2, activity.getDescription());
 		  pstm.setBoolean(3, activity.getNotify());
		  pstm.executeUpdate();
		  ResultSet rs = pstm.getGeneratedKeys();
		  if(rs.next())
		  {
			  last_inserted_id = rs.getInt(1);
		  }		  
		  conn.commit();
		  return last_inserted_id;
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0;
	}
  }
 
  public static void deleteActivity(Connection conn, int ID) throws SQLException {
      try {
    	  String sql = "Delete from Activities where ID= ?";
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
 
  
  
  
  
  
  
  public static List<ActivityTask> queryAllActivityTasks(Connection conn) throws SQLException {
	  String sql = "Select * from v_activitytasks";

      PreparedStatement pstm = conn.prepareStatement(sql);
 
      ResultSet rs = pstm.executeQuery();
      List<ActivityTask> list = new ArrayList<ActivityTask>();
      while (rs.next()) {
    	  int ID = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  int activityID = rs.getInt("ActivityID");
    	  String activityName = rs.getString("ActivityName");
    	  String activityDescription = rs.getString("ActivityDescription");
    	  ActivityTask activityTask = new ActivityTask(ID, name, description, activityID);
    	  activityTask.setActivityName(activityName);
    	  activityTask.setActivityDescription(activityDescription);
          list.add(activityTask);
      }
      return list;
  }
  
  
  public static List<ActivityTask> queryActivityTasks(Connection conn, int ActivityID) throws SQLException {
	  String sql = "Select * from v_activitytasks where ActivityID=?";

      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setInt(1, ActivityID);
 
      ResultSet rs = pstm.executeQuery();
      List<ActivityTask> list = new ArrayList<ActivityTask>();
      while (rs.next()) {
    	  int ID = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  int activityID = rs.getInt("ActivityID");
    	  String activityName = rs.getString("ActivityName");
    	  String activityDescription = rs.getString("ActivityDescription");
    	  ActivityTask activityTask = new ActivityTask(ID, name, description, activityID);
    	  activityTask.setActivityName(activityName);
    	  activityTask.setActivityDescription(activityDescription);
          list.add(activityTask);
      }
      return list;
  }
 
  public static ActivityTask findActivityTask(Connection conn, int ID) throws SQLException {
      String sql = "Select * from v_activitytasks where ID=?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setInt(1, ID);
 
      ResultSet rs = pstm.executeQuery();
 
      while (rs.next()) {
    	  int ID_2 = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  int activityID = rs.getInt("ActivityID");
    	  String activityName = rs.getString("ActivityName");
    	  String activityDescription = rs.getString("ActivityDescription");
    	  ActivityTask activityTask = new ActivityTask(ID_2, name, description, activityID);
    	  activityTask.setActivityName(activityName);
    	  activityTask.setActivityDescription(activityDescription);
    	  return activityTask;
      }
      return null;
  }
 
  public static void updateActivityTask(Connection conn, ActivityTask activityTask) throws SQLException {
      try {
		String sql = "Update ActivityTasks set Name=?, Description=?, ActivityID=? where ID=? ";
		  PreparedStatement pstm = conn.prepareStatement(sql);
		  pstm.setString(1, activityTask.getName());
		  pstm.setString(2, activityTask.getDescription());
		  pstm.setInt(3, activityTask.getActivityID());
		  pstm.setInt(4, activityTask.getID());
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
 
  public static int insertActivityTask(Connection conn, ActivityTask activityTask) throws SQLException {
      try {
    	  int last_inserted_id = 0;
    	  
    	  String sql = "Insert into ActivityTasks(Name, Description, ActivityID) values (?,?,?)";
 		  PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 		  pstm.setString(1, activityTask.getName());
		  pstm.setString(2, activityTask.getDescription());
 		  pstm.setInt(3,activityTask.getActivityID());
 		  pstm.executeUpdate();
		  ResultSet rs = pstm.getGeneratedKeys();
		  if(rs.next())
		  {
			  last_inserted_id = rs.getInt(1);
		  }		  
		  conn.commit();
		  return last_inserted_id;
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0;
	}
  }
 
  public static void deleteActivityTask(Connection conn, int ID) throws SQLException {
      try {
    	  String sql = "Delete from ActivityTasks where ID= ?";
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