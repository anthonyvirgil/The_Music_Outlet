// Venelin Koulaxazov
// 1032744
// ReviewTest
package junitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.Album;
import beans.Client;
import beans.Invoice;
import beans.Review;
import beans.Track;

import dbManager.DatabaseManager;

/**
 * JUnit test for the review related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @vesrion 1.6
 */
public class ReviewTest {

	private DatabaseManager dbManager;
	private ArrayList<Review> list;

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
	 * Tests if the getReview method returns a list of size zero if there are no
	 * reviews in the database.
	 */
	@Test
	public void testGetReviewsOnEmptyTable() {
		list = dbManager.getReviews();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getReviews method returns a list with the right amount of
	 * elements in the table that are in database after it has been populated.
	 */
	@Test
	public void testGetReviewsOnPopulatedTable() {
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
		dbManager.insertReview(new Review(1, 1, new Date(),
				"Venelin Koulaxazov", 5, "Title", "review text", true));
		list = dbManager.getReviews();

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateReview method actually changes some of the fields in a
	 * record in the reviews table
	 */
	@Test
	public void testUpdateReview() {
		// review to be updated
		Review review = new Review(1, 1, new Date(), "Venelin Koulaxazov", 5,
				"Title", "review text", true);
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
		dbManager.insertReview(review);

		// change values of the review and update
		review.setReviewId(1);
		review.setClientName("Venelin");
		review.setRating(4);

		dbManager.updateReview(review);

		list = dbManager.getReviews();
		// check if contents from database are the same as expected
		review = list.get(0);
		assertEquals("Venelin", review.getClientName());
		assertEquals(4, review.getRating());
	}

	/**
	 * Failure case of the updateReview method. An invalid inventoryId is sent.
	 */
	@Test
	public void testUpdateReviewFailure() {
		// review to be updated
		Review review = new Review(1, 1, new Date(), "Venelin Koulaxazov", 5,
				"Title", "review text", true);
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
		dbManager.insertReview(review);

		// change values of the review and update
		review.setReviewId(1);
		review.setInventoryId(2);

		assertFalse(dbManager.updateReview(review));
	}

	/**
	 * Tests if the insertReview method correctly adds a record to the reviews
	 * table
	 */
	@Test
	public void testInsertReview() {
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
		dbManager.insertReview(new Review(1, 1, new Date(),
				"Venelin Koulaxazov", 5, "Title", "review text", true));

		list = dbManager.getReviews();

		assertEquals("review text", list.get(0).getReviewText());
		assertEquals(5, list.get(0).getRating());
	}

	/**
	 * Failure case of the insertReview method. An inventoryId is sent.
	 */
	@Test
	public void testInsertReviewFailure() {
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
		assertFalse(dbManager.insertReview(new Review(2, 1, new Date(),
				"Venelin Koulaxazov", 5, "Title", "review text", true)));
	}

	/**
	 * Tests if the removeReview method deletes a record from the reviews table
	 */
	@Test
	public void testRemoveReview() {
		Review review = new Review(1, 1, new Date(), "Venelin Koulaxazov", 5,
				"Title", "review text", true);

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
		dbManager.insertReview(review);

		review.setReviewId(1);

		dbManager.removeReview(review);

		list = dbManager.getReviews();

		assertEquals(0, list.size());
	}
}
