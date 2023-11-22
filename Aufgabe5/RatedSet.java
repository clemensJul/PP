import java.util.Iterator;

// P -> Criteria
// X -> Element
// R -> Rated (R wie Result)

public interface RatedSet<X extends Rated<P, R>, P, R extends Calc<R>> extends Iterable<X> {
    //TODO do we need an rated method here?

    /**
     * Adds an element to the container if it is not already there.
     *
     * @param x item to add to container. Must be != null
     */
    void add(X x);

    /**
     * Adds a criterion to the container if it is not already there.
     *
     * @param p item to add to container criteria. Must be != null
     */
    void addCriterion(P p);

    /**
     * The iterator removes the last returned element retrieved by next from the entries added by {@link #add(Rated)}
     *
     * @return an iterator, which returns all entries of the container which where added by {@link #add(Rated)}.
     */
    @Override
    Iterator<X> iterator();

    /**
     * @return an iterator, which returns all entries of the container which where added by {@link #add(Rated)} and for which rated(p) is >= than r.
     */
    Iterator<X> iterator(P p, R r);

    /**
     * @return an iterator, which returns all entries of the container which where added by {@link #add(Rated)} and for which following condition is met:
     * The average of x.rated(p) over all criteria in this container is >= r.
     * */
    Iterator<X> iterator(R r);

    /**
     * The iterator removes the last returned element retrieved by next from the entries added by {@link #addCriterion(Object)}
     * 
     * @return an iterator, which returns all criteria of the container which where added by {@link #addCriterion(Object)}}.
     */
    Iterator<P> criterions();
}
