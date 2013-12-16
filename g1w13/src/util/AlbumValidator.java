//Anthony-Virgil Bermejo
//0831360
//AlbumValidator.java

package util;

import java.util.Date;

import beans.Album;

/**
 * Validates an Album object and checks if all fields are valid
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.0
 */
public class AlbumValidator {

	public AlbumValidator() {
		super();
	}

	/**
	 * Validates the fields of an Album object that all lengths are within the
	 * limits of the MySQL field
	 * 
	 * @param album
	 *            Album to be validated
	 * @return Error message to be displayed, null if there are no errors
	 */
	public String validateAlbum(Album album) {
		String errorMessage = null;
		String albumTitle = album.getAlbumTitle();
		String releaseDate = album.getReleaseDate();
		String artist = album.getArtist();
		String albumCover = album.getAlbumCover();
		String musicCategory = album.getMusicCategory();
		String recordLabel = album.getRecordLabel();
		int numOfTracks = album.getNumOfTracks();
		Date dateEntered = album.getDateEntered();
		double costPrice = album.getCostPrice();
		double listPrice = album.getListPrice();
		double salePrice = album.getSalePrice();

		if (albumTitle == null || albumTitle.length() > 200)
			errorMessage = "Invalid album title";
		if (!validateDate(releaseDate))
			errorMessage = "Invalid release date";
		if (artist == null || artist.length() > 200)
			errorMessage = "Invalid artist";
		if (albumCover == null || albumCover.length() > 200)
			errorMessage = "Invalid album cover file name";
		if (musicCategory == null || musicCategory.length() > 50)
			errorMessage = "Invalid music category";
		if (recordLabel == null || recordLabel.length() > 150)
			errorMessage = "Invalid recordLabel";
		if (numOfTracks < 0)
			errorMessage = "Invalid number of tracks";
		if (dateEntered == null)
			errorMessage = "Invalid date entered";
		if (costPrice < 0)
			errorMessage = "Invalid cost price";
		if (listPrice < 0)
			errorMessage = "Invalid list price";
		if (salePrice < 0)
			errorMessage = "Invalid sale price";
		if (listPrice < costPrice)
			errorMessage = "List price must be greater than cost price";

		return errorMessage;
	}

	/*
	 * Checks if a date matches a regular expression
	 */
	private boolean validateDate(String date) {
		boolean valid = false;

		if (date != null)
			if (date.matches("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$"))
				valid = true;

		return valid;
	}

}
