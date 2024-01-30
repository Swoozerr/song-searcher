public class Storage{
    private IterableMultiKeySortedCollectionInterface<Song> songDatabase;

    public Storage(IterableMultiKeySortedCollectionInterface<Song> songDatabase) {
        this.songDatabase = songDatabase;
    }

}
