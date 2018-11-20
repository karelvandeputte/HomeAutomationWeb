package vitso.activities.control;


import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import vitso.database.*;
import vitso.entities.*;



public class ActivityControl {
	
	MqttClient client;
	
	public void messageArrived(ServletContext servletContext, String topic, MqttMessage message)
	        throws Exception {
		
		Connection conn = MyUtils.getStoredConnection(servletContext);
		
		Activity activity = ActivitiesDA.findActivityByName(conn, message.toString());
		
		if ( activity != null )
		{
			List<ActivityTask> activityTasks = ActivitiesDA.queryActivityTasks(conn, activity.getID());
			for (ActivityTask activityTask : activityTasks)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				Date now = new Date();
				System.out.println(sdf.format(now).concat(" - ").concat(activityTask.getName()).concat(" - ").concat(activityTask.getDescription()) );
			}
			
		}
			
		conn.close();
		conn = null;
			
	}

}
