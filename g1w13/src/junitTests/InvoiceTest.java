// Venelin Koulaxazov
// 1032744
// InvoiceTest.java
package junitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.Client;
import beans.Invoice;

import dbManager.DatabaseManager;

/**
 * JUnit test for the invoice related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @version 1.4
 */
public class InvoiceTest {

	private DatabaseManager dbManager;
	private ArrayList<Invoice> list;

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
	 * Tests if the getInvoices method returns a list of size zero if there are
	 * no invoices in the database.
	 */
	@Test
	public void testGetInvoicesOnEmptyTable() {
		list = dbManager.getInvoices();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getInvoices method returns a list with the right amount of
	 * elements in the table that are in database after it has been populated.
	 */
	@Test
	public void testGetInvoicesOnPopulatedTable() {
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));

		dbManager.insertInvoice(new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false));

		list = dbManager.getInvoices();

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateInvoice method actually changes some of the fields in
	 * a record in the invoice table
	 */
	@Test
	public void testUpdateInvoice() {
		// invoice to be updated
		Invoice invoice = new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false);

		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));

		dbManager.insertInvoice(invoice);

		// change values of the invoice and update
		invoice.setSaleId(1);
		invoice.setGrossValue(30);

		dbManager.updateInvoice(invoice);

		list = dbManager.getInvoices();
		// check if contents from database are the same as expected
		invoice = list.get(0);
		assertEquals(30, invoice.getGrossValue(), 1);
	}

	/**
	 * Failure case of the updateInvoice method. An invalid clientId is sent.
	 */
	@Test
	public void testUpdateInvoiceFailure() {
		// invoice to be updated
		Invoice invoice = new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false);

		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));

		dbManager.insertInvoice(invoice);

		// change values of the invoice and update
		invoice.setSaleId(1);
		invoice.setClientId(2);

		assertFalse(dbManager.updateInvoice(invoice));
	}

	/**
	 * Tests if the insertInvoice method correctly adds a record to the invoice
	 * table
	 */
	@Test
	public void testInsertInvoice() {
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		dbManager.insertInvoice(new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false));

		list = dbManager.getInvoices();

		assertEquals(14.99, list.get(0).getNetValue(), 2);
		assertEquals(25, list.get(0).getGrossValue(), 2);
	}

	/**
	 * Failure case of the insertInvoice method. An invalid clientId is sent.
	 */
	@Test
	public void testInsertInvoiceFailure() {
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		assertFalse(dbManager.insertInvoice(new Invoice(new Date(), 2, 14.99,
				2, 3, 3, 25, false)));
	}

	/**
	 * Tests if the removeInvoice method deletes a record from the invoice table
	 */
	@Test
	public void testRemoveInvoice() {
		Invoice invoice = new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false);

		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		dbManager.insertInvoice(invoice);

		invoice.setSaleId(1);

		dbManager.removeInvoice(invoice);

		list = dbManager.getInvoices();

		assertEquals(0, list.size());
	}
}
