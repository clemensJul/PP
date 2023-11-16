import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Nest implements FormicariumPart {
    // nest needs an arena
    // for a specified amount of time, it can last without an arena
    Arena arena;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nest that)) return false;
        return Objects.equals(arena, that.arena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arena);
    }

    /**
     * @return
     */
    @Override
    public Compatibility compatibility() {
        return null;
    }

    /**
     * @return
     */
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<FormicariumPart> iterator() {
        return new NestIterator(this);
    }

    private static class NestIterator implements Iterator<FormicariumPart> {

        private boolean hasNext;

        private final FormicariumPart item;

        public NestIterator(FormicariumPart item) {
            this.hasNext = true;
            this.item = item;
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
            return hasNext;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public FormicariumPart next() throws NoSuchElementException {
            if (!hasNext) {
                throw new NoSuchElementException();
            }
            hasNext = false;
            return item;
        }
    }
}
