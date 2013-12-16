// Anthony-Virgil Bermejo
// 0831360
// AddTrackServlet.java
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
import beans.Track;

/**
 * Servlet that sends the user to the addTrack jsp
 * 
 * @author Anthony-Virgil Bermejo
 * @author 1.2
 */
@WebServlet(name = "AddTrackServlet", urlPatterns = { "/admin/addTrack" })
public class AddTrackServlet extends HttpServlet {

	private static final long serialVersionUID = 6630284735676989259L;

	public AddTrackServlet() {
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
		String servletError = null;
		Track track = null;
		Client client = null;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {

			track = (Track) request.getAttribute("track");
			servletError = (String) request.getAttribute("displayMessage");

			request.setAttribute("track", track);
			request.setAttribute("displayMessage", servletError);

			url = "/admin/addTrack.jsp";

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}

	}

}
