public class MediumRegulatedFormicarium extends RegulatedFormicarium {

    public MediumRegulatedFormicarium(double price) {
        super(price);
    }

    @Override
    Fitable fittedBySmallTropical(SmallTropicalAnt ant) {
        return Fitable.TOO_LARGE;
    }

    @Override
    Fitable fittedByMediumTropical(MediumTropicalAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    Fitable fittedByLargeTropical(LargeTropicalAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    public String toString() {
        return "MediumRegulatedFormicarium - " + price();
    }
}
