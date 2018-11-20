package vitso.entities;


/**
 * @author vitso
 *
 */
public class Group {

	private int ID;
	private String name;
	private String description;

	public Group() {
		super();
	}

	public Group(int ID, String name, String description) {
		super();
		this.ID = ID;
		this.name = name;
		this.description = description;
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
	
    
}
