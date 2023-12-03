@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "weight can only be set once",
        invariants = "",
        description = "Substrate type that can be put into a nest"
)
public class SandClay implements NestInteriorMaterial {
    private float weight;

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            preconditions = "must not be called multiple times - weight must be > 0",
            postconditions = "returns weight of sand clay and locks the method from "
    )
    public void fillSandClay(float weight) throws AlreadySetException {
        if (this.weight == 0) {
            this.weight = weight;
            return;
        }
        throw new AlreadySetException("weight was already set");
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            historyConstrains = "fillSandClay must be called first",
            postconditions = "returns weight of sand clay"
    )
    public float getWeight() throws NoProperitytSetException {
        if (this.weight == 0) {
            throw new NoProperitytSetException("no weight was set");
        }
        return weight;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Returns a string representation of this SandClay"
    )
    @Override
    public String toString() {
        return "SandClay";
    }
}
