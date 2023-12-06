public class LargeEuropeanAnt extends Ant {
    @Override
    public Fitable fits(Formicarium formicarium) {
        return formicarium.fittedByLargeEuropean(this);
    }

    @Override
    public String toString() {
        return "LargeEuropeanAnt";
    }
}
