public class Nest implements Part {
    private final int anSize;
    private final Quality quality;
    private Part criterion;

    /**
     * Initializes a Nest.
     *
     * @param size    sets the recommended ant size in mm for this nest
     * @param quality Quality
     */
    public Nest(int size, String quality) {
        this.anSize = size;

        if (!quality.equals("NOT_USABLE")) {
            quality = "BEGINNER";
        }
        this.quality = Quality.getQuality(quality);
    }

    /**
     * Returns the best quality of this and p.
     * If P is another Nest, the Quality "NOT_USABLE" is returned, since in a Formicarium there can only be one Nest.
     *
     * @param p P must be != null
     * @return rated Quality
     */
    @Override
    public Quality rated(Part p) {
        if (p instanceof Nest) {
            return Quality.NOT_USABLE;
        }
        return this.quality.atLeast(p.getQuality()) ? p.getQuality() : this.getQuality();
    }

    /**
     * @return quality of this.
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
     * Returns the best quality of this and the criterion set with {@link #setCriterion(Part)}..
     * If P is another Nest, the Quality "NOT_USABLE" is returned, since in a Formicarium there can only be one Nest.
     *
     * @return rated Quality
     * @throws NoCriterionSetException if the criterion was not set with {@link #setCriterion(Part)} before calling this method.
     */
    @Override
    public Quality rated() throws NoCriterionSetException {
        if (criterion == null) {
            throw new NoCriterionSetException();
        }
        return rated(criterion);
    }

    /**
     * @return the average recommended antSize for the Nest.
     */
    public int antSize() {
        return anSize;
    }
}
