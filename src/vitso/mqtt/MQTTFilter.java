package vitso.mqtt;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import vitso.database.ConnectionUtils;
import vitso.database.MyUtils;
import vitso.entities.MqttRelais;
import vitso.entities.Relais;
 
@WebFilter(filterName = "mqttFilter", urlPatterns = { "/mqtt/*" })
public class MQTTFilter implements Filter {
 
   public MQTTFilter() {
   }
 
   @Override
   public void init(FilterConfig fConfig) throws ServletException {
 
   }
 
   @Override
   public void destroy() {
 
   }
 
 
   // Check the target of the request is a servlet?
   private boolean needMQTT(HttpServletRequest request) {
       System.out.println("MQTT Filter");
       //
       // Servlet Url-pattern: /spath/*
       //
       // => /spath
       String servletPath = request.getServletPath();
       // => /abc/mnp
       String pathInfo = request.getPathInfo();
 
       String urlPattern = servletPath;
 
       if (pathInfo != null) {
           // => /spath/*
           urlPattern = servletPath + "/mqtt/*";
       }
 
       // Key: servletName.
       // Value: ServletRegistration
       Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
               .getServletRegistrations();
 
 
       // Collection of all servlet in your webapp.
       Collection<? extends ServletRegistration> values = servletRegistrations.values();
       for (ServletRegistration sr : values) {
           Collection<String> mappings = sr.getMappings();
           if (mappings.contains(urlPattern)) {
               return true;
           }
       }
       return false;
   }
 
   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
           throws IOException, ServletException {
 
       HttpServletRequest req = (HttpServletRequest) request;
 
 
       //
       // Only open connections for the special requests need
       // connection. (For example, the path to the servlet, JSP, ..)
       //
       // Avoid open connection for commons request
       // (for example: image, css, javascript,... )
       //
       if (this.needMQTT(req)) {
 
           System.out.println("Open mqtt Connection for: " + req.getServletPath());
       	
          
           MqttClient client = MyUtils.getStoredMqttClient(req.getServletContext());
           chain.doFilter(request, response);
       	
	

       }
 
       // With commons requests (images, css, html, ..)
       // No need to open the connection.        
       else {
 
           // Allow request to go forward
           // (Go to the next filter or target)            
           chain.doFilter(request, response);
       }
 
   }
 
}