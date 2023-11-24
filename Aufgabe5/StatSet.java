import java.util.Iterator;
import java.util.Objects;

public class StatSet<X extends Rated<? super P, R>, P, R extends Calc<R>> implements RatedSet<X, P, R> {
    GenericList<X> items;
    GenericList<P> criteria;
    private final GenericMap<String, Integer> methodCalls;

    public StatSet() {
        items = new GenericList<>();
        criteria = new GenericList<>();
        methodCalls = new GenericMap<>();
    }

    /**
     * @param x item to add to container. Must be != null
     */
    @Override
    public void add(X x) {
        incrementMap("add");
        for (X item : items) {
            if (item == x) {
                return;
            }
        }
        items.add(x);
    }

    private void incrementMap(String function) {
        methodCalls.put(function, methodCalls.getOrDefault(function, 0) + 1);
    }

    /**
     * @param p item to add to container criteria. Must be != null
     */
    @Override
    public void addCriterion(P p) {
        incrementMap("addCriterion");
        for (P crit : criteria) {
            if (crit == p) {
                return;
            }
        }
        criteria.add(p);
    }

    /**
     * @return
     */
    @Override
    public Iterator<X> iterator() {
        incrementMap("iterator");
        return new StatSetIterator<>(items);
    }

    /**
     * @param p
     * @param r
     * @return
     */
    @Override
    public Iterator<X> iterator(P p, R r) {
        incrementMap("iterator2");

        GenericList<X> sorted = new GenericList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).rated(p).atLeast(r)) {
                sorted.add(items.get(i));
            }
        }

        return new StatSetIterator<>(sorted);
    }

    private class StatSetIterator<InnerX> implements Iterator<InnerX> {
        private final Iterator<InnerX> iterator;
        private final GenericList<InnerX> list;

        private InnerX lastReturned;

        public StatSetIterator(GenericList<InnerX> list) {
            this.iterator = list.iterator();
            this.list = list;
        }

        /**
         * @return
         */
        @Override
        public boolean hasNext() {
            incrementMap("hasNext");
            return iterator.hasNext();
        }

        /**
         *
         */
        @Override
        public void remove() {
            incrementMap("remove");
            if (lastReturned == null) {
                return;
            }

            list.remove(lastReturned);
            lastReturned = null;
        }

        /**
         * @return
         */
        @Override
        public InnerX next() {
            incrementMap("next");
            lastReturned = iterator.next();
            return lastReturned;
        }
    }

    /**
     * @param r
     * @return
     */
    @Override
    public Iterator<X> iterator(R r) throws NoCriterionSetException {
        incrementMap("iterator3");
//        // calculate average
        R sum = null;

        for (X item : items) {
            for (P crit : criteria) {
                R rated = item.rated(crit);
                if (sum == null) {
                    sum = rated;
                } else {
                    sum = sum.sum(rated);
                }
            }
        }

        R average = sum.ratio(items.size());
        GenericList<X> itemsAboveAverage = new GenericList<>();

        for (X item : items) {
            if(item.rated().atLeast(average)) {
                itemsAboveAverage.add(item);
            }
        }

        return new StatSetIterator<>(items);
    }

    /**
     * @return
     */
    @Override
    public Iterator<P> criterions() {
        incrementMap("criterions");
        return new StatSetIterator<>(criteria);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatSet<?, ?, ?> statSet = (StatSet<?, ?, ?>) o;
        return Objects.equals(items, statSet.items) && Objects.equals(criteria, statSet.criteria);
    }

    /**
     * Returns a statistic of the amount the methods got called
     *
     * @return Method call statistic
     */
    public String statistics() {
        incrementMap("statistics");
        StringBuilder stats = new StringBuilder();

        GenericList<Integer> values = methodCalls.values();
        GenericList<String> keys = methodCalls.keys();
        for (int i = 0; i < methodCalls.size(); i++) {
            stats.append(keys.get(i)).append(" calls: ").append(values.get(i)).append("\n");
        }

        return stats.toString();
    }
}
