public class Nest implements Part<Nest> {

    private final int anSize;

    /**
     * @param size sets the recommended ant size in mm for this nest
     */
    private Nest(int size) {
        this.anSize = size;
    }

    /**
     * Two nests are incompatible.
     *
     * @param nest P must be != null
     * @return
     */
    @Override
    public Quality rated(Nest nest) {
        return null;
    }

    /**
     * @param nest
     */
    @Override
    public void setCriterion(Nest nest) {

    }

    /**
     * @return
     * @throws NoCriterionSetException
     */
    @Override
    public Quality rated() throws NoCriterionSetException {
        return null;
    }
}
