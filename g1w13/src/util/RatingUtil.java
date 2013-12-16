// Anthony-Virgil Bermejo
// 0831360
// RatingUtil.java
package util;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dbManager.DatabaseManager;

import beans.Review;
import beans.Track;

/**
 * Utility class for calculating a rating and determining which picture is
 * associated to which track.
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class RatingUtil {
	DatabaseManager dbManager = new DatabaseManager();

	/**
	 * No param constructor
	 */
	public RatingUtil() {

	}

	/**
	 * Calculates a track's average rating
	 * 
	 * @param trackId
	 *            Id of track in database
	 * @return Average rating
	 */
	public double calculateRating(int trackId) {
		double totalRatingValue = 0;
		double averageRating = 0;
		int noRatingCount = 0;
		ArrayList<Review> reviews = null;

		reviews = dbManager.getApprovedReviewsByTrackId(trackId, -1, -1);

		// iterate through all reviews
		for (int i = 0; i < reviews.size(); i++) {
			// if review is not "no rating" add it to calculation of average
			// rating
			if (reviews.get(i).getRating() != 0)
				totalRatingValue += reviews.get(i).getRating();
			else
				noRatingCount++;
		}

		// calculate average rating total
		if (reviews.size() > 0 && totalRatingValue != 0)
			averageRating = totalRatingValue / (reviews.size() - noRatingCount);
		else
			averageRating = 0;

		return averageRating;
	}

	/**
	 * Returns a formatted String of a track's rating
	 * 
	 * @param rating
	 *            The calculated rating value of a track
	 * @return Formatted String of a track's rating
	 */
	public String getFormattedRating(int trackId) {
		double rating = calculateRating(trackId);

		DecimalFormat df = new DecimalFormat("#.##");

		return df.format(rating);
	}

	/**
	 * Determines which image will be used according to the rating of a review
	 * 
	 * @param reviewId
	 *            Id of the review in the database
	 * @return Path of the rating image
	 */
	public String determineReviewRatingImage(Review review) {
		String ratingImage = "";

		switch (review.getRating()) {
		case 0:
			ratingImage = "Not Yet Rated";
			break;
		case 1:
			ratingImage = "images/rating_1.png";
			break;
		case 2:
			ratingImage = "images/rating_2.png";
			break;
		case 3:
			ratingImage = "images/rating_3.png";
			break;
		case 4:
			ratingImage = "images/rating_4.png";
			break;
		case 5:
			ratingImage = "images/rating_5.png";
			break;
		default:
			ratingImage = "images/rating_0.png";
			break;
		}

		return ratingImage;
	}

	/**
	 * Determines which image will be used according to the average rating of a
	 * track
	 * 
	 * @param trackId
	 *            Id of track in the database
	 * @return Path of the rating image
	 */
	public String determineTrackRatingImage(Track track) {
		String ratingImage = "";
		double averageRating = 0;

		averageRating = calculateRating(track.getInventoryId());

		if (averageRating == 0) {
			ratingImage = "Not Yet Rated";
		} else if (averageRating > 0 && averageRating < 0.75) {
			ratingImage = "images/rating_0half.png";
		} else if (averageRating >= 0.75 && averageRating < 1.25) {
			ratingImage = "images/rating_1.png";
		} else if (averageRating >= 1.25 && averageRating < 1.75) {
			ratingImage = "images/rating_1half.png";
		} else if (averageRating >= 1.75 && averageRating < 2.25) {
			ratingImage = "images/rating_2.png";
		} else if (averageRating >= 2.25 && averageRating < 2.75) {
			ratingImage = "images/rating_2half.png";
		} else if (averageRating >= 2.75 && averageRating < 3.25) {
			ratingImage = "images/rating_3.png";
		} else if (averageRating >= 3.25 && averageRating < 3.75) {
			ratingImage = "images/rating_3half.png";
		} else if (averageRating >= 3.75 && averageRating < 4.25) {
			ratingImage = "images/rating_4.png";
		} else if (averageRating >= 4.25 && averageRating < 4.75) {
			ratingImage = "images/rating_4half.png";
		} else if (averageRating >= 4.75 && averageRating <= 5) {
			ratingImage = "images/rating_5.png";
		}

		return ratingImage;
	}

	/**
	 * Populates an ArrayList of Strings of the paths to a rating image
	 * according to a track's rating
	 * 
	 * @param trackList
	 *            List of tracks
	 * @return List of path names to an image
	 */
	public ArrayList<String> createRatingImgPathList(ArrayList<Track> trackList) {
		ArrayList<String> pathList = new ArrayList<String>();

		for (Track track : trackList)
			pathList.add(determineTrackRatingImage(track));

		return pathList;
	}

	/**
	 * Creates an ArrayList of image paths for a list of reviews
	 * 
	 * @param reviewList
	 *            List of reviews
	 * @return List of path names for images
	 */
	public ArrayList<String> createReviewImgPathList(
			ArrayList<Review> reviewList) {
		ArrayList<String> pathList = new ArrayList<String>();

		for (Review review : reviewList)
			pathList.add(determineReviewRatingImage(review));

		return pathList;
	}
}
