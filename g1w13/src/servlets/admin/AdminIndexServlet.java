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
 * Servlet that sends the user to the admin index page
 * 
 */
@WebServlet(name = "AdminIndexServlet", urlPatterns = { "/admin/index" })
public class AdminIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminIndexServlet() {
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
		String url = null;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			
			// get number of reviews ready for approval
			int numOfReviews = getNumOfReviews();
			synchronized (session) {
				session.setAttribute("numOfReviews", numOfReviews);
			}
			
			// determine page to forward to
			url = "/admin/index.jsp";

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
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
	 * Gets the number of reviews that have not been approved yet.
	 */
	private int getNumOfReviews() {
		int numOfReviews = 0;
		DatabaseManager dbManager = new DatabaseManager();
		ArrayList<Review> reviews = dbManager.getReviewsByApprovalStatus(false,
				-1, -1);
		if (reviews != null)
			numOfReviews = reviews.size();
		return numOfReviews;
	}

}
