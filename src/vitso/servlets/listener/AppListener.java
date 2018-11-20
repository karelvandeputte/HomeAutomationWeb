package vitso.servlets.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;



import vitso.activities.control.ActivityControl;
import vitso.database.MyUtils;
import vitso.modules.control.MqttRelaisControl;

/**
 * Servlet implementation class AppListener
 */
@WebListener
public class AppListener implements ServletContextListener,MqttCallback {
	
	
	private String configFilePrefix = "vitso.modules.config."; //$NON-NLS-1$
	MqttClient client;
	MqttRelaisControl mqttrelaiscontrol = new MqttRelaisControl();
	ServletContext servletContext;

	public static final String MY_CHANNEL_TOPIC = "home/relais";
	public static final String MY_ACTIVITY_TOPIC = "home/activity";
	public static final String MY_AUTOREGISTRY_TOPIC = "home/autreg";
	
    public void contextInitialized(ServletContextEvent sce) {
        //application is deployed
        //create your object and load it
    	
		//get the application context
        ServletContext servletContext = sce.getServletContext();
//        
//        
//        Thread QueryModulesThread = new Thread(new QueryModules(servletContext));
//        QueryModulesThread.start();
//    	servletContext.setAttribute("QueryModulesThread", QueryModulesThread);


		try {
			MqttClient client = MyUtils.getStoredMqttClient(servletContext);
			MqttConnectOptions clientOptions = MyUtils.getStoredClientOptions(servletContext);
			client.setCallback(this);
		    client.connect(clientOptions);		
		    
		    
			int subQoS = 0;

			MqttTopic topic = client.getTopic(MY_CHANNEL_TOPIC);
			client.subscribe(MY_CHANNEL_TOPIC, subQoS);

		    
			MqttTopic activityTopic = client.getTopic(MY_ACTIVITY_TOPIC);
			client.subscribe(MY_ACTIVITY_TOPIC, subQoS);

			MqttTopic autregTopic = client.getTopic(MY_AUTOREGISTRY_TOPIC);
			client.subscribe(MY_AUTOREGISTRY_TOPIC, subQoS);


			
			
		} catch (MqttException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
    	
    	
    	
//    	ResourceBundle configBundle = ResourceBundle.getBundle(configFilePrefix.concat("mqttZolder"));
//    	MqttRelais mqttZolder= new MqttRelais(configBundle);


        //store the object in application scope
//        servletContext.setAttribute("mqttZolder", mqttZolder);
        
        
//		MqttRelaisControl mqttrelaiscontrol = new MqttRelaisControl();
		
//		try {
//			client = new MqttClient("tcp://192.168.0.240:1883", "Sending");
//		    MqttConnectOptions clientOptions = new MqttConnectOptions();
//		    clientOptions.setMaxInflight(100);
//		    client.connect(clientOptions);
//		    client.setCallback(this);
//			client.subscribe("home/relais");  
//			try {
//				mqttrelaiscontrol.getStatus_relais(client, mqttZolder, mqttZolder.getRelais1());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (MqttException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

    }
    public void contextDestroyed(ServletContextEvent sce) {
        //application is undeployed

    	
    	ServletContext servletContext = sce.getServletContext();
        Thread QueryModulesThread = (Thread)servletContext.getAttribute("QueryModulesThread");
        
        
        if (QueryModulesThread != null) {
        	QueryModulesThread.interrupt();
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
		try {
			String message_slots[] = message.toString().split(Pattern.quote("|"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date now = new Date();
			System.out.println(sdf.format(now).concat(" - ").concat(topic.toString()).concat(" - ").concat(message.toString()) );
			MqttRelaisControl ctrl;
			
			switch (topic.toString()) {
			case MY_CHANNEL_TOPIC:
				ctrl = new MqttRelaisControl();
				ctrl.messageArrived(servletContext, topic, message);				
				break;

			case MY_ACTIVITY_TOPIC:
				Thread t;
				System.out.println("Starting Processing Loop at: " + GetTimeStamp());
				//t = new Thread(ProcessActivity(""));
				//t.start();
				
				ActivityControl activityCtrl = new ActivityControl();
				activityCtrl.messageArrived(servletContext, topic, message);
				
				break;

			case MY_AUTOREGISTRY_TOPIC:
				ctrl = new MqttRelaisControl();
				ctrl.autregMessageArrived(servletContext, topic, message);				
				break;

			default:
				break;
			} 



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//mqttrelaiscontrol.messageArrived(servletContext, topic, message);
	}
	
	public static String GetTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		return sdf.format(now);
	}

	





	
	
	
}