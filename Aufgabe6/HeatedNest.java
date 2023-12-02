@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "",
        invariants = "",
        description = "type of nest that stores it's power"
)
public class HeatedNest extends Nest{

    private int power;
    public HeatedNest(float height, float width, NestInteriorMaterial material,int power) {
        super(height, width, material);
        this.power = power;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns power of heater"
    )
    public int getPower() {
        return power;
    }
}
