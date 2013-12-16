// Venelin Koulaxazov
// 1032744
// DownloadsServlet.java
package servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Track;

import dbManager.DatabaseManager;

/**
 * Servlet implementation class DownloadsServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "DownloadsServlet", urlPatterns = { "/downloads" })
public class DownloadsServlet extends HttpServlet {

	private static final long serialVersionUID = 6739952027871668055L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DatabaseManager dbManager = new DatabaseManager();
		ServletContext sc = getServletContext();
		String path = sc.getRealPath("/");
		String trackIdParam = request.getParameter("trackId");
		String url = null;

		if (trackIdParam != null && tryParse(trackIdParam)) {

			Track track = dbManager
					.getTrackById(Integer.parseInt(trackIdParam));

			if (track != null) {
				String name = track.getTrackTitle();
				response.setContentType("application/octet-stream");
				response.setHeader("content-disposition",
						"attachment; filename=\"" + name + "\"");

				FileInputStream in = new FileInputStream(path
						+ "/download/filter.mp3");
				PrintWriter out = response.getWriter();

				int i = in.read();
				while (i != -1) {
					out.write(i);
					i = in.read();
				}
				in.close();
				out.close();
			} else {
				request.setAttribute("displayMessage", "Track does not exist");
				url = "/index";

				forwardToPage(request, response, url);
			}
		} else {
			request.setAttribute("displayMessage", "Track does not exist");
			url = "/index";

			forwardToPage(request, response, url);
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
	 * Forwards the request and response to the specified url
	 */
	private void forwardToPage(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException,
			ServletException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
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
