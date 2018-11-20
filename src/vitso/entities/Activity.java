package vitso.entities;

/**
 * @author vitso
 *
 */
public class Activity {

	private int ID;
	private String name;
	private String description;
	private Boolean notify;

	public Activity() {
		super();
	}

	public Activity(int ID, String name, String description, Boolean notify) {
		super();
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.notify = notify;
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
	
	public Boolean getNotify() {
		return notify;
	}
	public void setNotify(Boolean notify) {
		this.notify = notify;
	}
	
}
