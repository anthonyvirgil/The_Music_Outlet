package servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Client;

import dbManager.DatabaseManager;

/**
 * Servlet implementation class DeleteFromInventoryServlet
 * 
 * @author Natacha Gabbamonte 0932340
 */
@WebServlet(name = "ChangeRemovalStatusServlet", urlPatterns = { "/admin/changeRemovalStatus" })
public class ChangeRemovalStatusServlet extends HttpServlet {

	private static final long serialVersionUID = -4987428287952328180L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeRemovalStatusServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/g1w13/admin/index");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String destination = "/g1w13/admin/index";
		String type = null;
		String idStr = null;
		String removalStatusStr = null;
		type = request.getParameter("type");
		idStr = request.getParameter("id");
		removalStatusStr = request.getParameter("removalStatus");
		Client client = null;
		HttpSession session = request.getSession();

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			if (type != null && idStr != null && tryParse(idStr)
					&& removalStatusStr != null) {
				Boolean removalStatus = null;
				if (removalStatusStr.equals("true"))
					removalStatus = true;
				else if (removalStatusStr.equals("false"))
					removalStatus = false;

				if (removalStatus != null) {
					try {

						int id = Integer.parseInt(idStr);
						DatabaseManager dbManager = new DatabaseManager();
						boolean success = false;

						if (type.equals("album")) {
							success = dbManager.updateAlbumRemovalStatusById(
									id, removalStatus);
						} else if (type.equals("track")) {
							success = dbManager.updateTrackRemovalStatusById(
									id, removalStatus);
						}
						if (success) {
							String referer = request.getHeader("Referer");
							if (referer.contains("/g1w13/admin/viewInv"))
								destination = referer.substring(referer
										.indexOf("/g1w13/admin/viewInv"));
						}
					} catch (NumberFormatException e) {
						// Invalid id, just redirects below.
					}
				}
			}
			response.sendRedirect(destination);
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
