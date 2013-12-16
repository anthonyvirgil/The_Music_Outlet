/*
 * Anthony-Virgil Bermejo
 * 0831360
 * ViewReportsServlet.java
 */
package servlets.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbManager.DatabaseManager;

import beans.Client;
import beans.ClientReport;
import beans.DetailedSalesReport;
import beans.TopSellersReport;
import beans.Track;
import beans.TrackAlbumSalesReport;

/**
 * Servlet that displays specific reports specified by the manager
 * 
 * @author Anthony-Virgil Bermejo
 * @version 2.1
 */
@WebServlet(name = "ViewReportsServlet", urlPatterns = { "/admin/reports" })
public class ViewReportsServlet extends HttpServlet {

	private static final long serialVersionUID = 7016857459461634600L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewReportsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Client client = null;
		String url = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DatabaseManager dbManager = new DatabaseManager();
		ArrayList<DetailedSalesReport> reportList = null;
		ArrayList<TrackAlbumSalesReport> itemReportList = null;
		ArrayList<ClientReport> clientReportList = null;
		ArrayList<TopSellersReport> topSellersList = null;
		ArrayList<Track> zeroTracksList = null;
		boolean detailed = false;
		double totalSales = 0.0;
		double totalCost = 0.0;
		double totalProfit = 0.0;
		double totalSalesSummary = 0.0;
		int totalDownloads = 0;

		// check if client exists and has admin rights
		synchronized (session) {
			client = (Client) session.getAttribute("client");
		}
		if (client != null && client.getStatus()) {

			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");

			// get the value of the search
			String searchValue = request.getParameter("searchValue");
			if (searchValue == null)
				searchValue = "";

			// determine the type of report
			String reportType = request.getParameter("reportType");
			if (reportType == null)
				reportType = "none";

			// check if report is detailed or not
			if (request.getParameter("detailed") != null)
				detailed = true;

			// get values for financial report
			totalSales = dbManager.getTotalSales();
			totalCost = dbManager.getTotalCostAlbums()
					+ dbManager.getTotalCostTracks();
			totalProfit = totalSales - totalCost;

			// total sales report
			if (reportType.equals("total_sales")) {

				// detailed report
				if (detailed) {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						reportList = dbManager.getTotalSalesDetailed(startDate,
								endDate, searchValue);
					} else {
						// no dates specified
						reportList = dbManager
								.getTotalSalesDetailed(searchValue);
					}

					request.setAttribute("reportList", reportList);
				}
				// summary report
				else {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						totalSalesSummary = dbManager.getTotalSales(startDate,
								endDate);
					} else {
						// no dates specified
						totalSalesSummary = totalSales;
					}
					request.setAttribute("totalSummary", totalSalesSummary);
				}
			}
			// sales by client
			else if (reportType.equals("sales_client")) {
				// detailed report
				if (detailed) {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						reportList = dbManager.getTotalSalesByClientDetailed(
								searchValue, startDate, endDate);
					} else {
						// no dates specified
						reportList = dbManager
								.getTotalSalesByClientDetailed(searchValue);
					}

					request.setAttribute("reportList", reportList);
				}
				// summary report
				else {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						totalSalesSummary = dbManager.getTotalSalesByClient(
								searchValue, startDate, endDate);
					} else {
						// no dates specified
						totalSalesSummary = dbManager
								.getTotalSalesByClient(searchValue);
					}
					request.setAttribute("totalSummary", totalSalesSummary);
				}
			}
			// sales by artist
			else if (reportType.equals("sales_artist")) {
				// detailed reports
				if (detailed) {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						reportList = dbManager.getTotalSalesByArtistDetailed(
								searchValue, startDate, endDate);
					} else {
						// no dates specified
						reportList = dbManager
								.getTotalSalesByArtistDetailed(searchValue);
					}

					request.setAttribute("reportList", reportList);
				}
				// summary report
				else {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						totalSalesSummary = dbManager.getTotalSalesByArtist(
								searchValue, startDate, endDate);
					} else {
						// no dates specified
						totalSalesSummary = dbManager
								.getTotalSalesByArtist(searchValue);
					}
					request.setAttribute("totalSummary", totalSalesSummary);
				}
			}
			// sales by track
			else if (reportType.equals("sales_track")) {
				// detailed reports
				if (detailed) {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						itemReportList = dbManager
								.getTotalSalesByTrackDetailed(searchValue,
										startDate, endDate);
					} else {
						// no dates specified
						itemReportList = dbManager
								.getTotalSalesByTrackDetailed(searchValue);
					}

					request.setAttribute("reportList", itemReportList);
				}
				// summary report
				else {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						totalSalesSummary = dbManager.getTotalSalesByTrack(
								searchValue, startDate, endDate);
						totalDownloads = dbManager.getTotalDownloadsByTrack(
								searchValue, startDate, endDate);
					} else {
						// no dates specified
						totalSalesSummary = dbManager
								.getTotalSalesByTrack(searchValue);
						totalDownloads = dbManager
								.getTotalDownloadsByTrack(searchValue);
					}
					request.setAttribute("totalSummary", totalSalesSummary);
					request.setAttribute("totalDownloads", totalDownloads);
				}
			}
			// sales by album
			else if (reportType.equals("sales_album")) {
				// detailed reports
				if (detailed) {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						itemReportList = dbManager
								.getTotalSalesByAlbumDetailed(searchValue,
										startDate, endDate);
					} else {
						// no dates specified
						itemReportList = dbManager
								.getTotalSalesByAlbumDetailed(searchValue);
					}

					request.setAttribute("reportList", itemReportList);
				}
				// summary report
				else {
					if (!startDate.equals("")) {
						// between two dates

						// if no end date specified, set to today
						if (endDate.equals(""))
							endDate = sdf.format(new Date());

						totalSalesSummary = dbManager.getTotalSalesByAlbum(
								searchValue, startDate, endDate);
						totalDownloads = dbManager.getTotalDownloadsByAlbum(
								searchValue, startDate, endDate);
					} else {
						// no dates specified
						totalSalesSummary = dbManager
								.getTotalSalesByAlbum(searchValue);
						totalDownloads = dbManager
								.getTotalDownloadsByAlbum(searchValue);
					}
					request.setAttribute("totalSummary", totalSalesSummary);
					request.setAttribute("totalDownloads", totalDownloads);
				}
			}
			// top sellers
			else if (reportType.equals("top_sellers")) {

				if (!startDate.equals("")) {
					// between two dates

					// if no end date specified, set to today
					if (endDate.equals(""))
						endDate = sdf.format(new Date());

					topSellersList = dbManager.getTopSellers(searchValue,
							startDate, endDate);
				} else {
					// no dates specified
					topSellersList = dbManager.getTopSellers(searchValue);
				}

				request.setAttribute("reportList", topSellersList);
			}
			// top clients
			else if (reportType.equals("top_clients")) {

				if (!startDate.equals("")) {
					// between two dates

					// if no end date specified, set to today
					if (endDate.equals(""))
						endDate = sdf.format(new Date());

					clientReportList = dbManager.getTopClients(searchValue,
							startDate, endDate);
				} else {
					// no dates specified
					clientReportList = dbManager.getTopClients(searchValue);
				}

				request.setAttribute("reportList", clientReportList);
			}
			// zero clients
			else if (reportType.equals("zero_clients")) {

				if (!startDate.equals("")) {
					// between two dates

					// if no end date specified, set to today
					if (endDate.equals(""))
						endDate = sdf.format(new Date());

					clientReportList = dbManager.getZeroClients(startDate,
							endDate);
				} else {
					// no dates specified
					clientReportList = dbManager.getZeroClients();
				}

				request.setAttribute("reportList", clientReportList);
			}
			// top clients
			else if (reportType.equals("zero_tracks")) {

				if (!startDate.equals("")) {
					// between two dates

					// if no end date specified, set to today
					if (endDate.equals(""))
						endDate = sdf.format(new Date());

					zeroTracksList = dbManager
							.getZeroTracks(startDate, endDate);
				} else {
					// no dates specified
					zeroTracksList = dbManager.getZeroTracks();
				}

				request.setAttribute("reportList", zeroTracksList);
			}

			request.setAttribute("searchValue", searchValue);
			request.setAttribute("reportType", reportType);
			request.setAttribute("totalSales", totalSales);
			request.setAttribute("totalCost", totalCost);
			request.setAttribute("totalProfit", totalProfit);
			request.setAttribute("detailed", detailed);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);

			url = "/admin/report.jsp";

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		} else {
			// user is not logged in or is not an admin, send to index
			response.sendRedirect("/g1w13/index");
		}
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
