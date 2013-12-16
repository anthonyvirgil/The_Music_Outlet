// Venelin Koulaxazov
// 1032744
// SurveyAnswer.java
package beans;

import java.io.Serializable;

/**
 * Java Bean for a survey answer in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class SurveyAnswer implements Serializable {

	private static final long serialVersionUID = -4924753807424207667L;
	private int surveyAnswerId;
	private int surveyId;
	private String answer;
	private int votes;

	/**
	 * Default constructor
	 */
	public SurveyAnswer() {
		answer = "";
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param surveyId
	 * @param answer
	 * @param votes
	 */
	public SurveyAnswer(int surveyId, String answer, int votes) {
		this.surveyId = surveyId;
		this.answer = answer;
		this.votes = votes;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param surveyAnswerId
	 * @param surveyId
	 * @param answer
	 * @param votes
	 */
	public SurveyAnswer(int surveyAnswerId, int surveyId, String answer,
			int votes) {
		this.surveyAnswerId = surveyAnswerId;
		this.surveyId = surveyId;
		this.answer = answer;
		this.votes = votes;
	}

	/**
	 * @return the surveyAnswerId
	 */
	public int getSurveyAnswerId() {
		return surveyAnswerId;
	}

	/**
	 * @param surveyAnswerId
	 *            the surveyAnswerId to set
	 */
	public void setSurveyAnswerId(int surveyAnswerId) {
		this.surveyAnswerId = surveyAnswerId;
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
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return the votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * @param votes
	 *            the votes to set
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
}
