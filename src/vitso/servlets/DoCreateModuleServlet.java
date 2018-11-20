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

/**
 * Servlet implementation class DoCreateModuleServlet
 */
@WebServlet("/doCreateModule")
public class DoCreateModuleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoCreateModuleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request.getServletContext());
  
        String name = (String) request.getParameter("name");
        String description = (String) request.getParameter("description");
        int number = Integer.parseInt(request.getParameter("number"));
        int port = Integer.parseInt(request.getParameter("port"));
//        String priceStr = (String) request.getParameter("price");
//        float price = 0;
//        try {
//            price = Float.parseFloat(priceStr);
//        } catch (Exception e) {
//        }
        MqttRelais mqtt = new MqttRelais(0,name, number,port);
  
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
                DBUtils.insertModule(conn, mqtt);
            } catch (SQLException e) {
                e.printStackTrace();
                errorString = e.getMessage();
            }
        }
         
        // Store infomation to request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("module", mqtt);
  
        // If error, forward to Edit page.
        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/createModuleView.jsp");
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
