import java.io.FileNotFoundException;

public interface FrontendInterface<T extends Comparable<T>> {
    
    /**
     * Starts the main command loop where the user selects commands.
     */
    void startCommandLoop();

    /**
     * Displays the main menu commands to the user.
     */
    void displayMainMenuCommands();

    /**
     * Displays the sub-menu commands to the user.
     */
    void displaySubMenuCommands();

    /**
     * Loads a playlist by name. 
     * @throws FileNotFoundException if the playlist name does not exist in the file.
     */
    T[] loadNewPlayList(String playlistName) throws FileNotFoundException;

    /**
     * Returns a list of songs with a specific danceability score from the currently loaded playlist.
     */
    T[] getListOfDanceabilitySongs(int desiredScore);

    /**
     * Calculates the average danceability score for the currently loaded playlist.
     */
    double getAverageDanceability();

    /**
     * Exits the application.
     */
    void exitApp();

    /**
     * Handles invalid inputs and provides feedback to the user.
     */
    String handleInvalidInput();
}

