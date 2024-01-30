public interface SongInterface<Song extends Comparable<Song>> {

    //returns the artist for the given song, throws exceptions if the inputs are not valid
    public String getArtist();

    //returns the title of the given song, throws exceptions if the inputs are not valid
    public String getTitle();

    //returns the year the song was released, throws exceptions if the inputs are not vaild
    public String getYear();

    //returns the genre of the song, throws exceptions if inputs are not valid
    public String getGenre();

    //returns danceability of the song
    public int getDanceability();
}
