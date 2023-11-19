import java.util.ArrayList;
import java.util.Iterator;

public class FormicariumSet implements Iterable<FormicariumItem> {
    private final ArrayList<FormicariumItem> formicariumItems;

    /**
     * Initializes a new FormicariumSet with the given items.
     *
     * @param formicariumItems items to initialize with, can be empty
     */
    public FormicariumSet(ArrayList<FormicariumItem> formicariumItems) {
        this.formicariumItems = new ArrayList<>();
        this.formicariumItems.addAll(formicariumItems);
    }

    /**
     * Adds an item to the FormicariumSet.
     * The item only gets added if it is not already in the Set with the same idendity.
     *
     * @param item Item to add, must be != null
     */
    public boolean add(FormicariumItem item) {
        if (checkIdentity(item)) {
            return false;
        }

        return formicariumItems.add(item);
    }

    /**
     * Checks if the list already contains an item with the same identity.
     *
     * @param itemToCheck item to check
     * @return true, if identical item is already in list
     */
    private boolean checkIdentity(FormicariumItem itemToCheck) {
        return formicariumItems.stream().anyMatch(item -> item == itemToCheck);
    }

    /**
     * Returns an iterator over the elements of FormicariumSet.
     * Same elements are only available once.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<FormicariumItem> iterator() {
        return new FormicariumSetIterator(formicariumItems);
    }
}
