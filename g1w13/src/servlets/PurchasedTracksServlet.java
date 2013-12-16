// Venelin Koulaxazov
// 1032744
// PurchasedTracksServlet.java
package servlets;

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
import beans.Track;

/**
 * Servlet implementation class PurchasedTracksServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "PurchasedTracksServlet", urlPatterns = { "/purchasedTracks" })
public class PurchasedTracksServlet extends HttpServlet {

	private static final long serialVersionUID = 1711745028978319295L;

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
		String url = "";
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		DatabaseManager dbManager = new DatabaseManager();
		Client sessionClient = null;

		synchronized (session) {
			sessionClient = (Client) session.getAttribute("client");
		}
		ArrayList<Track> tracks = null;

		if (sessionClient == null) {
			url = "/login.jsp";
			request.setAttribute("errorMessage",
					"You must be logged in to view your downloads");
		} else {
			url = "/downloads.jsp";
			tracks = dbManager.getPurchasedTracksByClient(sessionClient
					.getClientId());
			request.setAttribute("purchasedTracks", tracks);
		}
		dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
