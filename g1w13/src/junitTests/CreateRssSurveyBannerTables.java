// Venelin Koulaxazov
// 1032744
// CreateRssSurveyBannerTables.java
package junitTests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dbManager.DatabaseManager;

/**
 * JUnit testing for the four methods that create the RssFeed, Survey,
 * SurveyAnswer, and BannerAd tables
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class CreateRssSurveyBannerTables {

	private DatabaseManager dbManager;

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
	}

	/**
	 * Tests to see if the tables are successfully created in the database
	 */
	@Test
	public void testCreateTables() {
		// drop the tables first in case they already exist
		dbManager.dropTable("rssFeed");
		dbManager.dropTable("surveyAnswer");
		dbManager.dropTable("survey");
		dbManager.dropTable("bannerAd");

		// re-create the tables
		dbManager.createRssFeedTable();
		dbManager.createSurveyTable();
		dbManager.createSurveyAnswerTable();
		dbManager.createBannerAdTable();

		// check if the tables are in the database
		assertTrue(dbManager.checkIfTablesExist());
	}

}
