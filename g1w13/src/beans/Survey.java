// Venelin Koulaxazov
// 1032744
// Survey.java
package beans;

import java.io.Serializable;

/**
 * Java Bean for a survey in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class Survey implements Serializable {

	private static final long serialVersionUID = 5576598927959294452L;
	private int surveyId;
	private String surveyName;
	private String surveyQuestion;
	private boolean status;

	/**
	 * Default constructor
	 */
	public Survey() {
		surveyName = "";
		surveyQuestion = "";
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param surveyName
	 * @param surveyQuestion
	 * @param status
	 */
	public Survey(String surveyName, String surveyQuestion, boolean status) {
		this.surveyName = surveyName;
		this.surveyQuestion = surveyQuestion;
		this.status = status;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param surveyId
	 * @param surveyName
	 * @param surveyQuestion
	 * @param status
	 */
	public Survey(int surveyId, String surveyName, String surveyQuestion,
			boolean status) {
		this.surveyId = surveyId;
		this.surveyName = surveyName;
		this.surveyQuestion = surveyQuestion;
		this.status = status;
	}

	/**
	 * @return the surveyId
	 */
	public int getSurveyId() {
		return surveyId;
	}

	/**
	 * @param surveyId
	 *            the surveyId to set
	 */
	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	/**
	 * @return the surveyName
	 */
	public String getSurveyName() {
		return surveyName;
	}

	/**
	 * @param surveyName
	 *            the surveyName to set
	 */
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	/**
	 * @return the surveyQuestion
	 */
	public String getSurveyQuestion() {
		return surveyQuestion;
	}

	/**
	 * @param surveyQuestion
	 *            the surveyQuestion to set
	 */
	public void setSurveyQuestion(String surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	/**
	 * @return the status
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
}
