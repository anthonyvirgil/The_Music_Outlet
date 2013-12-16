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
 * Servlet implementation class ChangeApprovalReviewServlet
 * 
 * @author Natacha Gabbamonte 0932340
 */
@WebServlet(name = "ChangeApprovalReviewServlet", urlPatterns = { "/admin/changeApprovalReview" })
public class ChangeApprovalReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeApprovalReviewServlet() {
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
		String approval = request.getParameter("approval");
		HttpSession session = request.getSession();
		Client client = null;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			if (reviewIdStr != null && approval != null) {
				if (tryParse(reviewIdStr)) {
					Boolean approvalBool = null;
					if (approval.equalsIgnoreCase("false"))
						approvalBool = false;
					else if (approval.equalsIgnoreCase("true"))
						approvalBool = true;
					if (approvalBool != null) {
						int reviewId = Integer.parseInt(reviewIdStr);
						DatabaseManager dbManager = new DatabaseManager();
						if (dbManager.updateReviewApprovalStatus(reviewId,
								approvalBool))

							displayMessage = approvalBool ? "Review has been approved!"
									: "Review has been disapproved!";
						else
							displayMessage = "Change of approval status was unsuccessful.";
					} else
						displayMessage = "Could not change approval status, invalid approval status.";
				} else {
					displayMessage = "Could not change approval status, invalid ID.";
				}
			} else
				displayMessage = "Could not change approval status, parameter missing.";

			url = "/admin/reviews";

			request.setAttribute("displayMessage", displayMessage);
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
