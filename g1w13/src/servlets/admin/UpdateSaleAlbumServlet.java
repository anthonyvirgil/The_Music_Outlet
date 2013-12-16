package servlets.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.AlbumValidator;

import beans.Album;
import beans.Client;
import dbManager.DatabaseManager;

/**
 * Servlet that updates the sale price of an album
 * 
 * @author Dorian Mein
 */
@WebServlet(name = "UpdateSaleAlbumServlet", urlPatterns = { "/admin/updateSaleAlbum" })
public class UpdateSaleAlbumServlet extends HttpServlet {
	private static final long serialVersionUID = 67890091L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateSaleAlbumServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// create a new album
		Album album = null;
		Double saleVal = null;
		String url = null;
		String servletError = null;
		String displayMessage = null;
		String albumIdStr = request.getParameter("albumId");
		String salePriceStr = request.getParameter("salePrice");
		AlbumValidator albumValidator = new AlbumValidator();
		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			if (albumIdStr != null && tryParseInt(albumIdStr)) {
				// get the album for the id we have in parameters in the
				// database
				DatabaseManager dbManag = new DatabaseManager();
				Integer albumId = Integer.parseInt(albumIdStr);
				album = dbManag.getAlbumById(albumId);

				if (salePriceStr != null && tryParseDouble(salePriceStr)) {
					saleVal = Double.parseDouble(salePriceStr);

					// We test if we have an album for this Id
					if (album != null) {
						// test if the sale have a value and if it's good
						album.setSalePrice(saleVal);
						servletError = albumValidator.validateAlbum(album);

						if (servletError == null) {
							// edit album in database
							dbManag.updateAlbum(album);
							displayMessage = "Album successfully edited";
							request.setAttribute("album", album);
							request.setAttribute("displayMessage",
									displayMessage);
						} else {
							// there was an error, display message to user
							request.setAttribute("album", album);
							request.setAttribute("displayMessage", servletError);
						}
					}
				} else {
					// there was an error parsing price
					request.setAttribute("album", album);
					request.setAttribute("displayMessage",
							"Error updating sale price");
				}

				// url to dispatch
				url = "/admin/editSaleAlbum.jsp";
			} else {
				// album does not exist
				request.setAttribute("displayMessage", "Album does not exist");
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
