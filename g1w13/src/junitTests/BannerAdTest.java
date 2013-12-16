// Venelin Koulaxazov
// 1032744
// BannerAdTest.java
package junitTests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import beans.BannerAd;

import dbManager.DatabaseManager;
import java.util.ArrayList;

/**
 * JUnit test for the bannerAd related methods from the DatabaseManager
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class BannerAdTest {

	private DatabaseManager dbManager;
	private ArrayList<BannerAd> list;

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
		dbManager.dropTable("bannerAd");

		// re-create the tables
		dbManager.createBannerAdTable();
	}

	/**
	 * Tests if the getBannerAds method returns a list of size zero if there are
	 * no banner ads in the database.
	 */
	@Test
	public void testGetBannerAdsOnEmptyTable() {
		list = dbManager.getBannerAds();

		assertEquals(0, list.size());
	}

	/**
	 * Tests if the getBannerAds method returns a list with the right amount of
	 * elements in the table that are in database after it has been populated.
	 */
	@Test
	public void testGetBannerAdsOnPopulatedTable() {
		dbManager.insertBannerAd(new BannerAd("url", 1, "source", true));
		list = dbManager.getBannerAds();

		assertEquals(1, list.size());
	}

	/**
	 * Tests if the updateBannerAd method actually changes some of the fields in
	 * a record in the bannerAd table
	 */
	@Test
	public void testUpdateBannerAd() {
		// survey to be updated
		BannerAd bannerAd = new BannerAd("url", 1, "source", true);

		dbManager.insertBannerAd(bannerAd);

		// change values of the banner ad and update
		bannerAd.setUrl("New url");
		bannerAd.setImageSource("New source");

		dbManager.updateBannerAd(bannerAd);

		list = dbManager.getBannerAds();
		// check if contents from database are the same as expected
		bannerAd = list.get(0);
		assertEquals("New url", bannerAd.getUrl());
		assertEquals("New source", bannerAd.getImageSource());
	}

	/**
	 * Failure case of the updateBannerAd method. An invalid type is sent.
	 */
	@Test
	public void testUpdateBannerAdFailure() {
		// banner ad to be updated
		BannerAd bannerAd = new BannerAd("url", 1, "source", true);

		dbManager.insertBannerAd(bannerAd);

		// change values of the bannerAd and update
		bannerAd.setUrl("This is a new and very long url which will be longer "
				+ "than 150 charachters in order to prove that an update is impossible "
				+ "since there is a violation in the field's length");

		assertFalse(dbManager.updateBannerAd(bannerAd));
	}

	/**
	 * Tests if the insertBannerAd method correctly adds a record to the
	 * bannerAd table
	 */
	@Test
	public void testInsertBannerAd() {
		dbManager.insertBannerAd(new BannerAd("url", 1, "source", true));

		list = dbManager.getBannerAds();

		assertEquals("url", list.get(0).getUrl());
		assertEquals(1, list.get(0).getType());
	}

	/**
	 * Failure case of the insertBannerAd method. An invalid type is sent.
	 */
	@Test
	public void testInsertBannerAdFailure() {
		assertFalse(dbManager
				.insertBannerAd(new BannerAd(
						"This is a new and very long url which will be longer "
								+ ""
								+ "than 150 charachters in order to prove that an update is impossible "
								+ "since there is a violation in the field's length",
						10, "source", true)));
	}

	/**
	 * Tests if the removeBannerAd method deletes a record from the BannerAd
	 * table.
	 */
	@Test
	public void testRemoveBannerAd() {
		BannerAd bannerAd = new BannerAd("url", 1, "source", true);

		dbManager.insertBannerAd(bannerAd);

		bannerAd.setBannerId(1);

		dbManager.removeBannerAd(bannerAd);

		list = dbManager.getBannerAds();

		assertEquals(0, list.size());
	}
}