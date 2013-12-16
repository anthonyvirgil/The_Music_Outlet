package servlets.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbManager.DatabaseManager;

import beans.Album;
import beans.Client;
import beans.Track;

/**
 * Servlet implementation class ViewInventoryServlet
 * 
 * @author Natacha Gabbamonte 0932340
 */
@WebServlet(name = "ViewInventoryServlet", urlPatterns = { "/admin/viewInv",
		"/admin/sale" })
public class ViewInventoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final int NUM_OF_RECORDS_PER_PAGE = 10;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewInventoryServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DatabaseManager dbManager = null;
		ArrayList<Track> tracks = null;
		ArrayList<Album> albums = null;
		Integer searchTracksTotal = 0, searchAlbumsTotal = 0;
		int tracksPage, albumsPage, maxTracksPage, maxAlbumsPage;

		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}
		String url = null;
		if (client != null && client.getStatus()) {

			// If newQuery is false, it means we are changing page.
			String newQuery = request.getParameter("newQuery");

			String tracksPageStr = request.getParameter("tracksPage");
			if (tracksPageStr == null)
				tracksPageStr = "1";
			if (!tryParse(tracksPageStr))
				tracksPage = 1;
			else
				tracksPage = Integer.parseInt(tracksPageStr);

			String albumsPageStr = request.getParameter("albumsPage");
			if (albumsPageStr == null)
				albumsPageStr = "1";
			if (!tryParse(albumsPageStr))
				albumsPage = 1;
			else
				albumsPage = Integer.parseInt(albumsPageStr);

			String searchValue = request.getParameter("searchValue");

			String searchType = request.getParameter("searchType");
			if (searchType == null)
				searchType = "Track";
			if (searchValue == null)
				searchValue = "";

			dbManager = new DatabaseManager();
			synchronized (session) {
				searchTracksTotal = (Integer) session
						.getAttribute("searchTracksTotal");
				searchAlbumsTotal = (Integer) session
						.getAttribute("searchAlbumsTotal");
			}

			// If newQuery is null or true, this is a new search. Also, we
			// cannot function with NEITHER totals for albums or tracks (that
			// are
			// suppose to be in the session) therefore if BOTH are not there, we
			// must make
			// a new search.
			if (newQuery == null || newQuery.equals("true")
					|| (searchAlbumsTotal == null && searchTracksTotal == null)) {

				// Getting all records (to calculate totals).
				SearchResults searchResults = queryDatabase(request, response,
						searchType, searchValue, -1, -1, -1, -1, dbManager);
				tracks = searchResults.getTracks();
				albums = searchResults.getAlbums();
				if (tracks != null) {
					searchTracksTotal = tracks.size();
				} else {
					searchTracksTotal = 0;
				}
				if (albums != null) {
					searchAlbumsTotal = albums.size();
				} else {
					searchAlbumsTotal = 0;
				}
				// Getting range of records.
				searchResults = queryDatabase(request, response, searchType,
						searchValue, 0, NUM_OF_RECORDS_PER_PAGE, 0,
						NUM_OF_RECORDS_PER_PAGE, dbManager);
				albums = searchResults.getAlbums();
				tracks = searchResults.getTracks();
				albumsPage = 1;
				tracksPage = 1;

				Integer[] maxPages = setMaxPages(searchAlbumsTotal,
						searchTracksTotal);
				maxAlbumsPage = maxPages[0];
				maxTracksPage = maxPages[1];
			} else {
				// This means that we are only changing page, therefore the
				// stored totals, and the sent in pages will be used to
				// determine
				// which selection of records to return.
				Integer[] maxPages = setMaxPages(searchAlbumsTotal,
						searchTracksTotal);
				maxAlbumsPage = maxPages[0];
				maxTracksPage = maxPages[1];
				if (albumsPage < 1)
					albumsPage = 1;
				if (albumsPage > maxAlbumsPage)
					albumsPage = maxAlbumsPage;
				if (tracksPage < 1)
					tracksPage = 1;
				if (tracksPage > maxTracksPage)
					tracksPage = maxTracksPage;
				SearchResults searchResults = queryDatabase(request, response,
						searchType, searchValue, albumsPage
								* NUM_OF_RECORDS_PER_PAGE
								- NUM_OF_RECORDS_PER_PAGE,
						NUM_OF_RECORDS_PER_PAGE, tracksPage
								* NUM_OF_RECORDS_PER_PAGE
								- NUM_OF_RECORDS_PER_PAGE,
						NUM_OF_RECORDS_PER_PAGE, dbManager);
				albums = searchResults.getAlbums();
				tracks = searchResults.getTracks();
			}

			synchronized (session) {
				// Only the totals are needed for longer than one page. This is
				// so that we do not have to requery the whole table to find the
				// count each time they change page.
				session.setAttribute("searchTracksTotal", searchTracksTotal);
				session.setAttribute("searchAlbumsTotal", searchAlbumsTotal);

				request.setAttribute("searchTracks", tracks);
				request.setAttribute("searchAlbums", albums);
				request.setAttribute("tracksPage", tracksPage);
				request.setAttribute("albumsPage", albumsPage);
				request.setAttribute("maxTracksPage", maxTracksPage);
				request.setAttribute("maxAlbumsPage", maxAlbumsPage);
				request.setAttribute("searchValue", searchValue);
				request.setAttribute("searchType", searchType);
			}

			forward(request, response, searchValue, searchType);
		} else {
			url = "/g1w13/index";
			response.sendRedirect(url);
		}
	}

	/*
	 * This queries the database depending on what type of search is required.
	 * 
	 * @param canGoStraightToPage Whether it should redirect to the album/track
	 * page if only one result is returned.
	 * 
	 * @param request The request
	 * 
	 * @param response The response
	 * 
	 * @param type The string type of search
	 * 
	 * @param searchValue The search value
	 * 
	 * @param albumStart Where to start taking album records
	 * 
	 * @param albumSize How many album records to take.
	 * 
	 * @param trackStart Where to start taking track records
	 * 
	 * @param trackSize How many tracks to take.
	 * 
	 * @return True=not redirected, False=redirected
	 * 
	 * @throws IOException
	 * 
	 * @throws ServletException
	 */
	private SearchResults queryDatabase(HttpServletRequest request,
			HttpServletResponse response, String searchType,
			String searchValue, int albumStart, int albumSize, int trackStart,
			int trackSize, DatabaseManager dbManager) throws IOException,
			ServletException {
		ArrayList<Album> albums = null;
		ArrayList<Track> tracks = null;
		switch (searchType) {
		case "Artist":
			albums = dbManager.searchAlbumsByArtist(searchValue, albumStart,
					albumSize, false);
			tracks = dbManager.searchTracksByArtist(searchValue, trackStart,
					trackSize, false);
			break;
		case "Track":
			tracks = dbManager.searchTracksByName(searchValue, trackStart,
					trackSize, false);
			break;
		case "Album":
			albums = dbManager.searchAlbumsByName(searchValue, albumStart,
					albumSize, false);
			break;
		case "Genre":
			albums = dbManager.searchAlbumsByGenre(searchValue, albumStart,
					albumSize, false);
			tracks = dbManager.searchTracksByGenre(searchValue, trackStart,
					trackSize, false);
			break;
		}
		return new SearchResults(albums, tracks);
	}

	/*
	 * Sets the maximum page numbers for albums and tracks.
	 */
	private Integer[] setMaxPages(Integer searchAlbumsTotal,
			Integer searchTracksTotal) {
		Integer maxAlbumsPage, maxTracksPage;
		if (searchTracksTotal != null) {
			// Calculating the maximum number of pages from the total.
			maxTracksPage = searchTracksTotal / NUM_OF_RECORDS_PER_PAGE;
			if (searchTracksTotal % NUM_OF_RECORDS_PER_PAGE != 0)
				maxTracksPage++;
		} else
			maxTracksPage = 0;

		if (searchAlbumsTotal != null) {
			// Calculating the maximum number of pages from the total.
			maxAlbumsPage = searchAlbumsTotal / NUM_OF_RECORDS_PER_PAGE;
			if (searchAlbumsTotal % NUM_OF_RECORDS_PER_PAGE != 0)
				maxAlbumsPage++;
		} else
			maxAlbumsPage = 0;
		return new Integer[] { maxAlbumsPage, maxTracksPage };
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

	/*
	 * Forwards to the view.jsp
	 */
	private void forward(HttpServletRequest request,
			HttpServletResponse response, String searchValue, String type)
			throws ServletException, IOException {
		String destination = "/admin/view.jsp?searchValue=" + searchValue
				+ "&searchType=" + type;
		String uri = request.getRequestURI();
		if (uri.endsWith("/admin/sale"))
			destination = "/admin/sale.jsp?searchValue=" + searchValue
					+ "&searchType=" + type;
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(destination);

		dispatcher.forward(request, response);
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
	 * This object keeps track of various search parameters.
	 */
	private class SearchResults {
		private ArrayList<Album> albums = null;
		private ArrayList<Track> tracks = null;

		/**
		 * No-param constructor
		 */
		public SearchResults() {
		}

		/**
		 * Constructor
		 * 
		 * @param albums
		 *            The albums
		 * @param tracks
		 *            The tracks
		 */
		public SearchResults(ArrayList<Album> albums, ArrayList<Track> tracks) {
			this.albums = albums;
			this.tracks = tracks;
		}

		/**
		 * Gets the albums
		 * 
		 * @return The albums
		 */
		public ArrayList<Album> getAlbums() {
			return albums;
		}

		/**
		 * Sets the albums
		 * 
		 * @param albums
		 *            The albums
		 */
		public void setAlbums(ArrayList<Album> albums) {
			this.albums = albums;
		}

		/**
		 * Gets the tracks
		 * 
		 * @return The tracks
		 */
		public ArrayList<Track> getTracks() {
			return tracks;
		}

		/**
		 * Sets the tracks
		 * 
		 * @param tracks
		 *            The tracks
		 */
		public void setTracks(ArrayList<Track> tracks) {
			this.tracks = tracks;
		}
	}
}
