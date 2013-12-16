// Venelin Koulaxazov
// 1032744
// RssFeedTest.java
package junitTests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.Rss;

import dbManager.DatabaseManager;
import java.util.ArrayList;

/**
 * JUnit test for the rssFeed related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class RssFeedTest {

	private DatabaseManager dbManager;
	private ArrayList<Rss> list;

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
		dbManager.dropTable("rssFeed");

		// re-create the table
		dbManager.createRssFeedTable();
	}

	/**
	 * Tests if the getRssFeeds method returns a list of size zero if there are
	 * no rssFeeds in the database.
	 */
	@Test
	public void testGetRssFeedsOnEmptyTable() {
		list = dbManager.getRssFeeds();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getRssFeeds method returns a list with the right amount of
	 * elements in the table that are in database after it has been populated.
	 */
	@Test
	public void testGetRssFeedsOnPopulatedTable() {
		dbManager.insertRssFeed(new Rss("MTV feed", "www.mtv.com", false));
		list = dbManager.getRssFeeds();

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateRssFeed method actually changes some of the fields in
	 * a record in the rssFeed table
	 */
	@Test
	public void testUpdateRssFeed() {
		// rssFeed to be updated
		Rss rss = new Rss("MTV feed", "www.mtv.com", false);

		dbManager.insertRssFeed(rss);

		// change values of the rssFeed and update
		rss.setName("New name");
		rss.setUrl("www.new.com");

		dbManager.updateRssFeed(rss);

		list = dbManager.getRssFeeds();
		// check if contents from database are the same as expected
		rss = list.get(0);
		assertEquals("New name", rss.getName());
		assertEquals("www.new.com", rss.getUrl());
	}

	/**
	 * Failure case of the updateRssFeed method. An invalid name is sent.
	 */
	@Test
	public void testUpdateAlbumFailure() {
		// rssFeed to be updated
		Rss rss = new Rss("MTV feed", "www.mtv.com", false);

		dbManager.insertRssFeed(rss);

		// change values of the rssFeed and update
		rss.setName("This is a new name which will be longer than 100 characters "
				+ "in order to prove that an update is not possible");

		assertFalse(dbManager.updateRssFeed(rss));
	}

	/**
	 * Tests if the insertRssFeed method correctly adds a record to the rssFeed
	 * table
	 */
	@Test
	public void testInsertRss() {
		dbManager.insertRssFeed(new Rss("MTV feed", "www.mtv.com", false));

		list = dbManager.getRssFeeds();

		assertEquals("MTV feed", list.get(0).getName());
		assertEquals(false, list.get(0).isStatus());
	}

	/**
	 * Failure case of the insertRssFeed method. An invalid name is sent.
	 */
	@Test
	public void testInsertRssFeedFailure() {
		assertFalse(dbManager.insertRssFeed(new Rss(
				"This is a new name which will be longer than 100 characters "
						+ "in order to prove that an insert is not possible",
				"www.mtv.com", true)));
	}

	/**
	 * Tests if the removeRssFeed method deletes a record from the rssFeed
	 * table.
	 */
	@Test
	public void testRemoveRssFeed() {
		Rss rss = new Rss("MTV feed", "www.mtv.com", false);

		dbManager.insertRssFeed(rss);

		rss.setRssFeedId(1);

		dbManager.removeRssFeed(rss);

		list = dbManager.getRssFeeds();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getRssFeedById methods returns the needed rss
	 */
	@Test
	public void testGetRssFeedById() {
		Rss rss = new Rss("MTV music",
				"http://www.mtv.com/rss/news/news_full.jhtml", true);
		dbManager.insertRssFeed(rss);
		assertEquals("MTV music", dbManager.getRssFeedById(1));
	}

	/**
	 * Tests if the getCurrentRssFeed method returns the currently used rss feed
	 */
	@Test
	public void testGetCurrentRssFeed() {
		dbManager.insertRssFeed(new Rss("MTV music",
				"http://www.mtv.com/rss/news/news_full.jhtml", true));
		assertEquals("MTV music", dbManager.getCurrentRssFeed().getName());
	}

	/**
	 * Tests if the getOtherRssFeeds method returns other rss feeds
	 */
	@Test
	public void testGetOtherRssFeeds() {
		dbManager.insertRssFeed(new Rss("1",
				"http://www.mtv.com/rss/news/news_full.jhtml", false));
		dbManager.insertRssFeed(new Rss("2",
				"http://www.mtv.com/rss/news/news_full.jhtml", false));
		dbManager.insertRssFeed(new Rss("3",
				"http://www.mtv.com/rss/news/news_full.jhtml", false));

		list = dbManager.getOtherRssFeeds();

		assertEquals(3, list.size());
	}
}
