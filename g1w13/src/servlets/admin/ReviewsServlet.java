package servlets.admin;

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
import beans.Review;

/**
 * Servlet implementation class ReviewServlet
 * 
 * @author Natacha Gabbamonte 0932340
 */
@WebServlet(name = "ReviewsServlet", urlPatterns = { "/admin/reviews" })
public class ReviewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final int NUM_OF_RECORDS_PER_PAGE = 3;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Client client = null;
		String searchReviewsError = null;
		Integer reviewsTotal = 0;
		int reviewsPage, maxReviewsPage = 0;
		ArrayList<Review> browseReviews = null;
		ArrayList<Review> toBeApprovedReviews = null;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}
		searchReviewsError = null;

		if (client != null && client.getStatus()) {
			DatabaseManager dbManager = new DatabaseManager();
			int numOfReviews = 0;

			// Gets the reviews that have not be approved yet.
			toBeApprovedReviews = dbManager.getReviewsByApprovalStatus(false,
					-1, -1);
			if (toBeApprovedReviews != null) {
				// Updates the number of reviews that will be used in the
				// header.
				numOfReviews = toBeApprovedReviews.size();
				if (toBeApprovedReviews.size() == 0) {
					toBeApprovedReviews = null;
					numOfReviews = 0;
				}
			}
			// If newQuery is false, it means we are changing page.
			String newQuery = request.getParameter("newQuery");

			String reviewsPageStr = request.getParameter("reviewsPage");
			if (reviewsPageStr == null)
				reviewsPageStr = "1";
			if (!tryParse(reviewsPageStr))
				reviewsPage = 1;
			else
				reviewsPage = Integer.parseInt(reviewsPageStr);

			String searchValue = request.getParameter("searchValue");

			String searchType = request.getParameter("searchType");
			if (searchType == null)
				searchType = "trackId";
			if (searchValue == null)
				searchValue = "";

			String searchButtonPress = request.getParameter("search");
			if (searchButtonPress == null)
				searchButtonPress = "Browse all";

			synchronized (session) {
				reviewsTotal = (Integer) session.getAttribute("reviewsTotal");
			}

			if (newQuery == null || newQuery.equals("true")
					|| reviewsTotal == null) {
				ArrayList<Review> allBrowseReviews = null;
				reviewsPage = 1;
				if (searchButtonPress.equals("Search")) {
					if (searchType.equals("clientName")) {
						allBrowseReviews = dbManager
								.searchApprovedReviewsByClientName(searchValue,
										-1, -1);
						reviewsTotal = allBrowseReviews.size();
						browseReviews = getBrowseReviews(allBrowseReviews);
						maxReviewsPage = setMaxPages(reviewsTotal);
					} else {
						searchType = "trackId";
						if (tryParse(searchValue)) {
							allBrowseReviews = dbManager
									.getApprovedReviewsByTrackId(
											Integer.parseInt(searchValue), -1,
											-1);
							reviewsTotal = allBrowseReviews.size();
							browseReviews = getBrowseReviews(allBrowseReviews);
							maxReviewsPage = setMaxPages(reviewsTotal);
						} else {
							browseReviews = null;
							reviewsTotal = 0;
							searchReviewsError = "Invalid track id.";
						}
					}
				} else { // This means we want to browse all
					searchValue = "";
					allBrowseReviews = dbManager.getReviewsByApprovalStatus(
							true, -1, -1);
					reviewsTotal = allBrowseReviews.size();
					browseReviews = getBrowseReviews(allBrowseReviews);
					maxReviewsPage = setMaxPages(reviewsTotal);
				}
			} else {
				// Not a new query, therefore just need to change page.
				maxReviewsPage = setMaxPages(reviewsTotal);
				if (reviewsPage < 1)
					reviewsPage = 1;
				if (reviewsPage > maxReviewsPage)
					reviewsPage = maxReviewsPage;

				int startRecord = reviewsPage * NUM_OF_RECORDS_PER_PAGE
						- NUM_OF_RECORDS_PER_PAGE;

				if (searchButtonPress.equals("Search")) {
					if (searchType.equals("clientName")) {
						browseReviews = dbManager
								.searchApprovedReviewsByClientName(searchValue,
										startRecord, NUM_OF_RECORDS_PER_PAGE);
					} else {
						searchType = "trackId";
						if (tryParse(searchValue)) {
							browseReviews = dbManager
									.getApprovedReviewsByTrackId(
											Integer.parseInt(searchValue),
											startRecord,
											NUM_OF_RECORDS_PER_PAGE);
						} else {
							browseReviews = null;
							reviewsTotal = 0;
							searchReviewsError = "Invalid client id.";
						}
					}
				} else { // This means we are browsing all
					searchValue = "";
					browseReviews = dbManager.getReviewsByApprovalStatus(true,
							startRecord, NUM_OF_RECORDS_PER_PAGE);
				}
			}
			synchronized (session) {
				// Only things stored in the session is the review browse total,
				// and the count of to-be-approved reviews.
				session.setAttribute("numOfReviews", numOfReviews);
				session.setAttribute("reviewsTotal", reviewsTotal);

				request.setAttribute("browseReviews", browseReviews);
				request.setAttribute("reviewsPage", reviewsPage);
				request.setAttribute("maxReviewsPage", maxReviewsPage);
				request.setAttribute("searchType", searchType);
				request.setAttribute("searchValue", searchValue);
				request.setAttribute("search", searchButtonPress);

				request.setAttribute("toBeApprovedReviews", toBeApprovedReviews);

				request.setAttribute("searchReviewsError", searchReviewsError);

			}

			forwardToReviews(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/*
	 * Forwards to the reviews.jsp page.
	 */
	private void forwardToReviews(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/admin/reviews.jsp");
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

	/*
	 * Sets the maximum page numbers for the browsing reviews.
	 */
	private int setMaxPages(Integer reviewsTotal) {
		int maxReviewsPage;
		if (reviewsTotal != null) {
			// Calculating the maximum number of pages from the total.
			maxReviewsPage = reviewsTotal / NUM_OF_RECORDS_PER_PAGE;
			if (reviewsTotal % NUM_OF_RECORDS_PER_PAGE != 0)
				maxReviewsPage++;
		} else
			maxReviewsPage = 0;
		return maxReviewsPage;
	}

	/*
	 * Returns the browse reviews.
	 */
	private ArrayList<Review> getBrowseReviews(
			ArrayList<Review> allBrowseReviews) {
		int reviewsTotal = allBrowseReviews.size();
		ArrayList<Review> browseReviews;
		if (reviewsTotal <= NUM_OF_RECORDS_PER_PAGE)
			browseReviews = allBrowseReviews;
		else {
			browseReviews = new ArrayList<Review>();
			for (int i = 0; i < NUM_OF_RECORDS_PER_PAGE; i++)
				browseReviews.add(allBrowseReviews.get(i));
		}
		return browseReviews;
	}
}
