/**
 * 
 */
package vitso.entities;

import java.util.HashMap;
import java.util.Map;


/**
 * @author vitso
 *
 */
public class Relais {

	private int ID;
	private String name;
	private String description;
	private int moduleID;
	private int number;
    private int port;

	private String moduleName= "";
	private String moduleDescription= "";

	public enum relaisStatus {
        ON,
        OFF,
        LEFT,
        RIGHT,
    	UP,
    	DOWN
    }
    
    private relaisStatus status = relaisStatus.OFF;
    
    /**
	 * @param name
	 * @param number
	 * @param port
	 */
	public Relais() {
		super();
	}
	public Relais(int ID, String name, int number, int port) {
		super();
		this.ID = ID;
		this.name = name;
		this.number = number;
		this.port = port;
	}
	
	public Relais(int ID, String name, String description, int moduleID, int number, int port, String status) {
		super();
		this.ID = ID;
		this.name = name;
		this.description = description;
		this.moduleID = moduleID;
		this.number = number;
		this.port = port;
		this.setStatus(status);
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
	
	public int getModuleID() {
		return moduleID;
	}
	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
		
	public relaisStatus getStatus() {
		return status;
	}
	public void setStatus(String status) {
		switch (status) {
		case "0":	this.status = relaisStatus.OFF;
					break; 
		
		case "1":	this.status = relaisStatus.ON;
		break; 

		case "OFF":	this.status = relaisStatus.OFF;
		break; 

		case "ON":	this.status = relaisStatus.ON;
		break; 

		default:	this.status = relaisStatus.OFF;
					break; 
		}

	}
		
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleDescription() {
		return moduleDescription;
	}
	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}
	
	
	
	
	public String reprString() {
		return String.format("I am %s known as number %s at port %s", this.name, this.number, this.port);
	}
	
	public Map<String, String> reprJSON() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", this.name);
		map.put("number", String.valueOf(this.number));
		map.put("port", String.valueOf(this.port));
		return map;
		
	}
    
}
