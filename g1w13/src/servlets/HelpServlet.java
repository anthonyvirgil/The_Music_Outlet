// Venelin Koulaxazov
// 1032744
// HelpServlet.java
package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that will forward to the help page
 * 
 * @author Venelin Koulaxazov
 * @versions 1.1
 */
@WebServlet(name = "HelpServlet", urlPatterns = { "/help" })
public class HelpServlet extends HttpServlet {

	private static final long serialVersionUID = 1602348489422591982L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/help.jsp");
		dispatcher.forward(request, response);
	}

}
