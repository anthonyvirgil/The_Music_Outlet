// Anthony-Virgil Bermejo
// 0831360
// AddEditReviewServlet.java
package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.RatingUtil;

import beans.Album;
import beans.Client;
import beans.Review;
import beans.Track;

import dbManager.DatabaseManager;

/**
 * Servlet used to gather information that will be displayed in the add/edit
 * reviews page
 * 
 * @author Anthony-Virgil Bermejo
 * @version 2.3
 */
@WebServlet(name = "AddEditReviewServlet", urlPatterns = { "/addEditReview" })
public class AddEditReviewServlet extends HttpServlet {

	private static final long serialVersionUID = -7485433769122414082L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Track track = null;
		Album album = null;
		Client client = null;
		Review review = null;
		double trackRating = 0.0;
		RatingUtil ratingUtil = new RatingUtil();
		String url = null;
		String trackIdParam = request.getParameter("trackId");

		// get reference to the session object
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

		// get a handle to the specified Track using the id from the database
		DatabaseManager dbManager = new DatabaseManager();

		// check if parameter is null and that the param is an integer
		if (trackIdParam != null && tryParse(trackIdParam)) {

			// get reference to the track being reviewed
			int trackId = Integer.parseInt(trackIdParam);
			track = dbManager.getTrackById(trackId);

			if (track != null) {

				// get reference to the track's album object
				album = dbManager.getAlbumById(track.getAlbumId());

				// calculate rating value for the track
				trackRating = ratingUtil
						.calculateRating(track.getInventoryId());

				// set request's attributes
				request.setAttribute("track", track);
				request.setAttribute("album", album);
				request.setAttribute("trackRating", trackRating);

				// get client from the sessions
				synchronized (session) {
					client = (Client) session.getAttribute("client");
				}

				if (client != null) {
					// get reference to client's review
					review = dbManager.getReviewsByClientAndTrack(
							client.getClientId(), track.getInventoryId());
					request.setAttribute("review", review);

					// set to add edit review page
					url = "/addEditReview.jsp";
				} else {
					// not logged in, send to index with error message
					url = "/login.jsp";
					request.setAttribute("errorMessage",
							"You must be logged into add a review");
				}

			} else {
				url = "/index";
				request.setAttribute("displayMessage", "Track does not exist");
			}
		} else {
			url = "/index";
			request.setAttribute("displayMessage", "Track does not exist");
		}

		// forward request and response to the view
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
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
