import java.util.*;

public class CompositeFormicarium implements Formicarium {
    private final ArrayList<FormicariumPart> formicariumParts;
    private Compatibility compatibility;

    /**
     * Initializes a CompositeFormicarium with a given list.
     *
     * @param formicariumParts initial parts of CompositeFormicarium
     * @throws CompatibilityException if the given list is not compatible with each other
     */
    public CompositeFormicarium(ArrayList<FormicariumPart> formicariumParts) throws CompatibilityException {
        this.formicariumParts = new ArrayList<>();
        this.formicariumParts.addAll(formicariumParts);
        calculateCompatibility();
    }

    /**
     * Initializes a CompositeFormicarium with an empty list.
     *
     * @throws CompatibilityException should never occur.
     */
    public CompositeFormicarium() throws CompatibilityException{
        this.formicariumParts = new ArrayList<>();
        calculateCompatibility();
    }

    /**
     * Adds an item to CompositeFormicarium.
     *
     * @param item Item to add
     * @throws CompatibilityException if item is not compatible with the rest of the CompositeFormicarium
     * @return
     */
    // elements without same identity are allowed
    public boolean add(FormicariumPart item) throws CompatibilityException {
        if(checkIdentity(item)) {
            return false;
        }

        compatibility.compatible(item.compatibility());
        // compatibility should throw exception if not compatible
        formicariumParts.add(item);

        calculateCompatibility();
        return true;
    }

    public boolean remove(FormicariumPart item) throws CompatibilityException {
        boolean removed = formicariumParts.remove(item);

        if(removed) {
            calculateCompatibility();
        }

        return removed;
    }

    /**
     * Checks if the list already contains an item with the same identity.
     *
     * @param itemToCheck item to check
     * @return true, if identical item is already in list
     */
    private boolean checkIdentity(FormicariumPart itemToCheck) {
        for(FormicariumPart item : this)  {
            if(item == itemToCheck) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the Compatibility object of this based on the elements in the list.
     * If the list is empty, a new Compatibility object with open ranges is created.
     *
     * @throws CompatibilityException if elements in the list don´t overlap
     */
    private void calculateCompatibility() throws CompatibilityException {
        if(formicariumParts.isEmpty()) {
            int[] openRanges = new int[] {0, Integer.MAX_VALUE};
            compatibility = new Compatibility(openRanges, openRanges, openRanges, ETime.UNLIMITED);
        }

        List<Compatibility> compatibilities = formicariumParts.stream().map(Compatible::compatibility).toList();
        Compatibility compatibility = compatibilities.get(0);

        for (int i = 1; i < compatibilities.size(); i++) {
            compatibility = compatibility.compatible(compatibilities.get(i));
        }

        this.compatibility = compatibility;
    }

    /**
     * @return the Compatibility object of this, is != null
     */
    @Override
    public Compatibility compatibility() {
        return compatibility;
    }

    /**
     * Returns an iterator of this CompositeFormicarium.
     * If the CompositeFormicarium is empty, the iterator contains only one entry containing this.
     * Otherwise, all items in CompositeFormicarium are returned.
     *
     * @return an iterator.
     */
    public Iterator<FormicariumPart> iterator() {
        // if list is empty, return an iterator only containing this CompositeFormicarium
        if (formicariumParts.isEmpty()) {
            ArrayList<FormicariumPart> self = new ArrayList<>();
            self.add(this);
            return self.iterator();
        }

        return new FormicariumIterator();
    }


    private class FormicariumIterator implements Iterator<FormicariumPart> {
        private final Iterator<FormicariumPart> iterator;
        private Iterator<FormicariumPart> subIterator;

        public FormicariumIterator() {
            this.iterator = formicariumParts.iterator();

            // if listIter has next subIter will always get an iterator
            if (iterator.hasNext()) {
                subIterator = iterator.next().iterator();
            }
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            if (subIterator == null) {
                return iterator.hasNext();
            }
            return iterator.hasNext() || subIterator.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public FormicariumPart next() throws NoSuchElementException {
            if (subIterator.hasNext()) {
                return subIterator.next();
            } else if (iterator.hasNext()) {
                subIterator = iterator.next().iterator();
                return subIterator.next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositeFormicarium that)) return false;
        return Objects.equals(formicariumParts, that.formicariumParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formicariumParts);
    }
}
