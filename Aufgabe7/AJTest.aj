public aspect AJTest {

    private static int assignFormCount = 0;
    private static int visitorMethodsCount = 0;

    pointcut returnFormExecution():
            execution(* Institute.assignForm(..));

    after() returning: assignFormExecution() {
        assignFormCount++;
    }

    public static int getAssignFormCount() {
        return assignFormCount;
    }

    pointcut visitorCalls():
            (execution(* Ant.fits()) ||
                    execution(* Formicarium.fittedBy*()) ||
                    within(Formicarium+) &&
                            execution(* *(..))
                    );

    after(): visitorCalls() {
        visitorMethodsCount++;
    }

    public static int getVisitorMethodsCount(){return visitorMethodsCount;}
}
