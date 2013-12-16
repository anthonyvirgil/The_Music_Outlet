package servlets.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BannerAd;
import beans.Client;

import dbManager.DatabaseManager;

/**
 * Servlet implementation class UpdateBannerAdServlet
 */
@WebServlet(name = "UpdateBannerAdServlet", urlPatterns = { "/admin/updateBannerAd" })
public class UpdateBannerAdServlet extends HttpServlet {

	private static final long serialVersionUID = -1174292971117763791L;

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
		String url = "/admin/populateAds";
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		BannerAd currentBannerAd;
		BannerAd ad;
		String bannerAdId = request.getParameter("bannerAdId");
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			if (bannerAdId != null && tryParseInt(bannerAdId)) {
				String type = request.getParameter("typeOfTheAd");
				if (type.equals("bottom")) {
					currentBannerAd = dbManager.getCurrentBottomBannerAd();
				} else {
					currentBannerAd = dbManager.getCurrentRightBannerAd();
				}
				currentBannerAd.setStatus(false);
				dbManager.updateBannerAd(currentBannerAd);
				ad = dbManager.getBannerAdById(Integer.parseInt(bannerAdId));
				ad.setStatus(true);
				dbManager.updateBannerAd(ad);
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
	private boolean tryParseInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
