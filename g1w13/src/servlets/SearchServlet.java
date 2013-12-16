/**
 * This is the servlet used by the search page and search bar.
 * 
 * @author Natacha Gabbamonte 0932340
 */
package servlets;

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

import beans.Album;
import beans.Client;
import beans.Track;

import dbManager.DatabaseManager;

@WebServlet(name = "SearchServlet", urlPatterns = { "/search" })
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 2282144151069028631L;

	private final int NUM_OF_RECORDS_PER_PAGE = 10;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		DatabaseManager dbManager = null;
		ArrayList<Track> tracks = null;
		ArrayList<Album> albums = null;
		String genre = null;
		Integer searchTracksTotal = 0, searchAlbumsTotal = 0;
		Integer tracksPage = 0, albumsPage = 0, maxTracksPage = 0, maxAlbumsPage = 0;

		HttpSession session = request.getSession();
		String displayMessage = null;

		synchronized (session) {
			session.setAttribute("searchError", null);

			// Puts addToCart's displayMessage into the request instead of the
			// session
			displayMessage = (String) session.getAttribute("displayMessage");

			if (displayMessage != null) {

				session.setAttribute("displayMessage", null);

				request.setAttribute("displayMessage", displayMessage);
			}
		}

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
		if (searchValue == null || searchValue.equals("")) {
			synchronized (session) {
				session.setAttribute("searchTracksTotal", 0);
				session.setAttribute("searchAlbumsTotal", 0);
				session.setAttribute("searchError",
						"*Please enter a more refined search.");
			}

			request.setAttribute("searchTracks", null);
			request.setAttribute("searchAlbums", null);
			request.setAttribute("tracksPage", 1);
			request.setAttribute("albumsPage", 1);
			request.setAttribute("maxTracksPage", 1);
			request.setAttribute("maxAlbumsPage", 1);
			request.setAttribute("searchValue", "");
			request.setAttribute("searchType", searchType);

			forward(request, response, "", searchType);
			return;
		}
		dbManager = new DatabaseManager();
		synchronized (session) {
			searchTracksTotal = (Integer) session
					.getAttribute("searchTracksTotal");
			searchAlbumsTotal = (Integer) session
					.getAttribute("searchAlbumsTotal");
		}
		// If newQuery is null or true, this is a new search. Also, we cannot
		// function with NEITHER totals for albums or tracks (that are suppose
		// to
		// be in the session) therefore if BOTH are not there, we must make a
		// new search.
		if (newQuery == null || newQuery.equals("true")
				|| (searchAlbumsTotal == null && searchTracksTotal == null)) {
			// If queryDatabase returns false, it means it has redirected the
			// user because of a search that only returned one result, therefore
			// we must leave right away.
			SearchResults searchResults = queryDatabase(true, request,
					response, searchType, searchValue, -1, -1, -1, -1,
					dbManager);
			albums = searchResults.getAlbums();
			tracks = searchResults.getTracks();
			genre = searchResults.getGenre();
			if (searchResults.isRedirected())
				return;
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
			// Same as above, although it should never redirect if it gets
			// to this query.
			searchResults = queryDatabase(true, request, response, searchType,
					searchValue, 0, NUM_OF_RECORDS_PER_PAGE, 0,
					NUM_OF_RECORDS_PER_PAGE, dbManager);
			albums = searchResults.getAlbums();
			tracks = searchResults.getTracks();
			if (searchResults.isRedirected())
				return;
			albumsPage = 1;
			tracksPage = 1;

			Integer[] maxPages = setMaxPages(searchTracksTotal,
					searchAlbumsTotal);
			maxTracksPage = maxPages[0];
			maxAlbumsPage = maxPages[1];
		} else {
			// This means that we are only changing page, therefore the stored
			// totals, and the sent in pages will be used to determine which
			// selection of records to return.
			Integer[] maxPages = setMaxPages(searchTracksTotal,
					searchAlbumsTotal);
			maxTracksPage = maxPages[0];
			maxAlbumsPage = maxPages[1];
			if (albumsPage < 1)
				albumsPage = 1;
			if (albumsPage > maxAlbumsPage)
				albumsPage = maxAlbumsPage;
			if (tracksPage < 1)
				tracksPage = 1;
			if (tracksPage > maxTracksPage)
				tracksPage = maxTracksPage;
			SearchResults searchResults = queryDatabase(false, request,
					response, searchType, searchValue,
					albumsPage * NUM_OF_RECORDS_PER_PAGE
							- NUM_OF_RECORDS_PER_PAGE, NUM_OF_RECORDS_PER_PAGE,
					tracksPage * NUM_OF_RECORDS_PER_PAGE
							- NUM_OF_RECORDS_PER_PAGE, NUM_OF_RECORDS_PER_PAGE,
					dbManager);
			albums = searchResults.getAlbums();
			tracks = searchResults.getTracks();
			genre = searchResults.getGenre();

		}

		synchronized (session) {
			// Only the totals are needed for longer than one page. This is so
			// that we do not have to requery the whole table to find the count
			// each time they change page.
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

		// Saving GENRE to cookie and to client if logged in.
		if (genre != null && (newQuery == null || newQuery.equals("true"))) {
			Boolean canSaveCookie = (Boolean) session
					.getAttribute("canSaveCookie");

			if (canSaveCookie != null && canSaveCookie) {

				Cookie searchTypeCookie = new Cookie("genre", genre);
				searchTypeCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
				searchTypeCookie.setPath("/");
				response.addCookie(searchTypeCookie);
			}

			synchronized (session) {
				Client client = (Client) session.getAttribute("client");
				if (client != null) {
					client.setGenreLastSearch(genre);
					dbManager.updateClient(client);
				}
			}
		}
		forward(request, response, searchValue, searchType);
	}

	/*
	 * Forwards to the search.jsp
	 */
	private void forward(HttpServletRequest request,
			HttpServletResponse response, String searchValue, String searchType)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(
						"/search.jsp?searchValue=" + searchValue
								+ "&searchType=" + searchType);

		dispatcher.forward(request, response);
	}

	/*
	 * This queries the database depending on what searchType of search is
	 * required.
	 */
	private SearchResults queryDatabase(boolean canGoStraightToPage,
			HttpServletRequest request, HttpServletResponse response,
			String searchType, String searchValue, int albumStart,
			int albumSize, int trackStart, int trackSize,
			DatabaseManager dbManager) throws IOException, ServletException {
		SearchResults searchResults = null;
		boolean redirected = false;
		String genre = null;
		ArrayList<Album> albums = null;
		ArrayList<Track> tracks = null;
		switch (searchType) {
		case "Artist":
			albums = dbManager.searchAlbumsByArtist(searchValue, albumStart,
					albumSize, true);
			tracks = dbManager.searchTracksByArtist(searchValue, trackStart,
					trackSize, true);
			break;
		case "Track":
			tracks = dbManager.searchTracksByName(searchValue, trackStart,
					trackSize, true);
			if (tracks != null && tracks.size() == 1 && canGoStraightToPage) {
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher(
								"/displayTrack?trackId="
										+ tracks.get(0).getInventoryId());
				dispatcher.forward(request, response);
				redirected = true;
			}
			albums = null;
			break;
		case "Album":
			albums = dbManager.searchAlbumsByName(searchValue, albumStart,
					albumSize, true);
			if (albums != null && albums.size() == 1 && canGoStraightToPage) {
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher(
								"/displayAlbum?albumId="
										+ albums.get(0).getAlbumId());
				dispatcher.forward(request, response);
				redirected = true;
			}
			tracks = null;
			break;
		case "Genre":
			albums = dbManager.searchAlbumsByGenre(searchValue, albumStart,
					albumSize, true);
			tracks = dbManager.searchTracksByGenre(searchValue, trackStart,
					trackSize, true);
			// Getting the genre of the resulting categories.
			if (albums != null && albums.size() > 0)
				genre = searchValue;
			break;
		}
		searchResults = new SearchResults(genre, albums, tracks, redirected);
		return searchResults;
	}

	/*
	 * Sets the maximum page numbers for albums and tracks.
	 */
	private Integer[] setMaxPages(Integer searchTracksTotal,
			Integer searchAlbumsTotal) {
		Integer maxTracksPage;
		Integer maxAlbumsPage;
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
		return new Integer[] { maxTracksPage, maxAlbumsPage };
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
	 * This object keeps track of various search parameters.
	 */
	private class SearchResults {
		private String genre = null;
		private ArrayList<Album> albums = null;
		private ArrayList<Track> tracks = null;
		private boolean redirected = false;

		/**
		 * No-param constructor
		 */
		public SearchResults() {
		}

		/**
		 * Constructor
		 * 
		 * @param genre
		 *            The genre
		 * @param albums
		 *            The albums
		 * @param tracks
		 *            The tracks
		 * @param hasLeft
		 *            Whether execution must stop.
		 */
		public SearchResults(String genre, ArrayList<Album> albums,
				ArrayList<Track> tracks, boolean redirected) {
			this.genre = genre;
			this.albums = albums;
			this.tracks = tracks;
			this.redirected = redirected;
		}

		/**
		 * Gets the genre
		 * 
		 * @return The genre
		 */
		public String getGenre() {
			return genre;
		}

		/**
		 * Sets the genre
		 * 
		 * @param genre
		 *            The genre
		 */
		public void setGenre(String genre) {
			this.genre = genre;
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

		/**
		 * Gets has left param
		 * 
		 * @return Whether execution must be stopped.
		 */
		public boolean isRedirected() {
			return redirected;
		}

		/**
		 * Sets has left param
		 * 
		 * @param hasLeft
		 *            Whether execution must be stopped.
		 */
		public void setRedirected(boolean redirected) {
			this.redirected = redirected;
		}
	}
}
