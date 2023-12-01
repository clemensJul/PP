@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "",
        invariants = ""
)
public class SandClay implements NestInteriorMaterial {
    private float weight;
    public SandClay(float weight) {
        this.weight = weight;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns weight of sand clay"
    )
    public float getWeight() {
        return weight;
    }
}
