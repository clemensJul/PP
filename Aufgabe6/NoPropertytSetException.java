@CodedBy("Clemens")
@SignatureAndAssertions(
        description = "gets thrown if no properties of NestInteriorMaterial are already set"
)
public class NoPropertytSetException extends Exception {
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "Initializes a new NoPropertySetException"
    )
    public NoPropertytSetException(String message) {
        super(message);
    }
}
