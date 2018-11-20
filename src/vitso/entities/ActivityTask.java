/**
 * 
 */
package vitso.entities;

import java.util.HashMap;
import java.util.Map;

import vitso.modules.Messages;

/**
 * @author vitso
 *
 */
public class ActivityTask {

	private int ID;
	private String name;
	private String description;
	private int activityID;

	private String activityName= "";
	private String activityDescription= "";

	public ActivityTask() {
		super();
	}
	
	public ActivityTask(int ID, String name, String description, int activityID) {
		super();
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.activityID = activityID;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getActivityID() {
		return activityID;
	}
	public void setActivityID(int activityID) {
		this.activityID = activityID;
	}

	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getActivityDescription() {
		return activityDescription;
	}
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
    
}
