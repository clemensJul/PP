@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "weight can only be set once",
        invariants = ""
)
public class SandClay implements NestInteriorMaterial {
    private float weight;
    private boolean alreadySet;
    public SandClay() {
        alreadySet = false;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            preconditions = "must not be called multiple times - weight must be > 0",
            postconditions = "returns weight of sand clay and locks the method from "
    )
    public void fillSandClay(float weight) throws AlreadySetException {
        if (alreadySet) throw new AlreadySetException("weight was already set");
        this.weight = weight;
        this.alreadySet = true;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            historyConstrains = "fillSandClay must be called first",
            postconditions = "returns weight of sand clay"
    )
    public float getWeight() {
        return weight;
    }
}
