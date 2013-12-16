// Anthony-Virgil Bermejo
// 0831360
// ReportingQueryTest.java
package junitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import beans.ClientReport;
import beans.DetailedSalesReport;
import beans.TopSellersReport;
import beans.Track;
import beans.TrackAlbumSalesReport;

import java.util.ArrayList;

import dbManager.DatabaseManager;

/**
 * JUnit test for the reporting-related methods from the DatabaseManager
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.6
 */
public class ReportingQueryTest {

	private DatabaseManager dbManager;

	@Before
	public void setUp() throws Exception {
		dbManager = new DatabaseManager();
	}

	/**
	 * Tests the getTotalSales method which returns the sum of total sales in
	 * the store
	 */
	@Test
	public void getTotalSalesTest() {
		double totalSales = dbManager.getTotalSales();

		// checks actual amount of total sales is equal to the expected amount
		assertEquals(12.61, totalSales, 2);
	}

	/**
	 * Tests the getTotalSalesDetailed method which returns a list of inventory
	 * with their total sales.
	 */
	@Test
	public void getTotalSalesDetailedTest() {
		String startDate = "2012-11-01";
		String endDate = "2013-02-07";

		ArrayList<DetailedSalesReport> reportInfo = dbManager
				.getTotalSalesDetailed(startDate, endDate, "");

		// returns correct amount of records
		assertEquals(8, reportInfo.size());

		DetailedSalesReport firstRecordResult = reportInfo.get(0);
		DetailedSalesReport secondRecordResult = reportInfo.get(1);
		DetailedSalesReport thirdRecordResult = reportInfo.get(2);

		assertEquals(firstRecordResult.getAlbumId(), 33);
		assertEquals(firstRecordResult.getAlbumTitle(), "Burnin'");
		assertEquals(firstRecordResult.getAlbumArtist(), "Bob Marley");
		assertEquals(firstRecordResult.getSalePrice(), 4.03, 2);

		assertEquals(secondRecordResult.getTrackId(), 12);
		assertEquals(secondRecordResult.getTrackTitle(),
				"All Around the World (feat. Ludacris)");
		assertEquals(secondRecordResult.getTrackArtist(), "Justin Bieber");
		assertEquals(secondRecordResult.getSalePrice(), 1.14, 2);

		assertEquals(thirdRecordResult.getTrackId(), 50);
		assertEquals(thirdRecordResult.getTrackTitle(), "Over You");
		assertEquals(thirdRecordResult.getTrackArtist(), "Daughtry");
		assertEquals(thirdRecordResult.getSalePrice(), 1.14, 2);
	}

	/**
	 * Tests the getTotalSalesDetailed method which returns no results.
	 * Purchases have not been made between these dates
	 */
	@Test
	public void getTotalSalesDetailedZeroTest() {
		// dates are from last year
		String startDate = "2012-02-06";
		String endDate = "2012-02-06";

		ArrayList<DetailedSalesReport> reportInfo = dbManager
				.getTotalSalesDetailed(startDate, endDate, "");

		// returns 0 records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByClient for a client that exists in database
	 */
	@Test
	public void getTotalSalesByClientTest() {
		String client = "natgabb@hotmail.com";

		double totalClientSales = dbManager.getTotalSalesByClient(client);

		assertEquals(5.16, totalClientSales, 2);
	}

	/**
	 * Tests the getTotalSalesByClient for a clientId that doesn't exist
	 */
	@Test
	public void getTotalSalesByClientZeroTest() {
		String client = "FAKE_EMAIL@hotmail.com";

		double totalClientSales = dbManager.getTotalSalesByClient(client);

		assertEquals(0.0, totalClientSales, 2);
	}

	/**
	 * Tests the getTotalSalesByClientDetailed for a client that exists in
	 * database with purchases between two dates
	 */
	@Test
	public void getTotalSalesByClientDetailedTest() {
		String client = "natgabb@hotmail.com";
		String startDate = "2012-11-01";
		String endDate = "2013-02-07";

		ArrayList<DetailedSalesReport> reportInfo = dbManager
				.getTotalSalesByClientDetailed(client, startDate, endDate);

		// returns correct amount of records
		assertEquals(4, reportInfo.size());

		DetailedSalesReport firstRecordResult = reportInfo.get(0);
		DetailedSalesReport secondRecordResult = reportInfo.get(1);
		DetailedSalesReport thirdRecordResult = reportInfo.get(2);
		DetailedSalesReport fourthRecordResult = reportInfo.get(3);

		assertEquals(firstRecordResult.getAlbumId(), 34);
		assertEquals(firstRecordResult.getAlbumTitle(), "Welcome to Jamrock");
		assertEquals(firstRecordResult.getAlbumArtist(), "Damian Marley");
		assertEquals(firstRecordResult.getSalePrice(), 2.88, 2);

		assertEquals(secondRecordResult.getTrackId(), 6);
		assertEquals(secondRecordResult.getTrackTitle(), "Bonfire");
		assertEquals(secondRecordResult.getTrackArtist(), "Childish Gambino");
		assertEquals(secondRecordResult.getSalePrice(), 1.14, 2);

		assertEquals(thirdRecordResult.getTrackId(), 50);
		assertEquals(thirdRecordResult.getTrackTitle(), "Over You");
		assertEquals(thirdRecordResult.getTrackArtist(), "Daughtry");
		assertEquals(thirdRecordResult.getSalePrice(), 1.14, 2);

		assertEquals(fourthRecordResult.getTrackId(), 5);
		assertEquals(fourthRecordResult.getTrackTitle(), "Fire Fly");
		assertEquals(fourthRecordResult.getTrackArtist(), "Childish Gambino");
		assertEquals(fourthRecordResult.getSalePrice(), 1.14, 2);
	}

	/**
	 * Tests the getTotalSalesByClientDetailed for a client that does not exist
	 * in database
	 */
	@Test
	public void getTotalSalesByClientDetailedZeroTest() {
		String client = "fake_email@hotmail.com";
		String startDate = "2012-11-01";
		String endDate = "2013-02-07";

		ArrayList<DetailedSalesReport> reportInfo = dbManager
				.getTotalSalesByClientDetailed(client, startDate, endDate);

		// returns no records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByClientDetailed for a client that hasn't made
	 * purchases between the two dates
	 */
	@Test
	public void getTotalSalesByClientDetailedZeroTest2() {
		String client = "natgabb@hotmail.com";
		String startDate = "2010-02-01";
		String endDate = "2010-11-07";

		ArrayList<DetailedSalesReport> reportInfo = dbManager
				.getTotalSalesByClientDetailed(client, startDate, endDate);

		// returns no records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByArtist method that returns the amount of total
	 * sales for an artist
	 */
	@Test
	public void getTotalSalesByArtistTest() {
		String artistName = "Childish Gambino";

		double artistTotalSales = dbManager.getTotalSalesByArtist(artistName);

		assertEquals(2.28, artistTotalSales, 2);
	}

	/**
	 * Tests the getTotalSalesByArtist method that returns the amount of total
	 * sales for an artist that doesn't exist
	 */
	@Test
	public void getTotalSalesByArtistZeroTest() {
		String artistName = "She & Him";

		double artistTotalSales = dbManager.getTotalSalesByArtist(artistName);

		assertEquals(0.0, artistTotalSales, 2);
	}

	/**
	 * Tests the getTotalSalesByArtistDetailed method that returns String arrays
	 * of search results for an artist that exists with purchases made between
	 * two dates
	 */
	@Test
	public void getTotalSalesByArtistDetailedTest() {
		String startDate = "2012-11-01";
		String endDate = "2013-02-07";
		String artistName = "Childish Gambino";

		ArrayList<DetailedSalesReport> reportInfo = dbManager
				.getTotalSalesByArtistDetailed(artistName, startDate, endDate);

		// returns correct amount of records
		assertEquals(2, reportInfo.size());

		DetailedSalesReport firstRecordResult = reportInfo.get(0);
		DetailedSalesReport secondRecordResult = reportInfo.get(1);

		assertEquals(firstRecordResult.getTrackId(), 6);
		assertEquals(firstRecordResult.getTrackTitle(), "Bonfire");
		assertEquals(firstRecordResult.getTrackArtist(), "Childish Gambino");
		assertEquals(firstRecordResult.getSalePrice(), 1.14, 2);

		assertEquals(secondRecordResult.getTrackId(), 5);
		assertEquals(secondRecordResult.getTrackTitle(), "Fire Fly");
		assertEquals(secondRecordResult.getTrackArtist(), "Childish Gambino");
		assertEquals(secondRecordResult.getSalePrice(), 1.14, 2);
	}

	/**
	 * Tests the getTotalSalesByArtistDetailed method that returns no search
	 * results for an artist that doesn't exist
	 */
	@Test
	public void getTotalSalesByArtistDetailedZeroTest() {
		String startDate = "2012-11-01";
		String endDate = "2013-02-07";
		String artistName = "Britney Spears";

		ArrayList<DetailedSalesReport> reportInfo = dbManager
				.getTotalSalesByArtistDetailed(artistName, startDate, endDate);

		// returns no records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByArtistDetailed method that returns no search
	 * results for an artist that exists with no purchases made between two
	 * dates
	 */
	@Test
	public void getTotalSalesByArtistDetailedZeroTest2() {
		String startDate = "2010-11-01";
		String endDate = "2010-02-07";
		String artistName = "Childish Gambino";

		ArrayList<DetailedSalesReport> reportInfo = dbManager
				.getTotalSalesByArtistDetailed(artistName, startDate, endDate);

		// returns no records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByTrack method that will return the total sales
	 * for a track that exists in the database
	 */
	@Test
	public void getTotalSalesByTrackTest() {
		String trackTitle = "Over You";
		double totalTrackSales = dbManager.getTotalSalesByTrack(trackTitle);

		assertEquals(2.28, totalTrackSales, 2);
	}

	/**
	 * Tests the getTotalSalesByTrack method that will return the total sales
	 * for a track does not exist
	 */
	@Test
	public void getTotalSalesByTrackZeroTest() {
		String trackTitle = "This Is A Fake Song";
		double totalTrackSales = dbManager.getTotalSalesByTrack(trackTitle);

		assertEquals(0.00, totalTrackSales, 2);
	}

	/**
	 * Tests the getTotalDownloadsByTrack method that returns the total
	 * downloads of a track that exists
	 */
	@Test
	public void getTotalDownloadsByTrackTest() {
		String trackTitle = "Over You";
		int totalTrackDownloads = dbManager
				.getTotalDownloadsByTrack(trackTitle);

		assertEquals(2, totalTrackDownloads);
	}

	/**
	 * Tests the getTotalDownloadsByTrack method that returns the total
	 * downloads of a track that does not exist
	 */
	@Test
	public void getTotalDownloadsByTrackZeroTest() {
		String trackTitle = "Fake Song";
		int totalTrackDownloads = dbManager
				.getTotalDownloadsByTrack(trackTitle);

		assertEquals(0, totalTrackDownloads);
	}

	/**
	 * Tests the getTotalSalesByTrackDetailedTest that returns the reporting
	 * info for a track that exists and purchased between two dates
	 */
	@Test
	public void getTotalSalesByTrackDetailedTest() {
		String trackTitle = "Over You";
		String startDate = "2012-11-01";
		String endDate = "2013-02-07";

		ArrayList<TrackAlbumSalesReport> reportInfo = dbManager
				.getTotalSalesByTrackDetailed(trackTitle, startDate, endDate);

		// returns correct amount of records
		assertEquals(2, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByTrackDetailedTest that returns the reporting
	 * info for a track that does not exist
	 */
	@Test
	public void getTotalSalesByTrackDetailedZeroTest() {
		String trackTitle = "My Fake Song";
		String startDate = "2012-11-01";
		String endDate = "2013-02-07";

		ArrayList<TrackAlbumSalesReport> reportInfo = dbManager
				.getTotalSalesByTrackDetailed(trackTitle, startDate, endDate);

		// returns correct amount of records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByTrackDetailedTest that returns the reporting
	 * info for a track that exists but not purchased between two dates
	 */
	@Test
	public void getTotalSalesByTrackDetailedZeroTest2() {
		String trackTitle = "Over You";
		String startDate = "2010-11-01";
		String endDate = "2010-02-07";

		ArrayList<TrackAlbumSalesReport> reportInfo = dbManager
				.getTotalSalesByTrackDetailed(trackTitle, startDate, endDate);

		// returns correct amount of records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByAlbum method that returns the amount of total
	 * sales for an album that exists
	 */
	@Test
	public void getTotalSalesByAlbumTest() {
		String albumTitle = "Burnin'";
		double totalTrackSales = dbManager.getTotalSalesByAlbum(albumTitle);

		assertEquals(4.03, totalTrackSales, 2);
	}

	/**
	 * Tests the getTotalSalesByAlbum method that returns the amount of total
	 * sales for an album that does not exist
	 */
	@Test
	public void getTotalSalesByAlbumZeroTest() {
		String albumTitle = "Fake Album";
		double totalTrackSales = dbManager.getTotalSalesByAlbum(albumTitle);

		assertEquals(0.0, totalTrackSales, 2);
	}

	/**
	 * Tests the getTotalDownloadsByAlbum method that returns the total
	 * downloads of an album that exists
	 */
	@Test
	public void getTotalDownloadsByAlbumTest() {
		String albumTitle = "Burnin'";
		int totalAlbumDownloads = dbManager
				.getTotalDownloadsByAlbum(albumTitle);

		assertEquals(1, totalAlbumDownloads);
	}

	/**
	 * Tests the getTotalDownloadsByAlbum method that returns the total
	 * downloads of an album that does not exist
	 */
	@Test
	public void getTotalDownloadsByAlbumZeroTest() {
		String albumTitle = "Fake Album";
		int totalAlbumDownloads = dbManager
				.getTotalDownloadsByAlbum(albumTitle);

		assertEquals(0, totalAlbumDownloads);
	}

	/**
	 * Tests the getTotalSalesByAlbumDetailed method that returns reporting info
	 * for an album that exists in the database and was purchased between two
	 * dates
	 */
	@Test
	public void getTotalSalesByAlbumDetailedTest() {
		String startDate = "2012-11-01";
		String endDate = "2013-02-07";
		String albumTitle = "Welcome to Jamrock";

		ArrayList<TrackAlbumSalesReport> reportInfo = dbManager
				.getTotalSalesByAlbumDetailed(albumTitle, startDate, endDate);

		// returns correct amount of records
		assertEquals(1, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByAlbumDetailed method that returns reporting info
	 * for an album that does not exist
	 */
	@Test
	public void getTotalSalesByAlbumDetailedZeroTest() {
		String startDate = "2012-11-01";
		String endDate = "2013-02-07";
		String albumTitle = "Fake Album";

		ArrayList<TrackAlbumSalesReport> reportInfo = dbManager
				.getTotalSalesByAlbumDetailed(albumTitle, startDate, endDate);

		// returns correct amount of records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTotalSalesByAlbumDetailed method that returns reporting info
	 * for an album that exists but not purchased between the two dates
	 */
	@Test
	public void getTotalSalesByAlbumDetailedZeroTest2() {
		String startDate = "2010-11-01";
		String endDate = "2010-02-07";
		String albumTitle = "Welcome to Jamrock";

		ArrayList<TrackAlbumSalesReport> reportInfo = dbManager
				.getTotalSalesByAlbumDetailed(albumTitle, startDate, endDate);

		// returns correct amount of records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTopSellers method that returns reporting info for all
	 * inventory in order of total sales
	 */
	@Test
	public void getTopSellersTest() {
		ArrayList<TopSellersReport> reportInfo = dbManager.getTopSellers("");

		// returns correct amount of records
		assertEquals(7, reportInfo.size());

		TopSellersReport firstRecordResult = reportInfo.get(0);
		TopSellersReport secondRecordResult = reportInfo.get(1);
		TopSellersReport thirdRecordResult = reportInfo.get(2);
		TopSellersReport fourthRecordResult = reportInfo.get(3);

		assertEquals(firstRecordResult.getAlbumId(), 33);
		assertEquals(firstRecordResult.getAlbumTitle(), "Burnin'");
		assertEquals(firstRecordResult.getAlbumArtist(), "Bob Marley");
		assertEquals(firstRecordResult.getTotalSales(), 4.03, 2);

		assertEquals(secondRecordResult.getAlbumId(), 34);
		assertEquals(secondRecordResult.getAlbumTitle(), "Welcome to Jamrock");
		assertEquals(secondRecordResult.getAlbumArtist(), "Damian Marley");
		assertEquals(secondRecordResult.getTotalSales(), 2.88, 2);

		assertEquals(thirdRecordResult.getTrackId(), 50);
		assertEquals(thirdRecordResult.getTrackTitle(), "Over You");
		assertEquals(thirdRecordResult.getTrackArtist(), "Daughtry");
		assertEquals(thirdRecordResult.getTotalSales(), 2.28, 2);

		assertEquals(fourthRecordResult.getTrackId(), 5);
		assertEquals(fourthRecordResult.getTrackTitle(), "Fire Fly");
		assertEquals(fourthRecordResult.getTrackArtist(), "Childish Gambino");
		assertEquals(fourthRecordResult.getTotalSales(), 1.14, 2);
	}

	/**
	 * Tests the getTopSellers method that returns reporting info for all
	 * inventory in order of total sales between two dates
	 */
	@Test
	public void getTopSellersTest2() {
		ArrayList<TopSellersReport> reportInfo = dbManager.getTopSellers("",
				"2012-01-01", "2013-04-21");

		// returns correct amount of records
		assertEquals(7, reportInfo.size());
	}

	/**
	 * Tests the getTopSellers method that returns reporting info for all
	 * inventory in order of total sales between two dates where nothing was
	 * bought
	 */
	@Test
	public void getTopSellersTest3() {
		ArrayList<TopSellersReport> reportInfo = dbManager.getTopSellers("",
				"2015-01-01", "2015-04-21");

		// returns correct amount of records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTopSellers method that returns no reporting info for a
	 * non-existing item
	 */
	@Test
	public void getTopSellersZeroTest() {

		ArrayList<TopSellersReport> reportInfo = dbManager
				.getTopSellers("FAKE SONG");

		// returns correct amount of records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTopClients method which returns info of all clients that
	 * have purchased
	 */
	@Test
	public void getTopClientsTest() {

		ArrayList<ClientReport> reportInfo = dbManager.getTopClients("");

		// returns correct amount of records
		assertEquals(2, reportInfo.size());

		ClientReport firstRecordResult = reportInfo.get(0);
		ClientReport secondRecordResult = reportInfo.get(1);

		assertEquals(firstRecordResult.getClientId(), 2);
		assertEquals(firstRecordResult.getFirstName(), "Anthony-Virgil");
		assertEquals(firstRecordResult.getLastName(), "Bermejo");
		assertEquals(firstRecordResult.getEmail(), "anthony@bermejo.com");
		assertEquals(firstRecordResult.getTotalSales(), 7.45, 2);

		assertEquals(secondRecordResult.getClientId(), 1);
		assertEquals(secondRecordResult.getFirstName(), "Natacha");
		assertEquals(secondRecordResult.getLastName(), "Gabbamonte");
		assertEquals(secondRecordResult.getEmail(), "natgabb@hotmail.com");
		assertEquals(secondRecordResult.getTotalSales(), 5.16, 2);
	}

	/**
	 * Tests the getTopClients method which returns info of all clients that
	 * have purchased between two dates
	 */
	@Test
	public void getTopClientsTest2() {

		ArrayList<ClientReport> reportInfo = dbManager.getTopClients("",
				"2012-01-01", "2013-04-21");

		// returns correct amount of records
		assertEquals(2, reportInfo.size());
	}

	/**
	 * Tests the getTopClients method which returns info of all clients that
	 * have not purchased
	 */
	@Test
	public void getTopClientsZeroTest() {

		ArrayList<ClientReport> reportInfo = dbManager
				.getTopClients("FAKE CLIENT");

		// returns correct amount of records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getTopClients method which returns info of all clients that
	 * have purchased between two dates where nothing was bought
	 */
	@Test
	public void getTopClientsZeroTest2() {

		ArrayList<ClientReport> reportInfo = dbManager.getTopClients("",
				"2015-01-01", "2015-04-21");

		// returns correct amount of records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getZeroClients method which returns reporting info for all
	 * clients that have not made a purchase ever
	 */
	@Test
	public void getZeroClientsTest() {

		ArrayList<ClientReport> reportInfo = dbManager.getZeroClients();

		// returns correct amount of records
		assertEquals(2, reportInfo.size());
	}

	/**
	 * Tests the getZeroClients method which returns reporting info for all
	 * clients that have not made a purchase between two dates
	 */
	@Test
	public void getZeroClientsTest2() {

		ArrayList<ClientReport> reportInfo = dbManager.getZeroClients(
				"2013-01-01", "2013-04-21");

		// returns correct amount of records
		assertEquals(2, reportInfo.size());
	}

	/**
	 * Tests the getZeroClients method which returns reporting info for all
	 * clients that have all made purchases between two dates
	 */
	@Test
	public void getZeroClientsZeroTest() {

		ArrayList<ClientReport> reportInfo = dbManager.getZeroClients(
				"2012-01-01", "2013-04-21");

		// returns correct amount of records
		assertEquals(0, reportInfo.size());
	}

	/**
	 * Tests the getZeroTracks method which returns reporting info for all
	 * tracks that have not been purchased yet
	 */
	@Test
	public void getZeroTracksTest() {

		ArrayList<Track> reportInfo = dbManager.getZeroTracks();

		// returns correct amount of records
		assertEquals(100, reportInfo.size());
	}

	/**
	 * Tests the getZeroTracks method which returns reporting info for all
	 * tracks that have not been purchased yet between two dates
	 */
	@Test
	public void getZeroTracksTest2() {

		ArrayList<Track> reportInfo = dbManager.getZeroTracks("2012-01-01",
				"2013-04-21");

		// returns correct amount of records
		assertEquals(100, reportInfo.size());
	}

	/**
	 * Tests the getZeroTracks method which returns reporting info for all
	 * tracks that have not been purchased yet between two dates but some
	 * purchases on other dates
	 */
	@Test
	public void getZeroTracksTest3() {

		ArrayList<Track> reportInfo = dbManager.getZeroTracks("2013-01-01",
				"2013-04-21");

		// returns correct amount of records
		assertEquals(101, reportInfo.size());
	}
}
