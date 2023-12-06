public class SmallTropicalAnt extends TropicalAnt {
    @Override
    public Fitable fits(Formicarium formicarium) {
        return formicarium.fittedBySmallTropical(this);
    }
}
