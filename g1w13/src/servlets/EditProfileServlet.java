package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.ClientValidator;

import beans.Cart;
import beans.Client;
import dbManager.DatabaseManager;

/**
 * Servlet implementation class EditProfileServlet
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
@WebServlet(name = "EditProfileServlet", urlPatterns = { "/editProfile" })
public class EditProfileServlet extends HttpServlet {

	private static final long serialVersionUID = -4842120887931572358L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = "/profile.jsp#content";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = "";
		String servletError;
		RequestDispatcher dispatcher;
		ClientValidator clientValidator = new ClientValidator();
		DatabaseManager dbManager = new DatabaseManager();
		HttpSession session = request.getSession();
		Client sessionClient = null;

		synchronized (session) {
			sessionClient = (Client) session.getAttribute("client");
		}

		if (sessionClient != null) {

			Client editClient;
			// get all the information the user entered from the
			// profile.jsp
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

			editClient = new Client(title, lastName, firstName, company,
					address1, address2, city, province, country, postalCode,
					homePhone, cellPhone, sessionClient.getEmail(),
					sessionClient.getPassword(), sessionClient.getStatus(),
					sessionClient.getGenreLastSearch());

			servletError = clientValidator.validateClient(editClient,
					sessionClient.getPassword());

			if (servletError.equals("")) {
				editClient.setClientId(sessionClient.getClientId());
				dbManager.updateClient(editClient);
				synchronized (session) {
					session.setAttribute("client", editClient);
					Cart cart = (Cart) session.getAttribute("cart");
					cart.setProvince(editClient.getProvince());
					session.setAttribute("cart", cart);
				}
				request.setAttribute("displayMessage",
						"Profile successfully edited");
				url = "/index";
			} else
				url = "/profile.jsp#content";

			request.setAttribute("client", editClient);
			request.setAttribute("servletError", servletError);
			dispatcher = getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);

		} else {
			// there is no user logged in, send to index page
			request.setAttribute("displayMessage", "No user to edit profile");
			url = "/index";
			dispatcher = getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
	}
}
