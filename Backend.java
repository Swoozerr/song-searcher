import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Backend implements BackendInterface<Song> {
    IterableMultiKeySortedCollectionInterface<Song> songDatabase;

    public Backend(IterableMultiKeySortedCollectionInterface<Song> songDatabase) {
        this.songDatabase = songDatabase;
    }

    /** reads a csv file full of songs into Song objects and inserts them into the RBT
     *
     * @param fileName - name of the file to be read
     * @throws FileNotFoundException - if fileName is not a valid file
     */
    @Override
    public void readFromFile(String fileName) throws FileNotFoundException {

        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(fileName));
            line = reader.readLine(); // skip first line of headers
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");

                String title = row[0];
                String artist = row[1];
                String genre = row[2];
                String year = row[3];
                int danceability = Integer.parseInt(row[6]);
                
                Song song = new Song(artist, title, year, genre, danceability);
                songDatabase.insertSingleKey(song);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * finds the average danceability across all the songs in a given list of songs and returns that average
     *
     * @return - average danceability of the song list
     * @throws NullPointerException     - if a song has a null danceability
     * @throws IllegalArgumentException - if listOfSongs param isn't an array of songs
     */
    @Override
    public float findAvgDanceability() throws NullPointerException, IllegalArgumentException {
        Iterator<Song> iterator = songDatabase.iterator(); // get iterator of this songDatabase

        int totalDanceability = 0;
        while (iterator.hasNext()) {
            Song element = iterator.next();
            totalDanceability += element.getDanceability();
        }
        return (float) totalDanceability/songDatabase.numKeys();
    }

    /**
     * returns a list of Songs that  have a danceability over a specified int min
     * @param min - minimum danceability of a song to be considered
     * @return - list of Songs that have a minimum dancebility >= min
     * @throws NullPointerException - if min is null or danceability is null
     * @throws IllegalArgumentException - if listOfSongs param isn't an array of songs or min isn't an int
     */
    @Override
    public Song[] minDanceability(int min) throws NullPointerException, IllegalArgumentException {
        ArrayList<Song> danceable = new ArrayList<>();

        Song dummySong = new Song("a", "t", "1", "g", min); // song with param min danceability
        songDatabase.setIterationStartPoint(dummySong);
        Iterator<Song> iterator = songDatabase.iterator(); // get iterator of this songDatabase

        while (iterator.hasNext()) { // iterator will go through all songs with danceability > min
            Song element = iterator.next();
            danceable.add(element);
        }
        return danceable.toArray(new Song[danceable.size()]); // convert ArrayList danceable songs to List type
    }
}
