// Anthony-Virgil Bermejo
// 0831360
// ErrorPageServlet.java
package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to gather information that will be displayed in an error page
 * associated with a 404 or 500 status error
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.3
 */
@WebServlet(name = "ErrorPageServlet", urlPatterns = { "/errorPage404",
		"/errorPage500" })
public class ErrorPageServlet extends HttpServlet {

	private static final long serialVersionUID = 6315850462085013755L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getRequestURI();

		if (uri.contains("/errorPage404")) {
			// if 404 error code, send to associated jsp
			response.sendRedirect("/g1w13/error_404.jsp");
		} else if (uri.contains("/errorPage505")) {
			// if 500 error code, send to associated jsp
			response.sendRedirect("/g1w13/error_500.jsp");
		} else {
			// send to index page
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/index");
			dispatcher.forward(request, response);
		}
	}
}
