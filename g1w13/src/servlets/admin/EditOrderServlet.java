// Anthony-Virgil Bermejo
// 0831360
// AddAlbumServlet.java
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
import beans.Invoice;
import beans.InvoiceAlbum;
import beans.InvoiceTrack;

/**
 * Servlet implementation class EditOrderServlet
 * 
 * @author Anthony-Virgil Bermejo
 * @author 1.3
 */
@WebServlet(name = "EditOrderServlet", urlPatterns = { "/admin/editOrder" })
public class EditOrderServlet extends HttpServlet {

	private static final long serialVersionUID = -7473864639992874981L;

	public EditOrderServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = null;
		Client client = null;
		Client invoiceClient = null;
		Invoice invoice = null;
		int saleId;
		DatabaseManager dbManager = new DatabaseManager();
		ArrayList<InvoiceTrack> invoiceTracks = null;
		ArrayList<InvoiceAlbum> invoiceAlbums = null;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			String saleIdString = request.getParameter("saleId");

			if (saleIdString != null && tryParse(saleIdString)) {
				
				// get reference to the invoice
				saleId = Integer.parseInt(saleIdString);
				invoice = dbManager.getInvoiceBySaleId(saleId);

				if (invoice != null) {
					// invoice exists

					// get reference to client of invoice
					invoiceClient = dbManager.getClientByClientId(invoice
							.getClientId());

					// get reference to the items from invoice
					invoiceTracks = dbManager
							.getTracksFromDetailsBySaleId(saleId);
					invoiceAlbums = dbManager
							.getAlbumsFromDetailsBySaleId(saleId);

					// check if all items are removed, change status of invoice
					boolean removedOrder = true;
					for (InvoiceTrack invoiceTrack : invoiceTracks) {
						if (!invoiceTrack.isRemovalStatus()) {
							removedOrder = false;
							break;
						}
					}
					for (InvoiceAlbum invoiceAlbum : invoiceAlbums) {
						if (!invoiceAlbum.isRemovalStatus()) {
							removedOrder = false;
							break;
						}
					}

					// if all items in invoice are removed, change status of
					// invoice
					if (removedOrder) {
						invoice.setRemovalStatus(true);
						dbManager.updateInvoice(invoice);
					}

					// set request attributes
					request.setAttribute("invoice", invoice);
					request.setAttribute("invoiceClient", invoiceClient);
					request.setAttribute("invoiceTracks", invoiceTracks);
					request.setAttribute("invoiceAlbums", invoiceAlbums);
				}
			}

			url = "/admin/editOrder.jsp";

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
