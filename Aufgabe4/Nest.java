import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Nest implements Formicarium {
    private final Compatibility compatibility;

    /**
     * Initializes a Nest.
     */
    public Nest() {
        int[] size = new int[2];
        size[1] = 20;

        int[] temperature = new int[2];
        temperature[0] = -100;
        temperature[1] = 150;

        int[] humidity = new int[2];
        humidity[1] = Integer.MAX_VALUE;

        compatibility = new Compatibility(size, temperature, humidity, ETime.UNLIMITED);
    }

    /**
     * @return the Compatibility object of this
     */
    @Override
    public Compatibility compatibility() {
       return compatibility;
    }

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
