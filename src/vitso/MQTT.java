package vitso;


import java.io.BufferedReader;
import java.io.FileReader;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import flexjson.JSONDeserializer;
import io.nayuki.json.Json;
import vitso.entities.MqttRelais;
import vitso.json.JSONStringFactory;
import vitso.modules.control.MqttRelaisControl;

public class MQTT implements MqttCallback {

public MqttClient client;
MqttRelaisControl mqttrelaiscontrol = new MqttRelaisControl();


public MQTT() {
}

public static void main(String[] args) {
    new MQTT().listParams();
}

public void listParams() {
    try {
    	if (client == null) {
    		client = new MqttClient("tcp://192.168.0.240:1883", "Sending");
    		MqttConnectOptions clientOptions = new MqttConnectOptions();
    		clientOptions.setMaxInflight(100);
    		client.connect(clientOptions);
    		client.setCallback(this);
    		client.subscribe("home/params");   		
    		
    		
    	}
        
    } catch (MqttException e) {
        e.printStackTrace();
    }

    
    
}

public void doDemo() {
    try {
        client = new MqttClient("tcp://192.168.0.240:1883", "Sending");
        client.connect();
        client.setCallback(this);
        client.subscribe("home/test");
        MqttMessage message = new MqttMessage();
        message.setPayload("A single message from my computer fff"
                .getBytes());
        client.publish("home/test", message);
    } catch (MqttException e) {
        e.printStackTrace();
    }
    
    
    MqttRelais mqtt = new MqttRelais("test module");
    System.out.println("mqtt voorstelling: " + mqtt.reprString());
    
    mqtt.toJSON();
    
    
    try {
    	MqttRelais test = new MqttRelais("test module");
    	
    	BufferedReader br = new BufferedReader(new FileReader("test module" + ".json"));
    	
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();

	    while (line != null) {
	        sb.append(line);
	        sb.append(System.lineSeparator());
	        line = br.readLine();
	    }
	    String everything = sb.toString();

	    MqttRelais temp = new MqttRelais("test module");
	    //temp.fromJSON();
	    
	    MqttRelais tempMqtt = new JSONDeserializer<MqttRelais>().deserialize(everything);    

	    /*
	    MqttRelais tempMqtt = new JSONDeserializer<MqttRelais>()
	    		.use("class", new JSONStringFactory())
	    		.deserialize(everything, MqttRelais.class);
        
*/

    } catch (Exception e) {
        e.printStackTrace();
    }
    
    
}

@Override
public void connectionLost(Throwable cause) {
    // TODO Auto-generated method stub

}

@Override
public void messageArrived(String topic, MqttMessage message)
        throws Exception {
	
	
	mqttrelaiscontrol.messageArrived(client, topic, message);

}

@Override
public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub

}

}