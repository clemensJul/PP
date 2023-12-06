public class MediumUnregulatedFormicarium extends UnregulatedFormicarium {
    @Override
    Fitable fittedBySmallEuropean(SmallEuropeanAnt ant) {
        return Fitable.TOO_SMALL;
    }

    @Override
    Fitable fittedByMediumEuropean(MediumEuropeanAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    Fitable fittedByLargeEuropean(LargeEuropeanAnt ant) {
        return Fitable.TOO_LARGE;
    }
}
