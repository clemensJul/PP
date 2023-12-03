@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "",
        invariants = "",
        description = "type of nest that uses a watertank to create a moist environment"
)
public class MoistNest extends Nest {
    private float waterTankVolume;

    public MoistNest(float height, float width, NestInteriorMaterial material, float waterTankVolume) {
        super(height, width, material);
        this.waterTankVolume = waterTankVolume;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns volume of water tank"
    )
    public float getWaterTankVolume() {
        return waterTankVolume;
    }
}
