// Anthony-Virgil Bermejo
// 0831360
// DetailedSalesReport.java
package beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Java bean that represents a detailed sales report
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class DetailedSalesReport implements Serializable {

	// instance variables
	private static final long serialVersionUID = -7710349415225825305L;
	private int albumId;
	private String albumTitle;
	private String albumArtist;
	private int trackId;
	private String trackTitle;
	private String trackArtist;
	private double salePrice;
	private Date saleDate;
	private String clientEmail;

	/**
	 * 
	 */
	public DetailedSalesReport() {
		super();
		saleDate = new Date();
	}

	/**
	 * @param albumId
	 * @param albumTitle
	 * @param albumArtist
	 * @param trackId
	 * @param trackTitle
	 * @param trackArtist
	 * @param salePrice
	 * @param saleDate
	 * @param clientEmail
	 */
	public DetailedSalesReport(int albumId, String albumTitle,
			String albumArtist, int trackId, String trackTitle,
			String trackArtist, double salePrice, Date saleDate,
			String clientEmail) {
		super();
		this.albumId = albumId;
		this.albumTitle = albumTitle;
		this.albumArtist = albumArtist;
		this.trackId = trackId;
		this.trackTitle = trackTitle;
		this.trackArtist = trackArtist;
		this.salePrice = salePrice;
		this.saleDate = saleDate;
		this.clientEmail = clientEmail;
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
	 * Returns the id of the album
	 * 
	 * @return the albumId
	 */
	public int getAlbumId() {
		return albumId;
	}

	/**
	 * Sets the id of the album
	 * 
	 * @param albumId
	 *            the albumId to set
	 */
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	/**
	 * Returns the title of the album
	 * 
	 * @return the albumTitle
	 */
	public String getAlbumTitle() {
		return albumTitle;
	}

	/**
	 * Sets the title of the album
	 * 
	 * @param albumTitle
	 *            the albumTitle to set
	 */
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	/**
	 * Returns the artist of the album
	 * 
	 * @return the albumArtist
	 */
	public String getAlbumArtist() {
		return albumArtist;
	}

	/**
	 * Sets the artist of the album
	 * 
	 * @param albumArtist
	 *            the albumArtist to set
	 */
	public void setAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
	}

	/**
	 * Returns the id of the track
	 * 
	 * @return the trackId
	 */
	public int getTrackId() {
		return trackId;
	}

	/**
	 * Sets the id of the track
	 * 
	 * @param trackId
	 *            the trackId to set
	 */
	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}

	/**
	 * Returns the title of the track
	 * 
	 * @return the trackTitle
	 */
	public String getTrackTitle() {
		return trackTitle;
	}

	/**
	 * Sets the title of the track
	 * 
	 * @param trackTitle
	 *            the trackTitle to set
	 */
	public void setTrackTitle(String trackTitle) {
		this.trackTitle = trackTitle;
	}

	/**
	 * Returns the artist of the track
	 * 
	 * @return the trackArtist
	 */
	public String getTrackArtist() {
		return trackArtist;
	}

	/**
	 * Sets the artist of the track
	 * 
	 * @param trackArtist
	 *            the trackArtist to set
	 */
	public void setTrackArtist(String trackArtist) {
		this.trackArtist = trackArtist;
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

	/**
	 * @return the saleDate
	 */
	public Date getSaleDate() {
		return saleDate;
	}

	/**
	 * @param saleDate
	 *            the saleDate to set
	 */
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

}
