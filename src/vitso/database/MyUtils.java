package vitso.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import vitso.entities.UserAccount;
 
public class MyUtils {
 
	   public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	   
	   public static final String ATT_NAME_MQTT_CLIENT = "ATTRIBUTE_FOR_MQTT_CLIENT";

	   public static final String ATT_NAME_MQTT_CLIENT_OPTIONS = "ATTRIBUTE_FOR_MQTT_CLIENT_OPTIONS";

   private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
 
  
   
   
   public static void storeMqttClient(ServletContext servletContext, MqttClient client) {
	   servletContext.setAttribute(ATT_NAME_MQTT_CLIENT, client);
   }
 
  
   public static MqttClient getStoredMqttClient(ServletContext servletContext) {
	   MqttClient client=null;
		try {
			client = (MqttClient) servletContext.getAttribute(ATT_NAME_MQTT_CLIENT);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( client == null) {
			
			try {
				//client = new MqttClient("tcp://192.168.0.240:1883", "Sending");
				client = new MqttClient("tcp://192.168.1.110:1883", "Sending");
				storeMqttClient(servletContext, client);
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			
		}
       return client;
   }
   
   public static void storeMqttConnectOptions(ServletContext servletContext, MqttConnectOptions options) {
	   servletContext.setAttribute(ATT_NAME_MQTT_CLIENT_OPTIONS, options);
   }
 
   public static MqttConnectOptions getStoredClientOptions(ServletContext servletContext) {
	   MqttConnectOptions clientOptions = null;
		try {
			clientOptions = (MqttConnectOptions) servletContext.getAttribute(ATT_NAME_MQTT_CLIENT_OPTIONS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ( clientOptions == null) {
			
			clientOptions = new MqttConnectOptions();
			clientOptions.setMaxInflight(100);
			clientOptions.setCleanSession(true);
			clientOptions.setKeepAliveInterval(30);
			storeMqttConnectOptions(servletContext,clientOptions);
		    
			
		}
       return clientOptions;
      
   }
    
   // Store Connection in request attribute.
   // (Information stored only exist during requests)
   public static void storeConnection(ServletContext servletContext, Connection conn) {
	   servletContext.setAttribute(ATT_NAME_CONNECTION, conn);
   }
 
   // Get the Connection object has been stored in one attribute of the request.
   public static Connection getStoredConnection(ServletContext servletContext) {
	   Connection conn = null;
	   if (servletContext != null)
	   {
		   conn = (Connection) servletContext.getAttribute(ATT_NAME_CONNECTION);
	   }
	   else
	   {
		   try {
			conn = ConnectionUtils.getConnection();
		   conn.setAutoCommit(false);		
		   } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	   }
       
       return conn;
   }
 
   // Store user info in Session.
   public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
 
       // On the JSP can access ${loginedUser}
       session.setAttribute("loginedUser", loginedUser);
   }
 
 
   // Get the user information stored in the session.
   public static UserAccount getLoginedUser(HttpSession session) {
       UserAccount loginedUser = (UserAccount) session.getAttribute("loginedUser");
       return loginedUser;
   }
 
 
   // Store info in Cookie
   public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
       System.out.println("Store user cookie");
       Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
 
       // 1 day (Convert to seconds)
       cookieUserName.setMaxAge(24 * 60 * 60);
       response.addCookie(cookieUserName);
   }
 
   public static String getUserNameInCookie(HttpServletRequest request) {
       Cookie[] cookies = request.getCookies();
       if (cookies != null) {
           for (Cookie cookie : cookies) {
               if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                   return cookie.getValue();
               }
           }
       }
       return null;
   }
 
 
   // Delete cookie.
   public static void deleteUserCookie(HttpServletResponse response) {
       Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
 
       // 0 seconds (Expires immediately)
       cookieUserName.setMaxAge(0);
       response.addCookie(cookieUserName);
   }
 
}