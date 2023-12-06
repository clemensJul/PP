public class MediumUnregulatedFormicarium extends UnregulatedFormicarium {
    public MediumUnregulatedFormicarium(double price) {
        super(price);
    }

    @Override
    Fitable fittedBySmallEuropean(SmallEuropeanAnt ant) {
        return Fitable.TOO_LARGE;
    }

    @Override
    Fitable fittedByMediumEuropean(MediumEuropeanAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    Fitable fittedByLargeEuropean(LargeEuropeanAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    public String toString() {
        return "MediumUnregulatedFormicarium - " + price();
    }
}
