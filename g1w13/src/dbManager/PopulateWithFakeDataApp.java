// Natacha Gabbamonte
// 0932340
// PopulateWithFakeDataApp.java

package dbManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import beans.Client;
import beans.Detail;
import beans.Invoice;
import beans.Review;

/**
 * This mini-console app populates the Client, Reviews, Details and Invoice
 * tables with fake data for JUnit testing.
 * 
 * @author Natacha Gabbamonte 0932340
 * 
 */
public class PopulateWithFakeDataApp {

	private static final String LINE = "------------------------------";
	private static Scanner keyboard = null;
	private static DatabaseManager dbManager = null;

	private static final String CLIENT = "client";
	private static final String DETAILS = "details";
	private static final String INVOICE = "invoice";
	private static final String REVIEWS = "reviews";

	public static void main(String[] args) {
		int choice = 0;
		keyboard = new Scanner(System.in);
		do {
			printMainMessage();
			choice = readInput();
			switch (choice) {
			case 1:
				System.out
						.println("Deleting all data from Client, Reviews, Details and Invoice tables...");
				deleteAllData();
				break;
			case 2:
				System.out
						.println("Populating data into Client, Reviews, Details and Invoice tables...");
				populateTables();
				addDorian();
				break;
			case 3:
				System.out.println("Displaying simple reports...");
				displayReports();
				break;
			case 4:
				System.out.println("Goodbye!");
				break;
			}
		} while (choice != 4);
	}

	private static void printMainMessage() {
		System.out.println(LINE + "\n"
				+ "WELCOME TO THE FAKE DATA POPULATION APP" + "\n" + LINE);
		System.out.println("Select one of 4 options:\n1) Drop and Recreate"
				+ " the Client, Reviews, Details and Invoice tables.\n"
				+ "2) Populate the aforementioned tables with fake data."
				+ "\n3) View simple reports on the tables\n4) Exit");
	}

	private static int readInput() {
		int choice = 0;

		boolean invalid = true;
		do {
			try {
				choice = keyboard.nextInt();
				invalid = false;
			} catch (Exception e) {
				keyboard.nextLine();
				System.out.println("Invalid input, please try again");
			}

		} while (invalid);
		return choice;
	}

	public static void deleteAllData() {
		dbManager = new DatabaseManager();
		System.out.print("Dropping " + REVIEWS.toUpperCase() + " table...");
		if (dbManager.dropTable(REVIEWS))
			System.out.println("SUCCESS.");
		else
			System.out.println("FAILURE.");

		System.out.print("Dropping " + DETAILS.toUpperCase() + " table...");
		if (dbManager.dropTable(DETAILS))
			System.out.println("SUCCESS.");
		else
			System.out.println("FAILURE.");

		System.out.print("Dropping " + INVOICE.toUpperCase() + " table...");
		if (dbManager.dropTable(INVOICE))
			System.out.println("SUCCESS.");
		else
			System.out.println("FAILURE.");

		System.out.print("Dropping " + CLIENT.toUpperCase() + " table...");
		if (dbManager.dropTable(CLIENT))
			System.out.println("SUCCESS.");
		else
			System.out.println("FAILURE.");

		System.out.print("Creating " + CLIENT.toUpperCase() + " table...");
		if (dbManager.createClientTable())
			System.out.println("SUCCESS.");
		else
			System.out.println("FAILURE.");

		System.out.print("Creating " + INVOICE.toUpperCase() + " table...");
		if (dbManager.createInvoiceTable())
			System.out.println("SUCCESS.");
		else
			System.out.println("FAILURE.");

		System.out.print("Creating " + DETAILS.toUpperCase() + " table...");
		if (dbManager.createDetailsTable())
			System.out.println("SUCCESS.");
		else
			System.out.println("FAILURE.");

		System.out.print("Creating " + REVIEWS.toUpperCase() + " table...");
		if (dbManager.createReviewsTable())
			System.out.println("SUCCESS.");
		else
			System.out.println("FAILURE.");

		System.out.println("Done Dropping and Recreating all tables!");
	}

	public static void populateTables() {
		dbManager = new DatabaseManager();
		// POPULATING CLIENT TABLE
		System.out
				.println("\nPopulating " + CLIENT.toUpperCase() + " table...");
		addClient(new Client(-1, "Mrs.", "Gabbamonte", "Natacha", "",
				"123 Baker Street", "", "Montreal", "QC", "Canada", "h4r5t6",
				"(514)999-9999", "", "natgabb@hotmail.com", "passWord123",
				true, ""));
		addClient(new Client(-1, "Mr.", "Bermejo", "Anthony-Virgil", "",
				"1500 Sherbrooke", "", "Montreal", "AB", "Canada", "h1y7u8",
				"(450)223-5645", "", "anthony@bermejo.com", "antBerm123", true,
				""));
		addClient(new Client(-1, "Mr.", "Smith", "John", "", "15 St-Laurent",
				"", "Montreal", "PE", "Canada", "h1y7u8", "(514)231-1231", "",
				"johnsmith@mail.com", "venkoul123", false, ""));

		// POPULATING INVOICE AND DETAIL TABLES
		System.out.println("\nPopulating " + INVOICE.toUpperCase() + " and "
				+ DETAILS.toUpperCase() + " tables...");
		try {
			Invoice invoice1;
			invoice1 = new Invoice(-1, new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss").parse("2012-11-03 12:30:12"), 1,
					0.99, 0.1, 0.05, 0, 1.14, false);

			addInvoice(invoice1);
			addDetail(new Detail(invoice1.getSaleId(), 5, 0, 0.99, false));

			Invoice invoice2 = new Invoice(-1, new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss").parse("2013-02-06 12:44:16"), 2,
					6.47, 0.65, 0.33, 0, 7.45, false);
			addInvoice(invoice2);
			addDetail(new Detail(invoice2.getSaleId(), 12, 0, 0.99, false));
			addDetail(new Detail(invoice2.getSaleId(), 50, 0, 0.99, false));
			addDetail(new Detail(invoice2.getSaleId(), 72, 0, 0.99, false));
			addDetail(new Detail(invoice2.getSaleId(), 0, 33, 3.50, false));

			Invoice invoice3 = new Invoice(-1, new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss").parse("2013-01-06 12:44:16"), 1,
					3.49, 0.35, 0.18, 0, 4.02, false);
			addInvoice(invoice3);
			addDetail(new Detail(invoice3.getSaleId(), 0, 34, 1.51, false));
			addDetail(new Detail(invoice3.getSaleId(), 6, 0, 0.99, false));
			addDetail(new Detail(invoice3.getSaleId(), 50, 0, 0.99, false));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		// POPULATING REVIEWS TABLE
		System.out.println("\nPopulating " + REVIEWS.toUpperCase()
				+ " table...");
		addReview(new Review(55, 1, new Date(), "Natacha Gabbamonte", 4,
				"Title", "It's a great song!", true));
		addReview(new Review(56, 1, new Date(), "Natacha Gabbamonte", 5,
				"Title", "It's also a great song!", true));
		addReview(new Review(63, 1, new Date(), "Natacha Gabbamonte", 1,
				"Title", "Bad.", false));
		addReview(new Review(62, 2, new Date(), "Anthony B.", 3, "Title",
				"Cool song.", true));
		addReview(new Review(63, 2, new Date(), "Anthony B.", 1, "Title",
				"This song was terrible.", false));
		addReview(new Review(76, 3, new Date(), "Venelin K.", 3, "Title", "",
				false));
		addReview(new Review(77, 3, new Date(), "Venelin K.", 5, "Title", "",
				true));

	}

	private static void addDorian() {
		if (dbManager == null)
			dbManager = new DatabaseManager();
		System.out.println("Adding Dorian");
		addClient(new Client(-1, "Mr", "Mein", "Dorian", "",
				"123 Baker Street", "", "Montreal", "QC", "Canada", "h4r5t6",
				"(514)999-9999", "", "do@gmail.com", "passWord123", true, ""));
	}

	private static void addClient(Client c) {
		if (dbManager.insertClient(c))
			System.out.println("Successfully added client:\n\t" + c);
		else
			System.out.println("ERROR COULD NOT ADD:\n\t" + c);
	}

	private static void addInvoice(Invoice i) {
		if (dbManager.insertInvoice(i))
			System.out.println("Successfully added invoice:\n\t" + i);
		else
			System.out.println("ERROR COULD NOT ADD:\n\t" + i);

	}

	private static void addDetail(Detail d) {
		if (dbManager.insertDetail(d))
			System.out.println("Successfully added detail:\n\t" + d);
		else
			System.out.println("ERROR COULD NOT ADD:\n\t" + d);

	}

	private static void addReview(Review r) {
		if (dbManager.insertReview(r))
			System.out.println("Successfully added review:\n\t" + r);
		else
			System.out.println("ERROR COULD NOT ADD:\n\t" + r);
	}

	private static void displayReports() {
		dbManager = new DatabaseManager();
		System.out.println(LINE + "\n" + CLIENT.toUpperCase() + "\n" + LINE);
		ArrayList<Client> clients = dbManager.getClients();
		System.out.println("Number of records in " + CLIENT + ": "
				+ clients.size());
		for (Client c : clients)
			System.out.println(c);

		System.out.println(LINE + "\n" + REVIEWS.toUpperCase() + "\n" + LINE);
		ArrayList<Review> reviews = dbManager.getReviews();
		System.out.println("Number of records in " + REVIEWS + ": "
				+ reviews.size());
		for (Review r : reviews)
			System.out.println(r);

		System.out.println(LINE + "\n" + DETAILS.toUpperCase() + "\n" + LINE);
		ArrayList<Detail> details = dbManager.getDetails();
		System.out.println("Number of records in " + DETAILS + ": "
				+ details.size());
		for (Detail d : details)
			System.out.println(d);

		System.out.println(LINE + "\n" + INVOICE.toUpperCase() + "\n" + LINE);
		ArrayList<Invoice> invoices = dbManager.getInvoices();
		System.out.println("Number of records in " + INVOICE + ": "
				+ invoices.size());
		for (Invoice i : invoices)
			System.out.println(i);
	}
}
