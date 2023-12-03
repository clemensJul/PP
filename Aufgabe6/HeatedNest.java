@CodedBy("Clemens")
@SignatureAndAssertions(
        description = "type of nest that stores it's power"
)
public class HeatedNest extends Nest {

    private int power;
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            preconditions = "Height, Width and Power must be > 0 and material must be != null",
            postconditions = "Initializes a new HeatedNest"
    )
    public HeatedNest(float height, float width, NestInteriorMaterial material, int power) {
        super(height, width, material);
        this.power = power;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "Returns power of heater"
    )
    public int getPower() {
        return power;
    }
}
