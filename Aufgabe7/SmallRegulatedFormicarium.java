public class SmallRegulatedFormicarium extends RegulatedFormicarium {
    public SmallRegulatedFormicarium(double price) {
        super(price);
    }

    @Override
    Fitable fittedBySmallTropical(SmallTropicalAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    Fitable fittedByMediumTropical(MediumTropicalAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    Fitable fittedByLargeTropical(LargeTropicalAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    public String toString() {
        return "SmallRegulatedFormicarium - " + price();
    }
}
