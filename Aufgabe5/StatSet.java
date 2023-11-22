import java.util.*;

public class StatSet<X extends Rated<P, R>, P, R extends Calc<R>> implements RatedSet<X, P, R> {
    GenericList<X> items;
    GenericList<P> criteria;
    private GenericMap<String, Integer> methodCalls;

    public StatSet() {
        items = new GenericList<>();
        criteria = new GenericList<>();
    }

    /**
     * @param x item to add to container. Must be != null
     */
    @Override
    public void add(X x) {
        incrementMap("add");
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
            if(items.get(i).rated(p).atLeast(r)) {
                sorted.add(items.get(i));
            }
        }

        return new StatSetIterator<>(sorted);
    }

    private class StatSetIterator<InnerX> implements Iterator<InnerX> {
        private final Iterator<InnerX> iterator;

        public StatSetIterator(GenericList<InnerX> list) {
            this.iterator = list.iterator();
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
            Iterator.super.remove();
        }

        /**
         * @return
         */
        @Override
        public InnerX next() {
            incrementMap("next");
            return iterator.next();
        }
    }

    /**
     * @param r
     * @return
     */
    @Override
    public Iterator<X> iterator(R r) {
        incrementMap("iterator3");
//        // calculate average
//        P average = new P();
//        items.forEach(x -> {
//            criteria.forEach(crit -> {
//                x.rated(crit)
//            });
//        });
//
//        return items.stream().filter(x -> {
//            x.rated(average).atLeast()
//        }).iterator();
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
