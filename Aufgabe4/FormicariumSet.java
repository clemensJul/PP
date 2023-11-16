import java.util.*;

public class FormicariumSet implements Iterable<FormicariumItem> {
    private final ArrayList<FormicariumItem> formicariumItems;

    // index of the last returned element of iterator
    private FormicariumItem lastReturned;

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

    public void add(FormicariumItem item) {
        if(checkRecursiveIdentity(item)) {
            return;
        }

        formicariumItems.add(item);
    }

    private boolean checkRecursiveIdentity(FormicariumItem item) {
        return formicariumItems.stream().anyMatch(part -> {
            if(part instanceof Formicarium) {
                return checkRecursiveIdentity(part);
            }

            return part == item;
        });
    }

    @Override
    public Iterator<FormicariumItem> iterator() {
        return new FormicarSetIterator(formicariumItems);
    }

    private class FormicarSetIterator implements Iterator<FormicariumItem> {
        int counter = 0;
        HashMap<FormicariumItem, Integer> itemMap;

        public FormicarSetIterator(ArrayList<FormicariumItem> items) {
            // create a HashMap from the list
            itemMap = new HashMap<>();
            items.forEach(item -> itemMap.put(item, itemMap.getOrDefault(item, 1)));
        }

        // Returns true if the iteration has more elements.
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

        // Removes from the underlying collection the last element
        // returned by this iterator (optional operation).
        // This method can be called only once per call to next.
        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException("No element to remove");
            }

            int amountLeft = itemMap.get(lastReturned);
            if (amountLeft == 0) {
                itemMap.remove(lastReturned);
                counter--; // Adjust counter
                lastReturned = null; // Reset lastReturned after removal
            } else {
                itemMap.put(lastReturned, amountLeft - 1);
            }
        }

        // todo throw exception if count > amountLeft ??
        public void remove(int count) {
            if (lastReturned == null) {
                throw new IllegalStateException("No element to remove");
            }

            int amountLeft = itemMap.get(lastReturned);
            if (amountLeft - count < 0) {
                itemMap.remove(lastReturned);
                counter--; // Adjust counter
                lastReturned = null; // Reset lastReturned after removal
            } else {
                itemMap.put(lastReturned, amountLeft - count);
            }
        }

        public int count() {
            return itemMap.get(lastReturned);
        }
    }
}
