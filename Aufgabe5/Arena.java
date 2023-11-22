public class Arena implements Part<?> {

    private final float volume;
    private Quality quality;
    private Arena criterion;

    /**
     *
     * @param volume in liters
     * @param quality "needs to be "BEGINNER", "SEMIPRO" or "PRO"
     */
    public Arena(float volume,String quality) {
        this.volume = volume;
        if (!quality.equals("NOTUSABLE")) quality = "BEGINNER";
        this.quality = Quality.getQuality(quality);
    }

    /**
     * returns an object of type Quality. Most of the time it will be a worst object, but if two parts are not compatible return "NOTUSABLE"
     * @param  part must be != null
     * @return
     */
    @Override
    public Quality rated(Part<?> part) {
        return quality.sum(arena.quality);
    }

    /**
     * Sets the criterion used for rated, if there is no P given.
     *
     * @param arena
     */
    @Override
    public void setCriterion(Arena arena) {
        this.criterion = arena;
    }

    /**
     * Rates R based on P set with setCriterion.
     *
     * @return a new Object of R with rated properties.
     * @throws NoCriterionSetException if the criterion was not set with {@link #setCriterion(Object)} before calling this method.
     */
    @Override
    public Quality rated() throws NoCriterionSetException {
        if (this.criterion == null) throw new NoCriterionSetException("no criterion was set");
        return this.quality.sum(this.criterion.quality);
    }
}
