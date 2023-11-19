import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FormicariumSetIterator implements Iterator<FormicariumItem> {
    private int counter = 0;
    private final HashMap<FormicariumItem, Integer> itemMap;
    private final ArrayList<FormicariumItem> formicariumItems;

    // index of the last returned element of iterator
    private FormicariumItem lastReturned;

    /**
     * Initializes a new FormicariumSetIterator with the given items.
     *
     * @param items Items to iterator over
     */
    public FormicariumSetIterator(ArrayList<FormicariumItem> items) {
        // create a HashMap from the list
        itemMap = new HashMap<>();
        formicariumItems = items;
        items.forEach(item -> itemMap.put(item, itemMap.getOrDefault(item, 0) + 1));
    }

    /**
     * Returns true if the iteration has more elements.
     *
     * @return  true, if there are items left.
     */
    @Override
    public boolean hasNext() {
        return counter != itemMap.size();
    }

    // Returns the next element in the iteration.
    // Throws: NoSuchElementException – if the iteration has no more elements
    @Override
    public FormicariumItem next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        lastReturned = itemMap.keySet().stream().toList().get(counter);
        counter++;
        return lastReturned;
    }

    /**
     * Removes from the underlying collection the last element
     * This method can be called only once per call to next.
     */
    @Override
    public void remove() {
        if (lastReturned == null) {
            throw new IllegalStateException("No element to remove");
        }

        int amountLeft = itemMap.get(lastReturned);
        formicariumItems.remove(lastReturned);
        if (amountLeft == 1) {
            itemMap.remove(lastReturned);
            counter--; // Adjust counter
            lastReturned = null; // Reset lastReturned after removal
        } else {
            itemMap.put(lastReturned, amountLeft - 1);
        }
    }

    /**
     * Removes from the underlying collection the last element
     * This method can be called only once per call to next.
     *
     * @param count removes count amount of the same elements as the last returned from the list, as long as count < same element.
     */
    public void remove(int count) throws NoSuchElementException{
        if (lastReturned == null) {
            throw new IllegalStateException("No element to remove");
        }

        int amountLeft = itemMap.get(lastReturned);
        if (amountLeft - count < 0) {
            throw new NoSuchElementException("No element to remove");
        }

        for (int i = 0; i < count; i++) {
            formicariumItems.remove(lastReturned);
        }

        if (amountLeft - count == 0) {
            itemMap.remove(lastReturned);
            counter--; // Adjust counter
            lastReturned = null; // Reset lastReturned after removal
        } else {
            itemMap.put(lastReturned, amountLeft - count);
        }
    }

    /**
     * Returns the count of the last returned element in the list.
     *
     * @return count of element in list.
     */
    public int count() {
        return itemMap.get(lastReturned);
    }
}