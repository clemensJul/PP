import java.util.function.DoubleUnaryOperator;

public class Numeric implements DoubleUnaryOperator, Rated<DoubleUnaryOperator, Numeric>, Calc<Numeric> {
    private final double value;
    private DoubleUnaryOperator criterion;

    public Numeric(double value) {
        this.value = value;
    }

    /**
     * Returns the sum of two this and the object in add
     *
     * @param add must not be null
     * @return is never null
     */
    @Override
    public Numeric sum(Numeric add) {
        return new Numeric(this.value + add.value);
    }

    /**
     * Returns a new Numeric object with the value of this divided by the ratio parameter.
     *
     * @param ratio must not be null and not be 0
     * @return new Numeric object with adjusted value.
     */
    @Override
    public Numeric ratio(int ratio) {
        return new Numeric(this.value / ratio);
    }

    /**
     * returns boolean TRUE if @this is bigger or equal to
     *
     * @param compareTo must not be null
     * @return always returns true or false
     */
    @Override
    public boolean atLeast(Numeric compareTo) {
        return this.value >= compareTo.value;
    }

    /**
     * @param doubleUnaryOperator doubleUnaryOperator must be != null
     * @return a new Object of R with rated properties.
     */
    @Override
    public Numeric rated(DoubleUnaryOperator doubleUnaryOperator) {
        double ratedValue = doubleUnaryOperator.applyAsDouble(this.value);
        return new Numeric(ratedValue);
    }

    /**
     * Sets the criterion used for rated, if there is no P given.
     *
     * @param doubleUnaryOperator criterion to set
     */
    @Override
    public void setCriterion(DoubleUnaryOperator doubleUnaryOperator) {
        criterion = doubleUnaryOperator;
    }

    /**
     * Rates R based on P set with setCriterion.
     *
     * @return a new Object of R with rated properties.
     * @throws NoCriterionSetException if the criterion was not set with {@link #setCriterion(DoubleUnaryOperator)} before calling this method.
     */
    @Override
    public Numeric rated() throws NoCriterionSetException {
        if (criterion == null) {
            throw new NoCriterionSetException();
        }

        double ratedValue = criterion.applyAsDouble(this.value);
        return new Numeric(ratedValue);
    }

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     */
    // todo da bin i mir auch nit sicher wie man das richtig macht.
    @Override
    public double applyAsDouble(double operand) {
        return this.value + operand;
    }
}
