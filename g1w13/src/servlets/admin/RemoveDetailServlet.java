// Anthony-Virgil Bermejo
// 0831360
// RemoveDetailServlet.java
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
 * Servlet that removes a detail record from an invoice
 * 
 * @author Anthony-Virgil Bermejo
 * @author 1.3
 */
@WebServlet(name = "RemoveDetailServlet", urlPatterns = { "/admin/removeDetail" })
public class RemoveDetailServlet extends HttpServlet {

	private static final long serialVersionUID = -2132820072648636363L;

	public RemoveDetailServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		DatabaseManager dbManager = new DatabaseManager();
		String url = null;
		String detailIdString = null;
		String saleIdString = null;
		String status = null;
		Detail detail = null;
		Client client = null;
		int detailId;
		int saleId;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			detailIdString = request.getParameter("detailId");
			saleIdString = request.getParameter("saleId");
			status = request.getParameter("status");

			if (saleIdString != null && tryParse(saleIdString)) {

				// get reference to saleId of the detail
				saleId = Integer.parseInt(saleIdString);

				if (detailIdString != null && status != null
						&& tryParse(detailIdString)) {

					// get reference to the detail id of the item
					detailId = Integer.parseInt(detailIdString);

					// get reference to the detail associated with id
					detail = dbManager.getDetailByDetailId(detailId);

					// change removal status of item in details
					if (status.equals("false"))
						detail.setRemovalStatus(true);
					else
						detail.setRemovalStatus(false);

					// update detail in database
					dbManager.updateDetailRemovalStatus(detail);

					// get list of all details from invoice
					ArrayList<Detail> detailList = dbManager
							.getDetailsBySaleId(saleId);

					// check what the status is of details in invoice
					boolean detailStatus = false;
					for (Detail aDetail : detailList) {
						detailStatus = aDetail.isRemovalStatus();

						// break out if removalStatus is false
						// order is still false
						if (!detailStatus) {
							break;
						}
					}

					// set status of invoice
					Invoice invoice = dbManager.getInvoiceBySaleId(saleId);
					invoice.setRemovalStatus(detailStatus);
					dbManager.updateInvoice(invoice);

					// display success message
					request.setAttribute("displayMessage",
							"Status of item updated");

				} else {
					// error occurred trying to remove item from client
					request.setAttribute("displayMessage",
							"Detail does not exist");
				}

				url = "/admin/editOrder?saleId=" + saleId;
			} else {
				// invoice does not exist
				url = "/admin/orders";
				request.setAttribute("displayMessage", "Invoice does not exist");
			}

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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
