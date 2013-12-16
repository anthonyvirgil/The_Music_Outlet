// Venelin Koulaxazov
// 1032744
// Rss.java
package beans;

import java.io.Serializable;

/**
 * Java Bean for a Rss feed in the music store
 * 
 * @author Venelin Koulaxazov
 * @version 1.0
 */
public class Rss implements Serializable {

	private static final long serialVersionUID = -3888107679035804703L;
	private int rssFeedId;
	private String name;
	private String url;
	private boolean status;

	/**
	 * Default constructor
	 */
	public Rss() {
		name = "";
		url = "";
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param name
	 * @param url
	 * @param status
	 */
	public Rss(String name, String url, boolean status) {
		this.name = name;
		this.url = url;
		this.status = status;
	}

	/**
	 * Constructor that sets the fields to the user preferences
	 * 
	 * @param rssFeedId
	 * @param name
	 * @param url
	 * @param status
	 */
	public Rss(int rssFeedId, String name, String url, boolean status) {
		this.rssFeedId = rssFeedId;
		this.name = name;
		this.url = url;
		this.status = status;
	}

	/**
	 * @return the rssFeedId
	 */
	public int getRssFeedId() {
		return rssFeedId;
	}

	/**
	 * @param rssFeedId
	 *            the rssFeedId to set
	 */
	public void setRssFeedId(int rssFeedId) {
		this.rssFeedId = rssFeedId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
