public aspect AJTest {

    private static int returnFormCount = 0;

    pointcut returnFormExecution():
            execution(* Institute.returnForm(..));

    after() returning: returnFormExecution() {
        returnFormCount++;
        System.out.println("returnForm method called. Count: " + returnFormCount);
    }

    public static int getReturnFormCount() {
        return returnFormCount;
    }
}
