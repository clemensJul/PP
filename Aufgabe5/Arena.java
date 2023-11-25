public class Arena implements Part {
    private final float volume;
    private final Quality quality;
    private Part criterion;

    public Arena(float volume, String quality) {
        this.volume = volume;
        if (!quality.equals("NOT_USABLE")) {
            quality = "BEGINNER";
        }
        this.quality = Quality.getQuality(quality);
    }

    /**
     * returns an object of type Quality. Most of the time it will be the worst object, but if two parts are not compatible return "NOT_USABLE"
     *
     * @param p P must be != null
     * @return
     */
    // TODO: berechnung passt so sicher nicht - wieso?
    @Override
    public Quality rated(Part p) {

       return this.getQuality().atLeast(p.getQuality())?p.getQuality():this.getQuality();
    }

    /**
     * @return
     */
    // TODO: lass mas so, sonst wirds nie fertig
    @Override
    public Quality getQuality() {
        return quality;
    }

    /**
     * Sets the criterion used for rated, if there is no P given.
     *
     * @param part
     */
    @Override
    public void setCriterion(Part part) {
        this.criterion = part;
    }

    /**
     * Rates R based on P set with setCriterion.
     *
     * @return a new Object of R with rated properties.
     * @throws NoCriterionSetException if the criterion was not set with {@link #setCriterion(Part)} before calling this method.
     */
    @Override
    public Quality rated() throws NoCriterionSetException {
        if (criterion == null) {
            throw new NoCriterionSetException();
        }

        return rated(criterion);
    }
}
