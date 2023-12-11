/**
 *History-invariant: counts the number of times Formicarium.fittedBy or any of it's subclass methods are called is called
 */
public aspect VisitorAspect {

    private static int visitorMethodsCount = 0;

    pointcut visitorCalls():
            (execution(* Ant.fits()) ||
                    execution(* Formicarium.fittedBy*()) ||
                    within(Formicarium+) &&
                            execution(* *(..))
                    );

    after(): visitorCalls() {
        visitorMethodsCount++;
    }

    public static int getVisitorMethodsCount() {
        return visitorMethodsCount;
    }
}
