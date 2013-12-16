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
import beans.Invoice;

/**
 * Servlet that gathers information to be displayed in the printInvoice page
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.2
 * 
 */
@WebServlet(name = "PrintInvoiceServlet", urlPatterns = { "/printInvoice" })
public class PrintInvoiceServlet extends HttpServlet {

	private static final long serialVersionUID = -1925772394773826051L;

	public PrintInvoiceServlet() {
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
		Invoice invoice = null;
		String url = "";
		String displayMessage = "";

		// get reference to session object
		session = request.getSession();

		// get reference to shopping cart attached to invoice
		synchronized (session) {
			cart = (Cart) session.getAttribute("oldCart");
			invoice = (Invoice) session.getAttribute("invoice");
		}

		if (cart != null && invoice != null) {
			request.setAttribute("cart", cart);
			request.setAttribute("invoice", invoice);

			// remove attribute from session
			synchronized (session) {
				session.removeAttribute("oldCart");
				session.removeAttribute("invoice");
			}

			url = "/printInvoice.jsp";

		} else {
			// cart does not exists, redirect to index with message
			displayMessage = "You do not have an invoice to print";
			request.setAttribute("displayMessage", displayMessage);
			url = "/index";
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
