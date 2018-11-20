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

/**
 * Servlet implementation class DeleteRelaisServlet
 */
@WebServlet("/deleteRelais")
public class DeleteRelaisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteRelaisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request.getServletContext());
 
        int id = Integer.parseInt(request.getParameter("id"));
 
        String errorString = null;
 
        try {
            DBUtils.deleteRelais(conn, id);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
         
 
        // If an error redirected to an error page.
        if (errorString != null) {
 
            // Store the information in the request attribute, before forward to views.
            request.setAttribute("errorString", errorString);
            //
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/views/deleteRelaisErrorView.jsp");
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
