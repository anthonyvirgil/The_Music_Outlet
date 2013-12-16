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

import util.CartUtil;

import beans.Album;
import beans.Cart;
import beans.Client;
import beans.Track;

import dbManager.DatabaseManager;

/**
 * This servlet verifies that the items included in the cart are all valid
 * before forwarding to the finalization page.
 * 
 * @author Natacha Gabbamonte 0932340
 */
@WebServlet(name = "VerifyCartServlet", urlPatterns = { "/verifyCart",
		"/removeConflicts" })
public class VerifyCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VerifyCartServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/g1w13/index");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DatabaseManager dbManager = new DatabaseManager();
		HttpSession session = request.getSession();
		Client client = null;
		Cart cart = null;
		ArrayList<Album> albumConflicts = null;
		ArrayList<Track> trackConflicts = null;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
			cart = (Cart) session.getAttribute("cart");
		}
		String destination = null;

		if (client == null) {
			// They should not be able to get here if they are
			// not logged in.
			response.sendRedirect("/g1w13/userLogin");
		} else if (cart == null)
			// This would be an odd occurence.
			response.sendRedirect("/g1w13/index");
		else {

			String uri = request.getRequestURI();
			if (uri.contains("/removeConflicts")) {
				synchronized (session) {
					albumConflicts = (ArrayList<Album>) session
							.getAttribute("albumConflicts");
					trackConflicts = (ArrayList<Track>) session
							.getAttribute("trackConflicts");
				}
				if (albumConflicts == null && trackConflicts == null)
					destination = "/index";
				else {
					if (albumConflicts != null) {
						for (Album album : albumConflicts)
							cart.removeAlbum(album);
					}
					if (trackConflicts != null) {
						for (Track track : trackConflicts)
							cart.removeTrack(track);
					}
				}
				if (cart.getCount() == 0)
					destination = "/cart";
				else {
					response.sendRedirect("/g1w13/finalization");
					return;
				}
				synchronized (session) {
					session.setAttribute("albumConflicts", null);
					session.setAttribute("trackConflicts", null);
				}
			} else {
				CartUtil cartUtil = new CartUtil(dbManager);
				albumConflicts = new ArrayList<Album>();
				trackConflicts = new ArrayList<Track>();

				for (Track t : cart.getTracks())
					if (cartUtil.doesClientOwnTrack(t, client))
						trackConflicts.add(t);
				for (Album a : cart.getAlbums()) {
					if (cartUtil.doesClientOwnAlbum(a, client))
						albumConflicts.add(a);
					else if (cartUtil
							.doesClientOwnAllTracksFromAlbum(a, client))
						albumConflicts.add(a);
				}

				int count = albumConflicts.size() + trackConflicts.size();
				destination = "/conflicts.jsp";
				if (albumConflicts.size() == 0) {
					albumConflicts = null;
					if (trackConflicts.size() == 0) {
						destination = "/finalization";
						trackConflicts = null;
					}
				} else if (trackConflicts.size() == 0)
					trackConflicts = null;

				synchronized (session) {
					session.setAttribute("albumConflicts", albumConflicts);
					session.setAttribute("trackConflicts", trackConflicts);
					request.setAttribute("totalConflicts", count);
				}
			}
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(destination);
			dispatcher.forward(request, response);
		}
	}
}
