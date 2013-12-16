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
import beans.BannerAd;

import dbManager.DatabaseManager;

/**
 * Servlet implementation class RemoveBannerAdServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "RemoveBannerAdServlet", urlPatterns = { "/admin/removeBannerAd" })
public class RemoveBannerAdServlet extends HttpServlet {

	private static final long serialVersionUID = -8086917989291359774L;

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
		RequestDispatcher dispatcher;
		String url = null;
		String bannerAdStr = request.getParameter("bannerAdId");
		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			if (bannerAdStr != null && tryParse(bannerAdStr)) {
				String type = request.getParameter("typeOfTheAd");
				BannerAd bannerAd = dbManager.getBannerAdById(Integer
						.parseInt(bannerAdStr));
				dbManager.removeBannerAd(bannerAd);
				url = "/admin/populateAds";
				request.setAttribute("type", type);

			} else {
				// banner does not exist
				request.setAttribute("displayMessage", "Banner does not exist");
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
	private boolean tryParse(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
