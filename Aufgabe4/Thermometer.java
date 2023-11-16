import java.util.Iterator;
import java.util.NoSuchElementException;

public class Thermometer implements Instrument, FormicariumPart {
    private final int quality;

    public Thermometer(int quality) {
        this.quality = quality;
    }

    @Override
    public Compatibility compatibility() {
        int[] size = new int[2];
        size[1] = Integer.MAX_VALUE;

        int[] temperature = new int[2];
        temperature[0] = -100 + (quality * 10);
        temperature[1] = 150 - (quality * 10);

        int[] humidity = new int[2];
        humidity[1] = Integer.MAX_VALUE;
        return new Compatibility(size, temperature, humidity);
    }

    @Override
    public int quality() {
        return quality;
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
        return new ThermometerIterator(this);
    }

    private static class ThermometerIterator implements Iterator<FormicariumPart> {
        private boolean hasNext;
        private final FormicariumPart item;

        public ThermometerIterator(FormicariumPart item) {
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
