/**
 * This class takes care of the client's Cart. It will display the cart, 
 * add albums or tracks to the cart, and also remove albums or tracks from the cart.
 * @author Natacha Gabbamonte 0932340
 */
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

import dbManager.DatabaseManager;

import beans.Album;
import beans.Cart;
import beans.Client;
import beans.Track;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet(name = "CartServlet", urlPatterns = { "/cart", "/addToCart",
		"/removeFromCart" })
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// Puts addToCart's displayMessage into the request instead of the
		// session
		synchronized (session) {
			String tempDM = (String) session.getAttribute("displayMessage");
			if (tempDM != null) {

				session.setAttribute("displayMessage", null);
				request.setAttribute("displayMessage", tempDM);
			}
		}
		forwardToCart(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// Puts addToCart's displayMessage into the request instead of the
		// session
		synchronized (session) {
			String tempDM = (String) session.getAttribute("displayMessage");
			if (tempDM != null) {
				session.setAttribute("displayMessage", null);

				request.setAttribute("displayMessage", tempDM);
			}
		}

		String uri = request.getRequestURI();
		String displayMessage = null;
		DatabaseManager dbManager = new DatabaseManager();
		Client client = null;
		Cart cart = null;
		synchronized (session) {
			cart = (Cart) session.getAttribute("cart");
			client = (Client) session.getAttribute("client");
		}

		int type = 0;

		if (cart == null) {
			forwardToIndex(request, response);
			return;
		}

		if (uri.contains("/cart")) {
			forwardToCart(request, response);
			return;
		} else if (uri.contains("/addToCart")) {
			type = 1;
			displayMessage = addOrRemove(request, type, client, dbManager, cart);

		} else if (uri.contains("/removeFromCart")) {
			type = 2;
			displayMessage = addOrRemove(request, type, client, dbManager, cart);
		}

		if (type == 1) { // Adding to cart
			if (displayMessage != null)
				synchronized (session) {
					session.setAttribute("displayMessage", displayMessage);
				}
			redirectToRequest(request, response, session);
		} else if (type == 2) { // Removing from cart
			if (displayMessage != null)
				request.setAttribute("displayMessage", displayMessage);
			forwardToCart(request, response);
		}
	}

	/*
	 * Adds or removes an album or track.
	 * 
	 * @param request The request
	 * 
	 * @param type Whether we are adding or removing (Adding=1, Removing=2)
	 * 
	 * @return Whether it was successful
	 */
	private String addOrRemove(HttpServletRequest request, int type,
			Client client, DatabaseManager dbManager, Cart cart) {
		String albumIdStr = (String) request.getParameter("albumId");
		String displayMessage = null;
		CartUtil cartUtil = new CartUtil(dbManager);
		if (albumIdStr != null && tryParse(albumIdStr)) { // Album
			// Adding or removing an Album
			int albumId = Integer.parseInt(albumIdStr);
			switch (type) {
			case 1: // Adding an album
				Album album = dbManager.getAlbumById(albumId);
				if (album != null) {
					if (client != null
							&& cartUtil.doesClientOwnAlbum(album, client))
						displayMessage = "You already own this album!";
					else if (client != null
							&& cartUtil.doesClientOwnAllTracksFromAlbum(album,
									client))
						displayMessage = "You already own all the tracks that come with this album!";
					else {
						synchronized (cart) {
							if (cart.addAlbum(album)) {
								displayMessage = "An album was added to your cart.";
								if (fixAlbumConflicts(album, cart))
									displayMessage += " You had individual songs from that album in your cart, they were removed.";
							} else
								displayMessage = "That album is already in your cart!";
						}
					}
				} else
					displayMessage = "That album does not exist!";
				break;
			case 2: // Removing an album
				synchronized (cart) {
					if (cart.removeAlbum(albumId))
						displayMessage = "Album removed from your cart.";
					else
						displayMessage = "Could not remove the album!";
				}
				break;

			}
		} else { // Track
			String trackIdStr = (String) request.getParameter("trackId");
			if (trackIdStr != null && tryParse(trackIdStr)) {
				// Adding or removing a Track
				int trackId = Integer.parseInt(trackIdStr);
				switch (type) {
				case 1: // Adding a track
					Track track = dbManager.getTrackById(trackId);
					if (track != null) {
						if (client != null
								&& cartUtil.doesClientOwnTrack(track, client))
							displayMessage = "You already own this track!";
						else if (hasTrackConflicts(track, cart)) {
							displayMessage = "You already have the album of that track in your cart! The track was not added.";
						} else {
							synchronized (cart) {
								if (cart.addTrack(track))
									displayMessage = "A track was added to your cart.";
								else
									displayMessage = "That track is already in your cart!";
							}
						}
					} else
						displayMessage = "That track does not exist!";
					break;
				case 2: // Removing a track
					synchronized (cart) {
						if (cart.removeTrack(trackId))
							displayMessage = "Track removed from your cart.";
						else
							displayMessage = "Could not remove the track!";
					}
					break;

				}
			} else
				// The required parameters are not there.
				displayMessage = "Couldn't add or remove from your cart!";

		}
		return displayMessage;
	}

	/*
	 * Finds conflicts with an album, and removes the conflicting tracks.
	 * 
	 * @param album The album
	 * 
	 * @return Returns true if a conflict occurred, false if not.
	 */
	private boolean fixAlbumConflicts(Album album, Cart cart) {
		ArrayList<Track> tracks = cart.getTracks();
		ArrayList<Track> conflicts = new ArrayList<Track>();

		for (int i = 0; i < tracks.size(); i++)
			if (tracks.get(i).getAlbumId() == album.getAlbumId()
					&& tracks.get(i).getTypeOfSale() != 0)
				conflicts.add(tracks.get(i));
		for (Track track : conflicts)
			cart.removeTrack(track);

		return conflicts.size() > 0;
	}

	/*
	 * Checks whether adding a track conflicts with albums that are already
	 * there.
	 * 
	 * @param track
	 * 
	 * @return Whether there is a conflict or not.
	 */
	private boolean hasTrackConflicts(Track track, Cart cart) {
		ArrayList<Album> albums = cart.getAlbums();
		for (Album album : albums)
			if (album.getAlbumId() == track.getAlbumId()
					&& track.getTypeOfSale() != 0)
				return true;
		return false;
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

	/*
	 * Redirects to the Cart JSP.
	 */
	private void forwardToCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/cart.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * Redirects to the Index Servlet.
	 */
	private void forwardToIndex(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/index");
		dispatcher.forward(request, response);
	}

	/*
	 * Redirects to the request if it contains a known page, otherwise forwards
	 * to the index.
	 */
	private void redirectToRequest(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String destination = null;
		String referer = request.getHeader("Referer");
		if (referer.contains("/g1w13/search"))
			destination = referer.substring(referer.indexOf("/g1w13/search"));
		else if (referer.contains("/g1w13/displayAlbum"))
			destination = referer.substring(referer
					.indexOf("/g1w13/displayAlbum"));
		else if (referer.contains("/g1w13/displayTrack"))
			destination = referer.substring(referer
					.indexOf("/g1w13/displayTrack"));
		else if (referer.contains("/g1w13/cart"))
			destination = referer.substring(referer.indexOf("/g1w13/cart"));
		else if (referer.contains("/g1w13/removeFromCart"))
			destination = "/g1w13/cart";
		else if (referer.contains("/g1w13/addEditReview"))
			destination = referer.substring(referer
					.indexOf("/g1w13/addEditReview"));
		else
			forwardToIndex(request, response);

		if (destination != null) {
			response.sendRedirect(destination + "#content");
		}
	}
}
