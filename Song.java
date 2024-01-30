public class Song implements SongInterface<Song>, Comparable<Song> {
    // Datafields
    private String artist;
    private String title;
    private String year;
    private String genre;
    private int danceability;

    public Song(String artist, String title, String year, String genre, int danceability) {
        this.artist = artist;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.danceability = danceability;
    }
    //returns the artist for the given song
    @Override
    public String getArtist() { return this.artist; }

    //returns the title of the given song
    @Override
    public String getTitle() { return this.title; }

    //returns the year the song was released
    @Override
    public String getYear() { return this.year; }

    //returns the genre of the song
    @Override
    public String getGenre() { return this.genre; }

    //returns danceability of the song
    @Override
    public int getDanceability() { return this.danceability; }

    public int compareTo(Song otherSong) {
        if (this.getDanceability() > otherSong.getDanceability()) { return 1; }
        if (this.getDanceability() == otherSong.getDanceability()) { return 0; }
        else { return -1; }
    }
}
