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
 * Servlet implementation class EditSaleTrackServlet
 * 
 * @author Dorian Mein
 */
@WebServlet(name = "EditSaleTrackServlet", urlPatterns = { "/admin/editSaleTrack" })
public class EditSaleTrackServlet extends HttpServlet {
	private static final long serialVersionUID = 154364598184631L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		HttpSession session = request.getSession();
		Client client = null;
		String trackIdStr = request.getParameter("trackId");
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			if (trackIdStr != null && tryParse(trackIdStr)) {
				// create a new track
				Track track = null;

				// create a new album
				Album album = null;

				// get the track for the id we have in parameters in the
				// database
				DatabaseManager dbManag = new DatabaseManager();
				Integer trackId = Integer.parseInt(trackIdStr);
				track = dbManag.getTrackById(trackId);
				album = dbManag.getAlbumById(track.getAlbumId());

				// We test if we have an Track for this Id
				if (track != null) {
					request.setAttribute("track", track);
					request.setAttribute("album", album);
				}
			}
			url = "/admin/editSaleTrack.jsp";

			// Dispatcher
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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
