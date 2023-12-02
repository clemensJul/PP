@CodedBy("Clemens")
@SignatureAndAssertions(
        description = "gets thrown if properties of NestInteriorMaterial are already set"
)
public class AlreadySetException extends Exception{
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "Initializes a new AlreadySetException with the given message"
    )
    public AlreadySetException(String message) {
        super(message);
    }
}
