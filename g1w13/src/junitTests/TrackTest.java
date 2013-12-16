// Venelin Koulaxazov
// 1032744
// DetailTest.java
package junitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dbManager.DatabaseManager;

import beans.Album;
import beans.Track;

/**
 * JUnit test for the track related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @vesrion 1.5
 */
public class TrackTest {

	private DatabaseManager dbManager;
	private ArrayList<Track> list;

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
		dbManager.dropTable("track");
		dbManager.dropTable("album");

		// re-create all the tables
		dbManager.createAlbumTable();
		dbManager.createTrackTable();
		dbManager.createReviewsTable();
		dbManager.createDetailsTable();

	}

	/**
	 * Tests if the getTracks method returns a list of size zero if there are no
	 * tracks in the database.
	 */
	@Test
	public void testGetTracksOnEmptyTable() {
		list = dbManager.getTracks(false);

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getTracks method returns a list with the right amount of
	 * elements in the table that are in database after it has been populated.
	 */
	@Test
	public void testGetTrackOnPopulatedTable() {
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(new Track(1, "Nothing Else Matters", "Metallica",
				"", "6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false));

		list = dbManager.getTracks(false);

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateTrack method actually changes some of the fields in a
	 * record in the track table
	 */
	@Test
	public void testUpdateTrack() {
		// track to be updated
		Track track = new Track(1, "Nothing Else Matters", "Metallica", "",
				"6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false);
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(track);

		// change values of the track and update
		track.setInventoryId(1);
		track.setSalePrice(0.79);
		track.setRemovalStatus(true);

		dbManager.updateTrack(track);

		list = dbManager.getTracks(false);
		// check if contents from database are the same as expected
		track = list.get(0);
		assertEquals(0.79, track.getSalePrice(), 2);
		assertEquals(true, track.isRemovalStatus());
	}

	/**
	 * Failure case of the updateTrack method. An invalid albumId is sent.
	 */
	@Test
	public void testUpdateTrackFailure() {
		// track to be updated
		Track track = new Track(1, "Nothing Else Matters", "Metallica", "",
				"6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false);
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(track);

		// change values of the track and update
		track.setInventoryId(1);
		track.setAlbumId(2);

		assertFalse(dbManager.updateTrack(track));
	}

	/**
	 * Tests if the insertTrack method correctly adds a record to the track
	 * table
	 */
	@Test
	public void testInsertTrack() {
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(new Track(1, "Nothing Else Matters", "Metallica",
				"", "6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false));

		list = dbManager.getTracks(false);

		assertEquals("Nothing Else Matters", list.get(0).getTrackTitle());
		assertEquals("Heavy metal", list.get(0).getMusicCategory());
	}

	/**
	 * Failure case of the insertTrack method. An invalid albumId is sent.
	 */
	@Test
	public void testInsertTrackFailure() {
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		assertFalse(dbManager.insertTrack(new Track(2, "Nothing Else Matters",
				"Metallica", "", "6:27", 1, "Heavy metal", "blackAlbum.jpg",
				0.69, 0.99, 0, new Date(), 1, false)));
	}

	/**
	 * Tests if the removeTrack method deletes a record from the track table
	 */
	@Test
	public void testRemoveTrack() {
		Track track = new Track(1, "Nothing Else Matters", "Metallica", "",
				"6:27", 1, "Heavy metal", "blackAlbum.jpg", 0.69, 0.99, 0,
				new Date(), 1, false);

		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		dbManager.insertTrack(track);

		track.setInventoryId(1);

		dbManager.removeTrack(track);

		list = dbManager.getTracks(false);

		assertEquals(0, list.size());
	}
}
