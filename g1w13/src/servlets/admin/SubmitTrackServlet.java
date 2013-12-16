// Anthony-Virgil Bermejo
//0831360
//SubmitTrackServlet.java
package servlets.admin;

import java.io.IOException;
import java.util.Date;

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
 * Servlet that adds a track to the inventory with required information
 * 
 * @author Anthony-Virgil Bermejo
 * @author 1.0
 */
@WebServlet(name = "SubmitTrackServlet", urlPatterns = { "/admin/submitTrack" })
public class SubmitTrackServlet extends HttpServlet {

	private static final long serialVersionUID = -4006466242962128658L;

	public SubmitTrackServlet() {
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
		String albumError = null;
		String displayMessage = null;
		Track track = null;
		Track existingTrack = null;
		Album existingAlbum = null;
		Client client = null;
		DatabaseManager dbManager = new DatabaseManager();
		TrackValidator trackValidator = new TrackValidator();

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			boolean removalStatus = false;
		
			// get all values inputted in the form
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

				// create a new track with form values
				track = new Track(0, trackTitle, artist, songWriter,
						songLength, 0, genre, "", costPrice, listPrice,
						salePrice, new Date(), typeOfSale, removalStatus);

				// get reference to a track using same artist
				existingTrack = dbManager.getTrackByArtistAndTitle(artist,
						trackTitle);

				// existing track doesn't exist
				if (existingTrack == null) {

					// check if album is already in database
					existingAlbum = dbManager.getAlbumByArtistAndTitle(artist,
							albumTitle);

					// album exists in database
					if (existingAlbum != null) {
						// set album Id and cover to that of the existing album
						track.setAlbumId(existingAlbum.getAlbumId());
						track.setAlbumCover(existingAlbum.getAlbumCover());

						// set selection number of track to the size of album +
						// 1
						track.setSelectionNum(existingAlbum.getNumOfTracks() + 1);

						// update number of tracks in album with added tracks
						existingAlbum.setNumOfTracks(existingAlbum
								.getNumOfTracks() + 1);

						// validate the track object
						servletError = trackValidator.validateTrack(track);

						if (servletError == null) {
							// update album in the database
							dbManager.updateAlbum(existingAlbum);

							// insert track into the database
							dbManager.insertTrack(track);

							displayMessage = "Track successfully added to inventory";
							request.setAttribute("displayMessage",
									displayMessage);
						} else {
							// there was an error, display message to user
							request.setAttribute("track", track);
							request.setAttribute("albumName", albumTitle);
							request.setAttribute("displayMessage", servletError);
						}
					} else {
						// album does not exist, user must create an album first
						albumError = "Album does not exist, you must create one first.";
						request.setAttribute("track", track);
						request.setAttribute("albumName", albumTitle);
						request.setAttribute("albumError", albumError);
					}

					url = "/admin/addTrack";
				} else {
					// track already exists in the database, display message
					servletError = "That track already exists in the database";
					url = "/admin/addTrack";
					request.setAttribute("track", track);
					request.setAttribute("albumName", albumTitle);
					request.setAttribute("displayMessage", servletError);
				}
			} else {
				// error parsing prices and type of sale
				servletError = "Unable to add track to inventory";
				url = "/admin/addTrack";
				request.setAttribute("track", track);
				request.setAttribute("albumName", albumTitle);
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
