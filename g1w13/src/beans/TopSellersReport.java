// Anthony-Virgil Bermejo
// 0831360
// TopSellersReport.java
package beans;

import java.io.Serializable;

/**
 * Represents a sales report that holds Top Sellers reporting information
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class TopSellersReport implements Serializable {

	// instance variables
	private static final long serialVersionUID = -984221408929597571L;
	private int albumId;
	private String albumTitle;
	private String albumArtist;
	private int trackId;
	private String trackTitle;
	private String trackArtist;
	private double totalSales;

	/**
	 * 
	 */
	public TopSellersReport() {
		super();
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
	public TopSellersReport(int albumId, String albumTitle, String albumArtist,
			int trackId, String trackTitle, String trackArtist,
			double totalSales) {
		super();
		this.albumId = albumId;
		this.albumTitle = albumTitle;
		this.albumArtist = albumArtist;
		this.trackId = trackId;
		this.trackTitle = trackTitle;
		this.trackArtist = trackArtist;
		this.totalSales = totalSales;
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
	 * @return the totalSales
	 */
	public double getTotalSales() {
		return totalSales;
	}

	/**
	 * @param totalSales
	 *            the totalSales to set
	 */
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}

}
