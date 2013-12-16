/*
 * Natacha Gabbamonte
 * 0932340
 * SurveyWithAnswers.java
 */
package beans;

import java.util.ArrayList;

/**
 * This contains a complete survey, which contains both the survey and all it's
 * answers.
 * 
 * @author Natacha Gabbamonte 0932340
 * @version 1.0
 * 
 */
public class SurveyWithAnswers {

	private Survey survey;
	private ArrayList<SurveyAnswer> answers;

	/**
	 * Constructor with no parameters.
	 */
	public SurveyWithAnswers() {
		survey = null;
		answers = null;
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param survey
	 *            The survey
	 * @param answers
	 *            The answers
	 */
	public SurveyWithAnswers(Survey survey, ArrayList<SurveyAnswer> answers) {
		super();
		this.survey = survey;
		this.answers = answers;
	}

	/**
	 * Gets the survey
	 * 
	 * @return
	 */
	public Survey getSurvey() {
		return survey;
	}

	/**
	 * Sets the survey
	 * 
	 * @param survey
	 *            The survey
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	/**
	 * Gets the survey answers
	 * 
	 * @return The survey answers
	 */
	public ArrayList<SurveyAnswer> getAnswers() {
		return answers;
	}

	/**
	 * Sets the survey answers
	 * 
	 * @param answers
	 *            The answers
	 */
	public void setAnswers(ArrayList<SurveyAnswer> answers) {
		this.answers = answers;
	}

	/**
	 * Gets the total number of votes on all the answers.
	 * 
	 * @return the total number of votes.
	 */
	public int getTotalVotes() {
		int count = 0;
		for (SurveyAnswer s : answers)
			count += s.getVotes();
		return count;
	}

	/**
	 * A string representation of this object
	 */
	@Override
	public String toString() {
		return "SurveyWithAnswers [survey=" + survey + ", answers=" + answers
				+ "]";
	}
}
