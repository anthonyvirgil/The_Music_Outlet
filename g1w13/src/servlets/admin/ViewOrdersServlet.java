/*
 * Anthony-Virgil Bermejo
 * 0831360
 * ViewOrdersServlet.java
 */
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
import beans.InvoiceSearch;

/**
 * Servlet that searches for all orders/invoices in the inventory
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.3
 */
@WebServlet(name = "ViewOrdersServlet", urlPatterns = { "/admin/orders" })
public class ViewOrdersServlet extends HttpServlet {

	private static final long serialVersionUID = -2024206584687027082L;

	private final int NUM_OF_RECORDS_PER_PAGE = 10;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewOrdersServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer searchOrdersTotal = 0;
		int maxOrdersPage = 0;
		int ordersPage = 0;
		DatabaseManager dbManager = null;
		ArrayList<InvoiceSearch> orders = null;
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}
		String url = null;

		// check if client exists and has admin rights
		if (client != null && client.getStatus()) {

			// If newQuery is false, it means we are changing pages.
			String newQuery = request.getParameter("newQuery");

			// get the current page being viewed
			String ordersPageStr = request.getParameter("ordersPage");
			if (ordersPageStr == null)
				ordersPageStr = "1";
			if (!tryParse(ordersPageStr))
				ordersPage = 1;
			else
				ordersPage = Integer.parseInt(ordersPageStr);

			// get the value of the search
			String searchValue = request.getParameter("searchValue");
			if (searchValue == null)
				searchValue = "";

			// determine the type of search
			String searchType = request.getParameter("searchType");
			if (searchType == null)
				searchType = "clientEmail";

			dbManager = new DatabaseManager();
			synchronized (session) {
				searchOrdersTotal = (Integer) session
						.getAttribute("searchOrdersTotal");
			}

			if (newQuery == null || newQuery.equals("true")
					|| searchOrdersTotal == null) {

				// query database to get all orders
				orders = queryDatabase(request, response, searchType,
						searchValue, -1, -1, dbManager);

				// set the total number of orders found
				if (orders != null) {
					searchOrdersTotal = orders.size();
				} else {
					searchOrdersTotal = 0;
				}

				// get all orders with a limit of records
				orders = queryDatabase(request, response, searchType,
						searchValue, 0, NUM_OF_RECORDS_PER_PAGE, dbManager);
				ordersPage = 1;

				maxOrdersPage = setMaxPages(searchOrdersTotal, maxOrdersPage);
			} else {
				// This means that we are only changing page, therefore the
				// stored totals, and the sent in pages will be used to
				// determine
				// which selection of records to return.
				maxOrdersPage = setMaxPages(searchOrdersTotal, maxOrdersPage);
				if (ordersPage < 1)
					ordersPage = 1;
				if (ordersPage > maxOrdersPage)
					ordersPage = maxOrdersPage;

				orders = queryDatabase(request, response, searchType,
						searchValue, ordersPage * NUM_OF_RECORDS_PER_PAGE
								- NUM_OF_RECORDS_PER_PAGE,
						NUM_OF_RECORDS_PER_PAGE, dbManager);
			}

			synchronized (session) {
				// set total number of searches found in inventory
				session.setAttribute("searchOrdersTotal", searchOrdersTotal);
			}
			request.setAttribute("searchOrders", orders);
			request.setAttribute("ordersPage", ordersPage);
			request.setAttribute("maxOrdersPage", maxOrdersPage);
			request.setAttribute("searchValue", searchValue);
			request.setAttribute("searchType", searchType);

			url = "/admin/viewOrders.jsp";
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	private ArrayList<InvoiceSearch> queryDatabase(HttpServletRequest request,
			HttpServletResponse response, String searchType,
			String searchValue, int searchStart, int searchSize,
			DatabaseManager dbManager) throws IOException, ServletException {
		ArrayList<InvoiceSearch> orders = null;

		if (searchType.equals("clientEmail")) {
			orders = dbManager.searchInvoicesByEmail(searchValue, searchStart,
					searchSize);
		} else {
			try {
				int saleId = Integer.parseInt(searchValue);
				orders = dbManager.searchInvoicesBySaleId(saleId, searchStart,
						searchSize);
			} catch (NumberFormatException e) {
				orders = dbManager.searchInvoicesByEmail("", searchStart,
						searchSize);
			}
		}

		return orders;
	}

	/*
	 * Sets the maximum page numbers for albums and tracks.
	 */
	private int setMaxPages(Integer searchOrdersTotal, int maxOrdersPage) {
		if (searchOrdersTotal != null) {
			// Calculating the maximum number of pages from the total.
			maxOrdersPage = searchOrdersTotal / NUM_OF_RECORDS_PER_PAGE;
			if (searchOrdersTotal % NUM_OF_RECORDS_PER_PAGE != 0)
				maxOrdersPage++;
		} else
			maxOrdersPage = 0;

		return maxOrdersPage;
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
