import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR})
@CodedBy("Raphael")
public @interface SignatureAndAssertions {
    String preconditions() default "";
    String postconditions() default "";
    String invariants() default "";
    String historyConstrains() default "";
}
