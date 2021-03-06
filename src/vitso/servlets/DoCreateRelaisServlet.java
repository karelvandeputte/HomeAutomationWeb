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

import vitso.database.*;
import vitso.entities.MqttRelais;
import vitso.entities.Relais;

/**
 * Servlet implementation class DoCreateRelaisServlet
 */
@WebServlet("/doCreateRelais")
public class DoCreateRelaisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateRelaisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request.getServletContext());
  
        String name = (String) request.getParameter("name");
        String description = (String) request.getParameter("description");
        int moduleID = Integer.parseInt(request.getParameter("moduleID"));
        int number = Integer.parseInt(request.getParameter("number"));
        int port = Integer.parseInt(request.getParameter("port"));
//        String priceStr = (String) request.getParameter("price");
//        float price = 0;
//        try {
//            price = Float.parseFloat(priceStr);
//        } catch (Exception e) {
//        }
        Relais relais = new Relais(0, name, description, moduleID, number, port, "OFF");
  
        String errorString = null;
  
       
//        // Product ID is the string literal [a-zA-Z_0-9]
//        // with at least 1 character
//        String regex = "\\w+";
//  
//        if (code == null || !code.matches(regex)) {
//            errorString = "Product Code invalid!";
//        }
  
        if (errorString == null) {
            try {
                DBUtils.insertRelais(conn, relais);
            } catch (SQLException e) {
                e.printStackTrace();
                errorString = e.getMessage();
            }
        }
         
        // Store information to request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("relais", relais);
        request.setAttribute("moduleID", moduleID);
  
        // If error, forward to Edit page.
        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/createRelaisView.jsp");
            dispatcher.forward(request, response);
        }
  
        // If everything nice.
        // Redirect to the product listing page.            
        else {
        	RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/RelaisList");
            dispatcher.forward(request, response);
        }
  
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
