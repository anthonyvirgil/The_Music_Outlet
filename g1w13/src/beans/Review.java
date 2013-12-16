// Venelin Koulaxazov
// 1032744
// Review.java
package beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Java Bean for a review in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.4
 */
public class Review implements Serializable {

	private static final long serialVersionUID = -4128579536158271430L;
	private int reviewId;
	private int inventoryId;
	private int clientId;
	private Date reviewDate;
	private String clientName;
	private int rating;
	private String reviewTitle;
	private String reviewText;
	private boolean approvalStatus;

	/**
	 * Default constructor
	 */
	public Review() {
		reviewDate = new Date();
		clientName = "";
		reviewTitle = "";
		reviewText = "";
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param inventoryId
	 * @param clientId
	 * @param reviewDate
	 * @param clientName
	 * @param rating
	 * @param reviewTitle
	 * @param reviewText
	 * @param approvalStatus
	 */
	public Review(int inventoryId, int clientId, Date reviewDate,
			String clientName, int rating, String reviewTitle,
			String reviewText, boolean approvalStatus) {
		super();
		this.inventoryId = inventoryId;
		this.clientId = clientId;
		this.reviewDate = reviewDate;
		this.clientName = clientName;
		this.rating = rating;
		this.reviewTitle = reviewTitle;
		this.reviewText = reviewText;
		this.approvalStatus = approvalStatus;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param reviewId
	 * @param inventoryId
	 * @param clientId
	 * @param reviewDate
	 * @param clientName
	 * @param rating
	 * @param reviewTitle
	 * @param reviewText
	 * @param approvalStatus
	 */
	public Review(int reviewId, int inventoryId, int clientId, Date reviewDate,
			String clientName, int rating, String reviewTitle,
			String reviewText, boolean approvalStatus) {
		super();
		this.reviewId = reviewId;
		this.inventoryId = inventoryId;
		this.clientId = clientId;
		this.reviewDate = reviewDate;
		this.clientName = clientName;
		this.rating = rating;
		this.reviewTitle = reviewTitle;
		this.reviewText = reviewText;
		this.approvalStatus = approvalStatus;
	}

	/**
	 * @return the reviewId
	 */
	public int getReviewId() {
		return reviewId;
	}

	/**
	 * @param reviewId
	 *            the reviewId to set
	 */
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
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
	 * @return the clientId
	 */
	public int getClientId() {
		return clientId;
	}

	/**
	 * @param clientId
	 *            the clientId to set
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the reviewDate
	 */
	public Date getReviewDate() {
		return reviewDate;
	}

	/**
	 * @param reviewDate
	 *            the reviewDate to set
	 */
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clientName
	 *            the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the reviewTitle
	 */
	public String getReviewTitle() {
		return reviewTitle;
	}

	/**
	 * @param reviewTitle
	 *            the reviewTitle to set
	 */
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	/**
	 * @return the reviewText
	 */
	public String getReviewText() {
		return reviewText;
	}

	/**
	 * @param reviewText
	 *            the reviewText to set
	 */
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	/**
	 * @return the approvalStatus
	 */
	public boolean isApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * @param approvalStatus
	 *            the approvalStatus to set
	 */
	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", inventoryId=" + inventoryId
				+ ", reviewDate=" + reviewDate + ", clientName=" + clientName
				+ ", rating=" + rating + ", reviewTitle=" + reviewTitle
				+ ", reviewText=" + reviewText + ", approvalStatus="
				+ approvalStatus + "]";
	}
}
