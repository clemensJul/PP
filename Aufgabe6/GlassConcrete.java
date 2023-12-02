@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "height and width can only be set once",
        invariants = ""
)
public class GlassConcrete implements NestInteriorMaterial{
    private float height;
    private float width;

    public GlassConcrete() {

    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            historyConstrains = "placeDimensions must be called first",
            postconditions = "returns volume of water tank"
    )
    public float getHeight() {
        return height;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            historyConstrains = "placeDimensions must be called first",
            postconditions = "returns volume of water tank"
    )
    public float getWidth() {
        return width;
    }
}
