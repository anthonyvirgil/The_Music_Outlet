package servlets.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Album;
import beans.Client;
import dbManager.DatabaseManager;

/**
 * Servlet implementation class EditSaleAlbumServlet
 * 
 * @author Dorian Mein
 */
@WebServlet(name = "EditSaleAlbumServlet", urlPatterns = { "/admin/editSaleAlbum" })
public class EditSaleAlbumServlet extends HttpServlet {
	private static final long serialVersionUID = 1532631L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		HttpSession session = request.getSession();
		Client client = null;
		String albumIdStr = request.getParameter("albumId");
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			// create a new album
			Album album = null;

			// get the album for the id we have in parameters in the database
			DatabaseManager dbManag = new DatabaseManager();

			if (albumIdStr != null && tryParse(albumIdStr)) {

				// Get album
				Integer albumId = Integer.parseInt(albumIdStr);
				album = dbManag.getAlbumById(albumId);

				// We test if we have an album for this Id
				if (album != null) {
					request.setAttribute("album", album);
				}

				// url to dispatch
				url = "/admin/editSaleAlbum.jsp";
			} else {
				// album does not exist in database
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
