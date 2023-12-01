@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "",
        invariants = ""
)
public class GlassConcrete implements NestInteriorMaterial{
    private float height;
    private float width;

    public GlassConcrete(float height, float width) {
        this.height = height;
        this.width = width;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns volume of water tank"
    )
    public float getHeight() {
        return height;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns volume of water tank"
    )
    public float getWidth() {
        return width;
    }
}
