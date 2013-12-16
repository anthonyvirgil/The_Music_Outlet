// Anthony-Virgil Bermejo
// 0831360
// AddAlbumServlet.java
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

/**
 * Servlet that sends the user to the addAlbum jsp
 * 
 * @author Anthony-Virgil Bermejo
 * @author 1.2
 */
@WebServlet(name = "AddAlbumServlet", urlPatterns = { "/admin/addAlbum" })
public class AddAlbumServlet extends HttpServlet {

	private static final long serialVersionUID = 6630284735676989259L;

	public AddAlbumServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		String servletError = null;
		Album album = null;
		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			// get reference to album and message and set them to request
			album = (Album) request.getAttribute("album");
			servletError = (String) request.getAttribute("displayMessage");

			request.setAttribute("album", album);
			request.setAttribute("displayMessage", servletError);

			// determine the url to be forwarded
			url = "/admin/addAlbum.jsp";

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);

		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}
	}
}
