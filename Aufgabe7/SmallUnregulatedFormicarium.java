public class SmallUnregulatedFormicarium extends UnregulatedFormicarium {
    public SmallUnregulatedFormicarium(double price) {
        super(price);
    }

    @Override
    Fitable fittedBySmallEuropean(SmallEuropeanAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    Fitable fittedByMediumEuropean(MediumEuropeanAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    Fitable fittedByLargeEuropean(LargeEuropeanAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    public String toString() {
        return "SmallUnregulatedFormicarium - " + price();
    }
}
