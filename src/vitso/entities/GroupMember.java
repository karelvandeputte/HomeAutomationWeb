package vitso.entities;

/**
 * @author vitso
 *
 */
public class GroupMember {

	private int ID;
	private String name;
	private String description;
	private int groupID;
	private int relaisID;

	private String groupName = "";
	private String groupDescription = "";

	private String relaisName = "";
	private String relaisDescription = "";


	public GroupMember() {
		super();
	}

	public GroupMember(int ID, String name, String description, int groupID, int relaisID) {
		super();
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.groupID = groupID;
		this.relaisID = relaisID;
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
	
	public int getGroupID() {
		return groupID;
	}
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
	public int getRelaisID() {
		return relaisID;
	}
	public void setRelaisID(int relaisID) {
		this.relaisID = relaisID;
	}
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDescription() {
		return groupDescription;
	}
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getRelaisName() {
		return relaisName;
	}
	public void setRelaisName(String relaisName) {
		this.relaisName = relaisName;
	}
	public String getRelaisDescription() {
		return relaisDescription;
	}
	public void setRelaisDescription(String relaisDescription) {
		this.relaisDescription = relaisDescription;
	}

    
}
