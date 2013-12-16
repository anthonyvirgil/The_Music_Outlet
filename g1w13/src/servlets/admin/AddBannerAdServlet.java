// Venelin Koulaxazov
// 1032744
// AddBannerAdServlet.java
package servlets.admin;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import beans.BannerAd;
import beans.Client;

import dbManager.DatabaseManager;

/**
 * Servlet implementation class AddBannerAdServlet
 * 
 * @author Venelin Koulaxazov
 */
@WebServlet(name = "AddBannerAdServlet", urlPatterns = { "/addBannerAd" })
public class AddBannerAdServlet extends HttpServlet {

	private static final long serialVersionUID = 7704146783115103422L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String servletError = null;
		DatabaseManager dbManager = new DatabaseManager();
		boolean uploadSuccess = false;
		String link = null;
		String url;
		String fileName = null;
		String type = null;
		Client client;

		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}

		// check if someone is logged in and if they have admin status
		if (client != null && client.getStatus()) {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);

			if (isMultipart) {

				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				try {
					List<FileItem> items = upload.parseRequest(request);
					Iterator<FileItem> iterator = items.iterator();
					while (iterator.hasNext()) {
						FileItem item = iterator.next();

						if (!item.isFormField()) {
							fileName = item.getName();
							String root = getServletContext().getRealPath("/");
							File path = new File(root + "/images/ads");

							if (!path.exists()) {
								boolean status = path.mkdirs();
								if (!status)
									uploadSuccess = false;
							}

							// if file extension is an image, upload to server
							if (validateFileExtension(fileName)) {
								File uploadedFile = new File(path + "/"
										+ fileName);
								item.write(uploadedFile);
								uploadSuccess = true;
							} else
								uploadSuccess = false;
						} else {
							if (item.getFieldName().equals("link"))
								link = item.getString();
							else if (item.getFieldName().equals("newAdType"))
								type = item.getString();
						}
					}
				} catch (FileUploadException e) {
					uploadSuccess = false;
				} catch (Exception e) {
					uploadSuccess = false;
				}
			}

			int typeInt;
			if (type.equals("bottom"))
				typeInt = 1;
			else
				typeInt = 2;
			if (link != null) {
				BannerAd newBannerAd = new BannerAd(link, typeInt, fileName,
						false);
				if (uploadSuccess) {
					dbManager.insertBannerAd(newBannerAd);
				} else {
					servletError = "Unable to upload banner ad image";
				}
			} else
				servletError = "You must enter a url";

			url = "/admin/populateAds";
			request.setAttribute("link", link);
			request.setAttribute("servletError", servletError);
			request.setAttribute("type", type);
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}
	}

	private boolean validateFileExtension(String fileName) {
		boolean valid = false;
		String[] tokens = fileName.split("\\.(?=[^\\.]+$)");
		String fileExtension = tokens[1].toUpperCase();

		// check if extension is a file extension
		if (fileExtension.equals("JPG") || fileExtension.equals("BMP")
				|| fileExtension.equals("GIF") || fileExtension.equals("PNG"))
			valid = true;

		return valid;
	}
}
