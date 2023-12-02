import java.util.HashMap;

@CodedBy("Raphael")
@SignatureAndAssertions(
        postconditions = "returns a HashMap of a histogram of the users methods in class"
)
public class ClassAnnotationObject {
    private final HashMap<String, SignatureAndAssertions> assertionsPerMethod = new HashMap<>();

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Returns a HashMap which stores the name of a function as a key and the assertion as a value"
    )
    public HashMap<String, SignatureAndAssertions> getAssertionsPerMethod() {
        return assertionsPerMethod;
    }
}
