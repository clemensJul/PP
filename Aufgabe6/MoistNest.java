@CodedBy("Clemens")
@SignatureAndAssertions(
        description = "type of nest that uses a watertank to create a moist environment"
)
public class MoistNest extends Nest {
    private float waterTankVolume;

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            preconditions = "Height, Width and waterTankVolume must be > 0 and material must be != null",
            postconditions = "Initializes a new MoistNest"
    )
    public MoistNest(float height, float width, NestInteriorMaterial material, float waterTankVolume) {
        super(height, width, material);
        this.waterTankVolume = waterTankVolume;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "Returns volume of water tank"
    )
    public float getWaterTankVolume() {
        return waterTankVolume;
    }
}
