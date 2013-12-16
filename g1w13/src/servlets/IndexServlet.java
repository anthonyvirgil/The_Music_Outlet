// Anthony-Virgil Bermejo 0831360
// Natacha Gabbamonte 0932340
// HelpServlet.java
package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CookieUtil;

import beans.Album;
import beans.BannerAd;
import beans.Cart;
import beans.Client;
import beans.Rss;
import beans.Survey;
import beans.SurveyAnswer;
import beans.SurveyWithAnswers;

import dbManager.DatabaseManager;

/**
 * This is the Servlet used by the index page for logging in, logging out,
 * voting, and also for the retrieval of the suggusted list, new albums list,
 * and the albums on sale.
 * 
 * @author Natacha Gabbamonte 0932340
 * @author Anthony-Virgil Bermejo 0831360
 * @version 4.1
 */
@WebServlet(name = "IndexServlet", urlPatterns = { "/index", "/logout", "/vote" })
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 2282144151069028631L;

	/**
	 * Sets up various needed session components: on sale albums, suggested
	 * albums, new albums, survey, news feed.
	 * 
	 * @author Natacha Gabbamonte 0932340
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String destination = "/index.jsp";
		HttpSession session = request.getSession();
		DatabaseManager dbManager = new DatabaseManager();

		// Checking to see if the cookie attribute needs to be saved.
		setClientCookieAttribute(request, response, session);

		Rss currentRss = dbManager.getCurrentRssFeed();
		BannerAd bottomAd = dbManager.getCurrentBottomBannerAd();
		BannerAd rightAd = dbManager.getCurrentRightBannerAd();

		// get value of the real path of the application
		ServletContext sc = request.getServletContext();
		String path = sc.getContextPath();
		synchronized (session) {
			session.setAttribute("path", path);
		}

		synchronized (session) {
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart == null) {
				cart = new Cart();
				session.setAttribute("cart", cart);

			}
			session.setAttribute("rss", currentRss.getUrl());
			session.setAttribute("bottomAd", bottomAd);
			session.setAttribute("rightAd", rightAd);
		}

		String uri = request.getRequestURI();
		if (uri.contains("/logout"))
			doLogoutActions(request, session);
		else if (uri.contains("/index") || !uri.contains("/vote"))
			doIndexActions(request, session, dbManager);

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(destination);
		dispatcher.forward(request, response);

	}

	/**
	 * Handles the POST request. Checks to see if voting.
	 * 
	 * @author Natacha Gabbamonte 0932340
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		String destination = "/index.jsp";

		HttpSession session = request.getSession();
		DatabaseManager dbManager = new DatabaseManager();

		// Checking to see if the cookie attribute needs to be saved.
		setClientCookieAttribute(request, response, session);

		// get value of the real path of the application
		ServletContext sc = request.getServletContext();
		String path = sc.getContextPath();
		synchronized (session) {
			session.setAttribute("path", path);
		}

		if (uri.contains("/vote")) {
			doVoteActions(request, response, session, dbManager);
		}
		Rss currentRss = dbManager.getCurrentRssFeed();
		BannerAd bottomAd = dbManager.getCurrentBottomBannerAd();
		BannerAd rightAd = dbManager.getCurrentRightBannerAd();

		synchronized (session) {
			session.setAttribute("rss", currentRss.getUrl());
			session.setAttribute("bottomAd", bottomAd);
			session.setAttribute("rightAd", rightAd);
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(destination);
		dispatcher.forward(request, response);
	}

	/*
	 * Prepares the suggested,sales, and new albums and sets up the survey.
	 */
	private void doIndexActions(HttpServletRequest request,
			HttpSession session, DatabaseManager dbManager) {
		// Prepare the suggested and sales albums
		prepareSuggestedSalesNewAlbums(request, session, dbManager);

		// Set the current quiz

		SurveyWithAnswers currentSurvey = (SurveyWithAnswers) session
				.getAttribute("currentSurvey");
		if (currentSurvey == null)
			getCurrentSurvey(session, dbManager);

	}

	/*
	 * Resets the current survey to the one from the database.
	 * 
	 * @author Natacha Gabbamonte 0932340
	 */
	private void getCurrentSurvey(HttpSession session, DatabaseManager dbManager) {
		SurveyWithAnswers currentSurvey = new SurveyWithAnswers();
		ArrayList<Survey> allSurveys = dbManager.getSurveys();
		Survey current = null;
		for (Survey s : allSurveys) {
			if (s.getStatus()) {
				current = s;
				break;
			}
		}
		if (current != null) {
			currentSurvey.setSurvey(current);
			ArrayList<SurveyAnswer> answers = null;
			answers = dbManager.getSurveyAnswersBySurveyId(current
					.getSurveyId());
			currentSurvey.setAnswers(answers);
		}

		synchronized (session) {
			session.setAttribute("currentSurvey", currentSurvey);
		}

		// The survey may have changed. Therefore if this is no longer the quiz
		// this session has voted on, the voted is reset.
		Integer surveyAnswerId = (Integer) session.getAttribute("voted");
		if (surveyAnswerId != null) {
			boolean valid = false;
			for (SurveyAnswer sa : currentSurvey.getAnswers())
				if (sa.getSurveyAnswerId() == surveyAnswerId) {
					valid = true;
					break;
				}
			if (!valid)
				synchronized (session) {
					session.setAttribute("voted", null);
				}
		}
	}

	/*
	 * Does the voting action in case of a vote request.
	 * 
	 * @author Natacha Gabbamonte 0932340
	 */
	private void doVoteActions(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			DatabaseManager dbManager) {
		if (session.getAttribute("currentSurvey") != null) {
			if (session.getAttribute("voted") == null) {
				// The person has not voted yet.

				// Get their vote
				String surveyAnswerIdStr = request.getParameter("quiz");
				if (surveyAnswerIdStr != null && tryParse(surveyAnswerIdStr)) {
					int surveyAnswerId = Integer.parseInt(surveyAnswerIdStr);
					SurveyAnswer surveyAnswer = dbManager
							.getSurveyAnswersById(surveyAnswerId);

					if (surveyAnswer != null) {
						// Increment the vote for the answer
						surveyAnswer.setVotes(surveyAnswer.getVotes() + 1);
						if (dbManager.updateSurveyAnswer(surveyAnswer)) {
							// The voting process was successful.
							session.setAttribute("voted", surveyAnswerId);
						} else
							System.out.println("User was un-able to vote!");
					}
				}
			}
			// If the person has already voted, we simply want to refresh. We
			// also need to refresh after voting.
			getCurrentSurvey(session, dbManager);

		}
	}

	/*
	 * Does the logout actions required when logging out.
	 * 
	 * @author Natacha Gabbamonte 0932340
	 */
	private void doLogoutActions(HttpServletRequest request, HttpSession session) {
		synchronized (session) {
			session.setAttribute("client", null);
			Cart cart = (Cart) session.getAttribute("cart");
			cart.setProvince("QC");
			session.setAttribute("cart", cart);
		}
		request.setAttribute("displayMessage", "You are now logged out.");

	}

	/*
	 * Prepares the suggested and sales albums lists and stores them in the
	 * session.
	 * 
	 * @author Natacha Gabbamonte 0932340
	 * 
	 * @author Anthony-Virgil Bermejo 0831360
	 */
	private void prepareSuggestedSalesNewAlbums(HttpServletRequest request,
			HttpSession session, DatabaseManager dbManager) {
		ArrayList<Album> suggestedAlbums = new ArrayList<Album>();
		ArrayList<Album> suggestedAlbumsTemp = null;
		ArrayList<Album> salesAlbums = new ArrayList<Album>();
		ArrayList<Album> salesAlbumsTemp = null;
		ArrayList<Album> fiveLatestAlbums = null;
		ArrayList<String> genreList = null;

		fiveLatestAlbums = dbManager.getFirstFiveAlbumsSortedByDate();

		for (Album latestAlbum : fiveLatestAlbums) {
			if (latestAlbum.getAlbumTitle().length() > 20) {
				latestAlbum.setAlbumTitle(latestAlbum.getAlbumTitle()
						.substring(0, 20) + "...");
			}
		}

		CookieUtil cookieUtil = new CookieUtil(request.getCookies());
		String genre = cookieUtil.getCookieValue("genre");
		if (genre == null) {
			Client client = (Client) session.getAttribute("client");
			if (client != null) {
				genre = client.getGenreLastSearch();
			}
			// If the client isn't logged in and/or doesn't have a saved genre,
			// show a random one.
			if (genre == null) {
				genreList = dbManager.getGenres();
				genre = genreList
						.get(((int) (Math.random() * genreList.size())));
			}
		}

		salesAlbumsTemp = dbManager.getAlbumsOnSale(true);
		salesAlbums = getANumberOfRandomAlbums(salesAlbumsTemp, 3);

		suggestedAlbumsTemp = dbManager
				.searchAlbumsByGenre(genre, -1, -1, true);
		suggestedAlbums = getANumberOfRandomAlbums(suggestedAlbumsTemp, 3);

		synchronized (session) {
			session.setAttribute("suggestedAlbums", suggestedAlbums);
			session.setAttribute("suggestedAlbumsSize", suggestedAlbums.size());
			session.setAttribute("salesAlbums", salesAlbums);
			session.setAttribute("salesAlbumsSize", salesAlbums.size());
			session.setAttribute("fiveLatestAlbums", fiveLatestAlbums);
		}
	}

	/*
	 * Returns a certain number of random records from a collection.
	 */
	private ArrayList<Album> getANumberOfRandomAlbums(ArrayList<Album> albums,
			int numOfAlbums) {
		ArrayList<Album> randomAlbums = new ArrayList<Album>();
		Album tempAlbum = null;
		for (int i = 0; i < numOfAlbums; i++) {
			if (albums.size() > i) {
				int record;
				do {
					record = (int) (Math.random() * albums.size());
					tempAlbum = albums.get(record);
				} while (randomAlbums.contains(tempAlbum));
				if (tempAlbum.getAlbumTitle().length() > 20) {
					tempAlbum.setAlbumTitle(tempAlbum.getAlbumTitle()
							.substring(0, 20) + "...");
				}
				randomAlbums.add(tempAlbum);
			}
		}
		return randomAlbums;
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
	 * Tries to see if a cookie was saved by the CheckCookieServlet.
	 */
	private void setClientCookieAttribute(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws IOException {
		Boolean canSaveCookie = (Boolean) session.getAttribute("canSaveCookie");
		if (canSaveCookie == null) {

			CookieUtil cookieUtil = new CookieUtil(request.getCookies());
			String cookieTest = cookieUtil.getCookieValue("cookieTest");

			canSaveCookie = cookieTest != null;

			synchronized (session) {
				session.setAttribute("canSaveCookie", canSaveCookie);
			}
		}
	}
}
