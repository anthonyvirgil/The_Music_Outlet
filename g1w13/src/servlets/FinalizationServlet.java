// Venelin Koulaxazov
// 1032744
// FinalizationServlet.java
package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.CreditCard;

import util.CreditCardValidator;

/**
 * Servlet implementation class FinalizationServlet
 * 
 * @author Venelin Koulaxazov
 * @version 1.1
 */
@WebServlet(name = "FinalizationServlet", urlPatterns = { "/finalization" })
public class FinalizationServlet extends HttpServlet {

	private static final long serialVersionUID = 7714994912572600436L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String referer = request.getHeader("Referer");
		String url = "/index";
		// if coming from shopping cart, send to jsp
		if (referer.contains("/g1w13/verifyCart")
				|| referer.contains("/g1w13/removeConflicts")) {
			url = "/finalization.jsp";
		} else {
			request.setAttribute("displayMessage",
					"Cannot access this page from there");
		}
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = "/index";
		String referer = request.getHeader("Referer");
		// if coming from shopping cart, send to jsp
		if (referer.contains("/g1w13/cart")) {
			url = "/finalization.jsp";
		}
		// if coming from finalization, cart or conflicts
		else if (referer.contains("/g1w13/finalization")
				|| referer.contains("/g1w13/verifyCart")
				|| referer.contains("/g1w13/removeConflicts")) {

			// get reference to object that validates credit card
			CreditCardValidator creditCardValidator = new CreditCardValidator();
			CreditCard creditCard;

			// get all the information the user entered from the
			// finalization.jsp
			String cardHolder = request.getParameter("creditCardName");
			String cardNumber = request.getParameter("creditCardNumber");
			String expirationMonth = request.getParameter("month");
			String expirationYear = request.getParameter("year");
			String cardSecurityCode = request.getParameter("cardSecurityCode");

			creditCard = new CreditCard(cardHolder, cardNumber,
					expirationMonth, expirationYear, cardSecurityCode);

			if (creditCardValidator.validateCreditCard(creditCard)) {
				// credit card is valid, send to order confirmation page
				url = "/orderConfirmation";
				request.setAttribute("creditCard", creditCard);
			} else {
				// credit card is invalid, send back to finalization page
				url = "/finalization.jsp";
				request.setAttribute("errorMessage",
						"Invalid credit card information");
				request.setAttribute("creditCard", creditCard);
			}
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
}
