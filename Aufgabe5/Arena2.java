public class Arena2 implements Part<Part<?>> {
    /**
     * returns an object of type Quality. Most of the time it will be a worst object, but if two parts are not compatible return "NOTUSABLE"
     *
     * @param part P must be != null
     * @return
     */
    @Override
    public Quality rated(Part<?> part) {
        return part.;
    }

    /**
     * Sets the criterion used for rated, if there is no P given.
     *
     * @param part
     */
    @Override
    public void setCriterion(Part<?> part) {

    }

    /**
     * Rates R based on P set with setCriterion.
     *
     * @return a new Object of R with rated properties.
     * @throws NoCriterionSetException if the criterion was not set with {@link #setCriterion(Object)} before calling this method.
     */
    @Override
    public Quality rated() throws NoCriterionSetException {
        return null;
    }
}
