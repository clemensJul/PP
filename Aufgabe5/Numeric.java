import java.util.function.DoubleUnaryOperator;

public class Numeric<P extends DoubleUnaryOperator> implements Calc<Numeric<P>>, Rated<P, Numeric<P>>{
    double value;
    P criteria;

    public Numeric(Double value) {
        this.value = value;
    }

    /**
     * @param add must not be null
     * @return
     */
    @Override
    public Numeric<P> sum(Numeric<P> add) {
        return new Numeric<>(value + add.value);
    }

    /**
     * @param ratio must not be null
     * @return
     */
    @Override
    public Numeric<P> ratio(int ratio) {
        return new Numeric<>(value / ratio);
    }

    /**
     * @param compareTo must not be null
     * @return
     */
    @Override
    public boolean atLeast(Numeric<P> compareTo) {
        return value >= compareTo.value;
    }

    /**
     * @param p P must be != null
     * @return
     */
    @Override
    public Numeric<P> rated(P p) {
        return new Numeric<>(p.applyAsDouble(value));
    }

    /**
     * @param p
     */
    @Override
    public void setCriterion(P p) {
        criteria = p;
    }

    /**
     * @return
     * @throws NoCriterionSetException
     */
    @Override
    public Numeric<P> rated() throws NoCriterionSetException {
        if (criteria == null) {
            throw new NoCriterionSetException("");
        }
        return new Numeric<>(criteria.applyAsDouble(value));
    }
}
