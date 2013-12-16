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
 * Servlet used to gather information that will be displayed in an album page
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.6
 */
@WebServlet(name = "DisplayAlbumServlet", urlPatterns = { "/displayAlbum" })
public class DisplayAlbumServlet extends HttpServlet {

	private static final long serialVersionUID = -916550179357018753L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Album album = null;
		ArrayList<Track> albumTracks, allAlbumTracks, otherAlbumTracks = null;
		ArrayList<String> ratingImgPathList = null;
		ArrayList<String> trackPriceList = null;
		ArrayList<TrackReviews> trackReviewsList = null;
		ArrayList<Review> reviews = null;
		ArrayList<String> reviewImgPathList = null;
		ArrayList<String> singleTrackRatingImgList = null;
		PriceUtil priceUtil = new PriceUtil();
		RatingUtil ratingUtil = new RatingUtil();
		String url = "";
		String albumIdParam = request.getParameter("albumId");

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

		if (albumIdParam != null && tryParse(albumIdParam)) {

			// get a handle to the specified Album using the id from the
			// database
			DatabaseManager dbManager = new DatabaseManager();
			int albumId = Integer.parseInt(albumIdParam);
			album = dbManager.getAlbumById(albumId);
			Client client = null;
			boolean canSaveCookie = false;

			if (album != null) {

				synchronized (session) {
					canSaveCookie = (Boolean) session
							.getAttribute("canSaveCookie");
				}
				// save genre of album in a cookie
				String genre = album.getMusicCategory();
				if (canSaveCookie) {
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

				// get reference to all tracks in same album
				allAlbumTracks = dbManager.getTracksByAlbum(album.getAlbumId(),
						true);
				albumTracks = new ArrayList<Track>();
				otherAlbumTracks = new ArrayList<Track>();

				// Separate them between tracks that are sold with the album,
				// and
				// tracks that are only sold individually.
				for (Track t : allAlbumTracks)
					if (t.getTypeOfSale() == 0)
						otherAlbumTracks.add(t);
					else
						albumTracks.add(t);

				// create list of image paths for album tracks' ratings
				ratingImgPathList = ratingUtil
						.createRatingImgPathList(albumTracks);
				singleTrackRatingImgList = ratingUtil
						.createRatingImgPathList(otherAlbumTracks);

				// create list of prices for the tracks in same album
				trackPriceList = priceUtil.createTrackPriceList(albumTracks);

				// create array list of track review beans
				trackReviewsList = new ArrayList<TrackReviews>();
				for (Track track : albumTracks) {
					reviews = dbManager.getApprovedReviewsByTrackId(
							track.getInventoryId(), -1, -1);

					// gather list of paths to images for each review rating
					if (reviews.size() > 0) {
						reviewImgPathList = ratingUtil
								.createReviewImgPathList(reviews);

						trackReviewsList.add(new TrackReviews(track, reviews,
								reviewImgPathList));
					}
				}

				// set request's attributes
				request.setAttribute("album", album);
				request.setAttribute("trackReviews", trackReviewsList);
				request.setAttribute("albumTracks", albumTracks);
				request.setAttribute("otherAlbumTracks", otherAlbumTracks);
				request.setAttribute("ratingImgPathList", ratingImgPathList);
				request.setAttribute("singleTrackRatingImgList",
						singleTrackRatingImgList);
				request.setAttribute("trackPriceList", trackPriceList);

				// determine target url for dispatcher
				url = "/displayAlbum.jsp";

			} else {
				request.setAttribute("displayMessage", "Album does not exist");
				url = "/index";
			}
		} else {
			url = "/index";
			request.setAttribute("displayMessage", "Album does not exist");
		}

		// forward request and response to the view
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
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
