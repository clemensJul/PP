public class MediumTropicalAnt extends Ant {
    @Override
    public Fitable fits(Formicarium formicarium) {
        return formicarium.fittedByMediumTropical(this);
    }

    @Override
    public String toString() {
        return "MediumTropicalAnt";
    }
}
