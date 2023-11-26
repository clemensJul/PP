public class Arena implements Part {
    private final float volume;
    private final Quality quality;
    private Part criterion;

    /**
     * Initializes an Arena.
     *
     * @param volume  Volume
     * @param quality Quality
     */
    public Arena(float volume, String quality) {
        this.volume = volume;
        if (!quality.equals("NOT_USABLE")) {
            quality = "BEGINNER";
        }
        this.quality = Quality.getQuality(quality);
    }

    /**
     * Returns the best quality of this and p.
     *
     * @param p P must be != null
     * @return Quality
     */
    @Override
    public Quality rated(Part p) {
        return this.getQuality().atLeast(p.getQuality()) ? p.getQuality() : this.getQuality();
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
     * @param part Criterion to set, must be != null
     */
    @Override
    public void setCriterion(Part part) {
        this.criterion = part;
    }

    /**
     * Returns the best quality of this and the criterion set with {@link #setCriterion(Part)}.
     *
     * @return rated Quality.
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
     * @return the volume of the Arena.
     */
    public float volume() {
        return volume;
    }
}
