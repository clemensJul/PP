@CodedBy("Clemens")
@SignatureAndAssertions(
        description = "gets thrown if no properties of NestInteriorMaterial are already set"
)
public class NoProperitytSetException extends Exception{
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NoProperitytSetException(String message) {
        super(message);
    }
}
