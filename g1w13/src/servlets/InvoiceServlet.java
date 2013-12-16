// Anthony-Virgil Bermejo
// 0831360
// InvoiceServlet.java

package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.EmailSender;

import dbManager.DatabaseManager;

import beans.Album;
import beans.Cart;
import beans.Client;
import beans.Detail;
import beans.Invoice;
import beans.Track;

/**
 * Servlet that gathers information to be displayed in the invoice page
 * 
 * @author Anthony-Virgil Bermejo
 * @version 1.1
 * 
 */
@WebServlet(name = "InvoiceServlet", urlPatterns = { "/invoice" })
public class InvoiceServlet extends HttpServlet {

	private static final long serialVersionUID = 2060404842030988832L;

	public InvoiceServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = null;
		Cart cart = null;
		Client client = null;
		String url = "";
		String displayMessage = "";
		DatabaseManager dbManager = new DatabaseManager();
		Invoice invoice = null;

		// get reference to session object
		session = request.getSession();

		// get reference to necessary objects whose information will be
		// displayed
		synchronized (session) {
			cart = (Cart) session.getAttribute("cart");
			client = (Client) session.getAttribute("client");
		}

		if (cart != null && client != null) {
			// create invoice
			invoice = new Invoice(new Date(), client.getClientId(),
					cart.getSubTotal(), cart.getPSTTaxes(client.getProvince()),
					cart.getGSTTaxes(client.getProvince()),
					cart.getHSTTaxes(client.getProvince()),
					cart.getTotalTaxes(client.getProvince()), false);

			// insert invoice into database
			dbManager.insertInvoice(invoice);

			// create details with the albums in cart
			for (Album cartAlbum : cart.getAlbums()) {
				// insert detail into database
				dbManager.insertDetail(new Detail(invoice.getSaleId(), 0,
						cartAlbum.getAlbumId(), cartAlbum.getCurrentPrice(),
						false));
			}

			// create details with the tracks in cart
			for (Track cartTrack : cart.getTracks()) {
				// insert detail into database
				dbManager.insertDetail(new Detail(invoice.getSaleId(),
						cartTrack.getInventoryId(), 0, cartTrack
								.getCurrentPrice(), false));
			}

			// send email to client
			sendInvoiceEmail(cart, client);

			url = "/invoice.jsp";

			synchronized (session) {
				// set old cart and invoice to session object
				session.setAttribute("oldCart", cart);
				session.setAttribute("invoice", invoice);

				// add a new cart to the session object
				Cart newCart = new Cart();
				newCart.setProvince(client.getProvince());
				session.setAttribute("cart", newCart);
			}

		} else {
			// cart or client is null, send back to index
			displayMessage = "You must be logged in to continue";
			url = "/login.jsp";

			request.setAttribute("errorMessage", displayMessage);
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	/*
	 * Sends email to a client with information from their shopping cart
	 */
	private void sendInvoiceEmail(Cart cart, Client client) {
		StringBuilder sb = new StringBuilder();
		EmailSender emailSender = new EmailSender();
		ArrayList<Album> albums = cart.getAlbums();
		ArrayList<Track> tracks = cart.getTracks();

		// first message appears in email
		sb.append("Thank you for your online purchase at The MUSIC Outlet.\n\n");
		sb.append("Here is a copy of the invoice for this purchase:\n\n");

		// if albums were bought, display all information of the album
		if (albums.size() != 0) {
			sb.append("Albums:\n");
			for (Album cartAlbum : albums) {
				sb.append(cartAlbum.getArtist() + "\n"
						+ cartAlbum.getMusicCategory() + "\n"
						+ cartAlbum.getAlbumTitle() + "\n"
						+ cartAlbum.getCurrentPriceString() + "\n\n");
			}
		}

		// if tracks were bought, display all information of the track
		if (tracks.size() != 0) {
			sb.append("Tracks:\n");
			for (Track cartTrack : cart.getTracks()) {
				sb.append(cartTrack.getArtist() + "\n"
						+ cartTrack.getMusicCategory() + "\n"
						+ cartTrack.getTrackTitle() + "\n"
						+ cartTrack.getCurrentPriceString() + "\n\n");
			}
		}

		// display all invoice information of prices
		sb.append("Total off taxes: " + cart.getSubTotalString());
		sb.append("\nGST: " + cart.getGSTTaxesString(client.getProvince()));
		sb.append("\nHST: " + cart.getHSTTaxesString(client.getProvince()));
		sb.append("\nPST: " + cart.getPSTTaxesString(client.getProvince()));
		sb.append("\nTotal with taxes: "
				+ cart.getTotalTaxesString(client.getProvince()));

		// display thank you message
		sb.append("\n\nThank you for choosing The MUSIC Outlet, enjoy your music!");
		sb.append("\n\n-The MUSIC Outlet Team");

		// send email to client
		emailSender.sendMail(client.getEmail(),
				"The MUSIC Outlet - Purchase Invoice", sb.toString());
	}
}
