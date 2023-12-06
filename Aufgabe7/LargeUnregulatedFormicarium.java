public class LargeUnregulatedFormicarium extends UnregulatedFormicarium {
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
        return Fitable.PERFECT;
    }
}
