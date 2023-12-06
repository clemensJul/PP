public class SmallEuropeanAnt extends Ant {
    @Override
    public Fitable fits(Formicarium formicarium) {
        return formicarium.fittedBySmallEuropean(this);
    }

    @Override
    public String toString() {
        return "SmallEuropeanAnt";
    }
}
