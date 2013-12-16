package servlets.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.TrackValidator;

import beans.Album;
import beans.Client;
import beans.Track;
import dbManager.DatabaseManager;

/**
 * Servlet that updates the sale price of a track
 * 
 * @author Dorian Mein
 */
@WebServlet(name = "UpdateSaleTrackServlet", urlPatterns = { "/admin/updateSaleTrack" })
public class UpdateSaleTrackServlet extends HttpServlet {
	private static final long serialVersionUID = 634590091L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateSaleTrackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// create a new track
		Track track = null;
		Album album = null;
		Double saleVal = null;
		String url = null;
		String servletError = null;
		String displayMessage = null;
		String trackIdStr = request.getParameter("trackId");
		String salePriceStr = request.getParameter("salePrice");
		TrackValidator trackValidator = new TrackValidator();

		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			if (trackIdStr != null && tryParseInt(trackIdStr)) {
				// get the track for the id we have in parameters in the
				// database
				DatabaseManager dbManag = new DatabaseManager();
				Integer trackId = Integer.parseInt(trackIdStr);
				track = dbManag.getTrackById(trackId);

				if (salePriceStr != null && tryParseDouble(salePriceStr)) {
					saleVal = Double.parseDouble(salePriceStr);

					// We test if we have an track for this Id
					if (track != null && saleVal != null) {

						// get album of track
						album = dbManag.getAlbumById(track.getAlbumId());

						// test if the sale have a value and if it's good
						track.setSalePrice(saleVal);
						servletError = trackValidator.validateTrack(track);

						if (servletError == null) {
							// edit track in database
							dbManag.updateTrack(track);
							displayMessage = "Track successfully edited";
							request.setAttribute("track", track);
							request.setAttribute("album", album);
							request.setAttribute("displayMessage",
									displayMessage);
						} else {
							// there was an error, display message to user
							request.setAttribute("track", track);
							request.setAttribute("album", album);
							request.setAttribute("displayMessage", servletError);
						}
					}
				} else {
					// error parsing sale price
					request.setAttribute("track", track);
					request.setAttribute("album", album);
					request.setAttribute("displayMessage",
							"Error updating sale price");
				}

				// url to dispatch
				url = "/admin/editSaleTrack.jsp";
			} else {
				// track does not exist
				request.setAttribute("displayMessage", "Track does not exist");
				url = "/admin/index.jsp";
			}

			// Dispatcher
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
	 * Tries to parse a string to an int.
	 * 
	 * @param str The string
	 * 
	 * @return Whether the string can be parsed into an int.
	 */
	private boolean tryParseInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/*
	 * Tries to parse a string to a double.
	 * 
	 * @param str The string
	 * 
	 * @return Whether the string can be parsed into a double.
	 */
	private boolean tryParseDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
