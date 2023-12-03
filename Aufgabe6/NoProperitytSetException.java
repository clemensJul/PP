@CodedBy("Clemens")
@SignatureAndAssertions(
        description = "gets thrown if no properties of NestInteriorMaterial are already set"
)
public class NoProperitytSetException extends Exception {
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "Initializes a new NoPropertySetException"
    )
    public NoProperitytSetException(String message) {
        super(message);
    }
}
