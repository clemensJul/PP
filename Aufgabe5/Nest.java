public class Nest implements Part {

    private final int anSize;
    private final Quality quality;
    private Part criterion;

    /**
     * @param size sets the recommended ant size in mm for this nest
     */
    private Nest(int size, String quality) {
        this.anSize = size;
        if (!quality.equals("NOTUSABLE")) {
            quality = "BEGINNER";
        }
        this.quality = Quality.getQuality(quality);    }

    /**
     * returns an object of type Quality. Most of the time it will be a worst object, but if two parts are not compatible return "NOTUSABLE"
     *
     * @param p P must be != null
     * @return
     */
    @Override
    public Quality rated(Part p) {
        return null;
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
     * @throws NoCriterionSetException if the criterion was not set with {@link #setCriterion(Object)} before calling this method.
     */
    @Override
    public Quality rated() throws NoCriterionSetException {
        if(criterion == null) {
            throw new NoCriterionSetException();
        }
        return null;    }
}
