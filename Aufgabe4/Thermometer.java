import java.util.Iterator;
import java.util.NoSuchElementException;

public class Thermometer implements CompatibleInstrument {

    @Override
    public boolean compatibility(Compatible compare) {
        return false;
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

    private class ThermometerIterator implements Iterator<FormicariumPart> {

        private boolean hasNext;

        private FormicariumPart item;

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
        public FormicariumPart next() throws NoSuchElementException{
            if (!hasNext) throw new NoSuchElementException();
            hasNext = false;
            return item;
        }
    }
}
