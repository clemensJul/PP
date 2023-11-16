import java.util.Iterator;
import java.util.NoSuchElementException;

public class Arena implements FormicariumPart {
    int substrate;
    int material;

    public Arena(int substrate, int material) {
        this.substrate = substrate;
        this.material = material;
    }

    @Override
    public Compatibility compatibility() {
        int[] size = new int[2];
        size[0] = substrate * 7 + material * 7;
        size[1] = (substrate * 20 + material * 20) + 10;

        int[] humidity = new int[2];
        humidity[1] = (substrate * 20 + material * 20) + 10;

        int[] temperature = new int[2];
        temperature[0] = Integer.MIN_VALUE;
        temperature[1] = Integer.MAX_VALUE;

        return new Compatibility(size, temperature, humidity);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<FormicariumPart> iterator() {
        return new ArenaIterator(this);
    }

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
}