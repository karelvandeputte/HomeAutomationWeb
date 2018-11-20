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
 
public class GroupsDA {
 

  public static List<Group> queryGroups(Connection conn) throws SQLException {
	  String sql = "Select * from v_groups";

      PreparedStatement pstm = conn.prepareStatement(sql);
 
      ResultSet rs = pstm.executeQuery();
      List<Group> list = new ArrayList<Group>();
      while (rs.next()) {
    	  int ID = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  Group group = new Group(ID, name, description);
          list.add(group);
      }
      return list;
  }
 
  public static Group findGroup(Connection conn, int ID) throws SQLException {
      String sql = "Select * from v_groups where ID=?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setInt(1, ID);
 
      ResultSet rs = pstm.executeQuery();
 
      while (rs.next()) {
    	  int ID_2 = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  Group group = new Group(ID_2, name, description);
          return group;
      }
      return null;
  }
 
  public static Group findGroupByName(Connection conn, String groupName) throws SQLException {
      String sql = "Select * from v_groups where name=?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setString(1, groupName);
 
      ResultSet rs = pstm.executeQuery();
 
      while (rs.next()) {
    	  int ID_2 = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  Group group = new Group(ID_2, name, description);
          return group;
      }
      return null;
  }
  public static void updateGroup(Connection conn, Group group) throws SQLException {
      try {
		String sql = "Update Groups set Name=?, Description=? where ID=? ";
		  PreparedStatement pstm = conn.prepareStatement(sql);
		  pstm.setString(1, group.getName());
		  pstm.setString(2, "");
		  pstm.setInt(3, group.getID());
		  pstm.executeUpdate();
		  conn.commit();
	} catch (Exception e) {
		conn.rollback();
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
 
  public static int insertGroup(Connection conn, Group group) throws SQLException {
      try {
    	  int last_inserted_id = 0;
    	  
    	  String sql = "Insert into Groups(Name, Description) values (?,?)";
 		  PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 		  pstm.setString(1, group.getName());
 		  pstm.setString(2, group.getDescription());
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
 
  public static void deleteGroup(Connection conn, int ID) throws SQLException {
      try {
    	  String sql = "Delete from Groups where ID= ?";
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
 
  
  
  
  
  
  
  public static List<GroupMember> queryAllGroupMembers(Connection conn) throws SQLException {
	  String sql = "Select * from v_groupmembers";

      PreparedStatement pstm = conn.prepareStatement(sql);
 
      ResultSet rs = pstm.executeQuery();
      List<GroupMember> list = new ArrayList<GroupMember>();
      while (rs.next()) {
    	  int ID = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  int groupID = rs.getInt("GroupID");
    	  String groupName = rs.getString("GroupName");
    	  String groupDescription = rs.getString("GroupDescription");
    	  int relaisID = rs.getInt("RelaisID");
    	  String relaisName = rs.getString("RelaisName");
    	  String relaisDescription = rs.getString("RelaisDescription");
    	  GroupMember groupMember = new GroupMember(ID, name, description, groupID, relaisID);
    	  groupMember.setGroupName(groupName);
    	  groupMember.setGroupDescription(groupDescription);
    	  groupMember.setRelaisName(relaisName);
    	  groupMember.setRelaisDescription(relaisDescription);
          list.add(groupMember);
      }
      return list;
  }
  
  
  public static List<GroupMember> queryGroupMembers(Connection conn, int GroupID) throws SQLException {
	  String sql = "Select * from v_groupmembers where GroupID=?";

      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setInt(1, GroupID);
 
      ResultSet rs = pstm.executeQuery();
      List<GroupMember> list = new ArrayList<GroupMember>();
      while (rs.next()) {
    	  int ID = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  int groupID = rs.getInt("GroupID");
    	  String groupName = rs.getString("GroupName");
    	  String groupDescription = rs.getString("GroupDescription");
    	  int relaisID = rs.getInt("RelaisID");
    	  String relaisName = rs.getString("RelaisName");
    	  String relaisDescription = rs.getString("RelaisDescription");
    	  GroupMember groupMember = new GroupMember(ID, name, description, groupID, relaisID);
    	  groupMember.setGroupName(groupName);
    	  groupMember.setGroupDescription(groupDescription);
    	  groupMember.setRelaisName(relaisName);
    	  groupMember.setRelaisDescription(relaisDescription);
          list.add(groupMember);
      }
      return list;
  }
 
  public static GroupMember findGroupMember(Connection conn, int ID) throws SQLException {
      String sql = "Select * from v_groupmembers where ID=?";
 
      PreparedStatement pstm = conn.prepareStatement(sql);
      pstm.setInt(1, ID);
 
      ResultSet rs = pstm.executeQuery();
 
      while (rs.next()) {
    	  int ID_2 = rs.getInt("ID");
    	  String name = rs.getString("Name");
    	  String description = rs.getString("Description");
    	  int groupID = rs.getInt("GroupID");
    	  String groupName = rs.getString("GroupName");
    	  String groupDescription = rs.getString("GroupDescription");
    	  int relaisID = rs.getInt("RelaisID");
    	  String relaisName = rs.getString("RelaisName");
    	  String relaisDescription = rs.getString("RelaisDescription");
    	  GroupMember groupMember = new GroupMember(ID_2, name, description, groupID, relaisID);
    	  groupMember.setGroupName(groupName);
    	  groupMember.setGroupDescription(groupDescription);
    	  groupMember.setRelaisName(relaisName);
    	  groupMember.setRelaisDescription(relaisDescription);
    	  return groupMember;
      }
      return null;
  }
 
  public static void updateGroupMember(Connection conn, GroupMember groupMember) throws SQLException {
      try {
		String sql = "Update GroupMembers set Name=?, Description=?, GroupID=?, RelaisID=? where ID=? ";
		  PreparedStatement pstm = conn.prepareStatement(sql);
		  pstm.setString(1, groupMember.getName());
		  pstm.setString(2, groupMember.getDescription());
		  pstm.setInt(3, groupMember.getGroupID());
		  pstm.setInt(4, groupMember.getRelaisID());
		  pstm.setInt(5, groupMember.getID());
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
 
  public static int insertGroupMember(Connection conn, GroupMember groupMember) throws SQLException {
      try {
    	  int last_inserted_id = 0;
    	  
    	  String sql = "Insert into GroupMembers(Name, Description, GroupID, RelaisID) values (?,?,?,?)";
 		  PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 		  pstm.setString(1, groupMember.getName());
		  pstm.setString(2, groupMember.getDescription());
 		  pstm.setInt(3,groupMember.getGroupID());
 		  pstm.setInt(4,groupMember.getRelaisID());
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
 
  public static void deleteGroupMember(Connection conn, int ID) throws SQLException {
      try {
    	  String sql = "Delete from GroupMembers where ID= ?";
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