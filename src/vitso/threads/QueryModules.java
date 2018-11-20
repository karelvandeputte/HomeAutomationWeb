package vitso.threads;
//https://docs.oracle.com/javase/tutorial/essential/concurrency/simple.html

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import vitso.database.ConnectionUtils;
import vitso.entities.MqttRelais;
import vitso.entities.Relais;

public  class QueryModules implements Runnable, MqttCallback {
		
	ArrayList MqttRelaisList = new ArrayList();
	
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    final private String host = "192.168.0.240";
    final private String user = "admin";
    final private String passwd = "vitso102796015";
    final private String database = "HomeAutomation";
    
	MqttClient client;
	
    private ServletContext context;
    
    public QueryModules (ServletContext context) {
    	this.context = context;    	
    	
    }
    
    // Display a message, preceded by
    // the name of the current thread
    static void threadMessage(String message) {
        String threadName =
            Thread.currentThread().getName();
        System.out.format("%s: %s%n",
                          threadName,
                          message);
    }
    
    
	public void run() {
		
		
    	try {
    	
			connectToDB();
			connectToMqtt();
			
			
			
			int loopIterationCnt=0;
			
			while (true) {
				
				loopIterationCnt++;
				// Sleep between execution cycles
				try {


					for (int i = 1; true && i <= 10; i++) {
						Thread.sleep(1000);
					}

						System.out.println("looping..."  + client.getClientId().toString());
				}
				catch (Exception e) {
					e.printStackTrace();
				}


			} // End while runProcessing loop

			
			


					

    		
    		
    		


		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	public void connectToMqtt()  {
		try {
			client = new MqttClient("tcp://192.168.0.240:1883", "Sending");
		    MqttConnectOptions clientOptions = new MqttConnectOptions();
		    clientOptions.setMaxInflight(100);
		    clientOptions.setCleanSession(true);
		    clientOptions.setKeepAliveInterval(30);
			
		    client.setCallback(this);
		    client.connect(clientOptions);		
		    
		    String myTopic = "home/relais";
			MqttTopic topic = client.getTopic(myTopic);
			
			int subQoS = 0;
			client.subscribe(myTopic, subQoS);
			
		} catch (MqttException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
	}
	
    public void connectToDB() throws Exception {
        try {
        	
        	connect = ConnectionUtils.getConnection();
        	 
//                // This will load the MySQL driver, each DB has its own driver
//                Class.forName("com.mysql.jdbc.Driver");
//
//                // Setup the connection with the DB
//                connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"
//                                + database + "?" + "user=" + user + "&password=" + passwd);

        } catch (Exception e) {
                throw e;
        }
	}

	public void readModules() throws Exception {
	        try {
	                statement = connect.createStatement();
	                resultSet = statement.executeQuery("select * from " + database + ".v_mqttrelais");
	                while (resultSet.next()) {
	                	MqttRelais mqtt = new MqttRelais(resultSet.getInt("ID"),resultSet.getString("Name"), resultSet.getInt("Number"), resultSet.getInt("Port"));
                        MqttRelaisList.add(mqtt);
                        System.out.println(mqtt.reprString());
	                }
	        } catch (Exception e) {
	                throw e;
	        }
	}

	// You need to close the resultSet
	public void close() {
	        try {
	                if (resultSet != null) {
	                        resultSet.close();
	                }
	
	                if (statement != null) {
	                        statement.close();
	                }
	
	                if (connect != null) {
	                        connect.close();
	                }
	        } catch (Exception e) {
	
	        }
	}

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		
		
		//ex: mqttZolder|RAS|REPORT_RELAIS|1|1
		
		try {
			String message_slots[] = message.toString().split(Pattern.quote("|"));
			System.out.println(message.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		if (message_slots[1].equals("RAS")) {
//			if (message_slots[2].equals("REPORT_RELAIS")) {
//
//		    	try {
//		    		connect = con.connectToDB();
//		    		MqttRelaisList.clear();
//		    		RelaisList.clear();
//
//		    		readModules(message_slots[0]);
//					if (MqttRelaisList.size() > 0) {
//						readRelais((MqttRelais)MqttRelaisList.get(0),Integer.parseInt(message_slots[3]));
//						if (RelaisList.size() > 0) {
//							//Update relais status
//							Relais relais = (Relais)RelaisList.get(0);
//							relais.setStatus(message_slots[4]);
//							updateRelais((MqttRelais)MqttRelaisList.get(0),relais);
//						} 
////						else {
////							//Insert relais
////							Relais relais = new Relais(0, "", 0, 0)
////							insertRelais((MqttRelais)MqttRelaisList.get(0),(Relais)RelaisList.get(0));
////						}
//					}
//					con.close(connect,statement,resultSet);
//				} catch (Exception e2) {
//					// TODO Auto-generated catch block
//					e2.printStackTrace();
//				}
//			}
//		}
	}



}