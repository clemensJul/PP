public class SmallTropicalAnt extends Ant {
    @Override
    public Fitable fits(Formicarium formicarium) {
        return formicarium.fittedBySmallTropical(this);
    }

    @Override
    public String toString() {
        return "SmallTropicalAnt";
    }
}
