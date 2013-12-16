package servlets.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Client;

import dbManager.DatabaseManager;

/**
 * Servlet implementation class DeleteReviewServlet
 * 
 * Deletes a review with a certain reviewId from the database.
 * 
 * @author Natacha Gabbamonte 0932340
 */
@WebServlet(name = "DeleteReviewServlet", urlPatterns = { "/admin/deleteReview" })
public class DeleteReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteReviewServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Not supported, therefore redirected to index.
		response.sendRedirect("/g1w13/admin/index");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String displayMessage = null;
		String url = null;
		String reviewIdStr = request.getParameter("reviewId");
		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			if (reviewIdStr != null && tryParse(reviewIdStr)) {
				int reviewId = Integer.parseInt(reviewIdStr);
				DatabaseManager dbManager = new DatabaseManager();
				if (dbManager.removeReviewById(reviewId))
					displayMessage = "Review deleted successfully.";
				else
					displayMessage = "Could not delete the review.";
			} else
				displayMessage = "Could not delete review, invalid review id.";

			request.setAttribute("displayMessage", displayMessage);
			url = "/admin/reviews";

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
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
