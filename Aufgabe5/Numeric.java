import java.util.function.DoubleUnaryOperator;

public class Numeric implements Rated<DoubleUnaryOperator, Numeric>, Calc<Numeric> {
    double value;

    /**
     * returns the sum of two objects of type R. Sum does not necessarily be a number
     *
     * @param add must not be null
     * @return is never null
     */
    @Override
    public Numeric sum(Numeric add) {
        return null;
    }

    /**
     * returns the division of two objects of type R. Division does not necessarily be the same as for numbers
     *
     * @param ratio must not be null
     * @return
     */
    @Override
    public Numeric ratio(int ratio) {
        return null;
    }

    /**
     * returns boolean TRUE if @this is bigger or equal to
     *
     * @param compareTo must not be null
     * @return always returns true or false
     */
    @Override
    public boolean atLeast(Numeric compareTo) {
        return false;
    }

    /**
     * @param doubleUnaryOperator P must be != null
     * @return a new Object of R with rated properties.
     */
    @Override
    public Numeric rated(DoubleUnaryOperator doubleUnaryOperator) {
        return null;
    }

    /**
     * Sets the criterion used for rated, if there is no P given.
     *
     * @param doubleUnaryOperator
     */
    @Override
    public void setCriterion(DoubleUnaryOperator doubleUnaryOperator) {

    }

    /**
     * Rates R based on P set with setCriterion.
     *
     * @return a new Object of R with rated properties.
     * @throws NoCriterionSetException if the criterion was not set with {@link #setCriterion(Object)} before calling this method.
     */
    @Override
    public Numeric rated() throws NoCriterionSetException {
        return null;
    }
}
