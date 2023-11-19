import java.util.Iterator;
import java.util.NoSuchElementException;

public class Arena implements FormicariumPart {
    private final int substrate;
    private final int material;
    private final Nest nest;
    private final Compatibility compatibility;

    /**
     * Initializes an AntFarm.
     *
     * @param substrate Substrate, should be one of {@link ESubstrat}
     * @param material Material, should be one of {@link EContainerMaterial}
     * @param nest Nest, might be null
     */
    public Arena(int substrate, int material, Nest nest) {
        this.substrate = substrate;
        this.material = material;
        this.nest = nest;

        int[] size = new int[2];
        size[0] = this.substrate * 7 + this.material * 7;
        size[1] = (this.substrate * 20 + this.material * 20) + 10;

        int[] humidity = new int[2];
        humidity[1] = (this.substrate * 20 + this.material * 20) + 10;

        int[] temperature = new int[2];
        temperature[0] = Integer.MIN_VALUE;
        temperature[1] = Integer.MAX_VALUE;

        compatibility =  new Compatibility(size, temperature, humidity, this.nest == null ? ETime.MONTH : ETime.UNLIMITED);
    }

    /**
     * @return the Compatibility object of this, is != null
     */
    @Override
    public Compatibility compatibility() {
        return compatibility;
    }

    /**
     * Returns an iterator over arena.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<FormicariumPart> iterator() {
        return new ArenaIterator(this);
    }

    // simple iterator which simply returns the given item once.
    private static class ArenaIterator implements Iterator<FormicariumPart> {
        private boolean hasNext;
        private final FormicariumPart item;

        public ArenaIterator(FormicariumPart item) {
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
        if (this == o) return true;
        if (!(o instanceof Arena that)) return false;
        return this.compatibility().equals(that.compatibility());
    }

    @Override
    public int hashCode() {
        return this.compatibility().hashCode();
    }
}