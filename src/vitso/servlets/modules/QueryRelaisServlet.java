package vitso.servlets.modules;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
 * Servlet implementation class EditModuleServlet
 */
@WebServlet("/mqtt/queryRelais")
public class QueryRelaisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryRelaisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request.getServletContext());
        
        MqttClient client = MyUtils.getStoredMqttClient(this.getServletContext());
        
        
 
        int id = Integer.parseInt(request.getParameter("id"));
        String mode = request.getParameter("mode");
        
 
        Relais relais = null;
 
        String errorString = null;
 
        try {
        	relais = DBUtils.findRelais(conn, id);
        	
        	MqttRelaisControl ctrl = new MqttRelaisControl();
        	try {
        		
        		switch (mode) {
				case "query":
					break;

				case "toggle":
					ctrl.toggle_relais(client, relais);
					Thread.sleep(100);
					break;

				case "on":
					ctrl.turnon_relais(client, relais);
					Thread.sleep(100);
					break;

				case "off":
					ctrl.turnoff_relais(client, relais);
					Thread.sleep(100);
					break;

				default:
					break;
				}
        		ctrl.getStatus_relais(client, relais);
				
				
				
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	
        	
        	
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
 
         
        // If no error.
        // The product does not exist to edit.
        // Redirect to productList page.
        if (errorString != null && relais == null) {
            response.sendRedirect(request.getServletPath() + "/OperModuleList");
            return;
        }
 
        // Store errorString in request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("relais", relais);
 
        
        try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/OperModuleList");
        dispatcher.forward(request, response);
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
