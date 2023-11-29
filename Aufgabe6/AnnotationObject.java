import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnnotationObject {
    private final HashMap<String, Integer> methodsInClass = new HashMap<>();
    private final HashMap<String, Integer> constructorsInClass = new HashMap<>();
    private final ArrayList<String> responsibleEntities = new ArrayList<>();
    private int assertions = 0;

    public HashMap<String, Integer> getMethodsInClass() {
        return methodsInClass;
    }

    public HashMap<String, Integer> getConstructorsInClass() {
        return constructorsInClass;
    }

    public ArrayList<String> getResponsibleEntities() {
        return responsibleEntities;
    }

    public int getAssertions() {
        return assertions;
    }

    public void increaseAssertions() {
        assertions++;
    }
}
