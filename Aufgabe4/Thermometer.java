import java.util.Iterator;
import java.util.NoSuchElementException;

public class Thermometer implements Instrument, FormicariumPart {
    private final int quality;
    private final Compatibility compatibility;

    /**
     * Initializes a new Thermometer.
     *
     * @param quality, must be >= 0, and should be one of {@link EUsage}
     */
    public Thermometer(int quality) {
        this.quality = quality;

        int[] size = new int[2];
        size[1] = Integer.MAX_VALUE;

        int[] temperature = new int[2];
        temperature[0] = -100 + (quality * 10);
        temperature[1] = 150 - (quality * 10);

        int[] humidity = new int[2];
        humidity[1] = Integer.MAX_VALUE;

        compatibility = new Compatibility(size, temperature, humidity, ETime.UNLIMITED);
    }

    /**
     * @return the Compatibility object of this, is != null
     */
    @Override
    public Compatibility compatibility() {
        return compatibility;
    }

    /**
     * Returns the quality of thermometer.
     *
     * @return quality.
     */
    @Override
    public int quality() {
        return quality;
    }

    /**
     * Returns an iterator over thermometer.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<FormicariumPart> iterator() {
        return new ThermometerIterator(this);
    }

    // simple iterator which simply returns the given item once.
    private static class ThermometerIterator implements Iterator<FormicariumPart> {
        private boolean hasNext;
        private final FormicariumPart item;

        public ThermometerIterator(FormicariumPart item) {
            this.hasNext = true;
            this.item = item;
        }

        /**
         * Returns true if the iteration has more elements.
         *
         * @return  true, if there are items left.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Thermometer that)) {
            return false;
        }

         return this.compatibility().equals(that.compatibility());
    }

    @Override
    public int hashCode() {
        return this.compatibility().hashCode();
    }
}
