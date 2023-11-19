import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class AntFarm extends Nest {
    private final int substrate;
    private final Compatibility compatibility;

    /**
     * Initializes an AntFarm.
     * Values should be one of ESubstrat
     *
     * @param substrate Substrate
     */
    public AntFarm(int substrate) {
        this.substrate = substrate;
        int[] size = new int[2];
        size[1] = (substrate * 20) + 10;

        int[] humidity = new int[2];
        humidity[1] = (substrate * 20) + 10;

        int[] temperature = new int[2];
        temperature[1] = 150;

        compatibility = new Compatibility(size, temperature, humidity, ETime.WEEK);
    }

    /**
     * Returns an iterator over elements of AntFarm.
     * The iterator only contains this.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<FormicariumPart> iterator() {
        return new AntFarmIterator(this);
    }

    /**
     * @return the Compatibility object of this, is != null
     */
    @Override
    public Compatibility compatibility() {
        return compatibility;
    }

    // simple iterator which simply returns the given item once.
    private static class AntFarmIterator implements Iterator<FormicariumPart> {
        private boolean hasNext;
        private final FormicariumPart item;

        public AntFarmIterator(FormicariumPart item) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof AntFarm that)) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }
        return substrate == that.substrate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), substrate);
    }
}
