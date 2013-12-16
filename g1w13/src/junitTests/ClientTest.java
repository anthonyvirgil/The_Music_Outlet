// Venelin Koulaxazov
// 1032744
// ClientTest.java
package junitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.Client;

import dbManager.DatabaseManager;

import java.util.ArrayList;

/**
 * JUnit test for the client related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @version 1.4
 */
public class ClientTest {

	private DatabaseManager dbManager;
	private ArrayList<Client> list;

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

		dbManager.dropTable("reviews");
		dbManager.dropTable("details");
		dbManager.dropTable("invoice");
		dbManager.dropTable("client");
		dbManager.dropTable("track");
		dbManager.dropTable("album");

		// re-create all the tables
		dbManager.createAlbumTable();
		dbManager.createClientTable();
		dbManager.createTrackTable();
		dbManager.createInvoiceTable();
		dbManager.createReviewsTable();
		dbManager.createDetailsTable();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests if the getClients methods returns a list of size zero if there are
	 * not clients in the database.
	 */
	@Test
	public void testGetClientsOnEmptyTable() {
		list = dbManager.getClients();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getClients method returns a list with the right amount of
	 * elements in the table that are in the database after it has been
	 * populated.
	 */
	@Test
	public void testGetClientsOnPopulatedTable() {
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));

		list = dbManager.getClients();

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateClient method actually changes some of the fields in a
	 * record in the client table.
	 */
	@Test
	public void testUpdateClient() {
		// client to be updated
		Client client = new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal");

		dbManager.insertClient(client);

		// change values of the client and update
		client.setClientId(1);
		client.setEmail("newMail@mail.com");
		client.setGenreLastSearch("electronic");

		dbManager.updateClient(client);

		list = dbManager.getClients();
		// check if contents from database are the same as expected
		client = list.get(0);
		assertEquals("newMail@mail.com", client.getEmail());
		assertEquals("electronic", client.getGenreLastSearch());
	}

	/**
	 * Failure case for the updateClient method. An invalid title is sent.
	 */
	@Test
	public void testUpdateClientFailure() {
		// client to be updated
		Client client = new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal");

		dbManager.insertClient(client);

		// change values of the client and update
		client.setClientId(1);
		client.setTitle("Invalid title");

		assertFalse(dbManager.updateClient(client));
	}

	/**
	 * Tests if the insertClient method correctly adds a record to the client
	 * table.
	 */
	@Test
	public void testInsertClient() {
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));

		list = dbManager.getClients();

		assertEquals("Mr.", list.get(0).getTitle());
		assertEquals("Koulaxazov", list.get(0).getLastName());
	}

	/**
	 * Failure case for the insertClient method. An invalid title is sent.
	 */
	@Test
	public void testInsertClientFailure() {
		assertFalse(dbManager.insertClient(new Client("Invalid title",
				"Koulaxazov", "Venelin", "", "123 Fake St.", "", "LaSalle",
				"QC", "Canada", "H1H1H1", "514 123 4567", "", "me@mail.com",
				"password", false, "metal")));
	}

	/**
	 * Tests if the removeClient methods deletes a record from the client table.
	 */
	@Test
	public void testRemoveClient() {
		Client client = new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal");

		dbManager.insertClient(client);

		client.setClientId(1);

		dbManager.removeClient(client);

		list = dbManager.getClients();

		assertEquals(0, list.size());
	}
}
