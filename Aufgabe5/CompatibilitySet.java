import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class CompatibilitySet<X extends Rated<? super X, R>, R extends Calc<R>> implements RatedSet<X, X, R> {
    GenericList<X> items;
    GenericList<X> criteria;

    public CompatibilitySet() {
        this.items = new GenericList<>();
        this.criteria = new GenericList<>();
    }

    /**
     * @param x item to add to container. Must be != null
     */
    @Override
    public void add(X x) {
        for (X item : items) {
            if (item == x) {
                return;
            }
        }

        items.add(x);
    }

    /**
     * @param x item to add to container criteria. Must be != null
     */
    @Override
    public void addCriterion(X x) {
        for (X crit : criteria) {
            if (crit == x) {
                return;
            }
        }

        criteria.add(x);
    }

    /**
     * @return
     */
    public Iterator<X> identical() {
        GenericList<X> identicals = new GenericList<>();
        items.forEach(item -> {
            criteria.forEach(crit -> {
                if (item == crit) {
                    identicals.add(item);
                }
            });
        });

        return identicals.iterator();
    }


    /**
     * @return
     */
    @Override
    public Iterator<X> iterator() {
        return items.iterator();
    }

    /**
     * @param x
     * @param r
     * @return
     */
    @Override
    public Iterator<X> iterator(X x, R r) {
        return null;
    }

    /**
     * @param r
     * @return
     */
    @Override
    public Iterator<X> iterator(R r) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Iterator<X> criterions() {
        return criteria.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompatibilitySet<?, ?> that = (CompatibilitySet<?, ?>) o;

        // Überprüfung der Elemente
        for (X item : items) {
            boolean found = false;
            for (Rated<?, ?> oItem : that.items) {
                if (oItem == item) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        // Überprüfung der Kriterien
        for (X item : criteria) {
            boolean found = false;
            for (Rated<?, ?> oItem : that.criteria) {
                if (oItem == item) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
//        return items.stream().allMatch(item -> that.items.stream().anyMatch(oItem -> oItem == item)) && criteria.stream().allMatch(crit -> that.criteria.stream().anyMatch(oCrit -> oCrit == crit));
    }
}
