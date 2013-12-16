// Venelin Koulaxazov
// 1032744
// SurveyAnswerTest.java
package junitTests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.Survey;
import beans.SurveyAnswer;

import dbManager.DatabaseManager;
import java.util.ArrayList;

/**
 * JUnit test for the surveyAnswer related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class SurveyAnswerTest {

	private DatabaseManager dbManager;
	private ArrayList<SurveyAnswer> list;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dbManager = new DatabaseManager();
		dbManager.dropTable("surveyAnswer");
		dbManager.dropTable("survey");

		// re-create the tables
		dbManager.createSurveyTable();
		dbManager.createSurveyAnswerTable();
	}

	/**
	 * Tests if the getSurveyAnswers method returns a list of size zero if there
	 * are no survey answers in the database.
	 */
	@Test
	public void testGetSurveyAnswersOnEmptyTable() {
		list = dbManager.getSurveyAnswers();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getSurveyAnswers method returns a list with the right amount
	 * of elements in the table that are in database after it has been
	 * populated.
	 */
	@Test
	public void testGetSurveyAnswersOnPopulatedTable() {
		dbManager.insertSurvey(new Survey("name", "question", true));
		dbManager.insertSurveyAnswer(new SurveyAnswer(1, "answer", 1));
		list = dbManager.getSurveyAnswers();

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateSurveyAnswer method actually changes some of the
	 * fields in a record in the surveyAnswer table
	 */
	@Test
	public void testUpdateSurveyAnswer() {
		Survey survey = new Survey("name", "question", true);
		dbManager.insertSurvey(survey);

		// su`rvey answer to be updated
		SurveyAnswer surveyAnswer = new SurveyAnswer(1, "answer", 1);
		dbManager.insertSurveyAnswer(surveyAnswer);

		// change values of the survey answer and update
		surveyAnswer.setAnswer("New answer");
		surveyAnswer.setVotes(3);

		dbManager.updateSurveyAnswer(surveyAnswer);

		list = dbManager.getSurveyAnswers();
		// check if contents from database are the same as expected
		surveyAnswer = list.get(0);
		assertEquals("New answer", surveyAnswer.getAnswer());
		assertEquals(3, surveyAnswer.getVotes());
	}

	/**
	 * Failure case of the updateSurveyAnswer method. An invalid answer is sent.
	 */
	@Test
	public void testUpdateSurveyAnswerFailure() {
		Survey survey = new Survey("name", "question", true);
		dbManager.insertSurvey(survey);

		// survey answer to be updated
		SurveyAnswer surveyAnswer = new SurveyAnswer(1, "answer", 1);
		dbManager.insertSurveyAnswer(surveyAnswer);

		// change values of the survey and update
		surveyAnswer
				.setAnswer("This is a new answer which will be longer than 50 characters "
						+ "in order to prove that an update is not possible");

		assertFalse(dbManager.updateSurveyAnswer(surveyAnswer));
	}

	/**
	 * Tests if the insertSurveyAnswer method correctly adds a record to the
	 * surveyAnswer table
	 */
	@Test
	public void testInsertSurveyAnswer() {
		dbManager.insertSurvey(new Survey("name", "question", true));

		dbManager.insertSurveyAnswer(new SurveyAnswer(1, "answer", 1));
		list = dbManager.getSurveyAnswers();

		assertEquals("answer", list.get(0).getAnswer());
		assertEquals(1, list.get(0).getVotes());
	}

	/**
	 * Failure case of the insertSurveyAnswer method. An invalid answer is sent.
	 */
	@Test
	public void testInsertSurveyFailure() {
		dbManager.insertSurvey(new Survey("name", "question", true));
		assertFalse(dbManager
				.insertSurveyAnswer(new SurveyAnswer(
						1,
						"This is a new answer which will be longer than 50 characters "
								+ "in order to prove that an insert is not possible",
						1)));
	}

	/**
	 * Tests if the removeSurveyAnswer method deletes a record from the
	 * surveyAnswer table.
	 */
	@Test
	public void testRemoveSurveyAnswer() {
		Survey survey = new Survey("name", "question", true);
		dbManager.insertSurvey(survey);

		SurveyAnswer surveyAnswer = new SurveyAnswer(1, "answer", 1);
		surveyAnswer.setSurveyAnswerId(1);

		dbManager.removeSurveyAnswer(surveyAnswer);

		list = dbManager.getSurveyAnswers();

		assertEquals(0, list.size());
	}
}