// Anthony-Virgil Bermejo
// 0831360
// UpdateAlbumServlet.java
package servlets.admin;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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

import util.AlbumValidator;

import dbManager.DatabaseManager;

import beans.Album;
import beans.Client;

/**
 * Servlet that updates an album to the inventory with required information
 * 
 * @author Anthony-Virgil Bermejo
 * @author 1.2
 */
@WebServlet(name = "UpdateAlbumServlet", urlPatterns = { "/admin/updateAlbum" })
public class UpdateAlbumServlet extends HttpServlet {

	private static final long serialVersionUID = 7880230240803621748L;

	public UpdateAlbumServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = null;
		String servletError = null;
		String displayMessage = null;
		Album oldAlbum = null;
		Album album = null;
		DatabaseManager dbManager = null;
		Client client = null;
		AlbumValidator albumValidator = new AlbumValidator();
		boolean uploadSuccess = true;
		String albumTitle = null;
		String artist = null;
		boolean removalStatus = false;
		String fileName = null;
		String genre = null;
		String releaseDate = null;
		String recordLabel = null;
		String costPrice = null;
		String listPrice = null;
		String salePrice = null;
		String removalStatusString = null;
		String albumIdString = null;

		// get reference to client logged in
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
					
					// iterate through form values
					while (iterator.hasNext()) {
						FileItem item = iterator.next();

						
						if (!item.isFormField()) {
							// form value is a path to a file
							fileName = item.getName();

							if (fileName != null && !fileName.equals("")) {
								// determine path where image will be uploaded
								String root = getServletContext().getRealPath(
										"/");
								File path = new File(root + "/images/covers");

								// if directory doesn't exist, create it
								if (!path.exists()) {
									boolean status = path.mkdirs();
									if (!status)
										uploadSuccess = false;
								}

								// if file extension is an image, upload to
								// server
								if (validateFileExtension(fileName)) {
									File uploadedFile = new File(path + "/"
											+ fileName);
									item.write(uploadedFile);
								} else {
									uploadSuccess = false;
								}
							}
						} else {
							// get all values from form
							if (item.getFieldName().equals("albumTitle"))
								albumTitle = item.getString();
							else if (item.getFieldName().equals("artist"))
								artist = item.getString();
							else if (item.getFieldName().equals("genre"))
								genre = item.getString();
							else if (item.getFieldName().equals("releaseDate"))
								releaseDate = item.getString();
							else if (item.getFieldName().equals("recordLabel"))
								recordLabel = item.getString();
							else if (item.getFieldName().equals("costPrice"))
								costPrice = item.getString();
							else if (item.getFieldName().equals("listPrice"))
								listPrice = item.getString();
							else if (item.getFieldName().equals("salePrice"))
								salePrice = item.getString();
							else if (item.getFieldName()
									.equals("removalStatus"))
								removalStatusString = item.getString();
							else if (item.getFieldName().equals("albumId"))
								albumIdString = item.getString();
						}
					}
				} catch (FileUploadException e) {
					uploadSuccess = false;
				} catch (Exception e) {
					uploadSuccess = false;
				}
			}

			if (removalStatusString.equals("0"))
				removalStatus = false;
			else
				removalStatus = true;

			if (costPrice != null && listPrice != null && salePrice != null
					&& tryDoubleParse(costPrice) && tryDoubleParse(salePrice)
					&& tryDoubleParse(listPrice)) {

				double costPriceDouble = Double.parseDouble(costPrice);
				double listPriceDouble = Double.parseDouble(listPrice);
				double salePriceDouble = Double.parseDouble(salePrice);

				// create new album object with user-defined fields
				album = new Album(albumTitle, releaseDate, artist, fileName,
						genre, recordLabel, 0, new Date(), costPriceDouble,
						listPriceDouble, salePriceDouble, removalStatus);

				if (uploadSuccess) {

					dbManager = new DatabaseManager();

					// get reference to the album being edited
					if (tryIntParse(albumIdString)) {
						int albumId = Integer.parseInt(albumIdString);
						oldAlbum = dbManager.getAlbumById(albumId);

						if (oldAlbum != null) {

							if (fileName != null && !fileName.equals("")) {
								// create new album object
								album = new Album(oldAlbum.getAlbumId(),
										albumTitle, releaseDate, artist,
										fileName, genre, recordLabel,
										oldAlbum.getNumOfTracks(),
										oldAlbum.getDateEntered(),
										costPriceDouble, listPriceDouble,
										salePriceDouble, removalStatus);
							} else {
								// file name is null or empty
								album = new Album(oldAlbum.getAlbumId(),
										albumTitle, releaseDate, artist,
										oldAlbum.getAlbumCover(), genre,
										recordLabel, oldAlbum.getNumOfTracks(),
										oldAlbum.getDateEntered(),
										costPriceDouble, listPriceDouble,
										salePriceDouble, removalStatus);
							}

							// validate the album object
							servletError = albumValidator.validateAlbum(album);

							if (servletError == null) {
								// edit album in database
								dbManager.updateAlbum(album);
								displayMessage = "Album successfully edited";
								request.setAttribute("album", album);
								request.setAttribute("displayMessage",
										displayMessage);
							} else {
								// there was an error, display message to user
								request.setAttribute("album", album);
								request.setAttribute("displayMessage",
										servletError);
							}

							url = "/admin/editAlbum.jsp";

						} else {
							// album to edit does not exist in database
							request.setAttribute("displayMessage",
									"Album to edit does not exist");
							url = "/admin/index";
						}
					} else {
						// error uploading image
						servletError = "There was an error editing the album";
						url = "/admin/editAlbum.jsp";
						request.setAttribute("album", album);
						request.setAttribute("displayMessage", servletError);
					}
				} else {
					// image failed to upload
					servletError = "Unable to upload album cover image";
					url = "/admin/editAlbum.jsp";
					request.setAttribute("album", album);
					request.setAttribute("displayMessage", servletError);
				}
			} else {
				// error parsing prices
				servletError = "There was an error editing the album";
				url = "/admin/editAlbum.jsp";
				request.setAttribute("album", album);
				request.setAttribute("displayMessage", servletError);
			}

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

	/*
	 * Tries to parse a string to an int.
	 * 
	 * @param str The string
	 * 
	 * @return Whether the string can be parsed into an int.
	 */
	private boolean tryIntParse(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/*
	 * Tries to parse a string to a double.
	 * 
	 * @param str The string
	 * 
	 * @return Whether the string can be parsed into a double.
	 */
	private boolean tryDoubleParse(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
