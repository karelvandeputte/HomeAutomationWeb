/**
 * 
 */
package vitso.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import io.nayuki.json.Json;
import vitso.modules.control.mqtt_test;

import java.util.MissingResourceException;
import java.util.ResourceBundle;



/**
 * @author tn92285
 *
 */
public class MqttRelais {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1767712412382634693L;
	
	private int ID;
	
	private String name = "";
	private String namePrevious = "";
    private int number = 0;
    private int port = 0;
    
    List<Relais> relaisList = null;
    


    /**
	 * @param name
	 */
	public MqttRelais() {
		super();
	}
	
	public MqttRelais(String name) {
		super();
		this.name = name;
	}

	public MqttRelais(int ID, String name, int number, int port) {
		super();
		this.ID = ID;
		this.name = name;
		this.number = number;
		this.port = port;
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
	
	
	public String getNamePrevious() {
		return namePrevious;
	}
	public void setNamePrevious(String namePrevious) {
		this.namePrevious = namePrevious;
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
	
	public int getNumberOfRelais() {
		return relaisList.size();
	}

	
	
	
	public List<Relais> getRelaisList() {
		return relaisList;
	}

	public void setRelaisList(List<Relais> relaisList) {
		this.relaisList = relaisList;
	}

	public String reprString() {
		String repr = String.format("I am MQTT relais module %s ", this.name);
		repr = repr +  (char) 13;
		for (int i=0;i<relaisList.size();i++) {
			repr = repr + (char) 11 + getRelaisList().get(i).reprString() + (char) 13;
		}
		return repr;
		
		
	}
	
	
	/*
	public Map<String, Object> reprJSON() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", this.name);
		map.put("relais1", relais1.reprJSON());
		map.put("relais2", relais2.reprJSON());
		map.put("relais3", relais3.reprJSON());
		map.put("relais4", relais4.reprJSON());
		map.put("relais5", relais5.reprJSON());
		map.put("relais6", relais6.reprJSON());
		map.put("relais7", relais7.reprJSON());
		map.put("relais8", relais8.reprJSON());
		map.put("relais9", relais9.reprJSON());
		map.put("relais10", relais10.reprJSON());
		map.put("relais11", relais11.reprJSON());
		map.put("relais12", relais12.reprJSON());
		return map;
		
	}
	
	*/
    public Boolean toJSON() {
    	
        try {
            PrintWriter writer = new PrintWriter(this.name + ".json", "UTF-8");
            JSONSerializer serializer = new JSONSerializer();
            writer.println(serializer.serialize(this));
            writer.close();
        	System.out.println(String.format("Serialized the instance: %s", this.name));
        	return true;
        } catch (Exception e) {
        	System.out.println(String.format("Error in serializing the instance of this class: %s", this.name));
            e.printStackTrace();
            return false;
        }
    }

	
    public MqttRelais fromJSON() {
    	
        try {
        	BufferedReader br = new BufferedReader(new FileReader(this.name + ".json"));
        	
    	    StringBuilder sb = new StringBuilder();
    	    String line = br.readLine();

    	    while (line != null) {
    	        sb.append(line);
    	        sb.append(System.lineSeparator());
    	        line = br.readLine();
    	    }
    	    String everything = sb.toString();


  	    
    	    MqttRelais test = new JSONDeserializer<MqttRelais>().deserialize(everything);
            
            
        	return (MqttRelais) test;
        } catch (Exception e) {

        	System.out.println(String.format("Error in deserializing the instance of this class: %s", this.name));
            e.printStackTrace();
        	
            return null;
        }
    }

/*
    def fromJSON(self, moduleName):
        try:
            with open('%s.json' % moduleName,'r') as f:
                print("Deserializing the instance: %s" % moduleName)
                self.jObj = json.load(f, object_hook=decode_object)
                self.name = self.jObj["name"]
                self.relais1 = self.jObj["relais1"]["__relais__"]
                self.relais2 = self.jObj["relais2"]["__relais__"]
                self.relais3 = self.jObj["relais3"]["__relais__"]
                self.relais4 = self.jObj["relais4"]["__relais__"]


                
        except:
            print("Error in deserializing the instance of this class: %s" % moduleName)
            print(sys.exc_info())

*/
    
    
    
}


/*
    jObj = ""


    @relais12.setter
    def relais12(self, vals):
        if isinstance(vals,dict):
            self._relais12.name = vals["name"]
            self._relais12.number = vals["number"]
            self._relais12.port = vals["port"]
        else:
            name,number,port = vals
            self._relais12.name = name
            self._relais12.number = number
            self._relais12.port = port








    def toJSON(self):
        try:
            with open('%s.json' % self.name, 'w') as f:
                f.write(json.dumps(self.reprJSON(), indent=2, cls=CustomEncoder))
                f.close()

            print("Serialized the instance: %s" % self.name)

        except:
            print("Error in serializing the instance of this class: %s" % self.name)
            print(sys.exc_info())

    def fromJSON(self, moduleName):
        try:
            with open('%s.json' % moduleName,'r') as f:
                print("Deserializing the instance: %s" % moduleName)
                self.jObj = json.load(f, object_hook=decode_object)
                self.name = self.jObj["name"]
                self.relais1 = self.jObj["relais1"]["__relais__"]
                self.relais2 = self.jObj["relais2"]["__relais__"]
                self.relais3 = self.jObj["relais3"]["__relais__"]
                self.relais4 = self.jObj["relais4"]["__relais__"]


                
        except:
            print("Error in deserializing the instance of this class: %s" % moduleName)
            print(sys.exc_info())


*/