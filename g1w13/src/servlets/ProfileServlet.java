package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Client;

/**
 * Servlet implementation class ProfileServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "ProfileServlet", urlPatterns = { "/profile" })
public class ProfileServlet extends HttpServlet {

	private static final long serialVersionUID = 2922240775383415848L;

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
		String url = "";
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		Client sessionClient = null;

		// get reference to client logged in
		synchronized (session) {
			sessionClient = (Client) session.getAttribute("client");
		}

		// if there is no user logged in, send to login page
		if (sessionClient == null) {
			url = "/login.jsp";
			request.setAttribute("errorMessage",
					"You must be logged to view your profile");
		}
		// client is logged in, send to their profile
		else {
			url = "/profile.jsp";
			request.setAttribute("client", sessionClient);
		}
		dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
