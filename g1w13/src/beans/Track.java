// Venelin Koulaxazov
// 1032744
// Track.java
package beans;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Java Bean for a track in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.5
 */
public class Track implements Serializable {

	private static final long serialVersionUID = -6077018356634718033L;
	private int inventoryId;
	private int albumId;
	private String trackTitle;
	private String artist;
	private String songWriter;
	private String playLength;
	private int selectionNum;
	private String musicCategory;
	private String albumCover;
	private double costPrice;
	private double listPrice;
	private double salePrice;
	private Date dateEntered;
	private int typeOfSale;
	private boolean removalStatus;

	/**
	 * Default constructor
	 */
	public Track() {
		trackTitle = "";
		artist = "";
		songWriter = "";
		playLength = "";
		musicCategory = "";
		albumCover = "";
		dateEntered = new Date();
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param albumId
	 * @param trackTitle
	 * @param artist
	 * @param songWriter
	 * @param playLength
	 * @param selectionNum
	 * @param musicCategory
	 * @param albumCover
	 * @param costPrice
	 * @param listPrice
	 * @param salePrice
	 * @param dateEntered
	 * @param typeOfSale
	 * @param removalStatus
	 */
	public Track(int albumId, String trackTitle, String artist,
			String songWriter, String playLength, int selectionNum,
			String musicCategory, String albumCover, double costPrice,
			double listPrice, double salePrice, Date dateEntered,
			int typeOfSale, boolean removalStatus) {
		super();
		this.albumId = albumId;
		this.trackTitle = trackTitle;
		this.artist = artist;
		this.songWriter = songWriter;
		this.playLength = playLength;
		this.selectionNum = selectionNum;
		this.musicCategory = musicCategory;
		this.albumCover = albumCover;
		this.costPrice = costPrice;
		this.listPrice = listPrice;
		this.salePrice = salePrice;
		this.dateEntered = dateEntered;
		this.typeOfSale = typeOfSale;
		this.removalStatus = removalStatus;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param inventoryId
	 * @param albumId
	 * @param trackTitle
	 * @param artist
	 * @param songWriter
	 *            ;
	 * @param playLength
	 * @param selectionNum
	 * @param musicCategory
	 * @param albumCover
	 * @param costPrice
	 * @param listPrice
	 * @param salePrice
	 * @param dateEntered
	 * @param typeOfSale
	 * @param removalStatus
	 */
	public Track(int inventoryId, int albumId, String trackTitle,
			String artist, String songWriter, String playLength,
			int selectionNum, String musicCategory, String albumCover,
			double costPrice, double listPrice, double salePrice,
			Date dateEntered, int typeOfSale, boolean removalStatus) {
		super();
		this.inventoryId = inventoryId;
		this.albumId = albumId;
		this.trackTitle = trackTitle;
		this.artist = artist;
		this.songWriter = songWriter;
		this.playLength = playLength;
		this.selectionNum = selectionNum;
		this.musicCategory = musicCategory;
		this.albumCover = albumCover;
		this.costPrice = costPrice;
		this.listPrice = listPrice;
		this.salePrice = salePrice;
		this.dateEntered = dateEntered;
		this.typeOfSale = typeOfSale;
		this.removalStatus = removalStatus;
	}

	/**
	 * @return the inventoryId
	 */
	public int getInventoryId() {
		return inventoryId;
	}

	/**
	 * @param inventoryId
	 *            the inventoryId to set
	 */
	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	/**
	 * @return the albumId
	 */
	public int getAlbumId() {
		return albumId;
	}

	/**
	 * @param albumId
	 *            the albumId to set
	 */
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	/**
	 * @return the trackTitle
	 */
	public String getTrackTitle() {
		return trackTitle;
	}

	/**
	 * @param trackTitle
	 *            the trackTitle to set
	 */
	public void setTrackTitle(String trackTitle) {
		this.trackTitle = trackTitle;
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
	 * @return the songWriter
	 */
	public String getSongWriter() {
		return songWriter;
	}

	/**
	 * @param songWriter
	 *            the songWriter to set
	 */
	public void setSongWriter(String songWriter) {
		this.songWriter = songWriter;
	}

	/**
	 * @return the playLength
	 */
	public String getPlayLength() {
		return playLength;
	}

	/**
	 * @param playLength
	 *            the playLength to set
	 */
	public void setPlayLength(String playLength) {
		this.playLength = playLength;
	}

	/**
	 * @return the selectionNum
	 */
	public int getSelectionNum() {
		return selectionNum;
	}

	/**
	 * @param selectionNum
	 *            the selectionNum to set
	 */
	public void setSelectionNum(int selectionNum) {
		this.selectionNum = selectionNum;
	}

	/**
	 * @return the musicCategory
	 */
	public String getMusicCategory() {
		return musicCategory;
	}

	/**
	 * @param musicCategory
	 *            the musicCategory to set
	 */
	public void setMusicCategory(String musicCategory) {
		this.musicCategory = musicCategory;
	}

	/**
	 * @return the albumCover
	 */
	public String getAlbumCover() {
		return albumCover;
	}

	/**
	 * @param albumCover
	 *            the albumCover to set
	 */
	public void setAlbumCover(String albumCover) {
		this.albumCover = albumCover;
	}

	/**
	 * @return the costPrice
	 */
	public double getCostPrice() {
		return costPrice;
	}

	/**
	 * @param costPrice
	 *            the costPrice to set
	 */
	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * @return the listPrice
	 */
	public double getListPrice() {
		return listPrice;
	}

	/**
	 * @param listPrice
	 *            the listPrice to set
	 */
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	/**
	 * @return the salePrice
	 */
	public double getSalePrice() {
		return salePrice;
	}

	/**
	 * @param salePrice
	 *            the salePrice to set
	 */
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	/**
	 * @return the dateEntered
	 */
	public Date getDateEntered() {
		return dateEntered;
	}

	/**
	 * @param dateEntered
	 *            the dateEntered to set
	 */
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}

	/**
	 * @return the typeOfSale
	 */
	public int getTypeOfSale() {
		return typeOfSale;
	}

	/**
	 * @param typeOfSale
	 *            the typeOfSale to set
	 */
	public void setTypeOfSale(int typeOfSale) {
		this.typeOfSale = typeOfSale;
	}

	/**
	 * @return the removalStatus
	 */
	public boolean isRemovalStatus() {
		return removalStatus;
	}

	/**
	 * @param removalStatus
	 *            the removalStatus to set
	 */
	public void setRemovalStatus(boolean removalStatus) {
		this.removalStatus = removalStatus;
	}

	/**
	 * Returns the current price of the track
	 * 
	 * @return Value of the track's current price
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
	 * Returns the current price of the track in a currency String
	 * 
	 * @return Currency String of the current price
	 */
	public String getCurrentPriceString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getCurrentPrice());
	}

	/**
	 * Returns the sale price of the track in a currency String
	 * 
	 * @return Currency String of the sale price
	 */
	public String getSalePriceString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getSalePrice());
	}

	/**
	 * Returns the list price of the track in a currency String
	 * 
	 * @return Currency String of the list price
	 */
	public String getListPriceString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getListPrice());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Track [inventoryId=" + inventoryId + ", albumId=" + albumId
				+ ", trackTitle=" + trackTitle + ", artist=" + artist
				+ ", songWriter =" + songWriter + ", playLength=" + playLength
				+ ", selectionNum=" + selectionNum + ", musicCategory="
				+ musicCategory + ", albumCover=" + albumCover + ", costPrice="
				+ costPrice + ", listPrice=" + listPrice + ", salePrice="
				+ salePrice + ", dateEntered=" + dateEntered + ", typeOfSail="
				+ typeOfSale + ", removalStatus=" + removalStatus + "]";
	}

	/**
	 * Whether two Track objects are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj instanceof Track) {
			Track t = (Track) obj;
			if (t.getInventoryId() == this.getInventoryId())
				isEqual = true;
		}
		return isEqual;
	}
}
