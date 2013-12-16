// Anthony-Virgil Bermejo
// 0831360
// EditAlbumServlet.java
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

/**
 * Servlet that sends user to the editAlbum page
 * 
 * @author Anthony-Virgil Bermejo
 * @author 2.0
 */
@WebServlet(name = "EditAlbumServlet", urlPatterns = { "/admin/editAlbum" })
public class EditAlbumServlet extends HttpServlet {

	private static final long serialVersionUID = 4954791182464494037L;

	public EditAlbumServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Client client = null;
		String url = null;
		Album album = null;
		DatabaseManager dbManager = null;
		String albumIdParam = request.getParameter("albumId");

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			if (albumIdParam != null && tryParse(albumIdParam)) {

				// get a handle to album using the id from the database
				dbManager = new DatabaseManager();
				int albumId = Integer.parseInt(albumIdParam);
				album = dbManager.getAlbumById(albumId);

				if (album != null) {
					// album exists in database
					request.setAttribute("album", album);
					request.setAttribute("displayMessage",
							(String) request.getAttribute("displayMessage"));
					url = "/admin/editAlbum.jsp";

				} else {
					// album does not exist in database
					request.setAttribute("displayMessage",
							"Album does not exist");
					url = "/admin/index.jsp";
				}
			} else {
				// album does not exist in database
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

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/*
	 * Tries to parse a string into an int.
	 * 
	 * @param str The string
	 * 
	 * @return Whether the string can be parsed into an int or not
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
