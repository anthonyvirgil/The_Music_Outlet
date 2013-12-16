// Venelin Koulaxazov
// 1032744
// Album.java
package beans;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Java bean for an album in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.4
 */
public class Album implements Serializable {

	private static final long serialVersionUID = 6056813115724903583L;
	private int albumId;
	private String albumTitle;
	private String releaseDate;
	private String artist;
	private String albumCover;
	private String musicCategory;
	private String recordLabel;
	private int numOfTracks;
	private Date dateEntered;
	private double costPrice;
	private double listPrice;
	private double salePrice;
	private boolean removalStatus;

	/**
	 * Default constructor
	 */
	public Album() {
		albumTitle = "";
		albumCover = "";
		releaseDate = "";
		artist = "";
		musicCategory = "";
		recordLabel = "";
		dateEntered = new Date();
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param albumTitle
	 * @param releaseDate
	 * @param artist
	 * @param albumCover
	 * @param musicCategory
	 * @param recordLabel
	 * @param numOfTracks
	 * @param dateEntered
	 * @param costPrice
	 * @param listPrice
	 * @param salePrice
	 * @param removalStatus
	 */
	public Album(String albumTitle, String releaseDate, String artist,
			String albumCover, String musicCategory, String recordLabel,
			int numOfTracks, Date dateEntered, double costPrice,
			double listPrice, double salePrice, boolean removalStatus) {
		super();
		this.albumTitle = albumTitle;
		this.releaseDate = releaseDate;
		this.artist = artist;
		this.albumCover = albumCover;
		this.musicCategory = musicCategory;
		this.recordLabel = recordLabel;
		this.numOfTracks = numOfTracks;
		this.dateEntered = dateEntered;
		this.costPrice = costPrice;
		this.listPrice = listPrice;
		this.salePrice = salePrice;
		this.removalStatus = removalStatus;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param albumId
	 * @param albumTitle
	 * @param releaseDate
	 * @param artist
	 * @param albumCover
	 * @param musicCategory
	 * @param recordLabel
	 * @param numOfTracks
	 * @param dateEntered
	 * @param costPrice
	 * @param listPrice
	 * @param salePrice
	 * @param removalStatus
	 */
	public Album(int albumId, String albumTitle, String releaseDate,
			String artist, String albumCover, String musicCategory,
			String recordLabel, int numOfTracks, Date dateEntered,
			double costPrice, double listPrice, double salePrice,
			boolean removalStatus) {
		super();
		this.albumId = albumId;
		this.albumTitle = albumTitle;
		this.releaseDate = releaseDate;
		this.artist = artist;
		this.albumCover = albumCover;
		this.musicCategory = musicCategory;
		this.recordLabel = recordLabel;
		this.numOfTracks = numOfTracks;
		this.dateEntered = dateEntered;
		this.costPrice = costPrice;
		this.listPrice = listPrice;
		this.salePrice = salePrice;
		this.removalStatus = removalStatus;
	}

	/**
	 * Returns the album id
	 * 
	 * @return the albumId
	 */
	public int getAlbumId() {
		return albumId;
	}

	/**
	 * Sets the album id
	 * 
	 * @param albumId
	 *            the albumId to set
	 */
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	/**
	 * Returns the album title
	 * 
	 * @return the albumTitle
	 */
	public String getAlbumTitle() {
		return albumTitle;
	}

	/**
	 * Sets the album title
	 * 
	 * @param albumTitle
	 *            the albumTitle to set
	 */
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	/**
	 * Returns the album release date
	 * 
	 * @return the releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Sets the album release date
	 * 
	 * @param releaseDate
	 *            the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * Returns the album artist
	 * 
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Sets the album artist
	 * 
	 * @param artist
	 *            the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Returns the album cover
	 * 
	 * @return the albumCover
	 */
	public String getAlbumCover() {
		return albumCover;
	}

	/**
	 * Sets the album cover
	 * 
	 * @param albumCover
	 *            the albumCover to set
	 */
	public void setAlbumCover(String albumCover) {
		this.albumCover = albumCover;
	}

	/**
	 * Returns the music category
	 * 
	 * @return the musicCategory
	 */
	public String getMusicCategory() {
		return musicCategory;
	}

	/**
	 * Sets the music category
	 * 
	 * @param musicCategory
	 *            the musicCategory to set
	 */
	public void setMusicCategory(String musicCategory) {
		this.musicCategory = musicCategory;
	}

	/**
	 * Returns the record label
	 * 
	 * @return the recordLabel
	 */
	public String getRecordLabel() {
		return recordLabel;
	}

	/**
	 * Sets the record label
	 * 
	 * @param recordLabel
	 *            the recordLabel to set
	 */
	public void setRecordLabel(String recordLabel) {
		this.recordLabel = recordLabel;
	}

	/**
	 * Returns the number of album tracks
	 * 
	 * @return the numOfTracks
	 */
	public int getNumOfTracks() {
		return numOfTracks;
	}

	/**
	 * Sets the number of album of tracks
	 * 
	 * @param numOfTracks
	 *            the numOfTracks to set
	 */
	public void setNumOfTracks(int numOfTracks) {
		this.numOfTracks = numOfTracks;
	}

	/**
	 * Returns the date album was entered in inventory
	 * 
	 * @return the dateEntered
	 */
	public Date getDateEntered() {
		return dateEntered;
	}

	/**
	 * Sets the date album was entered in inventory
	 * 
	 * @param dateEntered
	 *            the dateEntered to set
	 */
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}

	/**
	 * Returns the cost price
	 * 
	 * @return the costPrice
	 */
	public double getCostPrice() {
		return costPrice;
	}

	/**
	 * Sets the cost price
	 * 
	 * @param costPrice
	 *            the costPrice to set
	 */
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * Returns the list price
	 * 
	 * @return the listPrice
	 */
	public double getListPrice() {
		return listPrice;
	}

	/**
	 * Sets the list price
	 * 
	 * @param listPrice
	 *            the listPrice to set
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	/**
	 * Returns the sale price
	 * 
	 * @return the salePrice
	 */
	public double getSalePrice() {
		return salePrice;
	}

	/**
	 * Sets the sale price
	 * 
	 * @param salePrice
	 *            the salePrice to set
	 */
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	/**
	 * Returns the removal status
	 * 
	 * @return the removalStatus
	 */
	public boolean isRemovalStatus() {
		return removalStatus;
	}

	/**
	 * Sets the removal status
	 * 
	 * @param removalStatus
	 *            the removalStatus to set
	 */
	public void setRemovalStatus(boolean removalStatus) {
		this.removalStatus = removalStatus;
	}

	/**
	 * Returns the current price of the album
	 * 
	 * @return Value of the album's current price
	 * 
	 */
	public double getCurrentPrice() {
		double price = 0.0;

		// determine if displayed price will be sale or cost price
		if (getSalePrice() != 0)
			price = getSalePrice();
		else
			price = getListPrice();

		return price;

	}

	/**
	 * Returns the current price of the album in a currency String
	 * 
	 * @return Currency String of the current price
	 */
	public String getCurrentPriceString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getCurrentPrice());
	}

	/**
	 * Returns the sale price of the album in a currency String
	 * 
	 * @return Currency String of the sale price
	 */
	public String getSalePriceString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getSalePrice());
	}

	/**
	 * Returns the list price of the album in a currency String
	 * 
	 * @return Currency String of the list price
	 */
	public String getListPriceString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getListPrice());
	}
}
