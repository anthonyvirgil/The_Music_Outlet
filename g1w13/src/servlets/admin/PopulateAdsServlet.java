// Venelin Koulaxazov
// 1032744
// PopulateAdsServlet.java
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

import beans.BannerAd;
import beans.Client;

import dbManager.DatabaseManager;

/**
 * Servlet implementation class PopulateAdsServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "PopulateAdsServlet", urlPatterns = { "/admin/populateAds" })
public class PopulateAdsServlet extends HttpServlet {

	private static final long serialVersionUID = 2311241905068873832L;

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
		BannerAd currentBannerAd = null;
		ArrayList<BannerAd> otherBannerAds = null;
		String url;
		HttpSession session = request.getSession();
		Client client;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			String type;
			type = request.getParameter("typeOfAd");
			if (type == null)
				type = (String) request.getAttribute("type");

			if (type.equals("bottom")) {
				currentBannerAd = dbManager.getCurrentBottomBannerAd();
				otherBannerAds = dbManager.getOtherBottomBannerAds();
			} else {
				currentBannerAd = dbManager.getCurrentRightBannerAd();
				otherBannerAds = dbManager.getOtherRightBannerAds();
			}

			request.setAttribute("type", type);
			request.setAttribute("currentBanner", currentBannerAd);
			request.setAttribute("otherBannerAds", otherBannerAds);
			url = "/admin/ad.jsp";

			request.setAttribute("type", type);
			dispatcher = getServletContext().getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}
	}
}
