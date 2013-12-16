/*
 * Anthony-Virgil Bermejo
 * 0831360
 * InvoiceAlbum.java
 */
package beans;

import java.io.Serializable;

/**
 * Java Bean for an invoice report regarding album sales
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class InvoiceAlbum implements Serializable {

	private static final long serialVersionUID = 4193665875523547879L;

	private int albumId;
	private String artist;
	private String albumTitle;
	private String genre;
	private double salePrice;
	private int detailId;
	private boolean removalStatus;

	/**
	 * Default Constructor
	 */
	public InvoiceAlbum() {
		super();
	}

	/**
	 * @param albumId
	 * @param artist
	 * @param albumTitle
	 * @param genre
	 * @param salePrice
	 * @param detailId
	 * @param removalStatus
	 */
	public InvoiceAlbum(int albumId, String artist, String albumTitle,
			String genre, double salePrice, int detailId, boolean removalStatus) {
		super();
		this.albumId = albumId;
		this.artist = artist;
		this.albumTitle = albumTitle;
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
	 * @return the albumTitle
	 */
	public String getAlbumTitle() {
		return albumTitle;
	}

	/**
	 * @param albumTitle
	 *            the albumTitle to set
	 */
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
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
