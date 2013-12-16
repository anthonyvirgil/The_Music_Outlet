//Natacha Gabbamonte
// 0932340
// ClientReviewInvoiceDetailsTest.java

package junitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import beans.Client;
import beans.Detail;
import beans.Invoice;
import beans.InvoiceAlbum;
import beans.InvoiceTrack;
import beans.Review;

import dbManager.DatabaseManager;

/**
 * This JUnit will test the various get methods of the Client, Review, Invoice
 * and Details tables.
 * 
 * @author Natacha Gabbamonte 0932340
 * 
 */
public class ClientReviewInvoiceDetailsTablesGetTest {

	private DatabaseManager dbManager = null;

	@Before
	public void before() {
		dbManager = new DatabaseManager();
	}

	//
	// TESTING CLIENT
	//

	/**
	 * Tests getting a client with a client id that exists.
	 */
	@Test
	public void getClientByClientIdTest1() {
		Client c = dbManager.getClientByClientId(1);
		assertTrue(c != null);
		assertEquals(1, c.getClientId());
	}

	/**
	 * Tests getting a client with a client id that doesn't exists.
	 */
	@Test
	public void getClientByClientIdTest2() {
		Client c = dbManager.getClientByClientId(99999);
		assertNull(c);
	}

	/**
	 * Tests getting a client with an email that exists in the database.
	 */
	@Test
	public void getClientByEmailTest1() {
		Client c = dbManager.getClientByEmail("natgabb@hotmail.com");
		assertEquals(1, c.getClientId());
	}

	/**
	 * Tests getting a client with an email that doesn't exists in the database.
	 */
	@Test
	public void getClientByEmailTest2() {
		Client c = dbManager
				.getClientByEmail("thisisanemailthatdoesntexist@idontexistdotcom.com");
		assertNull(c);
	}

	//
	// TESTING REVIEW
	//

	/**
	 * Tests getting a Review by Review Id with an ID that exits.
	 */
	@Test
	public void getReviewByReviewIdTest1() {
		Review r = dbManager.getReviewByReviewId(1);
		assertTrue(r != null);
		assertEquals(1, r.getClientId());
	}

	/**
	 * Tests getting a Review by Review Id with an ID that doesn't exits.
	 */
	@Test
	public void getReviewByReviewIdTest2() {
		Review r = dbManager.getReviewByReviewId(999999);
		assertNull(r);
	}

	/**
	 * Tests getting a Review by Track Id of an existing track.
	 */
	@Test
	public void getReviewsByTrackIdTest1() {
		ArrayList<Review> reviews = dbManager.getReviewsByTrackId(63, -1, -1);
		assertEquals(2, reviews.size());
	}

	/**
	 * Tests getting a list of Reviews by Track Id of a non-existing track.
	 */
	@Test
	public void getReviewsByTrackIdTest2() {
		ArrayList<Review> reviews = dbManager
				.getReviewsByTrackId(99999, -1, -1);
		assertEquals(0, reviews.size());
	}

	/**
	 * Tests getting a list of Reviews from a client that exists.
	 */
	@Test
	public void getReviewsByClientIdTest1() {

		ArrayList<Review> reviews = dbManager.getReviewsByClientId(2);
		assertEquals(2, reviews.size());
	}

	/**
	 * Tests getting a list of Reviews from a client that doesn't exist.
	 */
	@Test
	public void getReviewsByClientIdTest2() {

		ArrayList<Review> reviews = dbManager.getReviewsByClientId(99999);
		assertEquals(0, reviews.size());
	}

	/**
	 * Tests getting a Review from a client and track that exists
	 */
	@Test
	public void getReviewsByClientAndTrackTest() {

		Review review = dbManager.getReviewsByClientAndTrack(1, 55);
		assertNotNull(review);
	}

	/**
	 * Tests getting a Review from a client that exists but track does not exist
	 */
	@Test
	public void getReviewsByClientAndTrackTest2() {

		Review review = dbManager.getReviewsByClientAndTrack(1, 9999999);
		assertNull(review);
	}

	/**
	 * Tests getting a Review from a track that exists but client does not exist
	 */
	@Test
	public void getReviewsByClientAndTrackTest3() {

		Review review = dbManager.getReviewsByClientAndTrack(999999, 1);
		assertNull(review);
	}

	/**
	 * Tests getting a Review from a track that exists and is approved by an
	 * admin
	 */
	@Test
	public void getApprovedReviewsByTrackIdTest() {
		ArrayList<Review> reviews = dbManager.getApprovedReviewsByTrackId(55,
				-1, -1);
		assertEquals(1, reviews.size());
	}

	/**
	 * Tests getting a Review from a track exists and is not approved by an
	 * admin
	 */
	@Test
	public void getApprovedReviewsByTrackIdTest2() {
		ArrayList<Review> reviews = dbManager.getApprovedReviewsByTrackId(63,
				-1, -1);
		assertEquals(0, reviews.size());
	}

	/**
	 * Tests getting a Review from a track that doesn't exist
	 */
	@Test
	public void getApprovedReviewsByTrackIdTest3() {
		ArrayList<Review> reviews = dbManager.getApprovedReviewsByTrackId(
				99999999, -1, -1);
		assertEquals(0, reviews.size());
	}

	//
	// TESTING INVOICE
	//

	/**
	 * Tests getting an invoice by sale id with an existing id.
	 */
	@Test
	public void getInvoiceBySaleIdTest1() {
		Invoice i = dbManager.getInvoiceBySaleId(1);
		assertTrue(i != null);
		assertEquals(1, i.getSaleId());
	}

	/**
	 * Tests getting an invoice by sale id with an non-existing id.
	 */
	@Test
	public void getInvoiceBySaleIdTest2() {
		Invoice i = dbManager.getInvoiceBySaleId(99999);
		assertNull(i);
	}

	/**
	 * Tests gettings all the invoices for a client using a client id that
	 * exists.
	 */
	@Test
	public void getInvoicesByClientIdTest1() {
		ArrayList<Invoice> invoices = dbManager.getInvoicesByClientId(2);
		assertEquals(1, invoices.size());
	}

	/**
	 * Tests gettings all the invoices for a client using a client id that
	 * doesn't exists.
	 */
	@Test
	public void getInvoicesByClientIdTest2() {
		ArrayList<Invoice> invoices = dbManager.getInvoicesByClientId(99999);
		assertEquals(0, invoices.size());
	}

	/**
	 * Tests getting invoices from a date that does actually have invoices.
	 */
	@Test
	public void getInvoicesBySaleDateTest1() {

		ArrayList<Invoice> invoices = dbManager
				.getInvoicesBySaleDate("2012-11-03");
		assertEquals(1, invoices.size());
	}

	/**
	 * Tests getting invoices from a date that doesn't actually have invoices.
	 */
	@Test
	public void getInvoicesBySaleDateTest2() {
		ArrayList<Invoice> invoices = dbManager
				.getInvoicesBySaleDate("2012-11-05");
		assertEquals(0, invoices.size());
	}

	/**
	 * Tests getting invoices on a specific date by a specific client. In this
	 * test the client id exists, and the client does have an invoice on that
	 * date.
	 */
	@Test
	public void getInvoicesBySaleDateAndClientIdTest1() {
		ArrayList<Invoice> invoices = dbManager
				.getInvoicesBySaleDateAndClientId("2012-11-03", 1);
		assertEquals(1, invoices.size());
	}

	/**
	 * Tests getting invoices on a specific date by a specific client. In this
	 * test the client id exists, but the client does not have an invoice on
	 * that date.
	 */
	@Test
	public void getInvoicesBySaleDateAndClientIdTest2() {
		ArrayList<Invoice> invoices = dbManager
				.getInvoicesBySaleDateAndClientId("2013-01-06", 2);
		assertEquals(0, invoices.size());
	}

	/**
	 * Tests getting invoices on a specific date by a specific client. In this
	 * test the client id doesn't exist, but there is an invoice in the database
	 * with that sale date.
	 */
	@Test
	public void getInvoicesBySaleDateAndClientIdTest3() {
		ArrayList<Invoice> invoices = dbManager
				.getInvoicesBySaleDateAndClientId("2013-01-06", 99999);
		assertEquals(0, invoices.size());
	}

	/**
	 * Tests getting invoices on a specific date by a specific client. In this
	 * test the client id doesn't exist, and there are no invoices in the
	 * database with that date.
	 */
	@Test
	public void getInvoicesBySaleDateAndClientIdTest4() {
		ArrayList<Invoice> invoices = dbManager
				.getInvoicesBySaleDateAndClientId("2009-01-06", 99999);
		assertEquals(0, invoices.size());
	}

	/**
	 * Tests getting invoices between two dates. Tests the inclusive-ness.
	 */
	@Test
	public void getInvoicesByStartToEndDatesTest1() {
		ArrayList<Invoice> invoices = dbManager.getInvoicesByStartToEndDates(
				"2012-11-03", "2013-01-06");
		assertEquals(2, invoices.size());
	}

	/**
	 * Tests getting invoices between two dates. (No results)
	 */
	@Test
	public void getInvoicesByStartToEndDatesTest2() {
		ArrayList<Invoice> invoices = dbManager.getInvoicesByStartToEndDates(
				"2009-11-03", "2009-11-20");
		assertEquals(0, invoices.size());
	}

	/**
	 * Tests getting invoices between two dates for a certain client, with
	 * results.
	 */
	@Test
	public void getInvoicesByStartToEndDatesAndClientIdTest1() {
		ArrayList<Invoice> invoices = dbManager
				.getInvoicesByStartToEndDatesAndClientId("2013-01-03",
						"2013-02-20", 2);
		assertEquals(1, invoices.size());
	}

	/**
	 * Tests getting invoices between two dates for a certain client, with NO
	 * results.
	 */
	@Test
	public void getInvoicesByStartToEndDatesAndClientIdTest2() {
		ArrayList<Invoice> invoices = dbManager
				.getInvoicesByStartToEndDatesAndClientId("2009-11-03",
						"2009-11-20", 1);
		assertEquals(0, invoices.size());
	}

	//
	// TESTING DETAILS
	//

	/**
	 * Tests getting a detail by detail id that exists.
	 */
	@Test
	public void getDetailByDetailIdTest1() {
		Detail d = dbManager.getDetailByDetailId(1);
		assertTrue(d != null);
		assertEquals(1, d.getDetailId());
	}

	/**
	 * Tests getting a detail by detail id that doesn't exists.
	 */
	@Test
	public void getDetailByDetailIdTest2() {
		Detail d = dbManager.getDetailByDetailId(999999);
		assertTrue(d == null);
	}

	/**
	 * Tests getting Details with a sale id that exists (only one return).
	 */
	@Test
	public void getDetailsBySaleIdTest1() {
		ArrayList<Detail> details = dbManager.getDetailsBySaleId(1);
		assertEquals(1, details.size());
	}

	/**
	 * Tests getting Details with a sale id that exists (> 1 return).
	 */
	@Test
	public void getDetailsBySaleIdTest2() {
		ArrayList<Detail> details = dbManager.getDetailsBySaleId(2);
		assertEquals(4, details.size());
	}

	/**
	 * Tests getting Details with a sale id that doesn't exist.
	 */
	@Test
	public void getDetailsBySaleIdTest3() {
		ArrayList<Detail> details = dbManager.getDetailsBySaleId(999999);
		assertEquals(0, details.size());
	}

	/**
	 * Tests getting InvoiceTracks with a sale id that exists (1 return).
	 */
	@Test
	public void getInvoiceTracksBySaleIdTest() {
		ArrayList<InvoiceTrack> invoiceTracks = dbManager
				.getTracksFromDetailsBySaleId(1);
		assertEquals(1, invoiceTracks.size());
	}

	/**
	 * Tests getting InvoiceTracks with a sale id that exists (>1 return).
	 */
	@Test
	public void getInvoiceTracksBySaleIdTest2() {
		ArrayList<InvoiceTrack> invoiceTracks = dbManager
				.getTracksFromDetailsBySaleId(2);
		assertEquals(3, invoiceTracks.size());
	}

	/**
	 * Tests getting InvoiceTracks with a sale id that doesn't exist
	 */
	@Test
	public void getInvoiceTracksBySaleIdTest3() {
		ArrayList<InvoiceTrack> invoiceTracks = dbManager
				.getTracksFromDetailsBySaleId(99999999);
		assertEquals(0, invoiceTracks.size());
	}

	/**
	 * Tests getting InvoiceAlbums with a sale id that exists
	 */
	@Test
	public void getInvoiceAlbumsBySaleIdTest() {
		ArrayList<InvoiceAlbum> invoiceAlbums = dbManager
				.getAlbumsFromDetailsBySaleId(2);
		assertEquals(1, invoiceAlbums.size());
	}

	/**
	 * Tests getting InvoiceAlbums with a sale id that doesn't exist
	 */
	@Test
	public void getInvoiceAlbumsBySaleIdTest2() {
		ArrayList<InvoiceAlbum> invoiceAlbums = dbManager
				.getAlbumsFromDetailsBySaleId(1);
		assertEquals(0, invoiceAlbums.size());
	}
}
