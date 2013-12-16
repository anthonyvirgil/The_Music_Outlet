/**
 * This cookie util contains method for easy Cookie access.
 * 
 * @author Ken Fogel
 * @author Natacha Gabbamonte
 */
package util;

import javax.servlet.http.*;

public class CookieUtil {

	Cookie[] cookies = null;

	/**
	 * Constructor.
	 * 
	 * @param cookies
	 *            The cookies to be used.
	 */
	public CookieUtil(Cookie[] cookies) {
		this.cookies = cookies;
	}

	/**
	 * Returns the cookie associated with the given name.
	 * 
	 * @param cookieName
	 *            The cookie name
	 * @return The cookie string associated with that name. Null if not found.
	 */
	public String getCookieValue(String cookieName) {
		String cookieValue = null;
		Cookie cookie;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookieName.equals(cookie.getName())) {
					cookieValue = cookie.getValue();
				}
			}
		}
		return cookieValue;
	}
}