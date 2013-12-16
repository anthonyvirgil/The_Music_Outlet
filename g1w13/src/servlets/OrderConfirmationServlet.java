/*
 * Anthony-Virgil Bermejo
 * 0831360
 * OrderConfirmationServlet.java
 */
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
import beans.CreditCard;

/**
 * Servlet that gathers information to be displayed in the order confirmation
 * page
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.3
 * 
 */
@WebServlet(name = "OrderConfirmationServlet", urlPatterns = { "/orderConfirmation" })
public class OrderConfirmationServlet extends HttpServlet {

	private static final long serialVersionUID = -4911129758320913917L;

	public OrderConfirmationServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = null;
		Cart cart = null;
		Client client = null;
		CreditCard creditCard = null;
		String url = "";
		String displayMessage = "";

		// get reference to session object
		session = request.getSession();

		// get reference to objects whose information will be displayed

		// get cart and client objects
		synchronized (session) {
			cart = (Cart) session.getAttribute("cart");
			client = (Client) session.getAttribute("client");
		}

		// get credit card of client
		creditCard = (CreditCard) request.getAttribute("creditCard");

		if (cart != null && client != null && creditCard != null) {

			request.setAttribute("creditCard", creditCard);

			url = "/orderConfirmation.jsp";

		} else {
			// cart or client is null, send back to index
			displayMessage = "You must be logged in to continue";
			url = "/login.jsp";
			request.setAttribute("errorMessage", displayMessage);
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
