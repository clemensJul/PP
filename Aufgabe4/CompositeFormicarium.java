import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
    public CompositeFormicarium() throws CompatibilityException {
        this.formicariumParts = new ArrayList<>();
        calculateCompatibility();
    }

    /**
     * Adds an item to CompositeFormicarium.
     * If the identical item is already in the CompositeFormicarium, it does not get added.
     *
     * @param item Item to add
     * @return true, if the insertion was successful.
     * @throws CompatibilityException if item is not compatible with the rest of the CompositeFormicarium
     */
    public boolean add(FormicariumPart item) throws CompatibilityException {
        for (FormicariumPart part : item) {
            if (checkIdentity(part)) {
                return false;
            }
        }

        // compatibility throws exception if not compatible
        compatibility.compatible(item.compatibility());
        formicariumParts.add(item);

        calculateCompatibility();
        return true;
    }

    /**
     * Removes an item from the CompositeFormicarium.
     *
     * @param item item to remove.
     * @return true, if the removal was successful.
     * @throws CompatibilityException should not occur.
     */
    public boolean remove(FormicariumPart item) throws CompatibilityException {
        boolean removed = formicariumParts.remove(item);

        if (removed) {
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
        for (FormicariumPart item : this) {
            if (item == itemToCheck) {
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
        Compatibility compatibility;
        if (formicariumParts.isEmpty()) {
            int[] openRanges = new int[]{0, Integer.MAX_VALUE};
            compatibility = new Compatibility(openRanges, openRanges, openRanges, ETime.UNLIMITED);
        } else {
            List<Compatibility> compatibilities = formicariumParts.stream().map(Compatible::compatibility).toList();
            compatibility = compatibilities.get(0);

            for (int i = 1; i < compatibilities.size(); i++) {
                compatibility = compatibility.compatible(compatibilities.get(i));
            }

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

    // iterator which returns all items in the CompositeFormicarium recursively.
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
         * Returns true if there is a next element.
         *
         * @return true if there is a next element.
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
        if (this == o) {
            return true;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        if (((CompositeFormicarium) o).formicariumParts.size() != this.formicariumParts.size()) {
            return false;
        }

        // check all parts if there is in the other object any element which is meant to be equal.
        return formicariumParts.stream().allMatch(part -> ((CompositeFormicarium) o).formicariumParts.stream().anyMatch(other -> other.equals(part)));
    }

    @Override
    public int hashCode() {
        return this.compatibility().hashCode();
    }
}
