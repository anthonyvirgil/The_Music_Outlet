// Venelin Koulaxazov
// 1032744
// Detail.java
package beans;

import java.io.Serializable;

/**
 * Java Bean for an invoice detail in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.4
 */
public class Detail implements Serializable {

	private static final long serialVersionUID = -8289934219971484620L;
	private int detailId;
	private int saleId;
	private int inventoryId;
	private int albumId;
	private double salePrice;
	private boolean removalStatus;

	/**
	 * Default constructor
	 */
	public Detail() {
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param saleId
	 * @param inventoryId
	 * @param albumId
	 * @param salePrice
	 * @param removalStatus
	 */
	public Detail(int saleId, int inventoryId, int albumId, double salePrice,
			boolean removalStatus) {
		super();
		this.saleId = saleId;
		this.inventoryId = inventoryId;
		this.albumId = albumId;
		this.salePrice = salePrice;
		this.removalStatus = removalStatus;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param detailId
	 * @param saleId
	 * @param inventoryId
	 * @param albumId
	 * @param salePrice
	 * @param removalStatus
	 */
	public Detail(int detailId, int saleId, int inventoryId, int albumId,
			double salePrice, boolean removalStatus) {
		super();
		this.detailId = detailId;
		this.saleId = saleId;
		this.inventoryId = inventoryId;
		this.albumId = albumId;
		this.salePrice = salePrice;
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
	 * @return the saleId
	 */
	public int getSaleId() {
		return saleId;
	}

	/**
	 * @param saleId
	 *            the saleId to set
	 */
	public void setSaleId(int saleId) {
		this.saleId = saleId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Detail [detailId=" + detailId + ", saleId=" + saleId
				+ ", inventoryId=" + inventoryId + ", albumId=" + albumId
				+ ", salePrice=" + salePrice + ", removalStatus="
				+ removalStatus + "]";
	}
}
