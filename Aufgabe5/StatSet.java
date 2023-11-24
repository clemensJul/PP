import java.util.Iterator;

public class StatSet<X extends Rated<? super P, R>, P, R extends Calc<R>> implements RatedSet<X, P, R> {
    private final GenericList<X> items;
    private final GenericList<P> criteria;
    private final GenericMap<String, Integer> methodCalls;

    public StatSet() {
        items = new GenericList<>();
        criteria = new GenericList<>();
        methodCalls = new GenericMap<>();
    }

    /**
     * Adds an item to the container.
     *
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

    /**
     * This function increments the value of the map associated by function by one.
     * If the value does not already exist, it gets added to the map.
     *
     * @param function Function to increment, must be != null
     */
    private void incrementMap(String function) {
        methodCalls.put(function, methodCalls.getOrDefault(function, 0) + 1);
    }

    /**
     * Adds a criteria to the container.
     *
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
     * The iterator removes the last returned element retrieved by next from the entries added by {@link #add(Rated)}
     *
     * @return an iterator, which returns all entries of the container which where added by {@link #add(Rated)}.
     */
    @Override
    public Iterator<X> iterator() {
        incrementMap("iterator");
        return new StatSetIterator<>(items);
    }

    /**
     * The iterator removes the last returned element retrieved by next from the entries added by {@link #add(Rated)}
     *
     * @param p P
     * @param r R
     * @return an iterator, which returns all entries of the container which where added by {@link #add(Rated)} and for which rated(p) is >= than r.
     */
    @Override
    public Iterator<X> iterator(P p, R r) {
        incrementMap("iterator2");

        GenericList<X> itemsAbove = new GenericList<>();
        for (X item : items) {
            if (item.rated(p).atLeast(r)) {
                itemsAbove.add(item);
            }
        }

        return new StatSetPartIterator<>(itemsAbove, items);
    }

    /**
     * The iterator removes the last returned element retrieved by next from the entries added by {@link #add(Rated)}
     *
     * @return an iterator, which returns all entries of the container which where added by {@link #add(Rated)} and for which following condition is met:
     * The average of x.rated(p) over all criteria in this container is >= r.
     */
    @Override
    public Iterator<X> iterator(R r) {
        incrementMap("iterator3");
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
            // todo this check is probably wrong
            // todo error handling needs also to be done
//            if (item.rated().atLeast(average)) {
            itemsAboveAverage.add(item);
//            }
        }

        return new StatSetPartIterator<>(itemsAboveAverage, items);
    }

    /**
     * The iterator removes the last returned element retrieved by next from the entries added by {@link #addCriterion(Object)}
     *
     * @return an iterator, which returns all criteria of the container which where added by {@link #addCriterion(Object)}}.
     */
    @Override
    public Iterator<P> criterions() {
        incrementMap("criterions");
        return new StatSetIterator<>(criteria);
    }

    /**
     * Two objects of StatSet are equal if all criteria and all items are equal (independent of order)
     *
     * @param o Object to compare
     * @return if this is equal to o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatSet<?, ?, ?> statSet = (StatSet<?, ?, ?>) o;

        for (X item : items) {
            boolean foundItem = false;
            for (Rated<?, ?> oItem : statSet.items) {
                if (item == oItem) {
                    foundItem = true;
                    break;
                }
            }

            if(!foundItem) {
                return false;
            }
        }

        for (P crit : criteria) {
            boolean foundItem = false;
            for (Object oCrit : statSet.criteria) {
                if (crit == oCrit) {
                    foundItem = true;
                    break;
                }
            }

            if(!foundItem) {
                return false;
            }
        }

        return true;
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

    // TODO: Kein TODO aber so als frage falls es besser geht. i hab jetzt zwei iteratoren gebraucht, damit das löschen funktioniert
    // todo man gibt halt bei dem zweiten iterator einfach eine referenz auf die liste zurück, aus der man den eintrag löschen will bei remove
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

    private class StatSetPartIterator<InnerX> implements Iterator<InnerX> {
        private final Iterator<InnerX> iterator;
        private final GenericList<InnerX> listToIterate;
        private final GenericList<InnerX> sourceList;

        private InnerX lastReturned;

        public StatSetPartIterator(GenericList<InnerX> listToIterate, GenericList<InnerX> sourceList) {
            this.listToIterate = listToIterate;
            this.sourceList = sourceList;
            this.iterator = listToIterate.iterator();
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

            listToIterate.remove(lastReturned);
            sourceList.remove(lastReturned);
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
}
