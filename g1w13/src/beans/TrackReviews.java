/*
 * Anthony-Virgil Bermejo
 * 0831360
 * TrackReviews.java
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Java bean for the reviews for a track
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class TrackReviews implements Serializable {

	private static final long serialVersionUID = -1080459767888840629L;
	private Track track;
	private ArrayList<Review> reviews;
	private ArrayList<String> reviewImgPathList;

	/**
	 * Default constructor
	 */
	public TrackReviews() {
		super();
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param track
	 * @param reviews
	 * @param reviewImgPathList
	 */
	public TrackReviews(Track track, ArrayList<Review> reviews,
			ArrayList<String> reviewImgPathList) {
		super();
		this.track = track;
		this.reviews = reviews;
		this.reviewImgPathList = reviewImgPathList;
	}

	/**
	 * @return the track
	 */
	public Track getTrack() {
		return track;
	}

	/**
	 * @param track
	 *            the track to set
	 */
	public void setTrack(Track track) {
		this.track = track;
	}

	/**
	 * @return the reviews
	 */
	public ArrayList<Review> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews
	 *            the reviews to set
	 */
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * @return the reviewImgPathList
	 */
	public ArrayList<String> getReviewImgPathList() {
		return reviewImgPathList;
	}

	/**
	 * @param reviewImgPathList
	 *            the reviewImgPathList to set
	 */
	public void setReviewImgPathList(ArrayList<String> reviewImgPathList) {
		this.reviewImgPathList = reviewImgPathList;
	}
}
