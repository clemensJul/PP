public class Forceps implements Instrument {
    @Override
    public boolean compatibility(Compatible compare) {
        return false;
    }

    @Override
    public EUsage quality() {
        // do calculatiions for evaluating which value to return
        return EUsage.PRO;
    }
}
