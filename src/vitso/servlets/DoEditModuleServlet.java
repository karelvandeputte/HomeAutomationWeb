package vitso.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.paho.client.mqttv3.MqttClient;

import vitso.database.*;
import vitso.entities.*;
import vitso.modules.control.MqttRelaisControl;

/**
 * Servlet implementation class DoEditModuleServlet
 */
@WebServlet("/doEditModule")
public class DoEditModuleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoEditModuleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request.getServletContext());
        MqttClient client = MyUtils.getStoredMqttClient(request.getServletContext());
        
        
        int ID = Integer.parseInt(request.getParameter("id"));
        String name = (String) request.getParameter("name");
        String namePrevious = (String) request.getParameter("namePrevious");
        
        String description = (String) request.getParameter("description");
        int number = Integer.parseInt(request.getParameter("number"));
        int port = Integer.parseInt(request.getParameter("port"));
        
        MqttRelais mqtt = new MqttRelais(ID,name, number,port);
        mqtt.setNamePrevious(namePrevious);
  
        String errorString = null;
  
        try {
            DBUtils.updateModule(conn, mqtt);
            
            MqttRelaisControl ctrl = new MqttRelaisControl();
        	try {
				ctrl.setConfig(client, mqtt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
  
        // Store infomation to request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("module", mqtt);
  
  
        // If error, forward to Edit page.
        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/editModuleView.jsp");
            dispatcher.forward(request, response);
        }
         
        // If everything nice.
        // Redirect to the product listing page.            
        else {
            response.sendRedirect(request.getContextPath() + "/ModuleList");
        }
  
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
