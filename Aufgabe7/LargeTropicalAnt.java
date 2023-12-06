public class LargeTropicalAnt extends TropicalAnt {
    @Override
    public Fitable fits(Formicarium formicarium) {
        return formicarium.fittedByLargeTropical(this);
    }
}
