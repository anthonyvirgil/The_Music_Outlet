// Anthony-Virgil Bermejo
// 0831360
// PriceUtil.java
package util;

import java.text.NumberFormat;
import java.util.ArrayList;

import beans.Album;
import beans.Track;
import dbManager.DatabaseManager;

/**
 * Utility class determines whether the list price or the sale price will be
 * displayed and formats it into a currency display String
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.2
 */
public class PriceUtil {
	DatabaseManager dbManager = null;
	NumberFormat format = null;

	/**
	 * No param constructor
	 */
	public PriceUtil() {
		dbManager = new DatabaseManager();
		format = NumberFormat.getCurrencyInstance();
	}

	/**
	 * Creates an ArrayList of formatted currency Strings for a list of Tracks
	 * 
	 * @param trackList
	 *            ArrayList of Tracks
	 * @return ArrayList of Strings of formatted currency Strings
	 */
	public ArrayList<String> createTrackPriceList(ArrayList<Track> trackList) {
		ArrayList<String> priceList = new ArrayList<String>();

		for (Track track : trackList)
			priceList.add(track.getCurrentPriceString());

		return priceList;
	}

	/**
	 * Creates an ArrayList of formatted currency Strings for a list of Albums
	 * 
	 * @param trackList
	 *            ArrayList of Albums
	 * @return ArrayList of Strings of formatted currency Strings
	 */
	public ArrayList<String> createAlbumPriceList(ArrayList<Album> albumList) {
		ArrayList<String> priceList = new ArrayList<String>();

		for (Album album : albumList)
			priceList.add(album.getCurrentPriceString());

		return priceList;
	}
}
