// Venelin Koulaxazov
// 1032744
// AlbumTrackGetTest.java
package junitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.Album;
import beans.Track;

import dbManager.DatabaseManager;

/**
 * Tests the get methods for the Album and Track tables.
 * 
 * @author Venelin Koulaxazov
 * @version 1.1
 */
public class AlbumTrackTablesGetTest {

	private DatabaseManager dbManager = null;

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

	// Album get methods

	/**
	 * Tests getting albums by a specific genre
	 */
	@Test
	public void testGetAlbumsByGenre() {
		ArrayList<Album> albums = dbManager.getAlbumsByGenre("Metal", true);

		assertEquals(5, albums.size());
	}

	/**
	 * Tests getting albums by a specific genre when it is not in the database
	 */
	@Test
	public void testGetAlbumsByGenre2() {
		ArrayList<Album> albums = dbManager.getAlbumsByGenre(
				"Non-existent genre", true);

		assertEquals(0, albums.size());
	}

	/**
	 * Tests getting albums by a specific name
	 */
	@Test
	public void testGetAlbumsByName() {
		ArrayList<Album> albums = dbManager.getAlbumsByName("2001", true);

		assertEquals(1, albums.size());
	}

	/**
	 * Tests getting albums by a specific name when it is not in the database
	 */
	@Test
	public void testGetAlbumsByName2() {
		ArrayList<Album> albums = dbManager.getAlbumsByName(
				"Non-existent name", true);

		assertEquals(0, albums.size());
	}

	/**
	 * Tests getting albums by matching a name in any way
	 */
	@Test
	public void testSearchAlbumsByName() {
		ArrayList<Album> albums = dbManager.searchAlbumsByName("The", -1, -1,
				true);

		assertEquals(7, albums.size());
	}

	/**
	 * Tests getting albums by matching a name when it is not in the database
	 */
	@Test
	public void testSearchAlbumsByName2() {
		ArrayList<Album> albums = dbManager.searchAlbumsByName(
				"Non-existent name pattern", -1, -1, true);

		assertEquals(0, albums.size());
	}

	/**
	 * Tests getting albums by matching a name at the beginning
	 */
	@Test
	public void testBrowseAlbumsByName() {
		ArrayList<Album> albums = dbManager.browseAlbumsByName("The", true);

		assertEquals(3, albums.size());
	}

	/**
	 * Tests getting albums by matching a name at the beginning when it is not
	 * in the database
	 */
	@Test
	public void testBrowseAlbumsByName2() {
		ArrayList<Album> albums = dbManager.browseAlbumsByName(
				"Non-existent name pattern", true);

		assertEquals(0, albums.size());
	}

	/**
	 * Tests getting albums by artist
	 */
	@Test
	public void testSearchAlbumsByArtist() {
		ArrayList<Album> albums = dbManager.searchAlbumsByArtist("Metallica",
				-1, -1, true);

		assertEquals(1, albums.size());
	}

	/**
	 * Tests getting albums by artist when it is not in the database
	 */
	@Test
	public void testSearchAlbumsByArtist2() {
		ArrayList<Album> albums = dbManager.searchAlbumsByArtist(
				"Non-existent artist", -1, -1, true);

		assertEquals(0, albums.size());
	}

	/**
	 * Tests getting albums by genre
	 */
	@Test
	public void testSearchAlbumsByGenre() {
		ArrayList<Album> albums = dbManager.searchAlbumsByGenre("Pop", -1, -1,
				true);

		assertEquals(10, albums.size());
	}

	/**
	 * Tests getting albums by genre when it is not in the database
	 */
	@Test
	public void testSearchAlbumsByGenre2() {
		ArrayList<Album> albums = dbManager.searchAlbumsByArtist(
				"Non-existent genre", -1, -1, true);

		assertEquals(0, albums.size());
	}

	/**
	 * Tests getting album by id
	 */
	@Test
	public void testGetAlbumById() {
		Album album = dbManager.getAlbumById(7);

		assertEquals(7, album.getAlbumId());
	}

	/**
	 * Tests getting album by id when it is not in the database
	 */
	@Test
	public void testGetAlbumById2() {
		Album album = dbManager.getAlbumById(0);

		assertNull(album);
	}

	/**
	 * Tests getting album by artist and title when they both exist
	 */
	@Test
	public void testGetAlbumByArtistAndTitle() {
		Album album = dbManager.getAlbumByArtistAndTitle("The Beatles",
				"Abbey Road");

		assertNotNull(album);
	}

	/**
	 * Tests getting album by artist that exists and title that doesn't exist
	 */
	@Test
	public void testGetAlbumByArtistAndTitle2() {
		Album album = dbManager.getAlbumByArtistAndTitle("The Beatles",
				"FAKE TITLE");

		assertNull(album);
	}

	/**
	 * Tests getting album by artist that doesn't exist and title that exists
	 */
	@Test
	public void testGetAlbumByArtistAndTitle3() {
		Album album = dbManager.getAlbumByArtistAndTitle("FAKE ARTIST",
				"Abbey Road");

		assertNull(album);
	}

	/**
	 * Tests getting album by artist and title when they both do not exist
	 */
	@Test
	public void testGetAlbumByArtistAndTitle4() {
		Album album = dbManager.getAlbumByArtistAndTitle("The Beetles",
				"Rainbow Road");

		assertNull(album);
	}

	/**
	 * Tests getting albums by artist matching a string
	 */
	@Test
	public void testBrowseAlbumsByArtist() {
		ArrayList<Album> albums = dbManager.browseAlbumsByArtist("The", true);

		assertEquals(2, albums.size());
	}

	/**
	 * Tests getting albums by artist matching a string when it is not in the
	 * database
	 */
	@Test
	public void testBrowseAlbumsByArtist2() {
		ArrayList<Album> albums = dbManager.searchAlbumsByArtist(
				"Non-existent artist pattern", -1, -1, true);

		assertEquals(0, albums.size());
	}

	/**
	 * Tests getting albums on sale
	 */
	@Test
	public void testGetAlbumsOnSale() {
		Album album = new Album("Master Of Puppets", "1985-01-01", "Metallica",
				"blackAlbum.jpg", "Metal", "Epic Records", 12, new Date(),
				5.99, 9.99, 7.99, false);
		dbManager.insertAlbum(album);
		ArrayList<Album> albums = dbManager.getAlbumsOnSale(true);

		assertEquals(1, albums.size());

		dbManager.removeAlbum(album);
	}

	/**
	 * Tests getting albums on sale when no albums are on sale
	 */
	@Test
	public void testGetAlbumsOnSale2() {
		ArrayList<Album> albums = dbManager.getAlbumsOnSale(true);

		assertEquals(0, albums.size());
	}

	/**
	 * Tests getting albums sorted by the date they were entered
	 */
	@Test
	public void testGetAlbumsSortedByDate() {
		ArrayList<Album> albums = dbManager.getAlbumsSortedByDate(true);

		assertEquals(34, albums.size());
	}

	/**
	 * Tests getting first five albums sorted by the date they were entered
	 */
	@Test
	public void testGetFirstFiveAlbumsSortedByDate() {
		ArrayList<Album> albums = dbManager.getFirstFiveAlbumsSortedByDate();

		assertEquals(5, albums.size());
	}

	/**
	 * Tests getting albums by artist
	 */
	@Test
	public void testGetAlbumsByArtist() {
		ArrayList<Album> albums = dbManager.getAlbumsByArtist("Lamb Of God",
				true);

		assertEquals(1, albums.size());
	}

	/**
	 * Tests getting albums by artist when it is not in the database
	 */
	@Test
	public void testGetAlbumsByArtist2() {
		ArrayList<Album> albums = dbManager.searchAlbumsByArtist(
				"Non-existent artist", -1, -1, true);

		assertEquals(0, albums.size());
	}

	// Track get methods

	/**
	 * Tests getting tracks by name
	 */
	@Test
	public void testGetTracksByName() {
		ArrayList<Track> tracks = dbManager.getTracksByName(
				"Nothing Else Matters", true);

		assertEquals(1, tracks.size());
	}

	/**
	 * Tests getting tracks by name when it is not in the database
	 */
	@Test
	public void testGetTracksByName2() {
		ArrayList<Track> tracks = dbManager.getTracksByName(
				"Non-existent track", true);

		assertEquals(0, tracks.size());
	}

	/**
	 * Tests getting tracks by name matching a pattern
	 */
	@Test
	public void testSearchTracksByName() {
		ArrayList<Track> tracks = dbManager.searchTracksByName("Right", -1, -1,
				true);

		assertEquals(2, tracks.size());
	}

	/**
	 * Tests getting tracks by name matching a pattern when it is not in the
	 * database
	 */
	@Test
	public void testSearchTracksByName2() {
		ArrayList<Track> tracks = dbManager.getTracksByName(
				"Non-existent track pattern", true);

		assertEquals(0, tracks.size());
	}

	/**
	 * Tests getting tracks by name matching a pattern at the beginning
	 */
	@Test
	public void testBrowseTracksByName() {
		ArrayList<Track> tracks = dbManager.browseTracksByName("A", true);

		assertEquals(7, tracks.size());
	}

	/**
	 * Tests getting tracks by name matching a pattern at the beginning when it
	 * is not in the database
	 */
	@Test
	public void testBrowseTracksByName2() {
		ArrayList<Track> tracks = dbManager.browseTracksByName(
				"Non-existent track pattern", true);

		assertEquals(0, tracks.size());
	}

	/**
	 * Tests getting tracks by artist matching a pattern
	 */
	@Test
	public void testSearchTracksByArtist() {
		ArrayList<Track> tracks = dbManager.searchTracksByArtist("J", -1, -1,
				true);

		assertEquals(13, tracks.size());
	}

	/**
	 * Tests getting tracks by artist matching a pattern when it is not in the
	 * database
	 */
	@Test
	public void testSearchTracksByArtist2() {
		ArrayList<Track> tracks = dbManager.searchTracksByArtist(
				"Non-existent artist pattern", -1, -1, true);

		assertEquals(0, tracks.size());
	}

	/**
	 * Tests getting tracks by genre matching a pattern
	 */
	@Test
	public void testSearchTracksByGenre() {
		ArrayList<Track> tracks = dbManager.searchTracksByGenre("Pop", -1, -1,
				true);

		assertEquals(31, tracks.size());
	}

	/**
	 * Tests getting tracks by genre matching a pattern when it is not in the
	 * database
	 */
	@Test
	public void testSearchTracksByGenre2() {
		ArrayList<Track> tracks = dbManager.searchTracksByGenre(
				"Non-existent genre pattern", -1, -1, true);

		assertEquals(0, tracks.size());
	}

	/**
	 * Tests getting tracks by artist matching a pattern at the beginning
	 */
	@Test
	public void testBrowseTracksByArtist() {
		ArrayList<Track> tracks = dbManager.browseTracksByArtist("Ma", true);

		assertEquals(6, tracks.size());
	}

	/**
	 * Tests getting tracks by artist matching a pattern at the beginning when
	 * it is not in the database
	 */
	@Test
	public void testBrowseTracksByArtist2() {
		ArrayList<Track> tracks = dbManager.browseTracksByArtist(
				"Non-existent artist pattern", true);

		assertEquals(0, tracks.size());
	}

	/**
	 * Tests getting tracks by genre
	 */
	@Test
	public void testGetTracksByGenre() {
		ArrayList<Track> tracks = dbManager.getTracksByGenre("Metal", true);

		assertEquals(19, tracks.size());
	}

	/**
	 * Tests getting tracks by genre when it is not in the database
	 */
	@Test
	public void testGetTracksByGenre2() {
		ArrayList<Track> tracks = dbManager.getTracksByGenre(
				"Non-existent genre", true);

		assertEquals(0, tracks.size());
	}

	/**
	 * Tests getting tracks by artist
	 */
	@Test
	public void testGetTracksByArtist() {
		ArrayList<Track> tracks = dbManager.getTracksByArtist("Arch Enemy",
				true);

		assertEquals(3, tracks.size());
	}

	/**
	 * Tests getting tracks by artist when it is not in the database
	 */
	@Test
	public void testGetTracksByArtist2() {
		ArrayList<Track> tracks = dbManager.getTracksByArtist(
				"Non-existent artist", true);

		assertEquals(0, tracks.size());
	}

	/**
	 * Tests getting tracks by album
	 */
	@Test
	public void testGetTracksByAlbum() {
		ArrayList<Track> tracks = dbManager.getTracksByAlbum(27, true);

		assertEquals(4, tracks.size());
	}

	/**
	 * Tests getting tracks by album when it is not in the database
	 */
	@Test
	public void testGetTracksByAlbum2() {
		ArrayList<Track> tracks = dbManager.getTracksByAlbum(9999, true);

		assertEquals(0, tracks.size());
	}

	/**
	 * Tests getting track by id
	 */
	@Test
	public void testGetTrackById() {
		Track track = dbManager.getTrackById(7);

		assertEquals(7, track.getInventoryId());
	}

	/**
	 * Tests getting track by id when it is not in the database
	 */
	@Test
	public void testGetTrackById2() {
		Track track = dbManager.getTrackById(0);

		assertNull(track);
	}

	/**
	 * Tests getting track by artist and title that exist
	 */
	@Test
	public void testGetTrackByArtistAndTitle() {
		Track track = dbManager.getTrackByArtistAndTitle("The Beatles",
				"Something");

		assertNotNull(track);
	}

	/**
	 * Tests getting track by artist that doesn't exist and title that exists
	 */
	@Test
	public void testGetTrackByArtistAndTitle2() {
		Track track = dbManager.getTrackByArtistAndTitle("The Beetles",
				"Something");

		assertNull(track);
	}

	/**
	 * Tests getting track by artist that exists and title that doesn't exist
	 */
	@Test
	public void testGetTrackByArtistAndTitle3() {
		Track track = dbManager.getTrackByArtistAndTitle("The Beatles",
				"Sumthing");

		assertNull(track);
	}

	/**
	 * Tests getting track by artist and title that do not exist
	 */
	@Test
	public void testGetTrackByArtistAndTitle4() {
		Track track = dbManager.getTrackByArtistAndTitle("The Beetles",
				"Sumthing");

		assertNull(track);
	}

	/**
	 * Tests getting all the purchased tracks by a client
	 */
	@Test
	public void testGetPurchasedTracksByClient() {
		ArrayList<Track> tracks = dbManager.getPurchasedTracksByClient(2);

		assertEquals(7, tracks.size());
	}

	/**
	 * Tests getting all the genres from tracks in the database
	 */
	@Test
	public void testGetGenresTest() {
		ArrayList<String> genreList = dbManager.getGenres();

		assertEquals(7, genreList.size());
	}
}
