package vitso.modules.control;


import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.tomcat.jni.Time;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import vitso.database.DBUtils;
import vitso.database.MyUtils;
import vitso.entities.MqttRelais;
import vitso.entities.Relais;
import vitso.modules.Messages;



public class MqttRelaisControl {
	
	private String configFilePrefix = "vitso.modules.config."; //$NON-NLS-1$
					
	MqttClient client;
	
	public void messageArrived(MqttClient client, String topic, MqttMessage message)
	        throws Exception {
		
		//ex: MQTT_test|RAS|INIT|GET|MQTT_test
		
		String message_slots[] = message.toString().split(Pattern.quote("|"));
		
		if (message_slots[1].equals("RAS")) {
			if ( message_slots[2].equals("INIT") && message_slots[3].equals("GET") && message_slots[4].equals("CONFIG") ) {
				System.out.println("Message: " + message);   
				
//				ResourceBundle configBundle = ResourceBundle.getBundle(configFilePrefix.concat(message_slots[0]));
//				MqttRelais mqttrelais = new MqttRelais(configBundle);
//				System.out.println("MQTT: " + mqttrelais.reprString());   
//				
//				
//				sendConfig( client,message_slots, mqttrelais);
//				getStatus( client,mqttrelais);
				
			}
			System.out.println("Message: " + message);   
			
			
			
			
		}
	}

	public void messageArrived(ServletContext servletContext, String topic, MqttMessage message)
	        throws Exception {
		
		//ex: mqttZolder|RAS|REPORT_RELAIS|1|0
		
		String message_slots[] = message.toString().split(Pattern.quote("|"));
		
		
		
		if (message_slots[1].equals("RAS")) {
			
			Connection conn = MyUtils.getStoredConnection(servletContext);
			MqttRelais module = DBUtils.findModuleByName(conn, message_slots[0]);
				
			Relais rel = null;
			if ( message_slots[2].equals("REPORT_RELAIS") ) {
				for (int i=0;i<module.getRelaisList().size();i++){
					
					rel = module.getRelaisList().get(i);
					if ( rel.getNumber() == Integer.parseInt(message_slots[3]) ) {
						rel.setStatus(message_slots[4]);
						DBUtils.updateRelais(conn, rel);
						conn.commit();
					}
				}				
			}
			
			conn.close();
			conn = null;
			
			
		}
	}

	public void autregMessageArrived(ServletContext servletContext, String topic, MqttMessage message)
	        throws Exception {
		
		//ex: mqttZolder|RAS|REPORT_RELAIS|1|0
		
		String message_slots[] = message.toString().split(Pattern.quote("|"));
		Connection conn = MyUtils.getStoredConnection(servletContext);		
		
		
		if (message_slots[1].equals("RAS")) {

			MqttRelais module = DBUtils.findModuleByName(conn, message_slots[0]);
			
			if ( message_slots[2].equals("AUTREG_M") ) {
				
				

				if ( module == null) {
			        String name = (String) message_slots[0];
			        String description = "Auto registered";
			        int number = Integer.parseInt(message_slots[3]);
			        int port = Integer.parseInt(message_slots[4]);

			        MqttRelais mqtt = new MqttRelais(0,name, number,port);
			  
			        String errorString = null;
			  	  
			        if (errorString == null) {
			            try {
			                DBUtils.insertModule(conn, mqtt);
			            } catch (SQLException e) {
			                e.printStackTrace();
			                errorString = e.getMessage();
			            }
			        }
				}
				
			}
			
			
			if ( message_slots[2].equals("AUTREG_R") ) {
				module.setRelaisList(DBUtils.queryRelais(conn, module.getID())); 
				
		        String name = (String) message_slots[4];
		        String description = (String) "auto registered";
		        int moduleID = module.getID();
		        String[] num_port = message_slots[3].toString().split(",");
		        int number = Integer.parseInt(num_port[0]);
		        int port = Integer.parseInt(num_port[1]);		
		        
		        boolean createRelais = true;
				
				for ( Relais relais : module.getRelaisList() ) {
					if ( relais.getName().toString().equals(name) ) {
						createRelais = false;
					}
				}

		
				if ( createRelais ) {
		
			        Relais relais = new Relais(0, name, description, moduleID, number, port, "OFF");
			  
			        String errorString = null;
			  
			        if (errorString == null) {
			            try {
			                DBUtils.insertRelais(conn, relais);
			            } catch (SQLException e) {
			                e.printStackTrace();
			                errorString = e.getMessage();
			            }
			        }					
					
				}

				
			}
			

	         
			
			
			
			
			conn.close();
			conn = null;
			
			
		}
	}


	public void setRelaisConfig(MqttClient client,MqttRelais mqttrelais) throws Exception {
		

	    try {
	    	if (client == null) {
	    		client = new MqttClient("tcp://192.168.0.240:1883", "Sending");
		        client.connect();
	    	}
	        MqttMessage message = new MqttMessage();

	        
	        message.setPayload(String.valueOf("RAS|").concat(mqttrelais.getName()).concat("|CONF|NUMREL|").concat(String.valueOf(mqttrelais.getNumberOfRelais())) 
	                .getBytes());
	        client.publish("home/channel", message);

	        Thread.sleep(100);
	        int x,y,z =0;
	        
	        for (int i=0; i < mqttrelais.getRelaisList().size();i++)
	        {

	        	x = (i * 50) + 100;
	        	y = (i * 50) + 102;
	        	z = (i * 50) + 104;

	        	message.setPayload(String.valueOf("RAS|").concat(mqttrelais.getName()).concat("|CONF_RELAIS|" + String.valueOf(x) + "|").concat(String.valueOf(mqttrelais.getRelaisList().get(i).getNumber())) 
		                .getBytes());
		        client.publish("home/channel", message);
		        Thread.sleep(100);
		        
		        message.setPayload(String.valueOf("RAS|").concat(mqttrelais.getName()).concat("|CONF_RELAIS|" + String.valueOf(y) + "|").concat(String.valueOf(mqttrelais.getRelaisList().get(i).getPort())) 
		                .getBytes());
		        client.publish("home/channel", message);
		        Thread.sleep(100);
		        
		        message.setPayload(String.valueOf("RAS|").concat(mqttrelais.getName()).concat("|CONF_RELAIS|" + String.valueOf(z) + "|").concat(String.valueOf(mqttrelais.getRelaisList().get(i).getName())) 
		                .getBytes());
		        client.publish("home/channel", message);	        	
		        Thread.sleep(100);
	        	
	        	
	        }

	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public void setConfig(MqttClient client,MqttRelais mqttrelais) throws Exception {
		

	    try {
	    	if (client == null) {
	    		client = new MqttClient("tcp://192.168.0.240:1883", "Sending");
		        client.connect();
	    	}
	        MqttMessage message = new MqttMessage();
	        message.setPayload(String.valueOf("RAS|").concat(mqttrelais.getNamePrevious()).concat("|CONF|NAME|").concat(mqttrelais.getName())
	    	                .getBytes());
	        client.publish("home/channel", message);
	        
	        message.setPayload(String.valueOf("RAS|").concat(mqttrelais.getName()).concat("|CONF|NUMBER|").concat(String.valueOf(mqttrelais.getNumber())) 
	                .getBytes());
	        client.publish("home/channel", message);
	        
	        message.setPayload(String.valueOf("RAS|").concat(mqttrelais.getName()).concat("|CONF|NUMREL|").concat(String.valueOf(mqttrelais.getNumberOfRelais())) 
	                .getBytes());
	        client.publish("home/channel", message);

	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public void getStatus(MqttClient client,MqttRelais mqttrelais) throws Exception {
		

	    try {
	    	if (client == null) {
	    		client = new MqttClient("tcp://192.168.0.240:1883", "Sending");
	    		MqttConnectOptions clientOptions = new MqttConnectOptions();
	    		clientOptions.setMaxInflight(100);
	    		client.connect(clientOptions);
	    	}
	        MqttMessage message = new MqttMessage();
	        message.setPayload(String.valueOf("RAS|").concat(mqttrelais.getName()).concat("|GET|REL|").concat(mqttrelais.getName()) 
	                .getBytes());
	        client.publish("home/channel", message);


	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	}

	public void getStatus_relais(MqttClient client, Relais rel)
	        throws Exception {
	    try {
	        MqttMessage message = new MqttMessage();
	        message.setPayload(String.valueOf("RAS|").concat(rel.getModuleName()).concat("|GET_RELAIS|STATUS|").concat(String.valueOf(rel.getNumber()))
	                .getBytes());
	        client.publish("home/channel", message);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
	        System.out.println(sdf.format(now).concat(message.toString()));
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	}

	public void toggle_relais(MqttClient client, Relais rel)
	        throws Exception {
	    try {
	        MqttMessage message = new MqttMessage();
	        message.setPayload(String.valueOf("RAS|").concat(rel.getModuleName()).concat("|SET_RELAIS|").concat(String.valueOf(rel.getNumber())).concat("|TOGGLE")
	                .getBytes());
	        client.publish("home/channel", message);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
	        System.out.println(sdf.format(now).concat(message.toString()));
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	}

	public void turnon_relais(MqttClient client, Relais rel)
	        throws Exception {
	    try {
	        MqttMessage message = new MqttMessage();
	        message.setPayload(String.valueOf("RAS|").concat(rel.getModuleName()).concat("|SET_RELAIS|").concat(String.valueOf(rel.getNumber())).concat("|ON")
	                .getBytes());
	        client.publish("home/channel", message);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
	        System.out.println(sdf.format(now).concat(message.toString()));
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	}

	public void turnoff_relais(MqttClient client, Relais rel)
	        throws Exception {
	    try {
	        MqttMessage message = new MqttMessage();
	        message.setPayload(String.valueOf("RAS|").concat(rel.getModuleName()).concat("|SET_RELAIS|").concat(String.valueOf(rel.getNumber())).concat("|OFF")
	                .getBytes());
	        client.publish("home/channel", message);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
	        System.out.println(sdf.format(now).concat(message.toString()));
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	}

}
