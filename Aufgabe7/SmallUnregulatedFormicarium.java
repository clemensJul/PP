public class SmallUnregulatedFormicarium extends UnregulatedFormicarium {
    @Override
    Fitable fittedBySmallEuropean(SmallEuropeanAnt ant) {
        return Fitable.PERFECT;
    }

    @Override
    Fitable fittedByMediumEuropean(MediumEuropeanAnt ant) {
        return Fitable.TOO_LARGE;
    }

    @Override
    Fitable fittedByLargeEuropean(LargeEuropeanAnt ant) {
        return Fitable.TOO_XLARGE;
    }
}
