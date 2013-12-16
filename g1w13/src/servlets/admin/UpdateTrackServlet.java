// Anthony-Virgil Bermejo
// 0831360
// UpdateTrackServlet.java
package servlets.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.TrackValidator;

import dbManager.DatabaseManager;

import beans.Album;
import beans.Client;
import beans.Track;

/**
 * Servlet that updates a track in the inventory 
 * 
 * @author Anthony-Virgil Bermejo
 * @author 1.2
 */
@WebServlet(name = "UpdateAlbumServlet", urlPatterns = { "/admin/updateTrack" })
public class UpdateTrackServlet extends HttpServlet {

	private static final long serialVersionUID = 7449838572655453436L;

	public UpdateTrackServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = null;
		String servletError = null;
		String displayMessage = null;
		Track oldTrack = null;
		Track track = null;
		Album album = null;
		DatabaseManager dbManager = null;
		Client client = null;
		String albumError = null;
		TrackValidator trackValidator = new TrackValidator();

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			// get values of the form
			boolean removalStatus = false;
			String albumTitle = request.getParameter("albumTitle");
			String trackTitle = request.getParameter("trackTitle");
			String artist = request.getParameter("artist");
			String songWriter = request.getParameter("songWriter");
			String songLength = request.getParameter("songLength");
			String genre = request.getParameter("genre");
			String costPriceString = request.getParameter("costPrice");
			String listPriceString = request.getParameter("listPrice");
			String salePriceString = request.getParameter("salePrice");
			String typeOfSaleString = request.getParameter("typeOfSale");
			String removalStatusString = request.getParameter("removalStatus");

			if (costPriceString != null && listPriceString != null
					&& salePriceString != null && typeOfSaleString != null
					&& tryParseDouble(costPriceString)
					&& tryParseDouble(salePriceString)
					&& tryParseDouble(listPriceString)
					&& tryParseInt(typeOfSaleString)) {

				double costPrice = Double.parseDouble(costPriceString);
				double listPrice = Double.parseDouble(listPriceString);
				double salePrice = Double.parseDouble(salePriceString);
				int typeOfSale = Integer.parseInt(typeOfSaleString);

				if (removalStatusString.equals("0"))
					removalStatus = false;
				else
					removalStatus = true;

				dbManager = new DatabaseManager();

				// get reference to the track being edited
				int trackId = Integer.parseInt(request.getParameter("trackId"));
				oldTrack = dbManager.getTrackById(trackId);

				// check if track exists in database
				if (oldTrack != null) {

					// get reference to album of track being edited
					album = dbManager.getAlbumByArtistAndTitle(artist,
							albumTitle);

					// album exists in database
					if (album != null) {
						// create new track object with user-defined fields
						track = new Track(oldTrack.getInventoryId(),
								album.getAlbumId(), trackTitle, artist,
								songWriter, songLength,
								oldTrack.getSelectionNum(), genre,
								album.getAlbumCover(), costPrice, listPrice,
								salePrice, oldTrack.getDateEntered(),
								typeOfSale, removalStatus);

						// validate the track object
						servletError = trackValidator.validateTrack(track);

						if (servletError == null) {
							// edit track in database
							dbManager.updateTrack(track);

							displayMessage = "Track successfully edited";
							request.setAttribute("displayMessage",
									displayMessage);
						} else {
							// there was an error, display message to user
							request.setAttribute("track", track);
							request.setAttribute("displayMessage", servletError);
						}
					} else {
						// album does not exist, user must create the album
						// first
						albumError = "Album does not exist, you must create one first.";
						request.setAttribute("track", track);
						request.setAttribute("albumError", albumError);
					}

					url = "/admin/editTrack";

				} else {
					// track to edit does not exist in database
					request.setAttribute("displayMessage",
							"Track to edit does not exist");
					url = "/admin/index.jsp";
				}
			} else {
				// error parsing prices and type of sale
				servletError = "Unable to update track to inventory";
				url = "/admin/editTrack";
				request.setAttribute("track", track);
				request.setAttribute("displayMessage", servletError);
			}

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);

		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}

	}

	/*
	 * Tries to parse a string to an double.
	 * 
	 * @param str The string
	 * 
	 * @return Whether the string can be parsed into an double.
	 */
	private boolean tryParseDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
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
