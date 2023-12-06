public class LargeRegulatedFormicarium extends RegulatedFormicarium {
    public LargeRegulatedFormicarium(double price) {
        super(price);
    }

    @Override
    Fitable fittedBySmallTropical(SmallTropicalAnt ant) {
        return Fitable.TOO_XLARGE;
    }

    @Override
    Fitable fittedByMediumTropical(MediumTropicalAnt ant) {
        return Fitable.TOO_LARGE;
    }

    @Override
    Fitable fittedByLargeTropical(LargeTropicalAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    public String toString() {
        return "LargeRegulatedFormicarium - " + price();
    }
}
