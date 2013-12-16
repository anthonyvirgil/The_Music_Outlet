// Venelin Koulaxazov
// 1032744
// AlbumTest.java
package junitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.Album;

import dbManager.DatabaseManager;
import java.util.Date;
import java.util.ArrayList;

/**
 * JUnit test for the album related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @version 1.4
 */
public class AlbumTest {

	private DatabaseManager dbManager;
	private ArrayList<Album> list;

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
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests if the getAlbums method returns a list of size zero if there are no
	 * albums in the database.
	 */
	@Test
	public void testGetAlbumsOnEmptyTable() {
		list = dbManager.getAlbums(false);

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getAlbums method returns a list with the right amount of
	 * elements in the table that are in database after it has been populated.
	 */
	@Test
	public void testGetAlbumsOnPopulatedTable() {
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));
		list = dbManager.getAlbums(false);

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateAlbum method actually changes some of the fields in a
	 * record in the album table
	 */
	@Test
	public void testUpdateAlbum() {
		// album to be updated
		Album album = new Album("Black Album", "1991-01-01", "Metallica",
				"blackAlbum.jpg", "Metal", "Epic Records", 12, new Date(),
				5.99, 9.99, 0, false);

		dbManager.insertAlbum(album);

		// change values of the album and update
		album.setAlbumId(1);
		album.setAlbumTitle("Updated album");
		album.setRecordLabel("Updated record");

		dbManager.updateAlbum(album);

		list = dbManager.getAlbums(false);
		// check if contents from database are the same as expected
		album = list.get(0);
		assertEquals("Updated album", album.getAlbumTitle());
		assertEquals("Updated record", album.getRecordLabel());
	}

	/**
	 * Failure case of the updateAlbum method. An invalid musicCategory is sent.
	 */
	@Test
	public void testUpdateAlbumFailure() {
		// album to be updated
		Album album = new Album("Black Album", "1991-01-01", "Metallica",
				"blackAlbum.jpg", "Metal", "Epic Records", 12, new Date(),
				5.99, 9.99, 0, false);

		dbManager.insertAlbum(album);

		// change values of the album and update
		album.setAlbumId(1);
		album.setMusicCategory("This is an invalid music category which is longer than 45 characters.");

		assertFalse(dbManager.updateAlbum(album));
	}

	/**
	 * Tests if the insertAlbum method correctly adds a record to the album
	 * table
	 */
	@Test
	public void testInsertAlbum() {
		dbManager.insertAlbum(new Album("Black Album", "1991-01-01",
				"Metallica", "blackAlbum.jpg", "Metal", "Epic Records", 12,
				new Date(), 5.99, 9.99, 0, false));

		list = dbManager.getAlbums(false);

		assertEquals("Black Album", list.get(0).getAlbumTitle());
		assertEquals("Metallica", list.get(0).getArtist());
	}

	/**
	 * Failure case of the insertAlbum method. An invalid musicCategory is sent.
	 */
	@Test
	public void testInsertAlbumFailure() {
		assertFalse(dbManager
				.insertAlbum(new Album(
						"Black Album",
						"1991-01-01",
						"Metallica",
						"blackAlbum.jpg",
						"This is an invalid music category which is longer than 45 characters.",
						"Epic Records", 12, new Date(), 5.99, 9.99, 0, false)));
	}

	/**
	 * Tests if the removeAlbum method deletes a record from the album table.
	 */
	@Test
	public void testRemoveAlbum() {
		Album album = new Album("Master Of Puppets", "1985-01-01", "Metallica",
				"blackAlbum.jpg", "Metal", "Epic Records", 12, new Date(),
				5.99, 9.99, 7.99, false);

		dbManager.insertAlbum(album);

		album.setAlbumId(1);

		dbManager.removeAlbum(album);

		list = dbManager.getAlbums(false);

		assertEquals(0, list.size());
	}
}
