// Venelin Koulaxazov
// 1032744
// RegisterServlet.java
package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ClientValidator;
import util.EmailSender;

import beans.Client;
import dbManager.DatabaseManager;

/**
 * Servlet implementation class RegisterServlet
 * 
 * @author Venelin Koulaxazov
 * @author 1.1
 */
@WebServlet(name = "RegisterServlet", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = -2874585951964498084L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/register.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = "";
		String servletError;
		String displayMessage = null;
		RequestDispatcher dispatcher;
		DatabaseManager dbManager = new DatabaseManager();
		ClientValidator clientValidator = new ClientValidator();
		EmailSender emailSender = new EmailSender();
		Client client = new Client();

		// check if the email is already used by another use
		String email = request.getParameter("email");

		if (clientValidator.validateEmail(email)) {
			client = dbManager.getClientByEmail(email);
			if (client != null) {
				servletError = "This email is already in use";
				url = "/register.jsp";
			} else {
				// get all the information the user entered from the
				// register.jsp

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
				String password1 = request.getParameter("password1");
				String password2 = request.getParameter("password2");

				client = new Client(title, lastName, firstName, company,
						address1, address2, city, province, country,
						postalCode, homePhone, cellPhone, email, password1,
						false, "");

				servletError = clientValidator
						.validateClient(client, password2);

				if (servletError.equals("")) {
					if (dbManager.insertClient(client)) {
						emailSender
								.sendMail(
										email,
										"The MUSIC Outlet - Successful Registration - DO NOT REPLY",
										"Your registration with The MUSIC Outlet was successfull! \n\n"
												+ "Your username is: "
												+ email
												+ "\n\nYour password is: "
												+ password1
												+ "\n\nEnjoy your online shopping experience on our website"
												+ "\n\nThe MUSIC Outlet team is waiting for you");
						displayMessage = "Your registration was successful";
						url = "/index";
					} else {
						servletError = "Your registration was unsuccessful";
						url = "/register.jsp";
					}
				} else {
					url = "/register.jsp";
				}
			}
		} else {
			servletError = "The provided email is invalid";
			url = "/register.jsp";
		}

		request.setAttribute("registerClient", client);
		request.setAttribute("servletError", servletError);
		request.setAttribute("displayMessage", displayMessage);
		dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}
}
