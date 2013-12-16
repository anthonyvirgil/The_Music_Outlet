// Venelin Koulaxazov
// 1032744
// UpdateClientServlet.java
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

import dbManager.DatabaseManager;

import util.ClientValidator;

/**
 * Servlet implementation class UpdateClientServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "UpdateClientServlet", urlPatterns = { "/updateClient" })
public class UpdateClientServlet extends HttpServlet {

	private static final long serialVersionUID = -8463057363563943919L;

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
		String url = "";
		String servletError = "";
		RequestDispatcher dispatcher;
		ClientValidator clientValidator = new ClientValidator();
		DatabaseManager dbManager = new DatabaseManager();
		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			Client editClient;
			// get all the information the user entered from the
			// editClient.jsp
			String title = request.getParameter("title");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String company = request.getParameter("company");
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");
			String city = request.getParameter("city");
			String province = request.getParameter("province");
			String country = "Canada";
			String postalCode = request.getParameter("postalCode");
			String homePhone = request.getParameter("homePhone");
			String cellPhone = request.getParameter("cellPhone");
			String email = request.getParameter("email");
			String password = request.getParameter("password1");
			boolean status = Boolean.parseBoolean(request
					.getParameter("status"));
			String lastSearch = request.getParameter("lastSearch");

			editClient = new Client(title, lastName, firstName, company,
					address1, address2, city, province, country, postalCode,
					homePhone, cellPhone, email, password, status, lastSearch);

			servletError = clientValidator.validateClient(editClient, password);

			client = dbManager.getClientByEmail(email);
			editClient.setClientId(client.getClientId());

			if (servletError.equals("")) {
				dbManager.updateClient(editClient);
				request.setAttribute("displayMessage",
						"Client successfully edited");
				url = "/admin/index.jsp";
			} else
				url = "/admin/editClient.jsp#contentManag";

			request.setAttribute("existingClient", editClient);
			request.setAttribute("servletError", servletError);
			dispatcher = getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}
	}
}
