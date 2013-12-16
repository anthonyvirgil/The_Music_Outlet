// Venelin Koulaxazov
// 1032744
// SurveyTest.java
package junitTests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.Survey;

import dbManager.DatabaseManager;
import java.util.ArrayList;

/**
 * JUnit test for the survey related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class SurveyTest {

	private DatabaseManager dbManager;
	private ArrayList<Survey> list;

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
	 * Tests if the getSurveys method returns a list of size zero if there are
	 * no surveys in the database.
	 */
	@Test
	public void testGetSurveysOnEmptyTable() {
		list = dbManager.getSurveys();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getSurveys method returns a list with the right amount of
	 * elements in the table that are in database after it has been populated.
	 */
	@Test
	public void testGetSurveysOnPopulatedTable() {
		dbManager.insertSurvey(new Survey("name", "question", true));
		list = dbManager.getSurveys();

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateSurvey method actually changes some of the fields in a
	 * record in the survey table
	 */
	@Test
	public void testUpdateSurvey() {
		// survey to be updated
		Survey survey = new Survey("name", "question", true);

		dbManager.insertSurvey(survey);

		// change values of the survey and update
		survey.setSurveyName("New name");
		survey.setSurveyQuestion("New question");

		dbManager.updateSurvey(survey);

		list = dbManager.getSurveys();
		// check if contents from database are the same as expected
		survey = list.get(0);
		assertEquals("New name", survey.getSurveyName());
		assertEquals("New question", survey.getSurveyQuestion());
	}

	/**
	 * Failure case of the updateSurvey method. An invalid name is sent.
	 */
	@Test
	public void testUpdateSurveyFailure() {
		// survey to be updated
		Survey survey = new Survey("name", "question", true);

		dbManager.insertSurvey(survey);

		// change values of the survey and update
		survey.setSurveyName("This is a new name which will be longer than 50 characters "
				+ "in order to prove that an update is not possible");

		assertFalse(dbManager.updateSurvey(survey));
	}

	/**
	 * Tests if the insertSurvey method correctly adds a record to the survey
	 * table
	 */
	@Test
	public void testInsertSurvey() {
		dbManager.insertSurvey(new Survey("name", "question", true));

		list = dbManager.getSurveys();

		assertEquals("name", list.get(0).getSurveyName());
		assertEquals(true, list.get(0).getStatus());
	}

	/**
	 * Failure case of the insertSurvey method. An invalid name is sent.
	 */
	@Test
	public void testInsertSurveyFailure() {
		assertFalse(dbManager.insertSurvey(new Survey(
				"This is a new name which will be longer than 50 characters "
						+ "in order to prove that an insert is not possible",
				"question", true)));
	}

	/**
	 * Tests if the removeSurvey method deletes a record from the survey table.
	 */
	@Test
	public void testRemoveSurvey() {
		Survey survey = new Survey("name", "question", true);

		dbManager.insertSurvey(survey);

		survey.setSurveyId(1);

		dbManager.removeSurvey(survey);

		list = dbManager.getSurveys();

		assertEquals(0, list.size());
	}
}