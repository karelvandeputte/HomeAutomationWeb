package vitso.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  
		try {
			testAWS();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        // Forward to /WEB-INF/views/homeView.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp");
         
        dispatcher.forward(request, response);
         
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    // Sender loop
    public static void testAWS() throws Exception {
    	String ACCESS_KEY = "AKIAJKTOV5XTTIASLIJA";
    	String SECRET_KEY = "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";

        // Create a client
        AmazonSNSClient service = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));

        // Create a topic
        CreateTopicRequest createReq = new CreateTopicRequest()
            .withName("MyTopic");
        CreateTopicResult createRes = service.createTopic(createReq);

        for (;;) {

            // Publish to a topic
            PublishRequest publishReq = new PublishRequest()
                .withTopicArn(createRes.getTopicArn())
                .withMessage("Example notification sent at " + new Date());
            service.publish(publishReq);

            Thread.sleep(1000);
        }
    }

}
