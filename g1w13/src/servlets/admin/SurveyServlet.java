package servlets.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Client;
import beans.Survey;
import beans.SurveyAnswer;
import beans.SurveyWithAnswers;
import dbManager.DatabaseManager;

/**
 * Servlet implementation class SurveyServlet
 * 
 * @author Natacha Gabbamonte 0932340
 */
@WebServlet(name = "SurveyServlet", urlPatterns = { "/admin/survey" })
public class SurveyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SurveyServlet() {
		super();
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

		if (client != null && client.getStatus()) {
			// Creating a Database manager
			DatabaseManager dbManager = new DatabaseManager();
			SurveyWithAnswers currentSurvey = null;
			ArrayList<SurveyWithAnswers> otherSurveys = null;
			// Getting all the surveys
			ArrayList<SurveyWithAnswers> allSurveysWithAnswers = new ArrayList<SurveyWithAnswers>();

			ArrayList<Survey> allSurveys = dbManager.getSurveys();
			for (Survey s : allSurveys)
				allSurveysWithAnswers.add(new SurveyWithAnswers(s, dbManager
						.getSurveyAnswersBySurveyId(s.getSurveyId())));

			// Find the current survey in these and set the variable
			for (SurveyWithAnswers s : allSurveysWithAnswers)
				if (s.getSurvey().getStatus()) {
					currentSurvey = s;
					allSurveysWithAnswers.remove(s);
					break;
				}
			otherSurveys = allSurveysWithAnswers;

			// Setting the current survey and the other surveys.
			request.setAttribute("currentSurvey", currentSurvey);
			request.setAttribute("otherSurveys", otherSurveys);

			// Forward
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/admin/survey.jsp");
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
		HttpSession session = request.getSession();
		Client client = null;
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}
		int newCurrentSurveyId = -1;
		String displayMessage = "";
		boolean valid = true;

		if (client != null && client.getStatus()) {

			// Creating a Database Manager.
			DatabaseManager dbManager = new DatabaseManager();

			// Getting all the parameters required.
			String surveyName = request.getParameter("surveyName");
			String question = request.getParameter("question");
			String type = request.getParameter("survey");
			if (type.equals("new")) {

				Enumeration<String> paramNames = request.getParameterNames();
				ArrayList<String> answers = new ArrayList<String>();
				while (paramNames.hasMoreElements()) {
					String name = paramNames.nextElement();
					if (name.contains("opt"))
						answers.add(request.getParameter(name));
				}

				// Checking whether all the fields are valid
				if (!isValidString(question))
					valid = false;
				for (String answer : answers)
					if (!isValidString(answer))
						valid = false;

				if (valid) {
					// This means all the fields have something.
					Survey survey = new Survey(surveyName, question, false);
					if (dbManager.insertSurvey(survey)) {
						SurveyAnswer surveyAnswer;
						for (String answer : answers) {
							surveyAnswer = new SurveyAnswer(
									survey.getSurveyId(), answer, 0);
							if (!dbManager.insertSurveyAnswer(surveyAnswer)) {
								request.setAttribute("surveyErrorMessage",
										"Error while trying to add survey answer!");
								valid = false;
								break;
							}
							if (valid) {
								displayMessage = "The new survey "
										+ survey.getSurveyName()
										+ " was added!";
								newCurrentSurveyId = survey.getSurveyId();
							}
						}
					} else {
						valid = false;
						request.setAttribute("surveyErrorMessage",
								"Error while trying to add survey!");
					}

				} else {
					request.setAttribute("surveyErrorMessage",
							"All fields must have something!");
				}
			}
			// You can either get here after adding a quiz, or be setting a
			// pre-existing quiz.
			if (newCurrentSurveyId == -1) {
				newCurrentSurveyId = Integer.parseInt(type);
			}
			int currentSurveyId = Integer.parseInt(request
					.getParameter("currentSurveyId"));
			boolean successful = true;
			if (currentSurveyId != -1) {
				if (!dbManager
						.updateSurveyStatusUsingId(currentSurveyId, false)) {
					successful = false;
					displayMessage = "Could not change status of current survey.";
				}
			}
			if (successful) {
				if (dbManager.updateSurveyStatusUsingId(newCurrentSurveyId,
						true))
					displayMessage = "Successfully changed current survey!";
				else
					displayMessage = "Could not change status of new current survey.";
			}
			if (valid)
				request.setAttribute("displayMessage", displayMessage);
		} // end of if has permission
		doGet(request, response);
	}

	private boolean isValidString(String str) {
		return (str != null && str != "");
	}

}
