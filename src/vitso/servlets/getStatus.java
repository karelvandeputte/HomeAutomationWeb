package vitso.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import vitso.MQTT;
import vitso.database.connect;
import vitso.entities.MqttRelais;
import vitso.entities.Relais;
import vitso.entities.Relais.relaisStatus;
import vitso.modules.control.MqttRelaisControl;

/**
 * Servlet implementation class getStatus
 */
@WebServlet("/getStatus")
public class getStatus extends HttpServlet implements MqttCallback {
	private static final long serialVersionUID = 1L;
	
	HttpServletResponse globalResponse;
	HttpServletRequest globalRequest;
	MqttRelaisControl mqttrelaiscontrol = new MqttRelaisControl();
	MqttClient client;
	
	ArrayList MqttRelaisList = new ArrayList();
	ArrayList RelaisList = new ArrayList();
	
	private Connection connect = null;
	private connect con = new connect();
	private Statement statement = null;
    private ResultSet resultSet = null;
    private String database = "HomeAutomation";
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		globalResponse = response;
		globalRequest = request;
		
		String moduleName = request.getParameter("moduleName");

	    
    	try {
    		connect = con.connectToDB();
    		MqttRelaisList = new ArrayList();
    		RelaisList = new ArrayList();
    		
			readModules(moduleName);
			if (MqttRelaisList.size() > 0) {
				readRelais((MqttRelais)MqttRelaisList.get(0),0);
				
				if (RelaisList.size() > 0) {
		    		try {
		    			client = new MqttClient("tcp://192.168.0.240:1883", "Sending");
		    		    MqttConnectOptions clientOptions = new MqttConnectOptions();
		    		    clientOptions.setMaxInflight(100);
		    		    client.connect(clientOptions);
		    		    client.setCallback(this);
		    			client.subscribe("home/relais");  
		    			try {
		    				for  (int i=0;i<RelaisList.size();i++) {
		    					mqttrelaiscontrol.getStatus_relais(client, (Relais) RelaisList.get(i));
		    				}
		    			} catch (Exception e) {
		    				// TODO Auto-generated catch block
		    				e.printStackTrace();
		    			}
		    		} catch (MqttException e1) {
		    			// TODO Auto-generated catch block
		    			e1.printStackTrace();
		    		}  					
					
				}
				
				
			}
			con.close(connect,statement,resultSet);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	
	}

	
	public void readRelais(MqttRelais module, int relNumber) throws Exception {
        try {
                statement = connect.createStatement();
                String qry = "select * from " + database + ".v_relais where ModuleID = " + module.getID();
                if (relNumber != 0) {
                	qry = qry + " AND number = " + relNumber;
                }
                resultSet = statement.executeQuery(qry);
                while (resultSet.next()) {
                	int ID = resultSet.getInt("ID");
                	String name = resultSet.getString("Name");
                	int number = resultSet.getInt("Number");
                	int port = resultSet.getInt("Port");
                	String status = resultSet.getString("Status");
                	Relais rel = new Relais(ID,name, number, port);
                	rel.setStatus(status);
                    RelaisList.add(rel);
                    //System.out.println(rel.reprString());
                }
        } catch (Exception e) {
                throw e;
        }
	}
	
	public void updateRelais(MqttRelais module, Relais relais) throws Exception {
        try {
                PreparedStatement ps = connect.prepareStatement("UPDATE HomeAutomation.Relais SET LastUpdated=CURRENT_TIMESTAMP, Status = ? WHERE ID = ?");
        	    ps.setString(1,relais.getStatus().toString());
        	    ps.setInt(2,relais.getID());

        	    ps.executeUpdate();

        } catch (Exception e) {
                throw e;
        }
	}
	
//	public void insertRelais(MqttRelais module, Relais relais) throws Exception {
//        try {
//                statement = connect.createStatement();
//                String qry = "select * from " + database + ".v_relais where ModuleID = " + module.getID();
//                if (relNumber != 0) {
//                	qry = qry + " AND number = " + relNumber;
//                }
//                resultSet = statement.executeQuery(qry);
//                while (resultSet.next()) {
//                	int ID = resultSet.getInt("ID");
//                	String name = resultSet.getString("Name");
//                	int number = resultSet.getInt("Number");
//                	int port = resultSet.getInt("Port");
//                	Relais rel = new Relais(ID,name, number, port);
//                    RelaisList.add(rel);
//                    //System.out.println(rel.reprString());
//                }
//        } catch (Exception e) {
//                throw e;
//        }
//	}
	
	

	public void readModules(String moduleName) throws Exception {
	        try {
	                statement = connect.createStatement();
	                resultSet = statement.executeQuery("select * from " + database + ".v_mqttrelais where Name = '" + moduleName + "'");
	                while (resultSet.next()) {
	                	int ID = resultSet.getInt("ID");
	                	String name = resultSet.getString("Name");
	                	int number = resultSet.getInt("Number");
	                	int port = resultSet.getInt("Port");
	                	MqttRelais mqtt = new MqttRelais(ID, name, number, port);
                        MqttRelaisList.add(mqtt);
                        //System.out.println(mqtt.reprString());
	                }
	        } catch (Exception e) {
	                throw e;
	        }
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
		
		String message_slots[] = message.toString().split(Pattern.quote("|"));

		if (message_slots[1].equals("RAS")) {
			if (message_slots[2].equals("REPORT_RELAIS")) {

		    	try {
		    		connect = con.connectToDB();
		    		MqttRelaisList.clear();
		    		RelaisList.clear();

		    		readModules(message_slots[0]);
					if (MqttRelaisList.size() > 0) {
						readRelais((MqttRelais)MqttRelaisList.get(0),Integer.parseInt(message_slots[3]));
						if (RelaisList.size() > 0) {
							//Update relais status
							Relais relais = (Relais)RelaisList.get(0);
							relais.setStatus(message_slots[4]);
							updateRelais((MqttRelais)MqttRelaisList.get(0),relais);
						} 
//						else {
//							//Insert relais
//							Relais relais = new Relais(0, "", 0, 0)
//							insertRelais((MqttRelais)MqttRelaisList.get(0),(Relais)RelaisList.get(0));
//						}
					}
					con.close(connect,statement,resultSet);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		}
		
	}

}
