public class SmallRegulatedFormicarium extends RegulatedFormicarium {
    @Override
    Fitable fittedBySmallTropical(SmallTropicalAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    Fitable fittedByMediumTropical(MediumTropicalAnt ant) {
        return Fitable.TOO_LARGE;
    }

    @Override
    Fitable fittedByLargeTropical(LargeTropicalAnt ant) {
        return Fitable.TOO_XLARGE;
    }
}
