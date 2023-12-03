import java.util.ArrayList;
import java.util.HashMap;

@CodedBy("Raphael")
@SignatureAndAssertions(
        description = "An AnnotationObject describes the statistics of an User"
)
public class UserAnnotationObject {
    private final HashMap<String, Integer> methodsInClass = new HashMap<>();
    private final HashMap<String, Integer> constructorsInClass = new HashMap<>();
    private final ArrayList<String> responsibleEntities = new ArrayList<>();
    private int assertions = 0;

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "returns a HashMap of a histogram of the users methods in class"
    )
    public HashMap<String, Integer> getMethodsInClass() {
        return methodsInClass;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "returns a HashMap of a histogram of the users constructors in class"
    )
    public HashMap<String, Integer> getConstructorsInClass() {
        return constructorsInClass;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Returns a List of the Entities the user is responsible for"
    )
    public ArrayList<String> getResponsibleEntities() {
        return responsibleEntities;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Returns a counter of written assertion by the user"
    )
    public int getAssertions() {
        return assertions;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "increases assertionsCounter by one"
    )
    public void increaseAssertions() {
        assertions++;
    }
}
