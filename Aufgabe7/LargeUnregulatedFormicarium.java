public class LargeUnregulatedFormicarium extends UnregulatedFormicarium {
    public LargeUnregulatedFormicarium(double price) {
        super(price);
    }

    @Override
    Fitable fittedBySmallEuropean(SmallEuropeanAnt ant) {
        return Fitable.TOO_XLARGE;
    }

    @Override
    Fitable fittedByMediumEuropean(MediumEuropeanAnt ant) {
        return Fitable.TOO_LARGE;
    }

    @Override
    Fitable fittedByLargeEuropean(LargeEuropeanAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    public String toString() {
        return "LargeUnregulatedFormicarium - " + price();
    }
}
