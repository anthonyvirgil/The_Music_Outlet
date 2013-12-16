// Anthony-Virgil Bermejo
// 0831360
// DisplayTrackServlet.java
package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.PriceUtil;
import util.RatingUtil;

import beans.Album;
import beans.Client;
import beans.Review;
import beans.Track;
import beans.TrackReviews;

import dbManager.DatabaseManager;

/**
 * Servlet used to gather information that will be displayed in a track page
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.4
 */
@WebServlet(name = "DisplayTrackServlet", urlPatterns = { "/displayTrack" })
public class DisplayTrackServlet extends HttpServlet {

	private static final long serialVersionUID = 1897467273933685584L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Track track = null;
		Album album = null;
		ArrayList<Review> reviews = null;
		ArrayList<Track> albumTracks = null;
		ArrayList<String> ratingImgPathList = null;
		ArrayList<String> reviewImgPathList = null;
		ArrayList<String> trackPriceList = null;
		RatingUtil ratingUtil = new RatingUtil();
		PriceUtil priceUtil = new PriceUtil();
		TrackReviews trackReviews = null;
		double trackRating = 0.0;
		String url = null;
		String trackIdParam = request.getParameter("trackId");

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
		if (trackIdParam != null && tryParse(trackIdParam)) {

			// get a handle to the specified Track using the id from the
			// database
			DatabaseManager dbManager = new DatabaseManager();
			int trackId = Integer.parseInt(trackIdParam);
			track = dbManager.getTrackById(trackId);
			Client client = null;

			if (track != null) {

				Boolean canSaveCookie = (Boolean) session
						.getAttribute("canSaveCookie");
				String genre = track.getMusicCategory();
				// save genre of album in a cookie
				if (canSaveCookie != null && canSaveCookie) {
					// save genre of track in a cookie
					Cookie searchTypeCookie = new Cookie("genre", genre);
					searchTypeCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
					searchTypeCookie.setPath("/");
					response.addCookie(searchTypeCookie);
				}
				// update client with last genre searched
				synchronized (session) {
					client = (Client) session.getAttribute("client");
				}
				if (client != null) {
					client.setGenreLastSearch(genre);
					dbManager.updateClient(client);
				}

				// prepare suggested albums with the genre being searched
				prepareSuggestedAlbums(request, genre, dbManager);

				// get reference to the track's album object
				album = dbManager.getAlbumById(track.getAlbumId());

				// get reference to all tracks in same album
				albumTracks = dbManager.getTracksByAlbum(track.getAlbumId(),
						true);

				// create list of image paths for album tracks' ratings
				ratingImgPathList = ratingUtil
						.createRatingImgPathList(albumTracks);

				// create list of prices for the tracks in same album
				trackPriceList = priceUtil.createTrackPriceList(albumTracks);

				// get reference to all reviews for the track
				reviews = dbManager.getApprovedReviewsByTrackId(
						track.getInventoryId(), -1, -1);

				// gather list of paths to images for each review rating
				if (reviews.size() > 0) {
					reviewImgPathList = ratingUtil
							.createReviewImgPathList(reviews);
					trackReviews = new TrackReviews(track, reviews,
							reviewImgPathList);
				}

				// calculate rating value for the track
				trackRating = ratingUtil
						.calculateRating(track.getInventoryId());

				// set request's attributes
				request.setAttribute("track", track);
				request.setAttribute("album", album);
				request.setAttribute("rating", trackRating);
				request.setAttribute("trackReviews", trackReviews);
				request.setAttribute("albumTracks", albumTracks);
				request.setAttribute("ratingImgPathList", ratingImgPathList);
				request.setAttribute("trackPriceList", trackPriceList);

				// determine target url for dispatcher
				url = "/displayTrack.jsp";
			}
			// track does not exist, redirect to URL
			else {
				request.setAttribute("displayMessage", "Album does not exist");
				url = "/index";
			}
		} else {
			request.setAttribute("displayMessage", "Album does not exist");
			url = "/index";
		}
		// forward request and response to the view
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/*
	 * Gets 3 random albums for the suggested albums with last genre searched
	 */
	private void prepareSuggestedAlbums(HttpServletRequest request,
			String genre, DatabaseManager dbManager) {
		ArrayList<Album> suggestedAlbums = new ArrayList<Album>();
		ArrayList<Album> suggestedAlbumsTemp = null;

		suggestedAlbumsTemp = dbManager
				.searchAlbumsByGenre(genre, -1, -1, true);
		if (suggestedAlbumsTemp.size() > 0) {
			// Gets a random 3 records
			int record1 = -1, record2 = -1, record3 = -1;
			record1 = (int) (Math.random() * suggestedAlbumsTemp.size());
			if (suggestedAlbumsTemp.size() > 1) {
				do {
					record2 = (int) (Math.random() * suggestedAlbumsTemp.size());
				} while (record2 == record1);

				suggestedAlbums.add(suggestedAlbumsTemp.get(record2));
			}
			if (suggestedAlbumsTemp.size() > 2) {
				do {
					record3 = (int) (Math.random() * suggestedAlbumsTemp.size());
				} while (record3 == record1 || record3 == record2);
				suggestedAlbums.add(suggestedAlbumsTemp.get(record3));
			}
			suggestedAlbums.add(suggestedAlbumsTemp.get(record1));
		}

		// set list of suggested albums to session
		HttpSession session = request.getSession();
		synchronized (session) {
			session.setAttribute("suggestedAlbums", suggestedAlbums);
			session.setAttribute("suggestedAlbumsSize", suggestedAlbums.size());
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
