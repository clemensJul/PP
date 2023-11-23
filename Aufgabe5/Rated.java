public interface Rated<P , R extends Calc<R>> {
    /**
     * Returns an object of R evaluated based on type P
     * @param p P must be != null
     * @return a new Object of R with rated properties.
     */
    R rated(P p);

    /**
     * Sets the criterion used for rated, if there is no P given.
     */
    void setCriterion(P p);

    /**
     * Rates R based on P set with setCriterion.
     *
     * @return a new Object of R with rated properties.
     * @throws NoCriterionSetException if the criterion was not set with {@link #setCriterion(Object)} before calling this method.
     */
    R rated() throws NoCriterionSetException;
}
