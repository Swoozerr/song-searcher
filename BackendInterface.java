import java.io.*;

public interface BackendInterface<Song extends Comparable<Song>> {
    // reads data from the given file into a RBT, throws exceptions if fileName is not valid
    public void readFromFile(String fileName) throws FileNotFoundException;
   
    //returns the average danceability of an array of songs, throws exceptions if the array inputted is not valid
    public float findAvgDanceability() throws NullPointerException, IllegalArgumentException;

    // returns an array of songs that have a danceability above or equal to a number,
    //    throws exceptions if input array is not valid
    public Song[] minDanceability(int min) throws NullPointerException, IllegalArgumentException;
}
