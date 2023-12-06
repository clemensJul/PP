public class LargeTropicalAnt extends Ant {
    @Override
    public Fitable fits(Formicarium formicarium) {
        return formicarium.fittedByLargeTropical(this);
    }

    @Override
    public String toString() {
        return "LargeTropicalAnt";
    }
}
