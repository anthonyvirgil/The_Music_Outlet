package servlets.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbManager.DatabaseManager;

import beans.Client;
import beans.Survey;
import beans.SurveyAnswer;

/**
 * Servlet implementation class DeleteSurveyServlet
 * 
 * @author Natacha Gabbamonte 0932340
 */
@WebServlet(name = "DeleteSurveyServlet", urlPatterns = { "/admin/deleteSurvey" })
public class DeleteSurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteSurveyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}
		String destination = null;

		if (client != null && client.getStatus()) {
			String surveyIdStr = request.getParameter("surveyId");
			destination = "/admin/survey";
			String displayMessage = null;

			if (surveyIdStr != null && tryParse(surveyIdStr)) {
				int surveyId = Integer.parseInt(surveyIdStr);
				DatabaseManager dbManager = new DatabaseManager();
				Survey surveyToBeDeleted = dbManager.getSurveyById(surveyId);
				if (surveyToBeDeleted == null)
					displayMessage = "This survey does not exist.";
				else {
					ArrayList<SurveyAnswer> answersToBeDeleted = dbManager
							.getSurveyAnswersBySurveyId(surveyId);
					boolean valid = true;
					for (SurveyAnswer answer : answersToBeDeleted) {
						if (!dbManager.removeSurveyAnswer(answer)) {
							valid = false;
							break;
						}
					}
					if (valid) {
						if (dbManager.removeSurvey(surveyToBeDeleted)) {
							displayMessage = "Survey "
									+ surveyToBeDeleted.getSurveyName()
									+ " deleted.";
						} else {
							displayMessage = "Survey questions deleted but survey not deleted! Contact your programmer!";
						}
					} else {
						displayMessage = "Could not delete survey answers! Survey not deleted.";
					}
				}
				request.setAttribute("displayMessage", displayMessage);

			} else
				destination = "/admin/index";

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(destination);
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
	private boolean tryParse(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
