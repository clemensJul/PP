import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Formicarium implements FormicariumPart {
    ArrayList<FormicariumPart> formicariumParts;

    public Formicarium(ArrayList<FormicariumPart> formicariumParts) {
        this.formicariumParts = new ArrayList<>();
        this.formicariumParts.addAll(formicariumParts);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<FormicariumPart> iterator() {
        if(formicariumParts.isEmpty()) {
            ArrayList<FormicariumPart> copy = new ArrayList<>();
            copy.add(this);
            return copy.iterator();
        }

        return new FormicariumIterator();
    }

    private class FormicariumIterator implements Iterator<FormicariumPart> {
        private Iterator<FormicariumPart> listIter;
        private Iterator<FormicariumPart> subIter;

        public FormicariumIterator() {
            this.listIter = formicariumParts.iterator();

            // if listIter has next subIter will always get an iterator
            if (listIter.hasNext()) {
                subIter = listIter.next().iterator();
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
            if(subIter == null) {
                return listIter.hasNext();
            }
            return listIter.hasNext() || subIter.hasNext();
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
            if (subIter.hasNext()) {
                output = subIter.next();
            } else if (listIter.hasNext()) {
                subIter = listIter.next().iterator();
                output = subIter.next();
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
        return null;
    }
}
