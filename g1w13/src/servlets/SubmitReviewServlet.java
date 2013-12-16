// Anthony-Virgil Bermejo
// 0831360
// SubmitReviewServlet.java
package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Client;
import beans.Review;

import dbManager.DatabaseManager;

/**
 * Servlet used validate the submission of a review form
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
@WebServlet(name = "SubmitReviewServlet", urlPatterns = { "/submitReview" })
public class SubmitReviewServlet extends HttpServlet {

	private static final long serialVersionUID = 4547717099683139263L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Client client = null;
		String servletError = null;
		String titleError = null;
		String url = null;
		Review review = null;
		Review clientReview = null;
		DatabaseManager dbManager = new DatabaseManager();

		// get reference to the session object
		HttpSession session = request.getSession();

		// get references to form values
		String reviewTitle = request.getParameter("title");
		String ratingParam = request.getParameter("rating");
		String trackIdParam = request.getParameter("trackId");
		String reviewText = request.getParameter("comments");

		if (trackIdParam != null && tryParse(trackIdParam)
				&& ratingParam != null && tryParse(ratingParam)) {

			int trackId = Integer.parseInt(request.getParameter("trackId"));
			int rating = Integer.parseInt(request.getParameter("rating"));

			synchronized (session) {
				client = (Client) session.getAttribute("client");
			}
			// check if client is logged in
			if (client != null && !reviewTitle.equals("")) {

				// check if the client has a review associated with track
				clientReview = dbManager.getReviewsByClientAndTrack(
						client.getClientId(), trackId);

				if (clientReview != null) {
					// create new review object using form values
					review = new Review(clientReview.getReviewId(), trackId,
							client.getClientId(), new Date(),
							client.getFirstName() + " " + client.getLastName(),
							rating, reviewTitle, reviewText, false);

					// update review in database
					dbManager.updateReview(review);
				} else {
					// create new review object using form values
					review = new Review(trackId, client.getClientId(),
							new Date(), client.getFirstName() + " "
									+ client.getLastName(), rating,
							reviewTitle, reviewText, false);

					// insert review into database
					dbManager.insertReview(review);
				}

				// display track page with associated trackId
				request.setAttribute("trackId", trackId);
				request.setAttribute("displayMessage",
						"Your review has been submitted for approval");
				url = "/displayTrack";
			}
			// no title was entered
			else if (reviewTitle.equals("")) {
				// set an error message to be displayed
				titleError = "You must enter a title";
				request.setAttribute("titleError", titleError);
				url = "/addEditReview";
			}
			// no client is logged in
			else {
				// set error message to be displayed
				servletError = "You must be logged in to submit a review";
				request.setAttribute("errorMessage", servletError);
				url = "/login.jsp";
			}
		} else {
			request.setAttribute("errorMessage", "Error submitting review");
			url = "/addEditReview";
		}

		// forward request and response to the view
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
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
