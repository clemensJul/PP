public class Nest implements Part {
    private final int anSize;
    private final Quality quality;
    private Part criterion;

    /**
     * @param size sets the recommended ant size in mm for this nest
     */
    public Nest(int size, String quality) {
        this.anSize = size;

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
    @Override
    public Quality rated(Part p) {
        if (p instanceof Nest) {
            return Quality.NOT_USABLE;
        }
        return this.quality.atLeast(p.getQuality())?p.getQuality():this.getQuality();
    }

    /**
     * @return
     */
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

    // todo keine ahnung was wir hier berechnen sollen bzw. wie
    public int antSize() {
        return anSize;
    }
}
