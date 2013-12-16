/*
 * Anthony-Virgil Bermejo
 * 0831360
 * TrackValidator.java
 */
package util;

import java.util.Date;

import beans.Track;

/**
 * Validates a Track object and checks if all fields are valid
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class TrackValidator {

	public TrackValidator() {
		super();
	}

	/**
	 * Validates the fields of an Track object that all lengths are within the
	 * limits of the MySQL field
	 * 
	 * @param track
	 *            Track to be validated
	 * @return Error message to be displayed, null if there are no errors
	 */
	public String validateTrack(Track track) {
		String errorMessage = null;

		int albumId = track.getAlbumId();
		String trackTitle = track.getTrackTitle();
		String artist = track.getArtist();
		String songWriter = track.getSongWriter();
		String playLength = track.getPlayLength();
		int selectionNum = track.getSelectionNum();
		String musicCategory = track.getMusicCategory();
		String albumCover = track.getAlbumCover();
		double costPrice = track.getCostPrice();
		double listPrice = track.getListPrice();
		double salePrice = track.getSalePrice();
		Date dateEntered = track.getDateEntered();
		int typeOfSale = track.getTypeOfSale();

		if (albumId < 0)
			errorMessage = "Invalid album id";
		if (trackTitle == null || trackTitle.length() > 150)
			errorMessage = "Invalid error message";
		if (artist == null || artist.length() > 150)
			errorMessage = "Invalid artist";
		if (songWriter == null || songWriter.length() > 200)
			errorMessage = "Invalid song writer";
		if (playLength.length() > 8 || !validateSongLength(playLength))
			errorMessage = "Invalid song length";
		if (selectionNum <= 0)
			errorMessage = "Invalid selection number";
		if (musicCategory == null || musicCategory.length() > 45)
			errorMessage = "Invalid music categry";
		if (albumCover == null || albumCover.length() > 200)
			errorMessage = "Invalid album cover file name";
		if (costPrice < 0)
			errorMessage = "Invalid cost price";
		if (listPrice < 0)
			errorMessage = "Invalid list price";
		if (salePrice < 0)
			errorMessage = "Invalid sale price";
		if (dateEntered == null)
			errorMessage = "Invalid date entered";
		if (typeOfSale < 0 || typeOfSale > 2)
			errorMessage = "Invalid type of sale";
		if (costPrice > listPrice)
			errorMessage = "List price must be greater than cost price";

		return errorMessage;
	}

	/*
	 * Checks if song length matches a regular expression
	 */
	private boolean validateSongLength(String songLength) {
		boolean valid = false;

		if (songLength != null)
			if (songLength.matches("^([0-5][0-9]|0[0-9]|[0-9]):[0-5][0-9]$"))
				valid = true;

		return valid;
	}
}
