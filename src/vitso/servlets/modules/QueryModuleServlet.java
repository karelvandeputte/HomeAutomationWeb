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
@WebServlet("/mqtt/queryModule")
public class QueryModuleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryModuleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(this.getServletContext());
        
        MqttClient client = MyUtils.getStoredMqttClient(this.getServletContext());
        
        int id = Integer.parseInt(request.getParameter("id"));
 
        MqttRelais module = null;
 
        String errorString = null;
 
        try {
        	module = DBUtils.findModule(conn, id);
        	
        	MqttRelaisControl ctrl = new MqttRelaisControl();
        	try {
				ctrl.getStatus_relais(client, new Relais(0,"",1,1));
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
        if (errorString != null && module == null) {
            response.sendRedirect(request.getServletPath() + "/OperModuleList");
            return;
        }
 
        // Store errorString in request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("module", module);
 
        
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/views/modules/operModuleListView.jsp");
        dispatcher.forward(request, response);
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
