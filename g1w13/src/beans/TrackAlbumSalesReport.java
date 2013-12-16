// Anthony-Virgil Bermejo
// 0831360
// TrackAlbumSalesReport.java
package beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a sales report that holds reporting info for a track or an album
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class TrackAlbumSalesReport implements Serializable {

	// instance variables
	private static final long serialVersionUID = 328163071130448272L;
	private int itemId;
	private Date invoiceSaleDate;
	private double salePrice;
	private String itemTitle;
	private String artist;
	private String clientEmail;

	/**
	 * No param constructor, setting instance variables to default values
	 */
	public TrackAlbumSalesReport() {
		invoiceSaleDate = new Date();
	}

	/**
	 * @param itemId
	 * @param invoiceSaleDate
	 * @param salePrice
	 * @param itemTitle
	 * @param artist
	 * @param clientEmail
	 */
	public TrackAlbumSalesReport(int itemId, Date invoiceSaleDate,
			double salePrice, String itemTitle, String artist,
			String clientEmail) {
		super();
		this.itemId = itemId;
		this.invoiceSaleDate = invoiceSaleDate;
		this.salePrice = salePrice;
		this.itemTitle = itemTitle;
		this.artist = artist;
		this.clientEmail = clientEmail;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemTitle
	 */
	public String getItemTitle() {
		return itemTitle;
	}

	/**
	 * @param itemTitle
	 *            the itemTitle to set
	 */
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @param artist
	 *            the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * @return the clientEmail
	 */
	public String getClientEmail() {
		return clientEmail;
	}

	/**
	 * @param clientEmail
	 *            the clientEmail to set
	 */
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}

	/**
	 * Returns the sale date of the invoice
	 * 
	 * @return the invoiceSaleDate
	 */
	public Date getInvoiceSaleDate() {
		return invoiceSaleDate;
	}

	/**
	 * Sets the invoice sale date
	 * 
	 * @param invoiceSaleDate
	 *            the invoiceSaleDate to set
	 */
	public void setInvoiceSaleDate(Date invoiceSaleDate) {
		this.invoiceSaleDate = invoiceSaleDate;
	}

	/**
	 * Returns the sale price of the item
	 * 
	 * @return the salePrice
	 */
	public double getSalePrice() {
		return salePrice;
	}

	/**
	 * Sets the sale price of the item
	 * 
	 * @param salePrice
	 *            the salePrice to set
	 */
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

}
