package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.CookieUtil;

/**
 * Servlet implementation class CheckCookieServlet
 * 
 * This Servlet saves a cookie if the variable that keeps track of whether a the
 * client can save cookies is not set.
 * 
 */
@WebServlet(name = "CheckCookieServlet", urlPatterns = { "/checkCookie" })
public class CheckCookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckCookieServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Boolean canSaveCookie = (Boolean) session.getAttribute("canSaveCookie");
		if (canSaveCookie == null) {
			// Save a cookie
			String cookieTestStr = "Testing if you can save cookies";
			Cookie searchTypeCookie = new Cookie("cookieTest", cookieTestStr);
			searchTypeCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
			searchTypeCookie.setPath("/");
			response.addCookie(searchTypeCookie);
		}
		response.sendRedirect("/g1w13/index");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
