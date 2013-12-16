// Anthony-Virgil Bermejo
//0831360
//EditTrackServlet.java
package servlets.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbManager.DatabaseManager;

import beans.Album;
import beans.Client;
import beans.Track;

/**
 * Servlet that sends the user to the editTrack jsp
 * 
 * @author Anthony-Virgil Bermejo
 * @author 1.3
 */
@WebServlet(name = "EditTrackServlet", urlPatterns = { "/admin/editTrack" })
public class EditTrackServlet extends HttpServlet {

	private static final long serialVersionUID = -8575760678339506285L;

	public EditTrackServlet() {
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
		Track track = null;
		Album album = null;
		Client client = null;
		DatabaseManager dbManager = new DatabaseManager();
		String trackIdStr = request.getParameter("trackId");

		// get reference to client logged in
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			if (trackIdStr != null && tryParse(trackIdStr)) {
				// get a handle to track using the id from the database
				dbManager = new DatabaseManager();
				int trackId = Integer.parseInt(trackIdStr);
				track = dbManager.getTrackById(trackId);

				if (track != null) {
					// track exists in database

					// get reference to the track's album
					album = dbManager.getAlbumById(track.getAlbumId());

					request.setAttribute("track", track);
					request.setAttribute("album", album);
					request.setAttribute("displayMessage",
							(String) request.getAttribute("displayMessage"));
					url = "/admin/editTrack.jsp";

				} else {
					// track does not exist in database
					request.setAttribute("displayMessage",
							"Album does not exist");
					url = "/admin/index.jsp";
				}
			} else {
				// track does not exist in database
				request.setAttribute("displayMessage", "Album does not exist");
				url = "/admin/index.jsp";
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
