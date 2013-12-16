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

import beans.Client;
import beans.Rss;

import dbManager.DatabaseManager;

/**
 * Servlet implementation class PopulateRssServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "PopulateRssServlet", urlPatterns = { "/admin/populateRss" })
public class PopulateRssServlet extends HttpServlet {

	private static final long serialVersionUID = -4836847130439054850L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher;
		DatabaseManager dbManager = new DatabaseManager();
		Rss currentRss = dbManager.getCurrentRssFeed();
		ArrayList<Rss> otherRssFeeds = dbManager.getOtherRssFeeds();
		String url = null;
		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			request.setAttribute("currentRss", currentRss);
			request.setAttribute("otherRssFeeds", otherRssFeeds);
			url = "/admin/feed.jsp";

			dispatcher = getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}

	}

}
