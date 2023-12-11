
public abstract class Formicarium {
    private final double price;

    public Formicarium(double price) {
        this.price = price;
    }

    /**
     * This function returns an enum Fitable which describes the compatibility of the Ant with the Formicarium.
     *
     * @param ant SmallEuropeanAnt
     * @return enum Fitable
     */
    abstract Fitable fittedBySmallEuropean(SmallEuropeanAnt ant);

    /**
     * This function returns an enum Fitable which describes the compatibility of the Ant with the Formicarium.
     *
     * @param ant MediumEuropeanAnt
     * @return enum Fitable
     */
    abstract Fitable fittedByMediumEuropean(MediumEuropeanAnt ant);

    /**
     * This function returns an enum Fitable which describes the compatibility of the Ant with the Formicarium.
     *
     * @param ant LargeEuropeanAnt
     * @return enum Fitable
     */
    abstract Fitable fittedByLargeEuropean(LargeEuropeanAnt ant);

    /**
     * This function returns an enum Fitable which describes the compatibility of the Ant with the SmallTropicalAnt.
     *
     * @param ant SmallEuropeanAnt
     * @return enum Fitable
     */
    abstract Fitable fittedBySmallTropical(SmallTropicalAnt ant);

    /**
     * This function returns an enum Fitable which describes the compatibility of the Ant with the Formicarium.
     *
     * @param ant MediumTropicalAnt
     * @return enum Fitable
     */
    abstract Fitable fittedByMediumTropical(MediumTropicalAnt ant);

    /**
     * This function returns an enum Fitable which describes the compatibility of the Ant with the Formicarium.
     *
     * @param ant LargeTropicalAnt
     * @return enum Fitable
     */
    abstract Fitable fittedByLargeTropical(LargeTropicalAnt ant);

    public double price() {
        return this.price;
    }
}
