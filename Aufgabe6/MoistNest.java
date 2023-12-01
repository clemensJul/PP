@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "",
        invariants = ""
)
public class MoistNest extends Nest{
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
