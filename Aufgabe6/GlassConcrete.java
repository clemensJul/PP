@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstraints = "height and width can only be set once",
        description = "Substrate type that can be put into a nest"
)
public class GlassConcrete implements NestInteriorMaterial {
    private float concreteHeight = 0;
    private float concreteWidth = 0;

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            historyConstraints = "placeDimensions must be called first",
            postconditions = "returns volume of water tank"
    )
    public float getHeight() throws NoProperitytSetException {
        if (this.concreteHeight == 0) {
            throw new NoProperitytSetException("no height set");
        }
        return concreteHeight;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            preconditions = "height and width must be > 0",
            postconditions = "returns volume of water tank"
    )
    public void placeDimensions(float height, float width) throws AlreadySetException {
        if (this.concreteHeight == 0 && this.concreteWidth == 0) {
            this.concreteHeight = height;
            this.concreteWidth = width;
        } else {
            throw new AlreadySetException("dimensions were already set");
        }
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            historyConstraints = "placeDimensions must be called first",
            postconditions = "returns volume of water tank"
    )
    public float getWidth() throws NoProperitytSetException {
        if (this.concreteWidth == 0) {
            throw new NoProperitytSetException("no width set");
        }
        return concreteWidth;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Returns a string representation of this GlassConcrete"
    )
    @Override
    public String toString() {
        return "GlassConcrete";
    }
}
