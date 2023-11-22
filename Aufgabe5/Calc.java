public interface Calc<R> {

    /**
     * returns the sum of two objects of type R. Sum does not necessarily be a number
     *
     * @param add must not be null
     * @return is never null
     */
    R sum(R add);

    /**
     * returns the division of two objects of type R. Division does not necessarily be the same as for numbers
     *
     * @param ratio must not be null
     * @return
     */

    R ratio(int ratio);

    /**
     * returns boolean TRUE if @this is bigger or equal to
     *
     * @param compareTo must not be null
     * @return always returns true or false
     */
    boolean atLeast(R compareTo);
}
