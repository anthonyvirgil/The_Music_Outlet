// Venelin Koulaxazov
// 1032744
// PopulateInitialDatabase
package dbManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import beans.Album;
import beans.Track;

/**
 * Populates the initial database using a csv file containing all the albums and
 * tracks.
 * 
 * @author Venelin Koulaxazov
 * @version 1.4
 */
public class PopulateInitialDatabase {

	public static void main(String[] args) throws IOException {
		DatabaseManager dbManager = new DatabaseManager();
		String line = "";
		String[] row;
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(
					"src/initialDatabase/albums.csv"));

			while ((line = reader.readLine()) != null) {
				row = line.split(",");

				dbManager.insertAlbum(new Album(row[0], row[1], row[2], row[3],
						row[4], row[5], Integer.parseInt(row[6]), new Date(),
						Double.parseDouble(row[8]), Double.parseDouble(row[7]),
						Double.parseDouble(row[9]), Boolean
								.parseBoolean(row[10])));

			}
		} catch (IOException e) {
			e.printStackTrace();
			// log to a file for error
		} finally {
			reader.close();
		}

		try {
			reader = new BufferedReader(new FileReader(
					"src/initialDatabase/tracks.csv"));

			while ((line = reader.readLine()) != null) {
				row = line.split(",");
				dbManager.insertTrack(new Track(Integer.parseInt(row[0]),
						row[1], row[2], row[3], row[4], Integer
								.parseInt(row[5]), row[6], row[7], Double
								.parseDouble(row[9]), Double
								.parseDouble(row[8]), Double
								.parseDouble(row[10]), new Date(), Integer
								.parseInt(row[11]), Boolean
								.parseBoolean(row[12])));
			}
		} catch (IOException e) {
			e.printStackTrace();
			// log to a file for error
		} finally {
			reader.close();
		}
	}
}
