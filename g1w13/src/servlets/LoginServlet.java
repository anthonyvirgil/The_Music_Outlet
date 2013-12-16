// Anthony-Virgil Bermejo 0831360
// Natacha Gabbamonte 0932340
// LoginServlet.java
package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cart;
import beans.Client;

import dbManager.DatabaseManager;

/**
 * This is the Servlet used by the login page and the top navigation login.
 * 
 * @author Natacha Gabbamonte 0932340
 * @author Anthony-Virgil Bermejo 0831360
 * @version 1.4
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/login", "/userLogin" })
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 3535656831747710539L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		String destination = "/index.jsp";
		HttpSession session = request.getSession();
		Client client = null;

		// get reference to the client logged in
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// if there is no client logged in, send to login page
		if (client == null && uri.contains("/userLogin"))
			destination = "/login.jsp";

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(destination);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		DatabaseManager dbManager = new DatabaseManager();

		String uri = request.getRequestURI();
		String destination = "/index.jsp";
		if (uri.contains("/login")) {
			doLoginActions(request, session, dbManager, true);
		} else if (uri.contains("/userLogin")) {
			if (!doLoginActions(request, session, dbManager, false))
				destination = "/login.jsp";
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(destination);
		dispatcher.forward(request, response);
	}

	/*
	 * Does the login actions for the nav bar login form.
	 * 
	 * @author Natacha Gabbamonte 0932340
	 */
	private boolean doLoginActions(HttpServletRequest request,
			HttpSession session, DatabaseManager dbManager, boolean isNavLogin) {
		String errorMessageDestination = "displayMessage";
		boolean success = false;
		if (!isNavLogin)
			errorMessageDestination = "errorMessage";
		synchronized (session) {
			if (session.getAttribute("client") != null) {
				request.setAttribute("displayMessage",
						"You are already logged in");

			} else {
				String username = request.getParameter("username"
						+ (isNavLogin ? "" : "2"));
				String password = request.getParameter("password"
						+ (isNavLogin ? "" : "2"));
				if (username != null && username.length() > 0
						&& password != null && password.length() > 0) {
					Client client = dbManager.getClientByEmail(username);
					if (client != null) {
						if (client.getPassword().equals(password)) {
							session.setAttribute("client", client);
							Cart cart = (Cart) session.getAttribute("cart");
							cart.setProvince(client.getProvince());
							session.setAttribute("cart", cart);
							success = true;
							request.setAttribute("displayMessage",
									"You are now logged in.");
						} else
							request.setAttribute(errorMessageDestination,
									"You have entered an invalid username or password.");
					} else {

						request.setAttribute(errorMessageDestination,
								"This user does not exist.");
					}
				} else {
					request.setAttribute(errorMessageDestination,
							"Please enter a username and password.");
				}

			}
		}
		return success;
	}
}
