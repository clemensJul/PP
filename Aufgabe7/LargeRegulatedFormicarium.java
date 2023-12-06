public class LargeRegulatedFormicarium extends RegulatedFormicarium {
    @Override
    Fitable fittedBySmallTropical(SmallTropicalAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    Fitable fittedByMediumTropical(MediumTropicalAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    Fitable fittedByLargeTropical(LargeTropicalAnt ant) {
        return Fitable.PERFECT;
    }
}
