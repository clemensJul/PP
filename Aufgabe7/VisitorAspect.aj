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
