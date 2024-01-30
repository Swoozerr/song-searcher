import java.util.ArrayList;
import java.util.Iterator;

public class PlaceholderMultikey<T extends Comparable<Song>> implements IterableMultiKeySortedCollectionInterface<Song>{
    private ArrayList<Song> songList = new ArrayList<>();
    private int numKeys;

    public ArrayList<Song> getSongList() { return this.songList; }

    @Override
    public boolean insertSingleKey(Song song) {
        songList.add(song);
        return true;
    }

    @Override
    public int numKeys() {
        return 0;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void setIterationStartPoint(Comparable<Song> startPoint) {

    }

    @Override
    public boolean insert(KeyListInterface<Song> data) throws NullPointerException, IllegalArgumentException {
        return false;
    }

    @Override
    public boolean contains(Comparable<KeyListInterface<Song>> data) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }
}
