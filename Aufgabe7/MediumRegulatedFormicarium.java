public class MediumRegulatedFormicarium extends RegulatedFormicarium {
    @Override
    Fitable fittedBySmallTropical(SmallTropicalAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    Fitable fittedByMediumTropical(MediumTropicalAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    Fitable fittedByLargeTropical(LargeTropicalAnt ant) {
        return Fitable.TOO_SMALL;
    }
}
