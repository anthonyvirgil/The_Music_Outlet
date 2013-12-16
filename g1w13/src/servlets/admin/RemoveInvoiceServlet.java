// Anthony-Virgil Bermejo
// 0831360
// RemoveInvoiceServlet.java
package servlets.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbManager.DatabaseManager;

import beans.Client;
import beans.Detail;
import beans.Invoice;

/**
 * Servlet that removes an invoice and all details attached to it
 * 
 * @author Anthony-Virgil Bermejo
 * @author 1.0
 */
@WebServlet(name = "RemoveInvoiceServlet", urlPatterns = { "/admin/removeInvoice" })
public class RemoveInvoiceServlet extends HttpServlet {

	private static final long serialVersionUID = -2132820072648636363L;

	public RemoveInvoiceServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		DatabaseManager dbManager = new DatabaseManager();
		String url = null;
		String saleIdString = null;
		String status = null;
		ArrayList<Detail> detailsList = null;
		Client client = null;
		Invoice invoice = null;
		int saleId;

		// get reference to client logged in
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			saleIdString = request.getParameter("saleId");
			status = request.getParameter("status");

			if (saleIdString != null && status != null
					&& tryParse(saleIdString)) {

				// get reference to the sale id
				saleId = Integer.parseInt(saleIdString);

				// get reference to details associated with invoice id
				detailsList = dbManager.getDetailsBySaleId(saleId);

				// get reference to invoice
				invoice = dbManager.getInvoiceBySaleId(saleId);

				if (invoice != null) {

					// if removal status of invoice is false
					if (status.equals("false")) {

						// change all removal status to true for details
						for (Detail detail : detailsList) {
							// change removal status
							detail.setRemovalStatus(true);

							// update detail in database
							dbManager.updateDetailRemovalStatus(detail);
						}

						// change removal status of details to true
						invoice.setRemovalStatus(true);
						dbManager.updateInvoice(invoice);

						// display success message
						request.setAttribute("displayMessage",
								"All items in order removed");
					} else {
						// change all removal status to false for details
						for (Detail detail : detailsList) {

							// change removal status
							detail.setRemovalStatus(false);

							// update detail in database
							dbManager.updateDetailRemovalStatus(detail);
						}

						// change removal status of details to true
						invoice.setRemovalStatus(false);
						dbManager.updateInvoice(invoice);

						// display success message
						request.setAttribute("displayMessage",
								"All items in order re-added");
					}
				}

				url = "/admin/editOrder?saleId=" + saleId;
			} else {
				// error occurred trying to remove item from client
				request.setAttribute("displayMessage", "Invoice does not exist");
				url = "/admin/orders";
			}

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}

	}

	/*
	 * Tries to parse a string to an int.
	 * 
	 * @param str The string
	 * 
	 * @return Whether the string can be parsed into an int.
	 */
	private boolean tryParse(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
