import java.util.*;

public class FormicariumSet implements Iterable<FormicariumItem> {
    private final ArrayList<FormicariumItem> formicariumItems;

    // index of the last returned element of iterator
    private int lastReturned = -1;

    public FormicariumSet(List<FormicariumItem> formicariumItems) {
        this.formicariumItems = new ArrayList<>();
        this.formicariumItems.addAll(formicariumItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormicariumSet that)) return false;
        return Objects.equals(formicariumItems, that.formicariumItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formicariumItems);
    }

    @Override
    public Iterator<FormicariumItem> iterator() {
        return new FormicarSetIterator(formicariumItems);
    }

    private class FormicarSetIterator implements Iterator<FormicariumItem> {
        int counter = 0;
        List<FormicariumItem> items;

        public FormicarSetIterator(ArrayList<FormicariumItem> items) {
            // create a set from the list to avoid listing similar items multiple times
            this.items = (new HashSet<>(items)).stream().toList();
        }

        // Returns true if the iteration has more elements.
        @Override
        public boolean hasNext() {
            return counter != items.size();
        }

        // Returns the next element in the iteration.
        // Throws: NoSuchElementException – if the iteration has no more elements
        @Override
        public FormicariumItem next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = counter;
            return items.get(counter++);
        }

        // Removes from the underlying collection the last element
        // returned by this iterator (optional operation).
        // This method can be called only once per call to next.
        @Override
        public void remove() {
            if (lastReturned == -1) {
                throw new IllegalStateException("No element to remove");
            }
            items.remove(lastReturned);
            counter = lastReturned; // Adjust counter
            lastReturned = -1; // Reset lastReturned after removal
        }
    }
}
