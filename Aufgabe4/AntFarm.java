import java.util.Iterator;
import java.util.NoSuchElementException;

public class AntFarm extends Nest {
    int substrate;

    public AntFarm(int substrate) {
        this.substrate = substrate;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<FormicariumPart> iterator() {
        return new AntFarmIterator(this);
    }

    /**
     * @return
     */
    @Override
    public Compatibility compatibility() {
        int[] size = new int[2];
        size[1] = (substrate * 20) + 10;

        int[] humidity = new int[2];
        humidity[1] = (substrate * 20) + 10;

        int[] temperature = new int[2];
        temperature[1] = 150;

        return new Compatibility(size, temperature, humidity);
    }

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
}
