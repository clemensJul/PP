public abstract class RegulatedFormicarium extends Formicarium {
    /**
     * This function increments the value of the map associated by function by one.
     * If the value does not already exist, it gets added to the map.
     *
     * @param ant
     */
    @Override
    Fitable fittedBySmallEuropean(SmallEuropeanAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    Fitable fittedByMediumEuropean(MediumEuropeanAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    Fitable fittedByLargeEuropean(LargeEuropeanAnt ant) {
        return Fitable.TOO_SMALL;
    }
}
