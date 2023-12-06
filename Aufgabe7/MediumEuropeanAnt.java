public class MediumEuropeanAnt extends Ant {
    @Override
    public Fitable fits(Formicarium formicarium) {
        return formicarium.fittedByMediumEuropean(this);
    }

    @Override
    public String toString() {
        return "MediumEuropeanAnt";
    }
}
