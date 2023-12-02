@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "height and width can only be set once",
        invariants = "",
        description = "Substrate type that can be put into a nest"
)
public class GlassConcrete implements NestInteriorMaterial{
    private float height = 0;
    private float width = 0;

    public GlassConcrete() {

    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            historyConstrains = "placeDimensions must be called first",
            postconditions = "returns volume of water tank"
    )
    public float getHeight() throws NoProperitytSetException {

        if (this.height == 0) throw new NoProperitytSetException("no height set");
        return height;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            preconditions = "height and width must be > 0",
            postconditions = "returns volume of water tank"
    )
    public void placeDimensions(float height, float width) throws AlreadySetException {

        if (height == 0 && width == 0){
            this.height = height;
            this.width =width;
        }else throw new AlreadySetException("dimensions were already set");
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            historyConstrains = "placeDimensions must be called first",
            postconditions = "returns volume of water tank"
    )
    public float getWidth() throws NoProperitytSetException {
        if (this.width == 0) throw new NoProperitytSetException("no width set");
        return width;
    }
}
