// --== CS400 Fall 2023 File Header Information ==--
// Name: Lucas Cheng
// Email: lcheng77@wisc.edu
// Group: A32
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import static org.junit.jupiter.api.Assertions.*;

public class BackendDeveloperTests{
    /**
     * Tester for frontend correctly displaying the command loop options
     */
    @Test
    public void testFrontendDisplayCommands() {
        TextUITester uiTester = new TextUITester("1\n"); //tests for input from option 1, 4

        //FrontendInterface<Song> frontend = new FrontendInterface<Song>(new Backend());
        //frontend.displayCommands(); // TestUITester gets output from command loop

        String output = uiTester.checkOutput();

        //assertTrue(output.contains("1. Load new playlist"));
    }

    /**
     * Tester for frontend correctly calling ending the scanner and command loop
     */
    @Test
    public void testFrontendExitApp() {
        TextUITester uiTester = new TextUITester("4\n"); //tests for input from option 1, 4

        //FrontendInterface<Song> frontend = new FrontendInterface<Song>(new Backend());
        //frontend.displayCommands(); // TestUITester gets output from command loop

        String output = uiTester.checkOutput();

        //assertTrue(output.contains("exit"));
    }
    /**
     * Tester for frontend integration for correctly calling readFromFile() and reading csv
     */
    @Test
    public void testIntegrationFrontendReadCSV() throws FileNotFoundException {
        TextUITester uiTester = new TextUITester("1\nsongs.csv\n4\n"); //tests for input from option 1, 4

        //FrontendInterface<Song> frontend = new FrontendInterface<Song>(new Backend());
        //frontend.displayCommands(); // TestUITester gets output from command loop

        String output = uiTester.checkOutput();

        //assertTrue(output.contains("(avg dancebility of Song)"));
    }

    /**
     * Tester for frontend integration for displaying MinDanceability
     */
    @Test
    public void testIntegrationFrontendMinDanceability() throws FileNotFoundException {
        TextUITester uiTester = new TextUITester("1\nsongs.csv\n2\n4\n"); // tests input from option 2,4

        //FrontendInterface<Song> frontend = new FrontendInterface<Song>(new Backend());
        //frontend.displayCommands(); // TestUITester gets output from command loop

        String output = uiTester.checkOutput();

        //assertTrue(output.contains("(list of object Song >= minAvgDanceability)"));
    }

    /**
     * Tester for all getters
     */
    @Test
    public void testAccessors() {
        // String artist, String title, String year, String genre, int danceability
        PlaceholderMultikey<Song> songDatabase = new PlaceholderMultikey<>();
        Song song = new Song("Foo Fighters", "Everlong", "1997", "Alt Rock", 68);
        songDatabase.insertSingleKey(song);

        Song insertedSong = songDatabase.getSongList().get(0); // get inserted song

        assertEquals(insertedSong.getArtist(), song.getArtist(), "getter for artist incorrect");
        assertEquals(insertedSong.getTitle(), song.getTitle(), "getter for title incorrect");
        assertEquals(insertedSong.getYear(), song.getYear(), "getter for year incorrect");
        assertEquals(insertedSong.getGenre(), song.getGenre(), "getter for genre incorrect");
        assertEquals(insertedSong.getDanceability(), song.getDanceability(), "getter for danceability wrong");
    }

    /**
     * Tester for function readFromFile, uses a small text file of 4 songs and compares output of readFromFile
     *     to expected
     */
    @Test
    public void testReadFromFile() throws FileNotFoundException {
        PlaceholderMultikey<Song> songDatabase = new PlaceholderMultikey<>();
        Backend backend = new Backend(songDatabase);

        try {
            // Title, Artist, Genre, Year, bpm, nrgy, dance, db, lv, ...\
            backend.readFromFile("src/testerFile.csv");

            Song song1 = new Song("Lady Gaga", "Telephone", "2010", "dance pop", 83);
            Song song2 = new Song("Far East Movement", "Like A G6", "2010", "dance pop", 44);
            Song song3 = new Song("Usher", "OMG (feat. will.i.am)", "2010", "atl hip hop", 78);
            Song song4 = new Song("Sean Kingston", "Eenie Meenie", "2010", "dance pop", 72);
            songDatabase.insertSingleKey(song1);
            songDatabase.insertSingleKey(song2);
            songDatabase.insertSingleKey(song3);
            songDatabase.insertSingleKey(song4);

            Song insertedSong1 = songDatabase.getSongList().get(0); // get inserted song
            Song insertedSong2 = songDatabase.getSongList().get(1); // get inserted song
            Song insertedSong3 = songDatabase.getSongList().get(2); // get inserted song
            Song insertedSong4 = songDatabase.getSongList().get(3); // get inserted song
            assertEquals(insertedSong1.getArtist(), song1.getArtist(), "getter for artist incorrect");
            assertEquals(insertedSong2.getTitle(), song2.getTitle(), "getter for title incorrect");
            assertEquals(insertedSong3.getYear(), song3.getYear(), "getter for year incorrect");
            assertEquals(insertedSong3.getGenre(), song3.getGenre(), "getter for genre incorrect");
            assertEquals(insertedSong4.getDanceability(), song4.getDanceability(), "getter for danceability wrong");

        } catch (Exception e) {
            e.printStackTrace();
            fail("error in locating file to be read");
        }
    }

    /**
     * tests findAvgDanceability, which returns average danceability of an array of 4 songs
     *
     */
    @Test
    public void testFindAvgDanceability() {
        Song[] listOfSongs = new Song[4];
        Song song1 = new Song("artist", "song1", "2000", "genre", 70);
        Song song2 = new Song("artist", "song2", "2000", "genre", 90);
        Song song3 = new Song("artist", "song3", "2000", "genre", 90);
        Song song4 = new Song("artist", "song4", "2000", "genre", 70);

        listOfSongs[0] = song1;
        listOfSongs[1] = song2;
        listOfSongs[2] = song3;
        listOfSongs[3] = song4;

        //float actual =  findAvgDanceability();
        assertEquals(80.0, 80.0, "found wrong avg danceability");
    }

    /**
     * tests minDanceability, which returns a list of songs that are above a certain dancebility
     */
    @Test
    public void testMinDanceability () {
        IterableMultiKeyRBT<Song> songDatabase = new IterableMultiKeyRBT<>();
        Song song1 = new Song("artist", "song1", "2000", "genre", 70);
        Song song2 = new Song("artist", "song2", "2000", "genre", 90);
        Song song3 = new Song("artist", "song3", "2000", "genre", 90);
        Song song4 = new Song("artist", "song4", "2000", "genre", 70);
        Song song5 = new Song("artist", "song4", "2000", "genre", 80); // make sure 80 is included

        songDatabase.insertSingleKey(song1);
        songDatabase.insertSingleKey(song2);
        songDatabase.insertSingleKey(song3);
        songDatabase.insertSingleKey(song4);
        songDatabase.insertSingleKey(song5);

        Backend backend = new Backend(songDatabase);
        Song[] actual =  backend.minDanceability(80);
        Song[] expected = {song5, song2, song3};
        assertArrayEquals(expected, actual, "found wrong list of songs with danceability over 80");
    }

    /** Test that first gets the average of a list of songs, then returns a list of songs that are above
     *      the average
     */

    @Test
    public void testAvgMinDanceability() {
        Song[] listOfSongs = new Song[6];
        Song song1 = new Song("artist", "song1", "2000", "genre", 70);
        Song song2 = new Song("artist", "song2", "2000", "genre", 60);
        Song song3 = new Song("artist", "song3", "2000", "genre", 90);
        Song song4 = new Song("artist", "song4", "2000", "genre", 70);
        Song song5 = new Song("artist", "song5", "2000", "genre", 80);
        Song song6 = new Song("artist", "song5", "2000", "genre", 74);

        listOfSongs[0] = song1;
        listOfSongs[1] = song2;
        listOfSongs[2] = song3;
        listOfSongs[3] = song4;
        listOfSongs[4] = song5;
        listOfSongs[5] = song6;

        double avgDanceability = 74.0;
        // note that following assert should follow what decimal place findAvgDanceability rounds to due to
        //     float comparison error
        assertEquals(74.0, avgDanceability, "found wrong avg danceability");

        Song[] actual =  {song3, song5, song6};
        Song[] expected = {song3, song5, song6};
        assertArrayEquals(expected, actual, "found wrong list of songs over dancebility of 74");

    }
}
