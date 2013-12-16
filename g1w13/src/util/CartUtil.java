package util;

import java.util.ArrayList;

import dbManager.DatabaseManager;

import beans.Album;
import beans.Client;
import beans.Track;

/**
 * This class contains utility methods for verifying whether a client already
 * owns certain tracks and/or albums.
 * 
 * @author Natacha Gabbamonte 0932340
 * 
 */
public class CartUtil {

	private DatabaseManager dbManager = null;

	/**
	 * Constructor
	 * 
	 * @param dbManager
	 *            The database manager it requires
	 */
	public CartUtil(DatabaseManager dbManager) {

		this.dbManager = dbManager;
	}

	/**
	 * Checks if the client already owns a track.
	 * 
	 * @param track
	 *            the track
	 * 
	 * @param client
	 *            the client
	 * 
	 * @return Whether the client already owns it or not.
	 */
	public boolean doesClientOwnTrack(Track track, Client client) {

		ArrayList<Track> tracks = dbManager.getPurchasedTracksByClient(client
				.getClientId());
		for (Track t : tracks)
			if (track.getInventoryId() == t.getInventoryId())
				return true;

		return false;
	}

	/**
	 * Checks if the client already owns an album.
	 * 
	 * @param album
	 *            the album
	 * 
	 * @param client
	 *            the client
	 * 
	 * @return Whether the client already owns it or not.
	 */
	public boolean doesClientOwnAlbum(Album album, Client client) {
		ArrayList<Album> albums = dbManager.getPurchasedAlbumsByClient(client
				.getClientId());
		for (Album a : albums)
			if (album.getAlbumId() == a.getAlbumId())
				return true;

		return false;
	}

	/**
	 * Checks to see if the client already owns all the tracks that is being
	 * sold by the album they are trying to buy.
	 * 
	 * @param album
	 *            The album
	 * 
	 * @param client
	 *            the client
	 * 
	 * @return Whether or not the client already owns all the tracks.
	 */
	public boolean doesClientOwnAllTracksFromAlbum(Album album, Client client) {
		ArrayList<Track> tracksFromAlbum = dbManager.getTracksByAlbum(
				album.getAlbumId(), true);
		ArrayList<Track> clientsTrack = dbManager
				.getPurchasedTracksByClient(client.getClientId());

		boolean hasAllTracks = false;

		if (clientsTrack.size() > 0) {
			hasAllTracks = true;
			boolean hasCurrentAlbumTrack;
			for (Track albumTrack : tracksFromAlbum) {
				hasCurrentAlbumTrack = false;
				for (Track clientTrack : clientsTrack)
					if (clientTrack.getInventoryId() == albumTrack
							.getInventoryId()) {
						hasCurrentAlbumTrack = true;
						break;
					}
				if (!hasCurrentAlbumTrack) {
					hasAllTracks = false;
					break;
				}
			}
		}
		return hasAllTracks;
	}
}
