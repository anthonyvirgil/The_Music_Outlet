// Venelin Koulaxazov
// 1032744
// CreateTablesTest.java
package junitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dbManager.DatabaseManager;

/**
 * JUnit testing for the six methods that create the tables
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class CreateTablesTest {

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
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests to see if the three tables are successfully created in the
	 * database.
	 */
	@Test
	public void testCreateTables() {
		// drop the tables first in case they already exist
		dbManager.dropTable("reviews");
		dbManager.dropTable("details");
		dbManager.dropTable("invoice");
		dbManager.dropTable("client");
		dbManager.dropTable("track");
		dbManager.dropTable("album");

		// re-create all the tables
		dbManager.createAlbumTable();
		dbManager.createClientTable();
		dbManager.createInvoiceTable();
		dbManager.createTrackTable();
		dbManager.createReviewsTable();
		dbManager.createDetailsTable();

		// check if the tables are in the database
		assertTrue(dbManager.checkIfTablesExist());
	}

}
