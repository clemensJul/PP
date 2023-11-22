import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class CompatibilitySet<X extends Rated<X, R>, R extends Calc<R>> implements RatedSet<X, X, R> {
    ArrayList<X> items;
    ArrayList<X> criteria;

    public CompatibilitySet() {
        this.items = new ArrayList<>();
        this.criteria = new ArrayList<>();
    }

    /**
     * @param x item to add to container. Must be != null
     */
    @Override
    public void add(X x) {
        items.add(x);
    }

    /**
     * @param x item to add to container criteria. Must be != null
     */
    @Override
    public void addCriterion(X x) {
        criteria.add(x);
    }

    /**
     * @return
     */
    public Iterator<X> identical() {
        HashSet<X> identicals = new HashSet<>();
        items.forEach(item -> {
            criteria.forEach(crit -> {
                if (item == crit) {
                    identicals.add(item);
                }
            });
        });

        return identicals.stream().toList().iterator();
    }


    /**
     * @return
     */
    @Override
    public Iterator<X> iterator() {
        return null;
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
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompatibilitySet<?, ?> that = (CompatibilitySet<?, ?>) o;

        return items.stream().allMatch(item -> that.items.stream().anyMatch(oItem -> oItem == item)) && criteria.stream().allMatch(crit -> that.criteria.stream().anyMatch(oCrit -> oCrit == crit));
    }
}
