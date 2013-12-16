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
import beans.Rss;
import dbManager.DatabaseManager;

/**
 * Servlet implementation class UpdateRssServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "UpdateRssServlet", urlPatterns = { "/admin/updateRss" })
public class UpdateRssServlet extends HttpServlet {

	private static final long serialVersionUID = -6163353999009854310L;

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
		DatabaseManager dbManager = new DatabaseManager();
		HttpSession session = request.getSession();
		Client client = null;
		String url = null;
		String rssFeedId = request.getParameter("rssFeedId");
		RequestDispatcher dispatcher;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			if (rssFeedId != null && tryParseInt(rssFeedId)) {
				Rss currentRss = dbManager.getCurrentRssFeed();
				currentRss.setStatus(false);
				dbManager.updateRssFeed(currentRss);
				url = "/admin/populateRss";
				Rss rss = dbManager.getRssFeedById(Integer.parseInt(rssFeedId));
				rss.setStatus(true);
				dbManager.updateRssFeed(rss);
			} else {
				// rss does not exist
				request.setAttribute("displayMessage",
						"Rss feed does not exist");
				url = "/admin/index.jsp";
			}

			dispatcher = getServletContext().getRequestDispatcher(url);
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
	private boolean tryParseInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
