import java.util.*;

public class Formicarium implements FormicariumPart {
    ArrayList<FormicariumPart> formicariumParts;

    public Formicarium(ArrayList<FormicariumPart> formicariumParts) {
        this.formicariumParts = new ArrayList<>();
        this.formicariumParts.addAll(formicariumParts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Formicarium that)) return false;
        return Objects.equals(formicariumParts, that.formicariumParts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formicariumParts);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<FormicariumPart> iterator() {
        if (formicariumParts.isEmpty()) {
            ArrayList<FormicariumPart> copy = new ArrayList<>();
            copy.add(this);
            return copy.iterator();
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
            FormicariumPart output;
            if (subIterator.hasNext()) {
                output = subIterator.next();
            } else if (iterator.hasNext()) {
                subIterator = iterator.next().iterator();
                output = subIterator.next();
            } else {
                throw new NoSuchElementException();
            }
            return output;
        }
    }

    /**
     * @return
     */
    @Override
    public Compatibility compatibility() {
        Optional<Compatibility> compatibility = formicariumParts.stream().map(Compatible::compatibility).reduce(Compatibility::compatible);
        return compatibility.orElse(null);
    }
}
