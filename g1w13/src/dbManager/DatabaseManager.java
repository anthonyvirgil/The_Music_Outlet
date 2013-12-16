// Venelin Koulaxazov (1032744)
// Natacha Gabbamonte (0932340)
// Anthony-Virgil Bermejo (0831360)
// DatabaseManager.java

package dbManager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import beans.*;

/**
 * Database manager that connect to a MySQL database and has CRUD methods for
 * albums, clients, details, invoices, reviews, and tracks
 * 
 * @author Venelin Koulaxazov
 * @author Natacha Gabbamonte
 * @author Anthony-Virgil Bermejo
 * @version 3.2
 */
public class DatabaseManager {

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	private Logger logger = Logger.getLogger(getClass().getName());
	static {
		final InputStream inputStream = DatabaseManager.class
				.getResourceAsStream("/logging.properties");
		try {
			LogManager.getLogManager().readConfiguration(inputStream);

		} catch (final IOException e) {
			Logger.getAnonymousLogger().severe(
					"Could not load default logging.properties file");
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
	}

	/**
	 * Default constructor
	 */
	public DatabaseManager() {
		logger.info("DatabaseManager instantiated");
	}

	/**
	 * Checks if the contacts, folders and message tables exist in the database
	 * 
	 * @return true if the tables exists, otherwise false
	 */
	public boolean checkIfTablesExist() {
		String preparedQuery = "SHOW TABLES";
		boolean tablesExist = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();
				int i = 0;
				if (resultSet.next())
					do {
						i++;
					} while (resultSet.next());
				if (i == 10)
					tablesExist = true;

			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tablesExist;
	}

	/**
	 * Deletes all the data including the table's structure. Used only for JUnit
	 * testing.
	 * 
	 * @return if the operation was successful
	 */
	public boolean dropTable(String tableName) {
		String preparedQuery = "DROP TABLE " + tableName;
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error dropping table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulOperation;
	}

	/**
	 * Deletes all the data in the table without harming the structure. Used
	 * only for JUnit testing.
	 * 
	 * @return if the operation was successful
	 */
	public boolean truncateTable(String tableName) {
		String preparedQuery = "TRUNCATE " + tableName;
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error truncating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the album table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createAlbumTable() {
		String preparedQuery = "CREATE TABLE album ("
				+ "albumId int(5) NOT NULL AUTO_INCREMENT,"
				+ "albumTitle varchar(200) NOT NULL,"
				+ "releaseDate date DEFAULT NULL,"
				+ "artist varchar(200) NOT NULL,"
				+ "albumCover varchar(200) NOT NULL,"
				+ "musicCategory varchar(50) NOT NULL,"
				+ "recordLabel varchar(150) NOT NULL,"
				+ "numOfTracks int(11) NOT NULL,"
				+ "dateEntered datetime DEFAULT NULL,"
				+ "costPrice double DEFAULT NULL,"
				+ "listPrice double DEFAULT NULL,"
				+ "salePrice double DEFAULT NULL,"
				+ "removalStatus tinyint(2) DEFAULT NULL,"
				+ "PRIMARY KEY (albumId) ) ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the track table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createTrackTable() {
		String preparedQuery = "CREATE TABLE track ("
				+ "inventoryId int(5) NOT NULL AUTO_INCREMENT,"
				+ "albumId int(5) DEFAULT NULL,"
				+ "trackTitle varchar(150) NOT NULL,"
				+ "artist varchar (150) NOT NULL,"
				+ "songWriter varchar(200) NOT NULL,"
				+ "playLength varchar(8) DEFAULT NULL,"
				+ "selectionNum int(11) DEFAULT NULL,"
				+ "musicCategory varchar(45) DEFAULT NULL,"
				+ "albumCover varchar(200) DEFAULT NULL,"
				+ "costPrice double DEFAULT NULL,"
				+ "listPrice double DEFAULT NULL,"
				+ "salePrice double DEFAULT NULL,"
				+ "dateEntered datetime DEFAULT NULL,"
				+ "typeOfSale int(1) DEFAULT NULL,"
				+ "removalStatus tinyint(2) DEFAULT NULL,"
				+ "PRIMARY KEY (inventoryId),"
				+ "CONSTRAINT albumId FOREIGN KEY (albumId) REFERENCES album (albumId)"
				+ ") ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the client table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createClientTable() {
		String preparedQuery = "CREATE TABLE client ("
				+ "clientId int(5) NOT NULL AUTO_INCREMENT,"
				+ "title varchar(4) NOT NULL NULL,"
				+ "lastName varchar(100) NOT NULL,"
				+ "firstName varchar(100) NOT NULL,"
				+ "companyName varchar(100) DEFAULT '',"
				+ "address1 varchar(300) NOT NULL,"
				+ "address2 varchar(300) DEFAULT '',"
				+ "city varchar(100) NOT NULL,"
				+ "province varchar(45) NOT NULL,"
				+ "country varchar(45) NOT NULL, "
				+ "postalCode varchar(6) NOT NULL,"
				+ "homePhone varchar(13) NOT NULL,"
				+ "cellPhone varchar(13) DEFAULT '',"
				+ "email varchar(200) NOT NULL,"
				+ "password varchar(50) NOT NULL,"
				+ "status tinyint(2) NOT NULL NULL,"
				+ "genreLastSearch varchar(45) DEFAULT '',"
				+ "PRIMARY KEY (clientId),"
				+ "CONSTRAINT uniqueEmail UNIQUE (email) ) ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the invoice table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createInvoiceTable() {
		String preparedQuery = "CREATE TABLE invoice ("
				+ "saleId int(5) NOT NULL AUTO_INCREMENT,"
				+ "saleDate datetime DEFAULT NULL,"
				+ "clientId int(5) DEFAULT NULL,"
				+ "netValue double DEFAULT NULL,"
				+ "pst double DEFAULT NULL,"
				+ "gst double DEFAULT NULL,"
				+ "hst double DEFAULT NULL,"
				+ "grossValue double DEFAULT NULL,"
				+ "removalStatus tinyint(2) DEFAULT NULL,"
				+ "PRIMARY KEY (saleID),"
				+ "CONSTRAINT clientId FOREIGN KEY (clientId) REFERENCES client (clientId)"
				+ ") ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the reviews table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createReviewsTable() {
		String preparedQuery = "CREATE TABLE reviews ("
				+ "reviewId int(5) NOT NULL AUTO_INCREMENT,"
				+ "inventoryId int(5) DEFAULT NULL,"
				+ "clientId int(5) NOT NULL,"
				+ "reviewDate datetime DEFAULT NULL,"
				+ "clientName varchar (300) NOT NULL,"
				+ "rating int(1) NOT NULL,"
				+ "reviewTitle varchar(150),"
				+ "reviewText varchar (2000),"
				+ "approvalStatus tinyint(2) DEFAULT NULL,"
				+ "PRIMARY KEY (reviewId),"
				+ "CONSTRAINT clientIdReviews FOREIGN KEY (clientId) REFERENCES client(clientId),"
				+ "CONSTRAINT inventoryIdReviews FOREIGN KEY (inventoryId) REFERENCES track (inventoryId)"
				+ ") ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the details table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createDetailsTable() {
		String preparedQuery = "CREATE TABLE details ("
				+ "detailId int(5) NOT NULL AUTO_INCREMENT,"
				+ "saleId int(5) DEFAULT NULL,"
				+ "inventoryId int(5) DEFAULT NULL,"
				+ "albumId int (5) DEFAULT NULL,"
				+ "salePrice double DEFAULT NULL,"
				+ "removalStatus tinyint(2) DEFAULT NULL,"
				+ "PRIMARY KEY (detailId),"
				+ "CONSTRAINT saleId FOREIGN KEY (saleId) REFERENCES invoice (saleID),"
				+ "CONSTRAINT inventoryIdDetails FOREIGN KEY (inventoryId) REFERENCES track (inventoryId),"
				+ "CONSTRAINT albumIdDetails FOREIGN KEY (albumId) REFERENCES album (albumId)"
				+ ") ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the rssFeed table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createRssFeedTable() {
		String preparedQuery = "CREATE TABLE rssFeed ("
				+ "rssFeedId int (5) NOT NULL AUTO_INCREMENT,"
				+ "name varchar (100) DEFAULT NULL,"
				+ "url varchar (150) DEFAULT NULL,"
				+ "status tinyint (2) DEFAULT NULL,"
				+ "PRIMARY KEY (rssFeedId)) ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the survey table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createSurveyTable() {
		String preparedQuery = "CREATE TABLE survey ("
				+ "surveyId int (5) NOT NULL AUTO_INCREMENT,"
				+ "surveyName varchar (50) DEFAULT NULL,"
				+ "surveyQuestion varchar (250) DEFAULT NULL,"
				+ "status tinyint (2) DEFAULT NULL, PRIMARY KEY (surveyId)"
				+ ") ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the surveyAnswer table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createSurveyAnswerTable() {
		String preparedQuery = "CREATE TABLE surveyAnswer ("
				+ "surveyAnswerId int (5) NOT NULL AUTO_INCREMENT,"
				+ "surveyId int (5) DEFAULT NULL,"
				+ "answer varchar (50) DEFAULT NULL,"
				+ "votes int (5) DEFAULT NULL,"
				+ "PRIMARY KEY (surveyAnswerId),"
				+ "CONSTRAINT surveyIdSurveyAnswer FOREIGN KEY (surveyId) REFERENCES survey (surveyID)"
				+ ") ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Creates the bannerAd table
	 * 
	 * @return if the operation was successful
	 */
	public boolean createBannerAdTable() {
		String preparedQuery = "CREATE TABLE bannerAd ("
				+ "bannerAdId int(5) NOT NULL AUTO_INCREMENT,"
				+ "url varchar(150) DEFAULT NULL,"
				+ "type int(1) DEFAULT NULL,"
				+ "imageSource varchar (200) DEFAULT NULL,"
				+ "status tinyint (2) DEFAULT NULL,"
				+ "PRIMARY KEY (bannerAdId)) ENGINE=InnoDB";
		boolean successfulOperation = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.executeUpdate();
				successfulOperation = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error creating table", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulOperation;
	}

	/**
	 * Returns an array list of all the albums that are currently in the album
	 * table in the database.
	 * 
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return the array list containing all the albums
	 */
	public ArrayList<Album> getAlbums(boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album";

		if (withoutRemovedTracks)
			preparedQuery += " WHERE removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns all the albums that match the genre.
	 * 
	 * @param genre
	 *            The genre of the albums.
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> getAlbumsByGenre(String genre,
			boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE musicCategory = ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, genre);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by genre", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns a list of albums matching a name.
	 * 
	 * @param name
	 *            The name
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> getAlbumsByName(String name,
			boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE albumTitle = ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, name);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by name", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns a list of albums matching a name in any way (i.e *name*).
	 * 
	 * @param name
	 *            The name
	 * @param startRecord
	 *            The record to start returning records at (-1 for no LIMIT)
	 * @param numOfRecords
	 *            The number of records wanted (-1 for no LIMIT)
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * 
	 * @return The list of albums.
	 */
	public ArrayList<Album> searchAlbumsByName(String name, int startRecord,
			int numOfRecords, boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE albumTitle LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		preparedQuery += " ORDER BY albumTitle";

		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + name + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by name", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns a list of albums matching a genre in any way (i.e *genre*).
	 * 
	 * @param genre
	 *            The genre
	 * @param startRecord
	 *            The record to start returning records at (-1 for no LIMIT)
	 * @param numOfRecords
	 *            The number of records wanted (-1 for no LIMIT)
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> searchAlbumsByGenre(String genre, int startRecord,
			int numOfRecords, boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE musicCategory LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		preparedQuery += " ORDER BY musicCategory, albumTitle";

		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + genre + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by genre", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns a list of albums matching a string at the beginning (i.e
	 * string*).
	 * 
	 * @param name
	 *            The name
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> browseAlbumsByName(String name,
			boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE albumTitle LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, name + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by name", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns a list of albums matching an artist in any way (i.e *artist*).
	 * 
	 * @param name
	 *            The artist
	 * @param startRecord
	 *            The record to start returning records at (-1 for no LIMIT)
	 * @param numOfRecords
	 *            The number of records wanted (-1 for no LIMIT)
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> searchAlbumsByArtist(String artist,
			int startRecord, int numOfRecords, boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE artist LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery = "SELECT * FROM album WHERE removalStatus = 0 AND artist LIKE ?";

		preparedQuery += " ORDER BY artist, albumTitle";

		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + artist + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns a list of albums where the artist name matches the string at the
	 * beginning (i.e string*).
	 * 
	 * @param name
	 *            The artist
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> browseAlbumsByArtist(String artist,
			boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE artist LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery = "SELECT * FROM album WHERE removalStatus = 0 AND artist LIKE ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, artist + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns a list of albums on sale.
	 * 
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> getAlbumsOnSale(boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE salePrice != 0";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums on sale", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns a list of albums ordered by the date they were entered into the
	 * database. The newest albums will appear at the beginning of the list.
	 * 
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> getAlbumsSortedByDate(boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album ORDER BY dateEntered DESC";

		if (withoutRemovedTracks)
			preparedQuery = "SELECT * FROM album WHERE removalStatus = 0 ORDER BY dateEntered DESC";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by date", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns the first five albums ordered by the date they were entered into
	 * the database. The newest albums will appear at the beginning of the list.
	 * 
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> getFirstFiveAlbumsSortedByDate() {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE removalStatus = 0 ORDER BY dateEntered DESC LIMIT 5";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by date", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns a list of albums matching an artist.
	 * 
	 * @param name
	 *            The artist name
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of albums.
	 */
	public ArrayList<Album> getAlbumsByArtist(String artist,
			boolean withoutRemovedTracks) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT * FROM album WHERE artist = ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, artist);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return albums;
	}

	/**
	 * Returns an album matching an artist and albumTitle
	 * 
	 * @param artist
	 *            The artist of the album
	 * @param albumTitle
	 *            The title of the album
	 * @return The album.
	 */
	public Album getAlbumByArtistAndTitle(String artist, String albumTitle) {
		Album album = null;

		String preparedQuery = "SELECT * FROM album WHERE artist = ? AND albumTitle = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, artist);
				preparedStatement.setString(2, albumTitle);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					album = new Album(resultSet.getInt(1),
							resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5),
							resultSet.getString(6), resultSet.getString(7),
							resultSet.getInt(8), resultSet.getTimestamp(9),
							resultSet.getDouble(10), resultSet.getDouble(11),
							resultSet.getDouble(12), resultSet.getBoolean(13));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return album;
	}

	/**
	 * Returns a list of albums matching an artist.
	 * 
	 * @param albumId
	 *            The albumId
	 * @return The album.
	 */
	public Album getAlbumById(int albumId) {
		Album album = null;

		String preparedQuery = "SELECT * FROM album WHERE albumId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, albumId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					album = new Album(resultSet.getInt(1),
							resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5),
							resultSet.getString(6), resultSet.getString(7),
							resultSet.getInt(8), resultSet.getTimestamp(9),
							resultSet.getDouble(10), resultSet.getDouble(11),
							resultSet.getDouble(12), resultSet.getBoolean(13));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return album;
	}

	/**
	 * Updates an album in the album table with the specified parameters
	 * 
	 * @param anAlbum
	 * @return if the update was successful
	 */
	public boolean updateAlbum(Album anAlbum) {
		String preparedQuery = "UPDATE album SET albumTitle = ?, releaseDate = ?, artist = ?, albumCover = ?,"
				+ "musicCategory = ?, recordLabel = ?, numOfTracks = ?, dateEntered = ?, costPrice = ?, listPrice = ?, "
				+ "salePrice = ?, removalStatus = ? WHERE albumId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, anAlbum.getAlbumTitle());
				preparedStatement.setString(2, anAlbum.getReleaseDate());
				preparedStatement.setString(3, anAlbum.getArtist());
				preparedStatement.setString(4, anAlbum.getAlbumCover());
				preparedStatement.setString(5, anAlbum.getMusicCategory());
				preparedStatement.setString(6, anAlbum.getRecordLabel());
				preparedStatement.setInt(7, anAlbum.getNumOfTracks());
				preparedStatement.setTimestamp(8, new Timestamp(anAlbum
						.getDateEntered().getTime()));
				preparedStatement.setDouble(9, anAlbum.getCostPrice());
				preparedStatement.setDouble(10, anAlbum.getListPrice());
				preparedStatement.setDouble(11, anAlbum.getSalePrice());
				preparedStatement.setBoolean(12, anAlbum.isRemovalStatus());
				preparedStatement.setInt(13, anAlbum.getAlbumId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Updates an album's removal status using the specified id.
	 * 
	 * @param albumId
	 *            the album Id
	 * @param removalStatus
	 *            the removal status
	 * @return if the update was successful
	 */
	public boolean updateAlbumRemovalStatusById(int albumId,
			boolean removalStatus) {
		String preparedQuery = "UPDATE album SET removalStatus = ? WHERE albumId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setBoolean(1, removalStatus);
				preparedStatement.setInt(2, albumId);
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts an album in the album table with the specified parameters
	 * 
	 * @param anAlbum
	 * @return if the insert was successful
	 */
	public boolean insertAlbum(Album anAlbum) {
		String preparedQuery = "INSERT INTO album VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?)";
		boolean successfulInsert = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, anAlbum.getAlbumTitle());
				preparedStatement.setString(2, anAlbum.getReleaseDate());
				preparedStatement.setString(3, anAlbum.getArtist());
				preparedStatement.setString(4, anAlbum.getAlbumCover());
				preparedStatement.setString(5, anAlbum.getMusicCategory());
				preparedStatement.setString(6, anAlbum.getRecordLabel());
				preparedStatement.setInt(7, anAlbum.getNumOfTracks());
				preparedStatement.setTimestamp(8, new Timestamp(anAlbum
						.getDateEntered().getTime()));
				preparedStatement.setDouble(9, anAlbum.getCostPrice());
				preparedStatement.setDouble(10, anAlbum.getListPrice());
				preparedStatement.setDouble(11, anAlbum.getSalePrice());
				preparedStatement.setBoolean(12, anAlbum.isRemovalStatus());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					anAlbum.setAlbumId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulInsert;
	}

	/**
	 * Deletes an entry in the album table
	 * 
	 * @param anAlbum
	 * @return if the operation was successful
	 */
	public boolean removeAlbum(Album anAlbum) {
		String preparedQuery = "DELETE FROM album WHERE albumId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, anAlbum.getAlbumId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulRemove;
	}

	/**
	 * Returns an array list of all the clients that are currently in the client
	 * table in the database.
	 * 
	 * @return the array list containing all the clients
	 */
	public ArrayList<Client> getClients() {
		ArrayList<Client> clients = new ArrayList<Client>();

		String preparedQuery = "SELECT * FROM client";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						clients.add(new Client(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getString(10), resultSet
										.getString(11),
								resultSet.getString(12), resultSet
										.getString(13),
								resultSet.getString(14), resultSet
										.getString(15), resultSet
										.getBoolean(16), resultSet
										.getString(17)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting clients", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return clients;
	}

	/**
	 * Returns the client with the specified email. Returns null if no client
	 * was found or an exception occurred.
	 * 
	 * @param email
	 *            the email of the client.
	 * @return The client
	 */
	public Client getClientByClientId(int clientId) {
		Client client = null;

		String preparedQuery = "SELECT * FROM client where clientId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, clientId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					client = new Client(resultSet.getInt(1),
							resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5),
							resultSet.getString(6), resultSet.getString(7),
							resultSet.getString(8), resultSet.getString(9),
							resultSet.getString(10), resultSet.getString(11),
							resultSet.getString(12), resultSet.getString(13),
							resultSet.getString(14), resultSet.getString(15),
							resultSet.getBoolean(16), resultSet.getString(17));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting clients", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return client;
	}

	/**
	 * Returns the client with the specified email. Returns null if no client
	 * was found or an exception occurred.
	 * 
	 * @param email
	 *            the email of the client.
	 * @return The client
	 */
	public Client getClientByEmail(String email) {
		Client client = null;

		String preparedQuery = "SELECT * FROM client where email = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, email);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					client = new Client(resultSet.getInt(1),
							resultSet.getString(2), resultSet.getString(3),
							resultSet.getString(4), resultSet.getString(5),
							resultSet.getString(6), resultSet.getString(7),
							resultSet.getString(8), resultSet.getString(9),
							resultSet.getString(10), resultSet.getString(11),
							resultSet.getString(12), resultSet.getString(13),
							resultSet.getString(14), resultSet.getString(15),
							resultSet.getBoolean(16), resultSet.getString(17));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting clients by email", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return client;
	}

	/**
	 * Updates a client in the client table with the specified parameters
	 * 
	 * @param aClient
	 * @return if the update was successful
	 */
	public boolean updateClient(Client aClient) {
		String preparedQuery = "UPDATE client SET title = ?, lastName = ?, firstName = ?, "
				+ "companyName = ?, address1 = ?, address2 = ?, city = ?, province = ?, "
				+ "country = ?, postalCode = ?, homePhone = ?, cellPhone = ?, email = ?, "
				+ "password = ?, status = ?, genreLastSearch = ? WHERE clientId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, aClient.getTitle());
				preparedStatement.setString(2, aClient.getLastName());
				preparedStatement.setString(3, aClient.getFirstName());
				preparedStatement.setString(4, aClient.getCompanyName());
				preparedStatement.setString(5, aClient.getAddress1());
				preparedStatement.setString(6, aClient.getAddress2());
				preparedStatement.setString(7, aClient.getCity());
				preparedStatement.setString(8, aClient.getProvince());
				preparedStatement.setString(9, aClient.getCountry());
				preparedStatement.setString(10, aClient.getPostalCode());
				preparedStatement.setString(11, aClient.getHomePhone());
				preparedStatement.setString(12, aClient.getCellPhone());
				preparedStatement.setString(13, aClient.getEmail());
				preparedStatement.setString(14, aClient.getPassword());
				preparedStatement.setBoolean(15, aClient.getStatus());
				preparedStatement.setString(16, aClient.getGenreLastSearch());
				preparedStatement.setInt(17, aClient.getClientId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts a client in the client table with the specified parameters
	 * 
	 * @param aClient
	 * @return if the insert was successful
	 */
	public boolean insertClient(Client aClient) {
		String preparedQuery = "INSERT INTO client VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		boolean successfulInsert = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, aClient.getTitle());
				preparedStatement.setString(2, aClient.getLastName());
				preparedStatement.setString(3, aClient.getFirstName());
				preparedStatement.setString(4, aClient.getCompanyName());
				preparedStatement.setString(5, aClient.getAddress1());
				preparedStatement.setString(6, aClient.getAddress2());
				preparedStatement.setString(7, aClient.getCity());
				preparedStatement.setString(8, aClient.getProvince());
				preparedStatement.setString(9, aClient.getCountry());
				preparedStatement.setString(10, aClient.getPostalCode());
				preparedStatement.setString(11, aClient.getHomePhone());
				preparedStatement.setString(12, aClient.getCellPhone());
				preparedStatement.setString(13, aClient.getEmail());
				preparedStatement.setString(14, aClient.getPassword());
				preparedStatement.setBoolean(15, aClient.getStatus());
				preparedStatement.setString(16, aClient.getGenreLastSearch());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					aClient.setClientId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulInsert;
	}

	/**
	 * Deletes an entry in the client table
	 * 
	 * @param aClient
	 * @return if the operation was successful
	 */
	public boolean removeClient(Client aClient) {
		String preparedQuery = "DELETE FROM client WHERE clientId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aClient.getClientId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulRemove;
	}

	/**
	 * Returns an array list of all the details that are currently in the
	 * details table in the database.
	 * 
	 * @return the array list containing all the details
	 */
	public ArrayList<Detail> getDetails() {
		ArrayList<Detail> details = new ArrayList<Detail>();

		String preparedQuery = "SELECT * FROM details";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						details.add(new Detail(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getInt(3), resultSet
								.getInt(4), resultSet.getDouble(5), resultSet
								.getBoolean(6)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting details", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return details;
	}

	/**
	 * Returns a Detail object that matches the given detail id.
	 * 
	 * @param detailId
	 *            the detail id
	 * @return the detail object
	 */
	public Detail getDetailByDetailId(int detailId) {
		Detail detail = null;

		String preparedQuery = "SELECT * FROM details where detailId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, detailId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					detail = new Detail(resultSet.getInt(1),
							resultSet.getInt(2), resultSet.getInt(3),
							resultSet.getInt(4), resultSet.getDouble(5),
							resultSet.getBoolean(6));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting details", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return detail;
	}

	/**
	 * Returns all the details associated with a particular sale id (i.e invoice
	 * id).
	 * 
	 * @param saleId
	 *            The sale id
	 * @return The array list of details.
	 */
	public ArrayList<Detail> getDetailsBySaleId(int saleId) {
		ArrayList<Detail> details = new ArrayList<Detail>();

		String preparedQuery = "SELECT * FROM details WHERE saleId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, saleId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						details.add(new Detail(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getInt(3), resultSet
								.getInt(4), resultSet.getDouble(5), resultSet
								.getBoolean(6)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting details", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return details;
	}

	/**
	 * Returns all tracks from a specific invoice using a sale Id
	 * 
	 * @param saleId
	 *            The sale id
	 * @return The array list of tracks from invoice
	 */
	public ArrayList<InvoiceTrack> getTracksFromDetailsBySaleId(int saleId) {
		ArrayList<InvoiceTrack> invoiceTracks = new ArrayList<InvoiceTrack>();

		String preparedQuery = "select track.inventoryId, track.artist, track.trackTitle, track.musicCategory, details.salePrice, details.detailId, details.removalStatus from track INNER JOIN details ON track.inventoryId = details.inventoryId WHERE saleId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, saleId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoiceTracks.add(new InvoiceTrack(resultSet.getInt(1),
								resultSet.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getDouble(5),
								resultSet.getInt(6), resultSet.getBoolean(7)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting details", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoiceTracks;
	}

	/**
	 * Returns all albums from a specific invoice using a sale Id
	 * 
	 * @param saleId
	 *            The sale id
	 * @return The array list of albums from invoice
	 */
	public ArrayList<InvoiceAlbum> getAlbumsFromDetailsBySaleId(int saleId) {
		ArrayList<InvoiceAlbum> invoiceAlbum = new ArrayList<InvoiceAlbum>();

		String preparedQuery = "select album.albumId, album.artist, album.albumTitle, album.musicCategory, details.salePrice, details.detailId, details.removalStatus from album INNER JOIN details ON album.albumId = details.albumId WHERE saleId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, saleId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoiceAlbum.add(new InvoiceAlbum(resultSet.getInt(1),
								resultSet.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getDouble(5),
								resultSet.getInt(6), resultSet.getBoolean(7)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting details", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoiceAlbum;
	}

	/**
	 * Updates a detail in the details table with the specified parameters
	 * 
	 * @param aDetail
	 * @return if the update was successful
	 */
	public boolean updateDetail(Detail aDetail) {
		String preparedQuery = "UPDATE details SET saleId = ?, inventoryId = ?, albumId = ?, salePrice = ?, removalStatus = ? WHERE detailId = ?";

		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aDetail.getSaleId());
				preparedStatement.setInt(2, aDetail.getInventoryId());
				preparedStatement.setInt(3, aDetail.getAlbumId());
				preparedStatement.setDouble(4, aDetail.getSalePrice());
				preparedStatement.setBoolean(5, aDetail.isRemovalStatus());
				preparedStatement.setInt(6, aDetail.getDetailId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating a detail", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Updates a detail in the details table with the specified parameters
	 * 
	 * @param aDetail
	 * @return if the update was successful
	 */
	public boolean updateDetailRemovalStatus(Detail aDetail) {
		String preparedQuery = "UPDATE details SET removalStatus = ? WHERE detailId = ?";

		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setBoolean(1, aDetail.isRemovalStatus());
				preparedStatement.setInt(2, aDetail.getDetailId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating a detail", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts a detail in the details table with the specified parameters
	 * 
	 * @param aDetail
	 * @return if the insert was successful
	 */
	public boolean insertDetail(Detail aDetail) {
		String inventoryId = "?";
		if (aDetail.getInventoryId() == 0)
			inventoryId = "null";
		String albumId = "?";
		if (aDetail.getAlbumId() == 0)
			albumId = "null";

		String preparedQuery = "INSERT INTO details VALUES (null,?,"
				+ inventoryId + "," + albumId + ",?,?)";
		boolean successfulInsert = false;

		int count = 1;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(count++, aDetail.getSaleId());
				if (inventoryId.equals("?"))
					preparedStatement.setInt(count++, aDetail.getInventoryId());
				if (albumId.equals("?"))
					preparedStatement.setInt(count++, aDetail.getAlbumId());
				preparedStatement.setDouble(count++, aDetail.getSalePrice());
				preparedStatement
						.setBoolean(count++, aDetail.isRemovalStatus());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					aDetail.setDetailId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting a detail", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulInsert;
	}

	/**
	 * Deletes an entry in the details table
	 * 
	 * @param aDetail
	 * @return if the operation was successful
	 */
	public boolean removeDetail(Detail aDetail) {
		String preparedQuery = "DELETE FROM details WHERE detailId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aDetail.getDetailId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing detail", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulRemove;
	}

	/**
	 * Returns an array list of all the invoices that are currently in the
	 * invoice table in the database.
	 * 
	 * @return the array list containing all the invoices
	 */
	public ArrayList<Invoice> getInvoices() {
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();

		String preparedQuery = "SELECT * FROM invoice";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoices.add(new Invoice(resultSet.getInt(1), resultSet
								.getTimestamp(2), resultSet.getInt(3),
								resultSet.getDouble(4), resultSet.getDouble(5),
								resultSet.getDouble(6), resultSet.getDouble(7),
								resultSet.getDouble(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting invoices", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoices;
	}

	/**
	 * Returns an invoice that matches a certain sale id.
	 * 
	 * @param saleId
	 *            the sale id of the invoice
	 * @return the array list containing all the invoices
	 */
	public Invoice getInvoiceBySaleId(int saleId) {
		Invoice invoice = null;

		String preparedQuery = "SELECT * FROM invoice WHERE saleId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, saleId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					invoice = new Invoice(resultSet.getInt(1),
							resultSet.getTimestamp(2), resultSet.getInt(3),
							resultSet.getDouble(4), resultSet.getDouble(5),
							resultSet.getDouble(6), resultSet.getDouble(7),
							resultSet.getDouble(8), resultSet.getBoolean(9));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting invoices by sales", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoice;
	}

	/**
	 * Returns all invoices that match a certain client id.
	 * 
	 * @param clientId
	 *            the client id
	 * @return the array list containing all the invoices
	 */
	public ArrayList<Invoice> getInvoicesByClientId(int clientId) {
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();

		String preparedQuery = "SELECT * FROM invoice WHERE clientId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, clientId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoices.add(new Invoice(resultSet.getInt(1), resultSet
								.getTimestamp(2), resultSet.getInt(3),
								resultSet.getDouble(4), resultSet.getDouble(5),
								resultSet.getDouble(6), resultSet.getDouble(7),
								resultSet.getDouble(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting invoices by client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoices;
	}

	/**
	 * Returns an array list of all the invoices on a specified date (format:
	 * YYYY-mm-dd)
	 * 
	 * @param date
	 *            The date in the format YYY-mm-dd
	 * @return The array list of invoices
	 */
	public ArrayList<Invoice> getInvoicesBySaleDate(String date) {
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();

		String preparedQuery = "SELECT * FROM invoice WHERE saleDate = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, date);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoices.add(new Invoice(resultSet.getInt(1), resultSet
								.getTimestamp(2), resultSet.getInt(3),
								resultSet.getDouble(4), resultSet.getDouble(5),
								resultSet.getDouble(6), resultSet.getDouble(7),
								resultSet.getDouble(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting invoices by sale date", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoices;
	}

	/**
	 * Returns an array list of all the invoices on a specified date (format:
	 * YYYY-mm-dd) that matches a certain clientId.
	 * 
	 * @param date
	 *            The date in the format YYY-mm-dd
	 * @param clientId
	 *            The Id of a client
	 * @return The array list of invoices
	 */
	public ArrayList<Invoice> getInvoicesBySaleDateAndClientId(String date,
			int clientId) {
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();

		String preparedQuery = "SELECT * FROM invoice WHERE clientId = ? AND saleDate = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, clientId);
				preparedStatement.setString(2, date);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoices.add(new Invoice(resultSet.getInt(1), resultSet
								.getTimestamp(2), resultSet.getInt(3),
								resultSet.getDouble(4), resultSet.getDouble(5),
								resultSet.getDouble(6), resultSet.getDouble(7),
								resultSet.getDouble(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting invoices by sale date and client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoices;
	}

	/**
	 * Returns an array list of all the invoices that fall between a start date
	 * and an end date (both inclusive) (format: YYYY-mm-dd)
	 * 
	 * @param startDate
	 *            The start date in the format YYY-mm-dd
	 * @param endDate
	 *            The end date in the format YYY-mm-dd
	 * @return The array list of invoices
	 */
	public ArrayList<Invoice> getInvoicesByStartToEndDates(String startDate,
			String endDate) {
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();

		String preparedQuery = "SELECT * FROM invoice WHERE saleDate >= ? AND saleDate <= ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, startDate);
				preparedStatement.setString(2, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoices.add(new Invoice(resultSet.getInt(1), resultSet
								.getTimestamp(2), resultSet.getInt(3),
								resultSet.getDouble(4), resultSet.getDouble(5),
								resultSet.getDouble(6), resultSet.getDouble(7),
								resultSet.getDouble(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting invoices", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoices;
	}

	/**
	 * Returns an array list of all the invoices that fall between a start date
	 * and an end date (both inclusive) that matches a certain client
	 * Id.(format: YYYY-mm-dd)
	 * 
	 * @param startDate
	 *            The start date in the format YYY-mm-dd
	 * @param endDate
	 *            The end date in the format YYY-mm-dd
	 * @param clientId
	 *            The client Id
	 * @return The array list of invoices
	 */
	public ArrayList<Invoice> getInvoicesByStartToEndDatesAndClientId(
			String startDate, String endDate, int clientId) {
		ArrayList<Invoice> invoices = new ArrayList<Invoice>();

		String preparedQuery = "SELECT * FROM invoice WHERE clientId = ? AND saleDate >= ? AND saleDate <= ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, clientId);
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoices.add(new Invoice(resultSet.getInt(1), resultSet
								.getTimestamp(2), resultSet.getInt(3),
								resultSet.getDouble(4), resultSet.getDouble(5),
								resultSet.getDouble(6), resultSet.getDouble(7),
								resultSet.getDouble(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting an invoice", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoices;
	}

	/**
	 * Returns an array list of all the invoices searched by a sale id
	 * 
	 * @param email
	 *            Email of the client
	 * @param startRecord
	 *            The record to start returning records at (-1 for no LIMIT)
	 * @param numOfRecords
	 *            The number of records wanted (-1 for no LIMIT)
	 * @return List of all invoices from search result
	 */
	public ArrayList<InvoiceSearch> searchInvoicesByEmail(String email,
			int startRecord, int numOfRecords) {
		ArrayList<InvoiceSearch> invoiceList = new ArrayList<InvoiceSearch>();

		String preparedQuery = "SELECT invoice.*, client.email FROM invoice INNER JOIN client ON invoice.clientId = client.clientId WHERE client.email LIKE ? ORDER BY invoice.saleDate DESC";

		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + email + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoiceList.add(new InvoiceSearch(resultSet.getInt(1),
								resultSet.getTimestamp(2), resultSet.getInt(3),
								resultSet.getDouble(4), resultSet.getDouble(5),
								resultSet.getDouble(6), resultSet.getDouble(7),
								resultSet.getDouble(8),
								resultSet.getString(10), resultSet
										.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoiceList;
	}

	/**
	 * Returns an array list of all the invoices searched by a sale id
	 * 
	 * @param saleId
	 *            Id of the invoice
	 * @param startRecord
	 *            The record to start returning records at (-1 for no LIMIT)
	 * @param numOfRecords
	 *            The number of records wanted (-1 for no LIMIT)
	 * @return List of all invoices from search result
	 */
	public ArrayList<InvoiceSearch> searchInvoicesBySaleId(int saleId,
			int startRecord, int numOfRecords) {
		ArrayList<InvoiceSearch> invoiceList = new ArrayList<InvoiceSearch>();

		String preparedQuery = "SELECT invoice.*, client.email FROM invoice INNER JOIN client ON invoice.clientId = client.clientId WHERE saleId = ? ORDER BY invoice.saleDate DESC";

		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, saleId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						invoiceList.add(new InvoiceSearch(resultSet.getInt(1),
								resultSet.getTimestamp(2), resultSet.getInt(3),
								resultSet.getDouble(4), resultSet.getDouble(5),
								resultSet.getDouble(6), resultSet.getDouble(7),
								resultSet.getDouble(8),
								resultSet.getString(10), resultSet
										.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting albums by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return invoiceList;
	}

	/**
	 * Updates an invoice in the invoice table with the specified parameters
	 * 
	 * @param anInvoice
	 * @return if the update was successful
	 */
	public boolean updateInvoice(Invoice anInvoice) {
		String preparedQuery = "UPDATE invoice SET saleDate = ?, clientId = ?, netValue = ?, "
				+ "pst = ?, gst = ?, hst = ?, grossValue = ?, removalStatus = ? WHERE saleId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setTimestamp(1, new Timestamp(anInvoice
						.getSaleDate().getTime()));
				preparedStatement.setInt(2, anInvoice.getClientId());
				preparedStatement.setDouble(3, anInvoice.getNetValue());
				preparedStatement.setDouble(4, anInvoice.getPst());
				preparedStatement.setDouble(5, anInvoice.getGst());
				preparedStatement.setDouble(6, anInvoice.getHst());
				preparedStatement.setDouble(7, anInvoice.getGrossValue());
				preparedStatement.setBoolean(8, anInvoice.isRemovalStatus());
				preparedStatement.setInt(9, anInvoice.getSaleId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating an invoice", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts an invoice in the invoice table with the specified parameters
	 * 
	 * @param anInvoice
	 * @return if the insert was successful
	 */
	public boolean insertInvoice(Invoice anInvoice) {
		String preparedQuery = "INSERT INTO invoice VALUES (null,?,?,?,?,?,?,?,?)";
		boolean successfulInsert = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setTimestamp(1, new Timestamp(anInvoice
						.getSaleDate().getTime()));
				preparedStatement.setInt(2, anInvoice.getClientId());
				preparedStatement.setDouble(3, anInvoice.getNetValue());
				preparedStatement.setDouble(4, anInvoice.getPst());
				preparedStatement.setDouble(5, anInvoice.getGst());
				preparedStatement.setDouble(6, anInvoice.getHst());
				preparedStatement.setDouble(7, anInvoice.getGrossValue());
				preparedStatement.setBoolean(8, anInvoice.isRemovalStatus());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					anInvoice.setSaleId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting an invoice", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulInsert;
	}

	/**
	 * Deletes an entry in the invoice table
	 * 
	 * @param anInvoice
	 * @return if the operation was successful
	 */
	public boolean removeInvoice(Invoice anInvoice) {
		String preparedQuery = "DELETE FROM invoice WHERE saleId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, anInvoice.getSaleId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing invoice", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulRemove;
	}

	/**
	 * Returns an array list of all the reviews that are currently in the
	 * reviews table in the database.
	 * 
	 * @return the array list containing all the reviews
	 */
	public ArrayList<Review> getReviews() {
		ArrayList<Review> reviews = new ArrayList<Review>();

		String preparedQuery = "SELECT * FROM reviews ORDER BY reviewDate";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reviews.add(new Review(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getInt(3), resultSet
								.getTimestamp(4), resultSet.getString(5),
								resultSet.getInt(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting reviews", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return reviews;
	}

	/**
	 * Returns an array list of all the reviews that are currently in the
	 * reviews table in the database that fit a certain approval status.
	 * 
	 * @return the array list containing all the reviews wanted
	 */
	public ArrayList<Review> getReviewsByApprovalStatus(boolean approvalStatus,
			int startRecord, int numOfRecords) {
		ArrayList<Review> reviews = new ArrayList<Review>();

		String preparedQuery = "SELECT * FROM reviews WHERE approvalStatus = ? ORDER BY reviewDate DESC";
		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;
		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setBoolean(1, approvalStatus);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reviews.add(new Review(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getInt(3), resultSet
								.getTimestamp(4), resultSet.getString(5),
								resultSet.getInt(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting reviews", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return reviews;
	}

	/**
	 * Returns an array list of reviews that have a client name partly or fully
	 * matching the stirng sent in.
	 * 
	 * @param the
	 *            client name to be compared
	 * @return the array list containing all the reviews
	 */
	public ArrayList<Review> searchApprovedReviewsByClientName(
			String clientName, int startRecord, int numOfRecords) {
		ArrayList<Review> reviews = new ArrayList<Review>();

		String preparedQuery = "SELECT * FROM reviews WHERE approvalStatus = 1 AND clientName LIKE ? ORDER BY reviewDate DESC";
		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + clientName + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reviews.add(new Review(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getInt(3), resultSet
								.getTimestamp(4), resultSet.getString(5),
								resultSet.getInt(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting review", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return reviews;
	}

	/**
	 * Returns an array list of a review using an review id.
	 * 
	 * @return the array list containing all the reviews
	 */
	public Review getReviewByReviewId(int reviewId) {
		Review review = null;

		String preparedQuery = "SELECT * FROM reviews WHERE reviewId = ? ORDER BY reviewDate";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, reviewId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					review = new Review(resultSet.getInt(1),
							resultSet.getInt(2), resultSet.getInt(3),
							resultSet.getTimestamp(4), resultSet.getString(5),
							resultSet.getInt(6), resultSet.getString(7),
							resultSet.getString(8), resultSet.getBoolean(9));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting review", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return review;
	}

	/**
	 * Returns all the reviews for a certain track id.
	 * 
	 * @param trackInt
	 *            The track id.
	 * @return All the reviews for that track.
	 */
	public ArrayList<Review> getReviewsByTrackId(int trackInt, int startRecord,
			int numOfRecords) {
		ArrayList<Review> reviews = new ArrayList<Review>();

		String preparedQuery = "SELECT * FROM reviews where inventoryId = ? ORDER BY reviewDate";
		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;
		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, trackInt);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reviews.add(new Review(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getInt(3), resultSet
								.getTimestamp(4), resultSet.getString(5),
								resultSet.getInt(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting reviews by track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return reviews;
	}

	/**
	 * Returns all the approved reviews for a certain track id that are greater
	 * than 0.
	 * 
	 * @param trackInt
	 *            The track id.
	 * @return All the reviews for that track.
	 */
	public ArrayList<Review> getApprovedReviewsByTrackId(int trackInt,
			int startRecord, int numOfRecords) {
		ArrayList<Review> reviews = new ArrayList<Review>();

		String preparedQuery = "SELECT * FROM reviews where inventoryId = ? AND approvalStatus = 1 ORDER BY reviewDate DESC";
		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, trackInt);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reviews.add(new Review(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getInt(3), resultSet
								.getTimestamp(4), resultSet.getString(5),
								resultSet.getInt(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting reviews by track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return reviews;
	}

	/**
	 * Returns all the reviews for a certain client.
	 * 
	 * @param clientId
	 *            The client id.
	 * @return All the reviews for that client.
	 */
	public ArrayList<Review> getReviewsByClientId(int clientId) {
		ArrayList<Review> reviews = new ArrayList<Review>();

		String preparedQuery = "SELECT * FROM reviews where clientId = ? ORDER BY reviewDate";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, clientId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reviews.add(new Review(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getInt(3), resultSet
								.getTimestamp(4), resultSet.getString(5),
								resultSet.getInt(6), resultSet.getString(7),
								resultSet.getString(8), resultSet.getBoolean(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting reviews by client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return reviews;
	}

	/**
	 * Returns all the reviews for a certain client.
	 * 
	 * @param clientId
	 *            The client id.
	 * @return All the reviews for that client.
	 */
	public Review getReviewsByClientAndTrack(int clientId, int trackId) {
		Review review = null;

		String preparedQuery = "SELECT * FROM reviews where clientId = ? AND inventoryId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, clientId);
				preparedStatement.setInt(2, trackId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						review = new Review(resultSet.getInt(1),
								resultSet.getInt(2), resultSet.getInt(3),
								resultSet.getTimestamp(4),
								resultSet.getString(5), resultSet.getInt(6),
								resultSet.getString(7), resultSet.getString(8),
								resultSet.getBoolean(9));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting review by client and track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return review;
	}

	/**
	 * Updates a review in the reviews table with the specified parameters
	 * 
	 * @param aReview
	 * @return if the update was successful
	 */
	public boolean updateReview(Review aReview) {
		String preparedQuery = "UPDATE reviews SET inventoryId = ?, clientId = ?, reviewDate = ?, clientName = ?, "
				+ "rating = ?, reviewTitle = ?, reviewText = ?, approvalStatus = ? WHERE reviewId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aReview.getInventoryId());
				preparedStatement.setInt(2, aReview.getClientId());
				preparedStatement.setTimestamp(3, new Timestamp(aReview
						.getReviewDate().getTime()));
				preparedStatement.setString(4, aReview.getClientName());
				preparedStatement.setInt(5, aReview.getRating());
				preparedStatement.setString(6, aReview.getReviewTitle());
				preparedStatement.setString(7, aReview.getReviewText());
				preparedStatement.setBoolean(8, aReview.isApprovalStatus());
				preparedStatement.setDouble(9, aReview.getReviewId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating a review", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Updates a certain review's approval status.
	 * 
	 * @param the
	 *            review id
	 * @param the
	 *            approval status
	 * @return if the update was successful
	 */
	public boolean updateReviewApprovalStatus(int reviewId,
			boolean approvalStatus) {
		String preparedQuery = "UPDATE reviews SET approvalStatus = ? WHERE reviewId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setBoolean(1, approvalStatus);
				preparedStatement.setInt(2, reviewId);
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating a review", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts a review in the reviews table with the specified parameters
	 * 
	 * @param aReview
	 * @return if the insert was successful
	 */
	public boolean insertReview(Review aReview) {
		String preparedQuery = "INSERT INTO reviews VALUES (null,?,?,?,?,?,?,?,?)";
		boolean successfulInsert = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, aReview.getInventoryId());
				preparedStatement.setInt(2, aReview.getClientId());
				preparedStatement.setTimestamp(3, new Timestamp(aReview
						.getReviewDate().getTime()));
				preparedStatement.setString(4, aReview.getClientName());
				preparedStatement.setInt(5, aReview.getRating());
				preparedStatement.setString(6, aReview.getReviewTitle());
				preparedStatement.setString(7, aReview.getReviewText());
				preparedStatement.setBoolean(8, aReview.isApprovalStatus());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					aReview.setReviewId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting a review", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulInsert;
	}

	/**
	 * Deletes an entry in the reviews table
	 * 
	 * @param aReview
	 * @return if the operation was successful
	 */
	public boolean removeReview(Review aReview) {
		String preparedQuery = "DELETE FROM reviews WHERE reviewId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aReview.getReviewId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing review", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulRemove;
	}

	/**
	 * Deletes an entry in the reviews table
	 * 
	 * @param the
	 *            id of the review
	 * @return if the operation was successful
	 */
	public boolean removeReviewById(int reviewId) {
		String preparedQuery = "DELETE FROM reviews WHERE reviewId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, reviewId);
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing review", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulRemove;
	}

	/**
	 * Returns an array list of all the tracks that are currently in the track
	 * table in the database.
	 * 
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return the array list containing all the tracks
	 */
	public ArrayList<Track> getTracks(boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns all the tracks matching a name.
	 * 
	 * @param name
	 *            The name
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of tracks.
	 */
	public ArrayList<Track> getTracksByName(String name,
			boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track WHERE trackTitle = ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, name);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by name", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns all the tracks matching a name in any way (i.e *name*).
	 * 
	 * @param name
	 *            The name
	 * @param startRecord
	 *            The record to start returning records at (-1 for no LIMIT)
	 * @param numOfRecords
	 *            The number of records wanted (-1 for no LIMIT)
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of tracks.
	 */
	public ArrayList<Track> searchTracksByName(String name, int startRecord,
			int numOfRecords, boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track WHERE trackTitle LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		preparedQuery += " ORDER BY trackTitle";

		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + name + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by name", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns all the tracks matching the string at the beginning of it's name
	 * (i.e string*).
	 * 
	 * @param name
	 *            The name
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of tracks.
	 */
	public ArrayList<Track> browseTracksByName(String name,
			boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track WHERE trackTitle LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, name + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by name", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns all the tracks matching an artist in any way (i.e *name*).
	 * 
	 * @param name
	 *            The name
	 * @param startRecord
	 *            The record to start returning records at (-1 for no LIMIT)
	 * @param numOfRecords
	 *            The number of records wanted (-1 for no LIMIT)
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of tracks.
	 */
	public ArrayList<Track> searchTracksByArtist(String artist,
			int startRecord, int numOfRecords, boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track WHERE artist LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		preparedQuery += " ORDER BY artist, albumId";

		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + artist + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns all the tracks matching a genre in any way (i.e *genre*).
	 * 
	 * @param genre
	 *            The genre
	 * @param startRecord
	 *            The record to start returning records at (-1 for no LIMIT)
	 * @param numOfRecords
	 *            The number of records wanted (-1 for no LIMIT)
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of tracks.
	 */
	public ArrayList<Track> searchTracksByGenre(String genre, int startRecord,
			int numOfRecords, boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track WHERE musicCategory LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		preparedQuery += " ORDER BY musicCategory, trackTitle";

		if (startRecord >= 0 && numOfRecords >= 0)
			preparedQuery += " LIMIT " + startRecord + "," + numOfRecords;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + genre + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by genre", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns all the tracks matching with an artist name starting with the
	 * string sent in (i.e string*).
	 * 
	 * @param name
	 *            The name
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of tracks.
	 */
	public ArrayList<Track> browseTracksByArtist(String artist,
			boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track WHERE artist LIKE ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, artist + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns all the tracks matching a genre.
	 * 
	 * @param name
	 *            The genre
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of tracks.
	 */
	public ArrayList<Track> getTracksByGenre(String genre,
			boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track WHERE musicCategory = ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, genre);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by genre", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns all the tracks matching an artist.
	 * 
	 * @param name
	 *            The artist name
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of tracks.
	 */
	public ArrayList<Track> getTracksByArtist(String artist,
			boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track WHERE artist = ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, artist);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns all the tracks matching an album id.
	 * 
	 * @param name
	 *            The album id.
	 * @param withoutRemovedTracks
	 *            Whether the query should return the removed tracks
	 * @return The list of tracks.
	 */
	public ArrayList<Track> getTracksByAlbum(int albumId,
			boolean withoutRemovedTracks) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT * FROM track WHERE albumId = ?";

		if (withoutRemovedTracks)
			preparedQuery += " AND removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, albumId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return tracks;
	}

	/**
	 * Returns the tracks that have been bought by a particular client
	 * 
	 * @param clientId
	 * @return an ArrayList of Track objects
	 */
	public ArrayList<Track> getPurchasedTracksByClient(int clientId) {
		ArrayList<Track> tracks = new ArrayList<Track>();

		String preparedQuery = "SELECT t.inventoryId, t.albumId, t.trackTitle, t.artist, t.songWriter, "
				+ "t.playLength, t.selectionNum, t.musicCategory, t.albumCover, t.costPrice, t.listPrice, "
				+ "t.salePrice, t.dateEntered, t.typeOfSale, t.removalStatus FROM track t LEFT JOIN details "
				+ "d ON t.inventoryId = d.inventoryId LEFT JOIN invoice i ON d.saleId = i.saleId LEFT JOIN "
				+ "client c ON i.clientId = c.clientId WHERE c.clientId = ? AND d.removalStatus = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, clientId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}

				preparedQuery = "SELECT t.inventoryId, t.albumId, t.trackTitle, t.artist, t.songWriter, t.playLength, "
						+ "t.selectionNum, t.musicCategory, t.albumCover, t.costPrice, t.listPrice, t.salePrice, t.dateEntered, "
						+ "t.typeOfSale, t.removalStatus  FROM track t LEFT JOIN album a ON t.albumId = a.albumId  LEFT JOIN "
						+ "details d ON a.albumId = d.albumId LEFT JOIN invoice i ON d.saleId = i.saleId LEFT JOIN client c "
						+ "ON i.clientId = c.clientId WHERE c.clientId = ? AND t.typeOfSale != 0 AND d.removalStatus = 0";

				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, clientId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						tracks.add(new Track(resultSet.getInt(1), resultSet
								.getInt(2), resultSet.getString(3), resultSet
								.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getInt(7),
								resultSet.getString(8), resultSet.getString(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getTimestamp(13),
								resultSet.getInt(14), resultSet.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting tracks by album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return tracks;
	}

	/**
	 * Returns the albums that have been purchased by a client.
	 * 
	 * @param clientId
	 * @return an ArrayList of Albums objects
	 */
	public ArrayList<Album> getPurchasedAlbumsByClient(int clientId) {
		ArrayList<Album> albums = new ArrayList<Album>();

		String preparedQuery = "SELECT a.albumId, a.albumTitle, a.releaseDate, a.artist, a.albumCover, "
				+ " a.musicCategory, a.recordLabel, a.numOfTracks, a.dateEntered, a.costPrice, a.listPrice,"
				+ " a.salePrice, a.removalStatus FROM album a LEFT JOIN details "
				+ "d ON a.albumId = d.albumId LEFT JOIN invoice i ON d.saleId = i.saleId LEFT JOIN "
				+ "client c ON i.clientId = c.clientId WHERE c.clientId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, clientId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						albums.add(new Album(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getString(4), resultSet.getString(5),
								resultSet.getString(6), resultSet.getString(7),
								resultSet.getInt(8), resultSet.getTimestamp(9),
								resultSet.getDouble(10), resultSet
										.getDouble(11),
								resultSet.getDouble(12), resultSet
										.getBoolean(13)));
					} while (resultSet.next());
				}

			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting albums purchased by client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return albums;
	}

	/**
	 * Returns the track with the matching track id.
	 * 
	 * @param inventoryId
	 *            The inventoryId.
	 * @return The track.
	 */
	public Track getTrackById(int inventoryId) {
		Track track = null;

		String preparedQuery = "SELECT * FROM track WHERE inventoryId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, inventoryId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next())
					track = new Track(resultSet.getInt(1), resultSet.getInt(2),
							resultSet.getString(3), resultSet.getString(4),
							resultSet.getString(5), resultSet.getString(6),
							resultSet.getInt(7), resultSet.getString(8),
							resultSet.getString(9), resultSet.getDouble(10),
							resultSet.getDouble(11), resultSet.getDouble(12),
							resultSet.getTimestamp(13), resultSet.getInt(14),
							resultSet.getBoolean(15));

			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return track;
	}

	/**
	 * Returns the track with the matching track id.
	 * 
	 * @param inventoryId
	 *            The inventoryId.
	 * @return The track.
	 */
	public Track getTrackByArtistAndTitle(String artist, String trackTitle) {
		Track track = null;

		String preparedQuery = "SELECT * FROM track WHERE artist = ? AND trackTitle = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, artist);
				preparedStatement.setString(2, trackTitle);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next())
					track = new Track(resultSet.getInt(1), resultSet.getInt(2),
							resultSet.getString(3), resultSet.getString(4),
							resultSet.getString(5), resultSet.getString(6),
							resultSet.getInt(7), resultSet.getString(8),
							resultSet.getString(9), resultSet.getDouble(10),
							resultSet.getDouble(11), resultSet.getDouble(12),
							resultSet.getTimestamp(13), resultSet.getInt(14),
							resultSet.getBoolean(15));

			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return track;
	}

	/**
	 * Updates a track in the track table with the specified parameters
	 * 
	 * @param aTrack
	 * @return if the update was successful
	 */
	public boolean updateTrack(Track aTrack) {
		String preparedQuery = "UPDATE track SET albumId = ?, trackTitle = ?, artist = ?, songWriter = ?, "
				+ "playLength = ?, selectionNum = ?, musicCategory = ?, albumCover = ?, costPrice = ?, "
				+ "listPrice = ?, salePrice = ?, dateEntered = ?, typeOfSale = ?, removalStatus = ? "
				+ "WHERE inventoryId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aTrack.getAlbumId());
				preparedStatement.setString(2, aTrack.getTrackTitle());
				preparedStatement.setString(3, aTrack.getArtist());
				preparedStatement.setString(4, aTrack.getSongWriter());
				preparedStatement.setString(5, aTrack.getPlayLength());
				preparedStatement.setInt(6, aTrack.getSelectionNum());
				preparedStatement.setString(7, aTrack.getMusicCategory());
				preparedStatement.setString(8, aTrack.getAlbumCover());
				preparedStatement.setDouble(9, aTrack.getCostPrice());
				preparedStatement.setDouble(10, aTrack.getListPrice());
				preparedStatement.setDouble(11, aTrack.getSalePrice());
				preparedStatement.setTimestamp(12, new Timestamp(aTrack
						.getDateEntered().getTime()));
				preparedStatement.setInt(13, aTrack.getTypeOfSale());
				preparedStatement.setBoolean(14, aTrack.isRemovalStatus());
				preparedStatement.setInt(15, aTrack.getInventoryId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating a track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Updates a tracks's removal status using the specified id.
	 * 
	 * @param inventoryId
	 *            the track Id
	 * @param removalStatus
	 *            the removal status
	 * @return if the update was successful
	 */
	public boolean updateTrackRemovalStatusById(int inventoryId,
			boolean removalStatus) {
		String preparedQuery = "UPDATE track SET removalStatus = ? WHERE inventoryId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setBoolean(1, removalStatus);
				preparedStatement.setInt(2, inventoryId);
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts a track in the track table with the specified parameters
	 * 
	 * @param aTrack
	 * @return if the insert was successful
	 */
	public boolean insertTrack(Track aTrack) {
		String preparedQuery = "INSERT INTO track VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		boolean successfulInsert = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, aTrack.getAlbumId());
				preparedStatement.setString(2, aTrack.getTrackTitle());
				preparedStatement.setString(3, aTrack.getArtist());
				preparedStatement.setString(4, aTrack.getSongWriter());
				preparedStatement.setString(5, aTrack.getPlayLength());
				preparedStatement.setInt(6, aTrack.getSelectionNum());
				preparedStatement.setString(7, aTrack.getMusicCategory());
				preparedStatement.setString(8, aTrack.getAlbumCover());
				preparedStatement.setDouble(9, aTrack.getCostPrice());
				preparedStatement.setDouble(10, aTrack.getListPrice());
				preparedStatement.setDouble(11, aTrack.getSalePrice());
				preparedStatement.setTimestamp(12, new Timestamp(aTrack
						.getDateEntered().getTime()));
				preparedStatement.setInt(13, aTrack.getTypeOfSale());
				preparedStatement.setBoolean(14, aTrack.isRemovalStatus());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					aTrack.setInventoryId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting a track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulInsert;
	}

	/**
	 * Deletes an entry in the track table
	 * 
	 * @param aTrack
	 * @return if the operation was successful
	 */
	public boolean removeTrack(Track aTrack) {
		String preparedQuery = "DELETE FROM track WHERE inventoryId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aTrack.getInventoryId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error deleting track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulRemove;
	}

	// Reporting SELECT statements

	/**
	 * Returns the amount of total sales of all purchased entire inventory
	 * 
	 * @return Amount of total sales of purchased entire inventory
	 */
	public double getTotalSales() {
		String preparedQuery = "SELECT details.salePrice FROM details WHERE removalStatus = 0";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns the amount of total sales of all purchased entire inventory
	 * 
	 * @return Amount of total sales of purchased entire inventory
	 */
	public double getTotalSales(String startDate, String endDate) {
		String preparedQuery = "SELECT details.salePrice FROM details INNER JOIN invoice ON details.saleId = invoice.saleId WHERE details.removalStatus = 0 AND (CAST(invoice.saleDate AS DATE) >= ? AND CAST(invoice.saleDate AS DATE) <= ?)";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, startDate);
				preparedStatement.setString(2, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns the amount of total cost of all purchased tracks
	 * 
	 * @return Amount of total cost of all purchased tracks
	 */
	public double getTotalCostTracks() {
		String preparedQuery = "SELECT SUM(track.costPrice) FROM track INNER JOIN details ON track.inventoryId = details.inventoryId WHERE details.removalStatus = 0";
		double totalCost = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalCost += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalCost;
	}

	/**
	 * Returns the amount of total cost of all purchased albums
	 * 
	 * @return Amount of total cost of all purchased albums
	 */
	public double getTotalCostAlbums() {
		String preparedQuery = "SELECT SUM(album.costPrice) FROM album INNER JOIN details ON album.albumId = details.albumId WHERE details.removalStatus = 0";
		double totalCost = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalCost += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalCost;
	}

	/**
	 * Returns an array list of reporting details for total sales
	 * 
	 * @param searchValue
	 *            Search value
	 * @return Arraylist containing all reporting information for Total Sales
	 */
	public ArrayList<DetailedSalesReport> getTotalSalesDetailed(
			String searchValue) {
		String preparedQuery = "SELECT a.albumId, a.albumTitle, a.artist, t.inventoryId, t.trackTitle, t.artist, d.salePrice, i.saleDate, c.email FROM album a RIGHT JOIN details d ON a.albumId = d.albumId LEFT JOIN track t ON t.inventoryId = d.inventoryId RIGHT JOIN invoice i ON d.saleId = i.saleId INNER JOIN client c ON i.clientId = c.clientId WHERE d.removalStatus = 0 AND (a.albumTitle LIKE ? OR t.trackTitle LIKE ?) ORDER BY i.saleDate DESC, ISNULL(d.albumId), d.albumId ASC, ISNULL(d.inventoryId), d.inventoryId ASC";
		ArrayList<DetailedSalesReport> reportInfo = new ArrayList<DetailedSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + searchValue + "%");
				preparedStatement.setString(2, "%" + searchValue + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo.add(new DetailedSalesReport(resultSet
								.getInt(1), resultSet.getString(2), resultSet
								.getString(3), resultSet.getInt(4), resultSet
								.getString(5), resultSet.getString(6),
								resultSet.getDouble(7), resultSet
										.getTimestamp(8), resultSet
										.getString(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return reportInfo;
	}

	/**
	 * Returns an array list of reporting details for total sales
	 * 
	 * @param startDate
	 *            Start date of purchases
	 * @param endDate
	 *            End date of purchases
	 * @param searchValue
	 *            Search value
	 * @return Arraylist containing all reporting information for Total Sales
	 */
	public ArrayList<DetailedSalesReport> getTotalSalesDetailed(
			String startDate, String endDate, String searchValue) {
		String preparedQuery = "SELECT a.albumId, a.albumTitle, a.artist, t.inventoryId, t.trackTitle, t.artist, d.salePrice, i.saleDate, c.email FROM album a RIGHT JOIN details d ON a.albumId = d.albumId LEFT JOIN track t ON t.inventoryId = d.inventoryId RIGHT JOIN invoice i ON d.saleId = i.saleId INNER JOIN client c ON c.clientId = i.clientId WHERE (CAST(i.saleDate AS DATE) >= ? AND CAST(i.saleDate AS DATE) <= ?) AND d.removalStatus = 0 AND (a.albumTitle LIKE ? OR t.trackTitle LIKE ?) ORDER BY i.saleDate DESC, ISNULL(d.albumId), d.albumId ASC, ISNULL(d.inventoryId), d.inventoryId ASC";

		ArrayList<DetailedSalesReport> reportInfo = new ArrayList<DetailedSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, startDate);
				preparedStatement.setString(2, endDate);
				preparedStatement.setString(3, "%" + searchValue + "%");
				preparedStatement.setString(4, "%" + searchValue + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo.add(new DetailedSalesReport(resultSet
								.getInt(1), resultSet.getString(2), resultSet
								.getString(3), resultSet.getInt(4), resultSet
								.getString(5), resultSet.getString(6),
								resultSet.getDouble(7), resultSet
										.getTimestamp(8), resultSet
										.getString(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return reportInfo;
	}

	/**
	 * Returns the amount of total sales by client
	 * 
	 * @param clientEmail
	 *            Email of the client
	 * @return Amount of total sales by client
	 */
	public double getTotalSalesByClient(String clientEmail) {
		String preparedQuery = "SELECT SUM(details.salePrice) FROM client "
				+ "INNER JOIN invoice ON client.clientID = invoice.clientID INNER JOIN details ON details.saleId = invoice.saleId "
				+ "WHERE client.email LIKE ? and details.removalStatus = 0";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + clientEmail + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total sales by client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns the amount of total sales by client
	 * 
	 * @param clientEmail
	 *            Email of the client
	 * @param startDate
	 *            Start date
	 * @param endDate
	 *            End date
	 * @return Amount of total sales by client
	 */
	public double getTotalSalesByClient(String clientEmail, String startDate,
			String endDate) {
		String preparedQuery = "SELECT SUM(details.salePrice) FROM client "
				+ "INNER JOIN invoice ON client.clientID = invoice.clientID INNER JOIN details ON details.saleId = invoice.saleId "
				+ "WHERE client.email LIKE ? AND details.removalStatus = 0 AND (CAST(invoice.saleDate AS DATE) >= ? AND CAST(invoice.saleDate AS DATE) <= ?)";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + clientEmail + "%");
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total sales by client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns an array list of String arrays that contain reporting info for a
	 * client.
	 * 
	 * @param clientEmail
	 *            Email of the client
	 * @param startDate
	 *            Start date of purchases
	 * @param endDate
	 *            End date of purchases
	 * @return ArrayList of String arrays of client reporting info
	 */
	public ArrayList<DetailedSalesReport> getTotalSalesByClientDetailed(
			String clientEmail) {
		String preparedQuery = "SELECT a.albumId, a.albumTitle, a.artist, "
				+ "t.inventoryId, t.trackTitle, t.artist, "
				+ "d.salePrice, i.saleDate, c.email "
				+ "FROM album a RIGHT JOIN details d ON a.albumId = d.albumId "
				+ "LEFT JOIN track t ON t.inventoryId = d.inventoryId "
				+ "RIGHT JOIN invoice i ON d.saleId = i.saleId INNER JOIN client c ON i.clientId = c.clientId "
				+ "WHERE c.email LIKE ? AND d.removalStatus = 0 "
				+ "ORDER BY i.saleDate DESC, ISNULL(d.albumId), d.albumId ASC, ISNULL(d.inventoryId), d.inventoryId ASC";

		ArrayList<DetailedSalesReport> reportInfo = new ArrayList<DetailedSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + clientEmail + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo.add(new DetailedSalesReport(resultSet
								.getInt(1), resultSet.getString(2), resultSet
								.getString(3), resultSet.getInt(4), resultSet
								.getString(5), resultSet.getString(6),
								resultSet.getDouble(7), resultSet
										.getTimestamp(8), resultSet
										.getString(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total sales by client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return reportInfo;
	}

	/**
	 * Returns an array list of String arrays that contain reporting info for a
	 * client.
	 * 
	 * @param clientId
	 *            Id of the client
	 * @param startDate
	 *            Start date of purchases
	 * @param endDate
	 *            End date of purchases
	 * @return ArrayList of String arrays of client reporting info
	 */
	public ArrayList<DetailedSalesReport> getTotalSalesByClientDetailed(
			String clientEmail, String startDate, String endDate) {
		String preparedQuery = "SELECT a.albumId, a.albumTitle, a.artist, "
				+ "t.inventoryId, t.trackTitle, t.artist, "
				+ "d.salePrice, i.saleDate, c.email "
				+ "FROM album a RIGHT JOIN details d ON a.albumId = d.albumId "
				+ "LEFT JOIN track t ON t.inventoryId = d.inventoryId "
				+ "RIGHT JOIN invoice i ON d.saleId = i.saleId INNER JOIN client c ON i.clientId = c.clientId "
				+ "WHERE c.email LIKE ? AND d.removalStatus = 0 AND (CAST(i.saleDate AS DATE) >= ? AND CAST(i.saleDate AS DATE) <= ?) "
				+ "ORDER BY i.saleDate DESC, ISNULL(d.albumId), d.albumId ASC, ISNULL(d.inventoryId), d.inventoryId ASC";

		ArrayList<DetailedSalesReport> reportInfo = new ArrayList<DetailedSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + clientEmail + "%");
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo.add(new DetailedSalesReport(resultSet
								.getInt(1), resultSet.getString(2), resultSet
								.getString(3), resultSet.getInt(4), resultSet
								.getString(5), resultSet.getString(6),
								resultSet.getDouble(7), resultSet
										.getTimestamp(8), resultSet
										.getString(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total sales by client", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return reportInfo;
	}

	/**
	 * Returns the amount of total sales by artist
	 * 
	 * @param artistName
	 *            Name of artist
	 * @param startDate
	 *            Starting date
	 * @param endDate
	 *            Ending date
	 * @return Amount of total sales by artist
	 */
	public double getTotalSalesByArtist(String artistName) {
		String preparedQuery = "SELECT SUM(d.salePrice) FROM track t "
				+ "RIGHT JOIN details d ON t.inventoryID = d.inventoryID "
				+ "LEFT JOIN album a ON a.albumId = d.albumId "
				+ "WHERE (a.artist LIKE ? OR t.artist LIKE ?) AND d.removalStatus = 0";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + artistName + "%");
				preparedStatement.setString(2, "%" + artistName + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total sales by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns the amount of total sales by artist
	 * 
	 * @param artistName
	 *            Name of artist
	 * @param startDate
	 *            Starting date
	 * @param endDate
	 *            Ending date
	 * @return Amount of total sales by artist
	 */
	public double getTotalSalesByArtist(String artistName, String startDate,
			String endDate) {
		String preparedQuery = "SELECT SUM(d.salePrice) FROM track t "
				+ "RIGHT JOIN details d ON t.inventoryID = d.inventoryID "
				+ "INNER JOIN invoice i ON i.saleId = d.saleId "
				+ "LEFT JOIN album a ON a.albumId = d.albumId "
				+ "WHERE (a.artist LIKE ? OR t.artist LIKE ?) AND (CAST(i.saleDate AS DATE) >= ? AND CAST(i.saleDate AS DATE) <= ?) AND d.removalStatus = 0";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + artistName + "%");
				preparedStatement.setString(2, "%" + artistName + "%");
				preparedStatement.setString(3, startDate);
				preparedStatement.setString(4, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total sales by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns an array list of String arrays that contain reporting info for an
	 * artist.
	 * 
	 * @param artistName
	 *            Name of artist
	 * @param startDate
	 *            Start date of purchases
	 * @param endDate
	 *            End date of purchases
	 * @return ArrayList of String arrays of artist reporting info
	 */
	public ArrayList<DetailedSalesReport> getTotalSalesByArtistDetailed(
			String artistName) {
		String preparedQuery = "SELECT a.albumId, a.albumTitle, a.artist, "
				+ "t.inventoryId, t.trackTitle, t.artist, "
				+ "d.salePrice, i.saleDate, c.email "
				+ "FROM album a RIGHT JOIN details d ON a.albumId = d.albumId "
				+ "LEFT JOIN track t ON t.inventoryId = d.inventoryId "
				+ "RIGHT JOIN invoice i ON d.saleId = i.saleId "
				+ "INNER JOIN client c ON c.clientId = i.clientId "
				+ "WHERE (t.artist LIKE ? OR a.artist LIKE ?) AND d.removalStatus = 0 "
				+ "ORDER BY i.saleDate DESC, ISNULL(d.albumId), d.albumId ASC, ISNULL(d.inventoryId), d.inventoryId ASC";

		ArrayList<DetailedSalesReport> reportInfo = new ArrayList<DetailedSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + artistName + "%");
				preparedStatement.setString(2, "%" + artistName + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo.add(new DetailedSalesReport(resultSet
								.getInt(1), resultSet.getString(2), resultSet
								.getString(3), resultSet.getInt(4), resultSet
								.getString(5), resultSet.getString(6),
								resultSet.getDouble(7), resultSet
										.getTimestamp(8), resultSet
										.getString(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total sales by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return reportInfo;
	}

	/**
	 * Returns an array list of String arrays that contain reporting info for an
	 * artist.
	 * 
	 * @param artistName
	 *            Name of artist
	 * @param startDate
	 *            Start date of purchases
	 * @param endDate
	 *            End date of purchases
	 * @return ArrayList of String arrays of artist reporting info
	 */
	public ArrayList<DetailedSalesReport> getTotalSalesByArtistDetailed(
			String artistName, String startDate, String endDate) {
		String preparedQuery = "SELECT a.albumId, a.albumTitle, a.artist, "
				+ "t.inventoryId, t.trackTitle, t.artist, "
				+ "d.salePrice, i.saleDate, c.email "
				+ "FROM album a RIGHT JOIN details d ON a.albumId = d.albumId "
				+ "LEFT JOIN track t ON t.inventoryId = d.inventoryId "
				+ "RIGHT JOIN invoice i ON d.saleId = i.saleId "
				+ "INNER JOIN client c ON c.clientId = i.clientId "
				+ "WHERE d.removalStatus = 0 AND (CAST(i.saleDate AS DATE) >= ? AND CAST(i.saleDate AS DATE) <= ?) AND (t.artist LIKE ? OR a.artist LIKE ?) "
				+ "ORDER BY i.saleDate DESC, ISNULL(d.albumId), d.albumId ASC, ISNULL(d.inventoryId), d.inventoryId ASC";

		ArrayList<DetailedSalesReport> reportInfo = new ArrayList<DetailedSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, startDate);
				preparedStatement.setString(2, endDate);
				preparedStatement.setString(3, "%" + artistName + "%");
				preparedStatement.setString(4, "%" + artistName + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo.add(new DetailedSalesReport(resultSet
								.getInt(1), resultSet.getString(2), resultSet
								.getString(3), resultSet.getInt(4), resultSet
								.getString(5), resultSet.getString(6),
								resultSet.getDouble(7), resultSet
										.getTimestamp(8), resultSet
										.getString(9)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total sales by artist", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return reportInfo;
	}

	/**
	 * Returns the amount of total sales by track
	 * 
	 * @param trackTitle
	 *            Title of track
	 * @return Amount of total sales
	 */
	public double getTotalSalesByTrack(String trackTitle) {
		String preparedQuery = "SELECT SUM(details.salePrice) FROM track "
				+ "INNER JOIN details ON track.inventoryID = details.inventoryID "
				+ "WHERE track.trackTitle LIKE ? AND details.removalStatus = 0";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + trackTitle + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales by track",
						e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns the amount of total sales by track
	 * 
	 * @param trackTitle
	 *            Title of track
	 * @param startDate
	 *            Starting date of report
	 * @param endDate
	 *            Ending date of report
	 * @return Amount of total sales
	 */
	public double getTotalSalesByTrack(String trackTitle, String startDate,
			String endDate) {
		String preparedQuery = "SELECT SUM(details.salePrice) FROM track "
				+ "INNER JOIN details ON track.inventoryID = details.inventoryID "
				+ "INNER JOIN invoice ON details.saleId = invoice.saleId "
				+ "WHERE track.trackTitle LIKE ? AND details.removalStatus = 0 AND (CAST(invoice.saleDate AS DATE) >= ? AND CAST(invoice.saleDate AS DATE) <= ?)";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + trackTitle + "%");
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales by track",
						e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns the total amount of download of a track
	 * 
	 * @param trackTitle
	 *            Title of the track
	 * @return Number of total downloads of a track
	 */
	public int getTotalDownloadsByTrack(String trackTitle) {
		String preparedQuery = "SELECT COUNT(track.inventoryID) FROM track "
				+ "INNER JOIN details ON track.inventoryID = details.inventoryID "
				+ "WHERE track.trackTitle LIKE ? AND details.removalStatus = 0";
		int totalDownloads = 0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + trackTitle + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalDownloads += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total downloads by track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalDownloads;
	}

	/**
	 * Returns the total amount of download of a track
	 * 
	 * @param trackTitle
	 *            Title of the track
	 * @param startDate
	 *            Starting date of report
	 * @param endDate
	 *            Ending date of report
	 * @return Number of total downloads of a track
	 */
	public int getTotalDownloadsByTrack(String trackTitle, String startDate,
			String endDate) {
		String preparedQuery = "SELECT COUNT(track.inventoryID) FROM track "
				+ "INNER JOIN details ON track.inventoryID = details.inventoryID "
				+ "INNER JOIN invoice ON details.saleId = invoice.saleId "
				+ "WHERE track.trackTitle LIKE ? AND details.removalStatus = 0 AND "
				+ "(CAST(invoice.saleDate AS DATE) >= ? AND CAST(invoice.saleDate AS DATE) <= ?)";
		int totalDownloads = 0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + trackTitle + "%");
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalDownloads += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total downloads by track", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalDownloads;
	}

	/**
	 * Returns an array list of String arrays that contain reporting info for a
	 * track
	 * 
	 * @param trackTitle
	 *            Title of track
	 * @return ArrayList of String arrays of track reporting info
	 */
	public ArrayList<TrackAlbumSalesReport> getTotalSalesByTrackDetailed(
			String trackTitle) {
		String preparedQuery = "SELECT track.inventoryId, invoice.saleDate, details.salePrice, "
				+ "track.trackTitle, track.artist, client.email "
				+ "FROM details INNER JOIN track ON track.inventoryId = details.inventoryId "
				+ "INNER JOIN invoice ON details.saleId = invoice.saleId "
				+ "INNER JOIN client ON client.clientId = invoice.clientId "
				+ "WHERE track.trackTitle LIKE ? AND details.removalStatus = 0 "
				+ "ORDER BY invoice.saleDate DESC";

		ArrayList<TrackAlbumSalesReport> reportInfo = new ArrayList<TrackAlbumSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + trackTitle + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo
								.add(new TrackAlbumSalesReport(resultSet
										.getInt(1), resultSet.getTimestamp(2),
										resultSet.getDouble(3), resultSet
												.getString(4), resultSet
												.getString(5), resultSet
												.getString(6)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales by track",
						e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return reportInfo;
	}

	/**
	 * Returns an array list of String arrays that contain reporting info for a
	 * track
	 * 
	 * @param trackTitle
	 *            Title of track
	 * @param startDate
	 *            Start date of report
	 * @param endDate
	 *            End date of report
	 * @return ArrayList of String arrays of track reporting info
	 */
	public ArrayList<TrackAlbumSalesReport> getTotalSalesByTrackDetailed(
			String trackTitle, String startDate, String endDate) {
		String preparedQuery = "SELECT invoice.saleId, invoice.saleDate, details.salePrice, "
				+ "track.trackTitle, track.artist, client.email "
				+ "FROM details INNER JOIN track ON track.inventoryId = details.inventoryId "
				+ "INNER JOIN invoice ON details.saleId = invoice.saleId "
				+ "INNER JOIN client ON client.clientId = invoice.clientId "
				+ "WHERE track.trackTitle LIKE ? AND details.removalStatus = 0 AND "
				+ "(CAST(invoice.saleDate AS DATE) >= ? AND CAST(invoice.saleDate AS DATE) <= ?) "
				+ "ORDER BY invoice.saleDate DESC";

		ArrayList<TrackAlbumSalesReport> reportInfo = new ArrayList<TrackAlbumSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + trackTitle + "%");
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo
								.add(new TrackAlbumSalesReport(resultSet
										.getInt(1), resultSet.getTimestamp(2),
										resultSet.getDouble(3), resultSet
												.getString(4), resultSet
												.getString(5), resultSet
												.getString(6)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales by track",
						e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return reportInfo;
	}

	/**
	 * Returns the amount of total sales by album
	 * 
	 * @param albumTitle
	 *            Title of album
	 * @return Amount of total sales by album
	 */
	public double getTotalSalesByAlbum(String albumTitle) {
		String preparedQuery = "SELECT SUM(details.salePrice) FROM album "
				+ "INNER JOIN details ON album.albumID = details.albumID "
				+ "WHERE album.albumTitle LIKE ? AND details.removalStatus = 0";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + albumTitle + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales by album",
						e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns the amount of total sales by album
	 * 
	 * @param albumTitle
	 *            Title of album
	 * @return Amount of total sales by album
	 */
	public double getTotalSalesByAlbum(String albumTitle, String startDate,
			String endDate) {
		String preparedQuery = "SELECT SUM(details.salePrice) FROM album "
				+ "INNER JOIN details ON album.albumID = details.albumID "
				+ "INNER JOIN invoice ON invoice.saleId = details.saleId "
				+ "WHERE album.albumTitle LIKE ? AND details.removalStatus = 0 AND "
				+ "(CAST(invoice.saleDate AS DATE) >= ? AND CAST(invoice.saleDate AS DATE) <= ?)";
		double totalSales = 0.0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + albumTitle + "%");
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalSales += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales by album",
						e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalSales;
	}

	/**
	 * Returns the total amount of download of an album
	 * 
	 * @param albumTitle
	 *            Title of the album
	 * @return Number of total downloads of an album
	 */
	public int getTotalDownloadsByAlbum(String albumTitle) {
		String preparedQuery = "SELECT COUNT(album.albumId) FROM album "
				+ "INNER JOIN details ON album.albumId = details.albumID "
				+ "WHERE album.albumTitle LIKE ? AND details.removalStatus = 0";
		int totalDownloads = 0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + albumTitle + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalDownloads += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total downloads by album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalDownloads;
	}

	/**
	 * Returns the total amount of download of an album
	 * 
	 * @param albumTitle
	 *            Title of the album
	 * @return Number of total downloads of an album
	 */
	public int getTotalDownloadsByAlbum(String albumTitle, String startDate,
			String endDate) {
		String preparedQuery = "SELECT COUNT(album.albumId) FROM album "
				+ "INNER JOIN details ON album.albumId = details.albumID "
				+ "INNER JOIN invoice on invoice.saleId = details.saleId "
				+ "WHERE album.albumTitle LIKE ? AND details.removalStatus = 0 AND "
				+ "(CAST(invoice.saleDate AS DATE) >= ? AND CAST(invoice.saleDate AS DATE) <= ?)";
		int totalDownloads = 0;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + albumTitle + "%");
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						totalDownloads += resultSet.getDouble(1);
					} while (resultSet.next());
				}
			}

			catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting total downloads by album", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return totalDownloads;
	}

	/**
	 * Returns an array list of String arrays that contain reporting info for an
	 * album
	 * 
	 * @param albumTitle
	 *            Title of album
	 * @param startDate
	 *            Start date of purchases
	 * @param endDate
	 *            End date of purchases
	 * @return ArrayList of String arrays of album reporting information
	 */
	public ArrayList<TrackAlbumSalesReport> getTotalSalesByAlbumDetailed(
			String albumTitle) {
		String preparedQuery = "SELECT invoice.saleId, invoice.saleDate, details.salePrice, "
				+ "album.albumTitle, album.artist, client.email "
				+ "FROM details INNER JOIN album ON album.albumID = details.albumID "
				+ "INNER JOIN invoice ON details.saleID = invoice.saleID "
				+ "INNER JOIN client ON invoice.clientId = client.clientId "
				+ "WHERE album.albumTitle LIKE ? AND details.removalStatus = 0 "
				+ "ORDER BY invoice.saleDate DESC";

		ArrayList<TrackAlbumSalesReport> reportInfo = new ArrayList<TrackAlbumSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + albumTitle + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo
								.add(new TrackAlbumSalesReport(resultSet
										.getInt(1), resultSet.getTimestamp(2),
										resultSet.getDouble(3), resultSet
												.getString(4), resultSet
												.getString(5), resultSet
												.getString(6)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales by album",
						e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return reportInfo;
	}

	/**
	 * Returns an array list of String arrays that contain reporting info for an
	 * album
	 * 
	 * @param albumTitle
	 *            Title of album
	 * @param startDate
	 *            Start date of purchases
	 * @param endDate
	 *            End date of purchases
	 * @return ArrayList of String arrays of album reporting information
	 */
	public ArrayList<TrackAlbumSalesReport> getTotalSalesByAlbumDetailed(
			String albumTitle, String startDate, String endDate) {
		String preparedQuery = "SELECT invoice.saleId, invoice.saleDate, details.salePrice, "
				+ "album.albumTitle, album.artist, client.email "
				+ "FROM details INNER JOIN album ON album.albumID = details.albumID "
				+ "INNER JOIN invoice ON details.saleID = invoice.saleID "
				+ "INNER JOIN client on client.clientId = invoice.clientId "
				+ "WHERE album.albumTitle LIKE ? AND details.removalStatus = 0 AND"
				+ "(CAST(invoice.saleDate AS DATE) >= ? AND CAST(invoice.saleDate AS DATE) <= ?) "
				+ "ORDER BY invoice.saleDate DESC";

		ArrayList<TrackAlbumSalesReport> reportInfo = new ArrayList<TrackAlbumSalesReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + albumTitle + "%");
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						reportInfo
								.add(new TrackAlbumSalesReport(resultSet
										.getInt(1), resultSet.getTimestamp(2),
										resultSet.getDouble(3), resultSet
												.getString(4), resultSet
												.getString(5), resultSet
												.getString(6)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting total sales by album",
						e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return reportInfo;
	}

	/**
	 * Returns an array list of String arrays that contain info on the top
	 * sellers
	 * 
	 * @return ArrayList of String arrays of top sellers information
	 */
	public ArrayList<TopSellersReport> getTopSellers(String searchValue) {
		String preparedQuery = "SELECT a.albumId, a.albumTitle, a.artist, "
				+ "t.inventoryId, t.trackTitle, t.artist, "
				+ "SUM(d.salePrice)"
				+ "FROM album a RIGHT JOIN details d ON a.albumId = d.albumId "
				+ "LEFT JOIN track t ON t.inventoryId = d.inventoryId "
				+ "RIGHT JOIN invoice i ON d.saleId = i.saleId "
				+ "INNER JOIN client c ON c.clientId = i.clientId "
				+ "WHERE d.salePrice > 0 AND d.removalStatus = 0 AND (t.trackTitle LIKE ? OR a.albumTitle LIKE ?) "
				+ "GROUP BY d.albumId, d.inventoryId "
				+ "ORDER BY SUM(d.salePrice) DESC, ISNULL(d.albumId), d.albumId ASC, ISNULL(d.inventoryId), d.inventoryId ASC";

		ArrayList<TopSellersReport> topSellers = new ArrayList<TopSellersReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + searchValue + "%");
				preparedStatement.setString(2, "%" + searchValue + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						topSellers.add(new TopSellersReport(
								resultSet.getInt(1), resultSet.getString(2),
								resultSet.getString(3), resultSet.getInt(4),
								resultSet.getString(5), resultSet.getString(6),
								resultSet.getDouble(7)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting top sellers", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return topSellers;
	}

	/**
	 * Returns an array list of String arrays that contain info on the top
	 * sellers between two dates
	 * 
	 * @param searchValue
	 *            Value that is being searched
	 * @param startDate
	 *            Start Date
	 * @param endDate
	 *            End date
	 * 
	 * @return ArrayList of String arrays of top sellers information
	 */
	public ArrayList<TopSellersReport> getTopSellers(String searchValue,
			String startDate, String endDate) {
		String preparedQuery = "SELECT a.albumId, a.albumTitle, a.artist, "
				+ "t.inventoryId, t.trackTitle, t.artist, "
				+ "SUM(d.salePrice)"
				+ "FROM album a RIGHT JOIN details d ON a.albumId = d.albumId "
				+ "LEFT JOIN track t ON t.inventoryId = d.inventoryId "
				+ "RIGHT JOIN invoice i ON d.saleId = i.saleId "
				+ "INNER JOIN client c ON c.clientId = i.clientId "
				+ "WHERE d.salePrice > 0 AND d.removalStatus = 0 AND (t.trackTitle LIKE ? OR a.albumTitle LIKE ?) AND (CAST(i.saleDate AS DATE) >= ? AND CAST(i.saleDate AS DATE) <= ?) "
				+ "GROUP BY d.albumId, d.inventoryId "
				+ "ORDER BY SUM(d.salePrice) DESC, ISNULL(d.albumId), d.albumId ASC, ISNULL(d.inventoryId), d.inventoryId ASC";

		ArrayList<TopSellersReport> topSellers = new ArrayList<TopSellersReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + searchValue + "%");
				preparedStatement.setString(2, "%" + searchValue + "%");
				preparedStatement.setString(3, startDate);
				preparedStatement.setString(4, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						topSellers.add(new TopSellersReport(
								resultSet.getInt(1), resultSet.getString(2),
								resultSet.getString(3), resultSet.getInt(4),
								resultSet.getString(5), resultSet.getString(6),
								resultSet.getDouble(7)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting top sellers", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return topSellers;
	}

	/**
	 * Returns an array list of String arrays that contain information on all
	 * top clients
	 * 
	 * @return ArrayList of String arrays of top clients information
	 */
	public ArrayList<ClientReport> getTopClients(String clientEmail) {
		String preparedQuery = "SELECT c.clientId, c.firstName, c.lastName, c.email, SUM(d.salePrice) "
				+ "FROM client c INNER JOIN invoice i ON i.clientId = c.clientId "
				+ "INNER JOIN details d ON i.saleId = d.saleId "
				+ "WHERE c.email LIKE ? AND i.netValue > 0 AND d.removalStatus = 0 "
				+ "GROUP BY c.clientId ORDER BY SUM(d.salePrice) DESC";
		ArrayList<ClientReport> topClients = new ArrayList<ClientReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + clientEmail + "%");
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						topClients
								.add(new ClientReport(resultSet.getInt(1),
										resultSet.getString(2), resultSet
												.getString(3), resultSet
												.getString(4), resultSet
												.getDouble(5)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting top clients", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return topClients;
	}

	/**
	 * Returns an array list of String arrays that contain information on all
	 * top clients between two dates
	 * 
	 * 
	 * 
	 * @return ArrayList of String arrays of top clients information
	 */
	public ArrayList<ClientReport> getTopClients(String clientEmail,
			String startDate, String endDate) {
		String preparedQuery = "SELECT c.clientId, c.firstName, c.lastName, c.email, SUM(d.salePrice) "
				+ "FROM client c INNER JOIN invoice i ON i.clientId = c.clientId "
				+ "INNER JOIN details d ON i.saleId = d.saleId "
				+ "WHERE c.email LIKE ? AND i.netValue > 0 AND d.removalStatus = 0 AND (CAST(i.saleDate AS DATE) >= ? AND CAST(i.saleDate AS DATE) <= ?) "
				+ "GROUP BY c.clientId ORDER BY SUM(d.salePrice) DESC";
		ArrayList<ClientReport> topClients = new ArrayList<ClientReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, "%" + clientEmail + "%");
				preparedStatement.setString(2, startDate);
				preparedStatement.setString(3, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						topClients
								.add(new ClientReport(resultSet.getInt(1),
										resultSet.getString(2), resultSet
												.getString(3), resultSet
												.getString(4), resultSet
												.getDouble(5)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting top clients", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return topClients;
	}

	/**
	 * Returns an array list of String arrays that contain information on
	 * clients that have never made a purchase
	 * 
	 * @return ArrayList of String arrays of information of clients with zero
	 *         purchases
	 */
	public ArrayList<ClientReport> getZeroClients() {
		String preparedQuery = "SELECT client.clientId, client.firstName, client.lastName, client.email, SUM(invoice.grossValue) FROM client "
				+ "LEFT OUTER JOIN invoice ON invoice.clientId = client.clientId "
				+ "WHERE invoice.clientId IS NULL "
				+ "GROUP BY client.clientId ORDER BY client.clientId";
		ArrayList<ClientReport> zeroClients = new ArrayList<ClientReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						zeroClients
								.add(new ClientReport(resultSet.getInt(1),
										resultSet.getString(2), resultSet
												.getString(3), resultSet
												.getString(4), resultSet
												.getDouble(5)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting zero clients", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return zeroClients;
	}

	/**
	 * Returns an array list of String arrays that contain information on
	 * clients that have never made a purchase between two dates
	 * 
	 * @return ArrayList of String arrays of information of clients with zero
	 *         purchases
	 */
	public ArrayList<ClientReport> getZeroClients(String startDate,
			String endDate) {
		String preparedQuery = "SELECT client.clientId, client.firstName, client.lastName, client.email, SUM(invoice.grossValue) FROM client "
				+ "LEFT OUTER JOIN invoice ON invoice.clientId = client.clientId "
				+ "WHERE invoice.clientId IS NULL OR client.clientId NOT IN "
				+ "(SELECT invoice.clientId from invoice where (CAST(invoice.saleDate AS DATE) >= ? AND CAST(invoice.saleDate AS DATE) <= ?)) "
				+ "GROUP BY client.clientId ORDER BY client.clientId";
		ArrayList<ClientReport> zeroClients = new ArrayList<ClientReport>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, startDate);
				preparedStatement.setString(2, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						zeroClients
								.add(new ClientReport(resultSet.getInt(1),
										resultSet.getString(2), resultSet
												.getString(3), resultSet
												.getString(4), resultSet
												.getDouble(5)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting zero clients", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return zeroClients;
	}

	/**
	 * Returns an array list of String arrays that contain information on tracks
	 * that have not been purchased
	 * 
	 * @return ArrayList of String arrays of information on tracks that have not
	 *         been purchased
	 */
	public ArrayList<Track> getZeroTracks() {
		String preparedQuery = "SELECT * from track WHERE track.inventoryId "
				+ "NOT IN (SELECT inventoryID FROM details INNER JOIN "
				+ "invoice ON details.saleId = invoice.saleId "
				+ "WHERE inventoryId IS NOT NULL AND details.removalStatus = 0)";

		ArrayList<Track> zeroTracks = new ArrayList<Track>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						zeroTracks
								.add(new Track(resultSet.getInt(1), resultSet
										.getInt(2), resultSet.getString(3),
										resultSet.getString(4), resultSet
												.getString(5), resultSet
												.getString(6), resultSet
												.getInt(7), resultSet
												.getString(8), resultSet
												.getString(9), resultSet
												.getDouble(10), resultSet
												.getDouble(11), resultSet
												.getDouble(12), resultSet
												.getTimestamp(13), resultSet
												.getInt(14), resultSet
												.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting zero tracks", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return zeroTracks;
	}

	/**
	 * Returns an array list of String arrays that contain information on tracks
	 * that have not been purchased
	 * 
	 * @return ArrayList of String arrays of information on tracks that have not
	 *         been purchased
	 */
	public ArrayList<Track> getZeroTracks(String startDate, String endDate) {
		String preparedQuery = "SELECT * from track WHERE track.inventoryId "
				+ "NOT IN (SELECT inventoryID FROM details INNER JOIN "
				+ "invoice ON details.saleId = invoice.saleId "
				+ "WHERE(CAST(invoice.saleDate AS DATE) >= ? "
				+ "AND CAST(invoice.saleDate AS DATE) <= ?) "
				+ "AND inventoryId IS NOT NULL AND details.removalStatus = 0)";

		ArrayList<Track> zeroTracks = new ArrayList<Track>();

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, startDate);
				preparedStatement.setString(2, endDate);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						zeroTracks
								.add(new Track(resultSet.getInt(1), resultSet
										.getInt(2), resultSet.getString(3),
										resultSet.getString(4), resultSet
												.getString(5), resultSet
												.getString(6), resultSet
												.getInt(7), resultSet
												.getString(8), resultSet
												.getString(9), resultSet
												.getDouble(10), resultSet
												.getDouble(11), resultSet
												.getDouble(12), resultSet
												.getTimestamp(13), resultSet
												.getInt(14), resultSet
												.getBoolean(15)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting zero tracks", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return zeroTracks;
	}

	/**
	 * Returns an array list of all the rssFeeds that are currently in the
	 * rssFeed table in the database.
	 * 
	 * @return the array list containing all the rssFeeds
	 */
	public ArrayList<Rss> getRssFeeds() {
		ArrayList<Rss> rssFeeds = new ArrayList<Rss>();

		String preparedQuery = "SELECT * FROM rssFeed";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						rssFeeds.add(new Rss(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getBoolean(4)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting rss feeds", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return rssFeeds;
	}

	/**
	 * Updates an rssFeed in the rssFeed table with the specified parameters
	 * 
	 * @param anRssFeed
	 * @return if the update was successful
	 */
	public boolean updateRssFeed(Rss anRssFeed) {
		String preparedQuery = "UPDATE rssFeed SET name = ?, url = ?, status = ? WHERE rssFeedId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, anRssFeed.getName());
				preparedStatement.setString(2, anRssFeed.getUrl());
				preparedStatement.setBoolean(3, anRssFeed.isStatus());
				preparedStatement.setInt(4, anRssFeed.getRssFeedId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating an rss feed", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts an rssFeed in the rssFeed table with the specified parameters
	 * 
	 * @param anRssFeed
	 * @return if the insert was successful
	 */
	public boolean insertRssFeed(Rss anRssFeed) {
		String preparedQuery = "INSERT INTO rssFeed VALUES (null,?,?,?)";
		boolean successfulInsert = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, anRssFeed.getName());
				preparedStatement.setString(2, anRssFeed.getUrl());
				preparedStatement.setBoolean(3, anRssFeed.isStatus());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					anRssFeed.setRssFeedId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting an rss feed", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulInsert;
	}

	/**
	 * Deletes an entry in the rssFeed table
	 * 
	 * @param anRssFeed
	 * @return if the operation was successful
	 */
	public boolean removeRssFeed(Rss anRssFeed) {
		String preparedQuery = "DELETE FROM rssFeed WHERE rssFeedId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, anRssFeed.getRssFeedId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing rss feed", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulRemove;
	}

	/**
	 * Gets the rss feed by id
	 * 
	 * @param id
	 * @return the rss bean
	 */
	public Rss getRssFeedById(int id) {
		Rss rss = null;

		String preparedQuery = "SELECT * FROM rssFeed WHERE rssFeedId = ?;";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, id);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					rss = new Rss(resultSet.getInt(1), resultSet.getString(2),
							resultSet.getString(3), resultSet.getBoolean(4));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting current rss", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return rss;
	}

	/**
	 * Gets the rss feed that is currently in use
	 * 
	 * @return the rss bean
	 */
	public Rss getCurrentRssFeed() {
		Rss rss = null;

		String preparedQuery = "SELECT * FROM rssFeed WHERE status = 1;";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					rss = new Rss(resultSet.getInt(1), resultSet.getString(2),
							resultSet.getString(3), resultSet.getBoolean(4));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting current rss", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return rss;
	}

	/**
	 * Returns an array list of all the rssFeeds that are currently not in use.
	 * 
	 * @return the array list containing the rssFeeds
	 */
	public ArrayList<Rss> getOtherRssFeeds() {
		ArrayList<Rss> rssFeeds = new ArrayList<Rss>();

		String preparedQuery = "SELECT * FROM rssFeed WHERE status = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						rssFeeds.add(new Rss(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getBoolean(4)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting rss feeds", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return rssFeeds;
	}

	/**
	 * Returns an array list of all the surveys that are currently in the survey
	 * table in the database.
	 * 
	 * @return the array list containing all the surveys
	 */
	public ArrayList<Survey> getSurveys() {
		ArrayList<Survey> surveys = new ArrayList<Survey>();

		String preparedQuery = "SELECT * FROM survey";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						surveys.add(new Survey(resultSet.getInt(1), resultSet
								.getString(2), resultSet.getString(3),
								resultSet.getBoolean(4)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting surveys", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return surveys;
	}

	/**
	 * Returns the survey corresponding to that id.
	 * 
	 * @param surveyId
	 *            the survey id
	 * @return the survey
	 */
	public Survey getSurveyById(int surveyId) {
		Survey survey = null;

		String preparedQuery = "SELECT * FROM survey WHERE surveyId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, surveyId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					survey = new Survey(resultSet.getInt(1),
							resultSet.getString(2), resultSet.getString(3),
							resultSet.getBoolean(4));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting surveys", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return survey;
	}

	/**
	 * Updates a survey in the survey table with the specified parameters
	 * 
	 * @param aSurvey
	 * @return if the update was successful
	 */
	public boolean updateSurvey(Survey aSurvey) {
		String preparedQuery = "UPDATE survey SET surveyName = ?, surveyQuestion = ?, status = ? WHERE surveyId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, aSurvey.getSurveyName());
				preparedStatement.setString(2, aSurvey.getSurveyQuestion());
				preparedStatement.setBoolean(3, aSurvey.getStatus());
				preparedStatement.setInt(4, aSurvey.getSurveyId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating a survey", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Updates a survey in the survey table with the specified parameters
	 * 
	 * @param aSurvey
	 * @return if the update was successful
	 */
	public boolean updateSurveyStatusUsingId(int surveyId, boolean status) {
		String preparedQuery = "UPDATE survey SET status = ? WHERE surveyId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setBoolean(1, status);
				preparedStatement.setInt(2, surveyId);
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating a survey", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts a survey in the survey table with the specified parameters
	 * 
	 * @param aSurvey
	 * @return if the insert was successful
	 */
	public boolean insertSurvey(Survey aSurvey) {
		String preparedQuery = "INSERT INTO survey VALUES (null,?,?,?)";
		boolean successfulInsert = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, aSurvey.getSurveyName());
				preparedStatement.setString(2, aSurvey.getSurveyQuestion());
				preparedStatement.setBoolean(3, aSurvey.getStatus());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					aSurvey.setSurveyId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting a survey", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulInsert;
	}

	/**
	 * Deletes an entry in the survey table
	 * 
	 * @param aSurvey
	 * @return if the operation was successful
	 */
	public boolean removeSurvey(Survey aSurvey) {
		String preparedQuery = "DELETE FROM survey WHERE surveyId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aSurvey.getSurveyId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing survey", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulRemove;
	}

	/**
	 * Returns an array list of all the survey answers that are currently in the
	 * surveyAnswer table in the database.
	 * 
	 * @return the array list containing all the survey answers
	 */
	public ArrayList<SurveyAnswer> getSurveyAnswers() {
		ArrayList<SurveyAnswer> surveyAnswers = new ArrayList<SurveyAnswer>();

		String preparedQuery = "SELECT * FROM surveyAnswer";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						surveyAnswers.add(new SurveyAnswer(resultSet.getInt(1),
								resultSet.getInt(2), resultSet.getString(3),
								resultSet.getInt(4)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting survey answers", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return surveyAnswers;
	}

	/**
	 * Returns the SurveyAnswer corresponding to the given survey answer id.
	 * 
	 * @return the SurveyAnswer
	 */
	public SurveyAnswer getSurveyAnswersById(int surveyAnswerId) {
		SurveyAnswer surveyAnswer = null;

		String preparedQuery = "SELECT * FROM surveyAnswer WHERE surveyAnswerId = ? ";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, surveyAnswerId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next())
					surveyAnswer = new SurveyAnswer(resultSet.getInt(1),
							resultSet.getInt(2), resultSet.getString(3),
							resultSet.getInt(4));
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting survey answers", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return surveyAnswer;
	}

	/**
	 * Returns an array list of all the survey answers corresponding to a survey
	 * id.
	 * 
	 * @param surveyId
	 *            the id of the survey
	 * @return the array list containing all the survey answers
	 */
	public ArrayList<SurveyAnswer> getSurveyAnswersBySurveyId(int surveyId) {
		ArrayList<SurveyAnswer> surveyAnswers = new ArrayList<SurveyAnswer>();

		String preparedQuery = "SELECT * FROM surveyAnswer WHERE surveyId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, surveyId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						surveyAnswers.add(new SurveyAnswer(resultSet.getInt(1),
								resultSet.getInt(2), resultSet.getString(3),
								resultSet.getInt(4)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting survey answers", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return surveyAnswers;
	}

	/**
	 * Updates a survey answer in the surveyAnswer table with the specified
	 * parameters
	 * 
	 * @param aSurveyAnswer
	 * @return if the update was successful
	 */
	public boolean updateSurveyAnswer(SurveyAnswer aSurveyAnswer) {
		String preparedQuery = "UPDATE surveyAnswer SET surveyId = ?, answer = ?, votes = ? WHERE surveyAnswerId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aSurveyAnswer.getSurveyId());
				preparedStatement.setString(2, aSurveyAnswer.getAnswer());
				preparedStatement.setInt(3, aSurveyAnswer.getVotes());
				preparedStatement.setInt(4, aSurveyAnswer.getSurveyAnswerId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating a survey answer", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts a survey answer in the surveyAnswer table with the specified
	 * parameters
	 * 
	 * @param aSurveyAnswer
	 * @return if the insert was successful
	 */
	public boolean insertSurveyAnswer(SurveyAnswer aSurveyAnswer) {
		String preparedQuery = "INSERT INTO surveyAnswer VALUES (null,?,?,?)";
		boolean successfulInsert = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, aSurveyAnswer.getSurveyId());
				preparedStatement.setString(2, aSurveyAnswer.getAnswer());
				preparedStatement.setInt(3, aSurveyAnswer.getVotes());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					aSurveyAnswer.setSurveyAnswerId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting a survey answer", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulInsert;
	}

	/**
	 * Deletes an entry in the surveyAnswer table
	 * 
	 * @param aSurveyAnswer
	 * @return if the operation was successful
	 */
	public boolean removeSurveyAnswer(SurveyAnswer aSurveyAnswer) {
		String preparedQuery = "DELETE FROM surveyAnswer WHERE surveyAnswerId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aSurveyAnswer.getSurveyAnswerId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing survey answer", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulRemove;
	}

	/**
	 * Returns an array list of all the banner ads that are currently in the
	 * bannerAd table in the database.
	 * 
	 * @return the array list containing all the banner ads
	 */
	public ArrayList<BannerAd> getBannerAds() {
		ArrayList<BannerAd> bannerAds = new ArrayList<BannerAd>();

		String preparedQuery = "SELECT * FROM bannerAd";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						bannerAds
								.add(new BannerAd(resultSet.getInt(1),
										resultSet.getString(2), resultSet
												.getInt(3), resultSet
												.getString(4), resultSet
												.getBoolean(5)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting banner ads", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return bannerAds;
	}

	/**
	 * Updates a banner ad in the bannerAd table with the specified parameters
	 * 
	 * @param aBannerAd
	 * @return if the update was successful
	 */
	public boolean updateBannerAd(BannerAd aBannerAd) {
		String preparedQuery = "UPDATE bannerAd SET url = ?, type = ?, imageSource = ?, status = ? WHERE bannerAdId = ?";
		boolean successfulUpdate = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setString(1, aBannerAd.getUrl());
				preparedStatement.setInt(2, aBannerAd.getType());
				preparedStatement.setString(3, aBannerAd.getImageSource());
				preparedStatement.setBoolean(4, aBannerAd.isStatus());
				preparedStatement.setInt(5, aBannerAd.getBannerId());
				preparedStatement.executeUpdate();
				successfulUpdate = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error updating a banner ad", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulUpdate;
	}

	/**
	 * Inserts a banner ad in the bannerAd table with the specified parameters
	 * 
	 * @param aBannerAd
	 * @return if the insert was successful
	 */
	public boolean insertBannerAd(BannerAd aBannerAd) {
		String preparedQuery = "INSERT INTO bannerAd VALUES (null,?,?,?,?)";
		boolean successfulInsert = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, aBannerAd.getUrl());
				preparedStatement.setInt(2, aBannerAd.getType());
				preparedStatement.setString(3, aBannerAd.getImageSource());
				preparedStatement.setBoolean(4, aBannerAd.isStatus());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())
					aBannerAd.setBannerId(rs.getInt(1));
				successfulInsert = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error inserting a banner ad", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return successfulInsert;
	}

	/**
	 * Deletes an entry in the bannerAd table
	 * 
	 * @param aBannerAd
	 * @return if the operation was successful
	 */
	public boolean removeBannerAd(BannerAd aBannerAd) {
		String preparedQuery = "DELETE FROM bannerAd WHERE bannerAdId = ?";
		boolean successfulRemove = false;

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, aBannerAd.getBannerId());
				preparedStatement.executeUpdate();
				successfulRemove = true;
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error removing banner ad", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}

		return successfulRemove;
	}

	/**
	 * Returns the bannerAd corresponding to the bannerId
	 * 
	 * @param bannerAdId
	 * @return
	 */
	public BannerAd getBannerAdById(int bannerAdId) {
		BannerAd bannerAd = null;

		String preparedQuery = "SELECT * FROM bannerAd WHERE bannerAdId = ?";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				preparedStatement.setInt(1, bannerAdId);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					bannerAd = new BannerAd(resultSet.getInt(1),
							resultSet.getString(2), resultSet.getInt(3),
							resultSet.getString(4), resultSet.getBoolean(5));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting bannerAd", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return bannerAd;
	}

	/**
	 * Returns the current bottom bannerAd
	 * 
	 * @return
	 */
	public BannerAd getCurrentBottomBannerAd() {
		BannerAd bannerAd = null;

		String preparedQuery = "SELECT * FROM bannerAd WHERE type = 1 AND status = 1";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					bannerAd = new BannerAd(resultSet.getInt(1),
							resultSet.getString(2), resultSet.getInt(3),
							resultSet.getString(4), resultSet.getBoolean(5));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting current bottom bannerAd", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return bannerAd;
	}

	/**
	 * Returns the current right bannerAd
	 * 
	 * @return
	 */
	public BannerAd getCurrentRightBannerAd() {
		BannerAd bannerAd = null;

		String preparedQuery = "SELECT * FROM bannerAd WHERE type = 2 AND status = 1";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					bannerAd = new BannerAd(resultSet.getInt(1),
							resultSet.getString(2), resultSet.getInt(3),
							resultSet.getString(4), resultSet.getBoolean(5));
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING,
						"Error getting current right bannerAd", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return bannerAd;
	}

	/**
	 * Returns an array list of all the bottom banner ads that are currently not
	 * in use.
	 * 
	 * @return the array list containing the bannerAds
	 */
	public ArrayList<BannerAd> getOtherBottomBannerAds() {
		ArrayList<BannerAd> bannerAds = new ArrayList<BannerAd>();

		String preparedQuery = "SELECT * FROM bannerAd WHERE type = 1 AND status = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						bannerAds
								.add(new BannerAd(resultSet.getInt(1),
										resultSet.getString(2), resultSet
												.getInt(3), resultSet
												.getString(4), resultSet
												.getBoolean(5)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting banner ads", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return bannerAds;
	}

	/**
	 * Returns an array list of all the right banner ads that are currently not
	 * in use.
	 * 
	 * @return the array list containing the bannerAds
	 */
	public ArrayList<BannerAd> getOtherRightBannerAds() {
		ArrayList<BannerAd> bannerAds = new ArrayList<BannerAd>();

		String preparedQuery = "SELECT * FROM bannerAd WHERE type = 2 AND status = 0";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						bannerAds
								.add(new BannerAd(resultSet.getInt(1),
										resultSet.getString(2), resultSet
												.getInt(3), resultSet
												.getString(4), resultSet
												.getBoolean(5)));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting banner ads", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return bannerAds;
	}

	/**
	 * Returns an array list of all the genres associated with items in the
	 * inventory
	 * 
	 * @return the array list containing all the genres
	 */
	public ArrayList<String> getGenres() {
		ArrayList<String> genreList = new ArrayList<String>();

		String preparedQuery = "SELECT distinct(musicCategory) from track";

		if (openConnection()) {
			try {
				preparedStatement = connection.prepareStatement(preparedQuery);
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					do {
						genreList.add(resultSet.getString(1));
					} while (resultSet.next());
				}
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Error getting banner ads", e);
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException ex) {
					logger.log(Level.WARNING, "Error closing statement", ex);
				}
			}
			closeConnection();
		}
		return genreList;
	}

	/*
	 * Note to developer. One of these methods must always be commented out
	 * depending whether the methods above need to be JUnit tested or ran on the
	 * server
	 */

	/*
	 * Opens the connection for JUnit testing
	 */
	// private boolean openConnection() {
	// boolean retVal = true;
	// try {
	// Class.forName("com.mysql.jdbc.Driver").newInstance();
	// String url = "jdbc:mysql://waldo2.dawsoncollege.qc.ca:3306/g1w13";
	// String user = "g1w13";
	// String password = "prafkaph";
	// connection = DriverManager.getConnection(url, user, password);
	// } catch (ClassNotFoundException cnfex) {
	// retVal = false;
	// logger.log(Level.WARNING, "Driver not found", cnfex);
	// } catch (SQLException sqlex) {
	// logger.log(Level.WARNING, "Error opening the database", sqlex);
	// } catch (Exception e) {
	// logger.log(Level.WARNING, "Unknown error has occured", e);
	// }
	// return retVal;
	// }

	/*
	 * Opens the connection for Tomcat Server
	 */
	private boolean openConnection() {
		boolean retVal = true;
		try {

			// obtain environment naming context
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			// look up data source
			DataSource ds = (DataSource) envCtx.lookup("jdbc/g1w13");

			// allocate and use a connection from the pool
			connection = ds.getConnection();

		} catch (Exception sqlex) {
			retVal = false;
			logger.log(Level.WARNING, "Error opening the database", sqlex);
		}

		return retVal;
	}

	/*
	 * Closes the connection
	 */
	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException sqlex) {
			logger.log(Level.WARNING, "Error closing the database", sqlex);
		}
	}
}
