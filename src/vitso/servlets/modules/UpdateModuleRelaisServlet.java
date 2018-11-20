package vitso.servlets.modules;

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

import vitso.database.DBUtils;
import vitso.database.MyUtils;
import vitso.entities.MqttRelais;
import vitso.modules.control.MqttRelaisControl;

/**
 * Servlet implementation class UpdateModuleRelaisServlet
 */
@WebServlet("/UpdateModuleRelais")
public class UpdateModuleRelaisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateModuleRelaisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	Connection conn = MyUtils.getStoredConnection(request.getServletContext());
        MqttClient client = MyUtils.getStoredMqttClient(request.getServletContext());
        
    	int moduleID = Integer.parseInt(request.getParameter("moduleID"));
        
        MqttRelais module = null;
 
        String errorString = null;
 
        try {
        	module = DBUtils.findModule(conn, moduleID);
            MqttRelaisControl ctrl = new MqttRelaisControl();
         	try {
 				ctrl.setRelaisConfig(client, module);
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
            response.sendRedirect(request.getServletPath() + "/ModuleList");
            return;
        }
 
        // Store errorString in request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("module", module);
        request.setAttribute("moduleID", moduleID);
        
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/RelaisList");
        dispatcher.forward(request, response);
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
