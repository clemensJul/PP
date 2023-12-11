/**
*History-invariant: counts the number of times Institute.assignForm() is called
 */
public aspect AssignFormAspect {

    private static int assignFormCount = 0;

    pointcut assignFormExecution():
            execution(* Institute.assignForm(..));

    after(): assignFormExecution() {
        assignFormCount++;
    }

    public static int getAssignFormCount() {
        return assignFormCount;
    }
}
