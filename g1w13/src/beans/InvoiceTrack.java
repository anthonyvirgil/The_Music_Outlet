/*
 * Anthony-Virgil Bermejo
 * 0831360
 * InvoiceTrack.java
 */
package beans;

import java.io.Serializable;

/**
 * Java Bean for an invoice report regarding track sales
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class InvoiceTrack implements Serializable {

	private static final long serialVersionUID = 8415656893608609856L;
	private int trackId;
	private String artist;
	private String trackTitle;
	private String genre;
	private double salePrice;
	private int detailId;
	private boolean removalStatus;

	/**
	 * Default constructor
	 */
	public InvoiceTrack() {
		super();
	}

	/**
	 * @param trackId
	 * @param artist
	 * @param trackTitle
	 * @param genre
	 * @param salePrice
	 * @param detailId
	 * @param removalStatus
	 */
	public InvoiceTrack(int trackId, String artist, String trackTitle,
			String genre, double salePrice, int detailId, boolean removalStatus) {
		super();
		this.trackId = trackId;
		this.artist = artist;
		this.trackTitle = trackTitle;
		this.genre = genre;
		this.salePrice = salePrice;
		this.detailId = detailId;
		this.removalStatus = removalStatus;
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
	 * @return the detailId
	 */
	public int getDetailId() {
		return detailId;
	}

	/**
	 * @param detailId
	 *            the detailId to set
	 */
	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	/**
	 * @return the trackId
	 */
	public int getTrackId() {
		return trackId;
	}

	/**
	 * @param trackId
	 *            the trackId to set
	 */
	public void setTrackId(int trackId) {
		this.trackId = trackId;
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
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
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
}
