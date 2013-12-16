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
 * Servlet implementation class AddRssServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "AddRssServlet", urlPatterns = { "/AddRssServlet" })
public class AddRssServlet extends HttpServlet {

	private static final long serialVersionUID = 4849658668666486497L;

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
		RequestDispatcher dispatcher;
		String url = null;
		String name = (String) request.getParameter("name");
		String link = (String) request.getParameter("link");
		String servletError = null;
		Client client = null;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			if (name == null || link == null)
				servletError = "Please enter the required information";
			else {
				Rss rss = new Rss(name, link, false);
				if (dbManager.insertRssFeed(rss)) {
					name = "";
					link = "";
				}
			}
			request.setAttribute("name", name);
			request.setAttribute("link", link);
			request.setAttribute("servletError", servletError);
			url = "/admin/populateRss";
			dispatcher = getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}

	}

}
