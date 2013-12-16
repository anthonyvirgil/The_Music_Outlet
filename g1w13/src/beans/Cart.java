// Anthony-Virgil Bermejo 0831360
// Natacha Gabbamonte 0932340
// Cart.java
package beans;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Represents a shopping cart that can hold zero or more albums and zero or more
 * tracks of music
 * 
 * @author Anthony-Virgil Bermejo
 * @author Natacha Gabbamonte
 * @version 1.1
 */
public class Cart implements Serializable {
	private static final long serialVersionUID = 2514176254806434535L;
	private ArrayList<Track> tracks = null;
	private ArrayList<Album> albums = null;

	private String province = "QC";

	/**
	 * No param constructor that creates two ArrayLists of each item that a
	 * shopping cart can contain
	 */
	public Cart() {
		tracks = new ArrayList<Track>();
		albums = new ArrayList<Album>();
		province = "QC";
	}

	/**
	 * Returns the number of items within the shopping cart
	 * 
	 * @return Number of items
	 */
	public int getCount() {
		return (tracks.size() + albums.size());
	}

	/**
	 * Returns the province.
	 * 
	 * @return The province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Sets the province
	 * 
	 * @param province
	 *            The province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * Returns an ArrayList of all the tracks in the shopping cart
	 * 
	 * @return ArrayList of all tracks in cart
	 */
	public ArrayList<Track> getTracks() {
		return tracks;
	}

	/**
	 * Returns an ArrayList of all the albums in the shopping cart
	 * 
	 * @return ArrayList of all albums in cart
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * Adds a track to the shopping cart
	 * 
	 * @param track
	 *            Track to be added
	 * @return Whether the track was added
	 */
	public boolean addTrack(Track track) {
		if (tracks.contains(track))
			return false;
		return tracks.add(track);
	}

	/**
	 * Adds an album to the shopping card
	 * 
	 * @param album
	 *            Album to be added
	 * @return Whether the album was added
	 */
	public boolean addAlbum(Album album) {
		boolean canAdd = true;
		for (Album anAlbum : albums)
			if (anAlbum.getAlbumId() == album.getAlbumId()) {
				canAdd = false;
				break;
			}
		if (canAdd) {
			canAdd = albums.add(album);
		}
		return canAdd;
	}

	/**
	 * Removes a track from the shopping cart if it exists
	 * 
	 * @param track
	 *            Track to be removed
	 * @return Whether the operation was successful
	 */
	public boolean removeTrack(Track track) {
		if (tracks.contains(track)) {
			tracks.remove(track);
			return true;
		}
		return false;
	}

	/**
	 * Removes a track from the tracks list.
	 * 
	 * @param trackId
	 *            The track id of the track to be removed.
	 * @return Whether the removal was successful.
	 */
	public boolean removeTrack(int trackId) {
		boolean found = false;
		for (int i = 0; i < tracks.size(); i++)
			if (tracks.get(i).getInventoryId() == trackId) {
				tracks.remove(i);
				found = true;
				break;
			}
		return found;
	}

	/**
	 * Removes an album from the shopping cart if it exists
	 * 
	 * @param album
	 *            Album to be removed
	 * @return Whether the operation was successful
	 */
	public boolean removeAlbum(Album album) {
		if (albums.contains(album)) {
			albums.remove(album);
			return true;
		}
		return false;
	}

	/**
	 * Removes an album from the albums list.
	 * 
	 * @param albumId
	 *            The album id of the album
	 * @return Whether the operation was successful
	 */
	public boolean removeAlbum(int albumId) {
		boolean found = false;
		for (int i = 0; i < albums.size(); i++)
			if (albums.get(i).getAlbumId() == albumId) {
				albums.remove(i);
				found = true;
				break;
			}
		return found;
	}

	/**
	 * Returns the sub-total value of the cart.
	 * 
	 * @return The sub-total
	 */
	public double getSubTotal() {
		double subtotal = 0;
		for (Album a : albums)
			subtotal += a.getSalePrice() > 0 ? a.getSalePrice() : a
					.getListPrice();
		for (Track t : tracks)
			subtotal += t.getSalePrice() > 0 ? t.getSalePrice() : t
					.getListPrice();

		return subtotal;
	}

	/**
	 * Returns a formatted String of the cart's sub total
	 * 
	 * @return Formatted String of sub total
	 */
	public String getSubTotalString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getSubTotal());
	}

	/**
	 * Returns the HST taxes for the shopping cart
	 * 
	 * @param clientProvince
	 *            Province of client
	 * @return HST taxes
	 */
	public double getHSTTaxes(String clientProvince) {
		double taxes;

		switch (clientProvince) {
		case "BC":
			taxes = getSubTotal() * 0.12;
			break;
		case "NB":
			taxes = getSubTotal() * 0.13;
			break;
		case "NL":
			taxes = getSubTotal() * 0.13;
			break;
		case "NS":
			taxes = getSubTotal() * 0.15;
			break;
		case "ON":
			taxes = getSubTotal() * 0.13;
			break;
		default:
			taxes = 0;
		}

		return taxes;
	}

	/**
	 * Returns the HST taxes for the shopping cart
	 * 
	 * @return HST taxes
	 */
	public double getHSTTaxes() {
		double taxes;

		switch (province) {
		case "BC":
			taxes = getSubTotal() * 0.12;
			break;
		case "NB":
			taxes = getSubTotal() * 0.13;
			break;
		case "NL":
			taxes = getSubTotal() * 0.13;
			break;
		case "NS":
			taxes = getSubTotal() * 0.15;
			break;
		case "ON":
			taxes = getSubTotal() * 0.13;
			break;
		default:
			taxes = 0;
		}

		return taxes;
	}

	/**
	 * Returns the GST taxes for the shopping cart
	 * 
	 * @param clientProvince
	 *            Province of client
	 * @return GST taxes
	 */
	public double getGSTTaxes(String clientProvince) {
		double taxes;

		switch (clientProvince) {
		case "AB":
			taxes = getSubTotal() * 0.05;
			break;
		case "MB":
			taxes = getSubTotal() * 0.05;
			break;
		case "NT":
			taxes = getSubTotal() * 0.05;
			break;
		case "NU":
			taxes = getSubTotal() * 0.05;
			break;
		case "PE":
			taxes = getSubTotal() * 0.05;
			break;
		case "QC":
			taxes = getSubTotal() * 0.05;
			break;
		case "SK":
			taxes = getSubTotal() * 0.05;
			break;
		case "YT":
			taxes = getSubTotal() * 0.05;
			break;
		default:
			taxes = 0;
		}

		return taxes;
	}

	/**
	 * Returns the GST taxes for the shopping cart
	 * 
	 * @return GST taxes
	 */
	public double getGSTTaxes() {
		double taxes;

		switch (province) {
		case "AB":
			taxes = getSubTotal() * 0.05;
			break;
		case "MB":
			taxes = getSubTotal() * 0.05;
			break;
		case "NT":
			taxes = getSubTotal() * 0.05;
			break;
		case "NU":
			taxes = getSubTotal() * 0.05;
			break;
		case "PE":
			taxes = getSubTotal() * 0.05;
			break;
		case "QC":
			taxes = getSubTotal() * 0.05;
			break;
		case "SK":
			taxes = getSubTotal() * 0.05;
			break;
		case "YT":
			taxes = getSubTotal() * 0.05;
			break;
		default:
			taxes = 0;
		}

		return taxes;
	}

	/**
	 * Returns the PST taxes for the shopping cart
	 * 
	 * @param clientProvince
	 *            Province of client
	 * @return PST taxes
	 */
	public double getPSTTaxes(String clientProvince) {
		double taxes;

		switch (clientProvince) {
		case "MB":
			taxes = getSubTotal() * 0.07;
			break;
		case "PE":
			taxes = getSubTotal() * 0.10;
			break;
		case "QC":
			taxes = getSubTotal() * 0.09975;
			break;
		case "SK":
			taxes = getSubTotal() * 0.05;
			break;
		default:
			taxes = 0;
		}

		return taxes;
	}

	/**
	 * Returns the PST taxes for the shopping cart
	 * 
	 * @return PST taxes
	 */
	public double getPSTTaxes() {
		double taxes;

		switch (province) {
		case "MB":
			taxes = getSubTotal() * 0.07;
			break;
		case "PE":
			taxes = getSubTotal() * 0.10;
			break;
		case "QC":
			taxes = getSubTotal() * 0.09975;
			break;
		case "SK":
			taxes = getSubTotal() * 0.05;
			break;
		default:
			taxes = 0;
		}

		return taxes;
	}

	/**
	 * Returns a formatted String of the cart's GST taxes
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Formatted String of cart's GST/HST taxes
	 */
	public String getGSTTaxesString(String clientProvince) {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getGSTTaxes(clientProvince));
	}

	/**
	 * Returns a formatted String of the cart's GST taxes with the Cart's
	 * province.
	 * 
	 * @return Formatted String of cart's GST/HST taxes
	 */
	public String getGSTTaxesString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getGSTTaxes(province));
	}

	/**
	 * Returns a formatted String of the cart's HST taxes
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Formatted String of cart's GST/HST taxes
	 */
	public String getHSTTaxesString(String clientProvince) {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getHSTTaxes(clientProvince));
	}

	/**
	 * Returns a formatted String of the cart's HST taxes with the Cart's
	 * province.
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Formatted String of cart's GST/HST taxes
	 */
	public String getHSTTaxesString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getHSTTaxes(province));
	}

	/**
	 * Returns a formatted String of the cart's GST/HST taxes
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Formatted String of cart's GST/HST taxes
	 */
	public String getGSTHSTTaxesString(String clientProvince) {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getHSTTaxes(clientProvince)
				+ getGSTTaxes(clientProvince));
	}

	/**
	 * Returns a formatted String of the cart's PST taxes
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Formatted String of cart's PST taxes
	 */
	public String getPSTTaxesString(String clientProvince) {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getPSTTaxes(clientProvince));
	}

	/**
	 * Returns a formatted String of the cart's PST taxes with the Cart's
	 * province.
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Formatted String of cart's PST taxes
	 */
	public String getPSTTaxesString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getPSTTaxes(province));
	}

	/**
	 * Returns the total taxes of a cart's items
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Total taxes of a cart's items
	 */
	public double getTotalTaxes(String clientProvince) {
		return getSubTotal() + getGSTTaxes(clientProvince)
				+ getHSTTaxes(clientProvince) + getPSTTaxes(clientProvince);
	}

	/**
	 * Returns a formatted String of the cart's total taxes
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Formatted String of the cart's total taxes
	 */
	public String getTotalTaxesString(String clientProvince) {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getTotalTaxes(clientProvince));
	}

	/**
	 * Returns a formatted String of the cart's total taxes using the Cart's
	 * province.
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Formatted String of the cart's total taxes
	 */
	public String getTotalTaxesString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();

		return format.format(getTotalTaxes(province));
	}

	/**
	 * Returns a double of the cart's total taxes using the Cart's province.
	 * 
	 * @param clientProvince
	 *            Province of the client
	 * @return Formatted String of the cart's total taxes
	 */
	public double getTotalTaxes() {
		return getTotalTaxes(province);
	}

	public boolean getIsAlbumsEven() {
		return albums.size() % 2 == 0;
	}
}
