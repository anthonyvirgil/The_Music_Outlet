// Venelin Koulaxazov
// 1032744
// GetClientServlet.java
package servlets.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Client;

import util.ClientValidator;

import dbManager.DatabaseManager;

/**
 * Servlet implementation class GetClientServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "GetClientServlet", urlPatterns = { "/admin/getClient" })
public class GetClientServlet extends HttpServlet {

	private static final long serialVersionUID = 6146230790895588055L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = "/admin/editClient.jsp#contentManag";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String clientMessage = "";
		String invalidEmail = "";
		RequestDispatcher dispatcher;
		ClientValidator clientValidator = new ClientValidator();
		DatabaseManager dbManager = new DatabaseManager();
		Client editClient = new Client();
		String clientEmail = request.getParameter("clientEmail");

		String url = null;
		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			if (clientValidator.validateEmail(clientEmail)) {
				editClient = dbManager.getClientByEmail(clientEmail);
				if (editClient != null)
					clientMessage = "Information for "
							+ editClient.getFirstName() + " "
							+ editClient.getLastName();
				else
					clientMessage = "There is no client with this email";
			} else
				invalidEmail = "The provided email is invalid";

			request.setAttribute("invalidEmail", invalidEmail);
			request.setAttribute("clientMessage", clientMessage);
			request.setAttribute("existingClient", editClient);
			url = "/admin/editClient.jsp";
			
			dispatcher = getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}

	}

}
