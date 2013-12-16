// Venelin Koulaxazov
// 1032744
// TermsOfUseServlet
package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TermsOfUseServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "TermsOfUserServlet", urlPatterns = { "/termsOfUser" })
public class TermsOfUseServlet extends HttpServlet {

	private static final long serialVersionUID = -5642977772119173925L;

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
				.getRequestDispatcher("/termsOfUse.jsp");
		dispatcher.forward(request, response);
	}

}
