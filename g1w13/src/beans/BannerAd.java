// Venelin Koulaxazov
// 1032744
// BannerAd.java
package beans;

import java.io.Serializable;

/**
 * Java Bean for a banner ad in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class BannerAd implements Serializable {

	private static final long serialVersionUID = 2170769636716391284L;
	private int bannerId;
	private String url;
	private int type;
	private String imageSource;
	private boolean status;

	/**
	 * Default constructor
	 */
	public BannerAd() {
		url = "";
		imageSource = "";
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param url
	 * @param type
	 * @param imageSource
	 * @param status
	 */
	public BannerAd(String url, int type, String imageSource, boolean status) {
		this.url = url;
		this.type = type;
		this.imageSource = imageSource;
		this.status = status;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param bannerId
	 * @param url
	 * @param type
	 * @param imageSource
	 * @param status
	 */
	public BannerAd(int bannerId, String url, int type, String imageSource,
			boolean status) {
		this.bannerId = bannerId;
		this.url = url;
		this.type = type;
		this.imageSource = imageSource;
		this.status = status;
	}

	/**
	 * @return the bannerId
	 */
	public int getBannerId() {
		return bannerId;
	}

	/**
	 * @param bannerId
	 *            the bannerId to set
	 */
	public void setBannerId(int bannerId) {
		this.bannerId = bannerId;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the imageSource
	 */
	public String getImageSource() {
		return imageSource;
	}

	/**
	 * @param imageSource
	 *            the imageSource to set
	 */
	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
}
