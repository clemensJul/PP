import java.util.HashMap;

public class ClassAnnotationObject {
    private final HashMap<String, SignatureAndAssertions> assertionsPerMethod = new HashMap<>();

    public HashMap<String, SignatureAndAssertions> getAssertionsPerMethod() {
        return assertionsPerMethod;
    }
}
