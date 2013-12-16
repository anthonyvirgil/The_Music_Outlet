// Venelin Koulaxazov
// 1032744
// DetailTest.java
package junitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.Album;
import beans.Client;
import beans.Detail;
import beans.Invoice;
import beans.Track;

import dbManager.DatabaseManager;
import java.util.ArrayList;
import java.util.Date;

/**
 * JUnit test for the detail related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @vesrion 1.8
 */
public class DetailTest {

	private DatabaseManager dbManager;
	private ArrayList<Detail> list;

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

		dbManager.dropTable("details");
		dbManager.dropTable("reviews");
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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests if the getDetails method returns a list of size zero if there are
	 * no details in the database.
	 */
	@Test
	public void testGetDetailsOnEmptyTable() {
		list = dbManager.getDetails();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getDetails method returns a list with the right amount of
	 * elements in the table that are in database after it has been populated.
	 */
	@Test
	public void testGetDetailsOnPopulatedTable() {
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(new Track(1, "Nothing Else Matters", "Metallica",
				"", "6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false));
		dbManager.insertInvoice(new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false));
		dbManager.insertDetail(new Detail(1, 1, 1, 4.99, false));
		list = dbManager.getDetails();

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateDetail method actually changes some of the fields in a
	 * record in the details table
	 */
	@Test
	public void testUpdateDetail() {
		// detail to be updated
		Detail detail = new Detail(1, 1, 1, 4.99, false);
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(new Track(1, "Nothing Else Matters", "Metallica",
				"", "6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false));
		dbManager.insertInvoice(new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false));
		dbManager.insertDetail(detail);

		// change values of the detail and update
		detail.setDetailId(1);
		detail.setSalePrice(9.99);

		dbManager.updateDetail(detail);

		list = dbManager.getDetails();
		// check if contents from database are the same as expected
		detail = list.get(0);
		assertEquals(1, detail.getDetailId());
		assertEquals(9.99, detail.getSalePrice(), 2);
	}

	/**
	 * Failure case of the updateDetail method. An invalid inventoryId is sent.
	 */
	@Test
	public void testUpdateDetailFailure() {
		// detail to be updated
		Detail detail = new Detail(1, 1, 1, 4.99, false);
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(new Track(1, "Nothing Else Matters", "Metallica",
				"", "6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false));
		dbManager.insertInvoice(new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false));
		dbManager.insertDetail(detail);

		// change values of the detail and update
		detail.setDetailId(1);
		detail.setInventoryId(2);

		assertFalse(dbManager.updateDetail(detail));
	}

	/**
	 * Tests if the insertDetail method correctly adds a record to the details
	 * table
	 */
	@Test
	public void testInsertDetail() {
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(new Track(1, "Nothing Else Matters", "Metallica",
				"", "6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false));
		dbManager.insertInvoice(new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false));
		dbManager.insertDetail(new Detail(1, 1, 1, 4.99, false));

		list = dbManager.getDetails();

		assertEquals(1, list.get(0).getInventoryId());
		assertEquals(4.99, list.get(0).getSalePrice(), 2);
	}

	/**
	 * Failure case of the insertDetail method. An invalid inventoryId is sent.
	 */
	@Test
	public void testInsertDetailFailure() {
		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(new Track(1, "Nothing Else Matters", "Metallica",
				"", "6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false));
		dbManager.insertInvoice(new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false));
		assertFalse(dbManager.insertDetail(new Detail(1, 2, 1, 4.99, false)));
	}

	/**
	 * Tests if the removeDetail method deletes a record from the details table
	 */
	@Test
	public void testRemoveDetail() {
		Detail detail = new Detail(1, 1, 1, 4.99, false);

		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(new Track(1, "Nothing Else Matters", "Metallica",
				"", "6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false));
		dbManager.insertInvoice(new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false));
		dbManager.insertDetail(detail);

		detail.setDetailId(1);

		dbManager.removeDetail(detail);

		list = dbManager.getDetails();

		assertEquals(0, list.size());
	}

	/**
	 * Tests the updateDetailRemovalStatus method
	 */
	@Test
	public void testUpdateDetailRemovalStatus() {
		Detail detail = new Detail(1, 1, 1, 4.99, false);

		dbManager.insertClient(new Client("Mr.", "Koulaxazov", "Venelin", "",
				"123 Fake St.", "", "LaSalle", "QC", "Canada", "H1H1H1",
				"514 123 4567", "", "me@mail.com", "password", false, "metal"));
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(new Track(1, "Nothing Else Matters", "Metallica",
				"", "6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false));
		dbManager.insertInvoice(new Invoice(new Date(), 1, 14.99, 2, 3, 3, 25, false));
		dbManager.insertDetail(detail);

		detail.setRemovalStatus(true);

		dbManager.updateDetailRemovalStatus(detail);

		assertTrue(detail.isRemovalStatus());
	}
}
