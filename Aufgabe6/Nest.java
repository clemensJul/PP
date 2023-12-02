import java.util.Objects;

@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "material muss not be null",
        description = " abstract class of nest. Nest can only be exclusively moist nests or powered nest"
)
public abstract class Nest {
    private static int id_counter = 1;
    private final float depth;
    private final float height;
    private final float width;
    private final int id;
    private NestInteriorMaterial material;

    public Nest(float height, float width, NestInteriorMaterial material) {
        this.height = height;
        this.width = width;
        this.material = material;

        this.id = id_counter++;
        this.depth = 2f;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            preconditions = "material is a subtype of NestInteriorMaterial",
            postconditions = "material of nest is changed to parameter"
    )
    public void changeInteriorMaterial(NestInteriorMaterial material){
        this.material = material;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns volume by multiplying every dimension"
    )
    public float getVolume(){
        return this.depth * this.height * this.width;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns height of nest"
    )
    public float getHeight() {
        return height;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns width of nest"
    )
    public float getWidth() {
        return width;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns depth" +
                    " of nest"
    )
    public float getDepth() {
        return depth;
    }

    //TODO: hab lang rumprobiert, auch mit den multimethoden aber die gehn halt nid wirklich bei verschiedenen return types
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns weight of nest substrate"
    )
    public float getSubstrateWeight() throws NoProperitytSetException {
        if (material instanceof SandClay sandClay){
            return sandClay.getWeight();
        } return 0f;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns height of nest substrate"
    )
    public float getSubstrateHeight() throws NoProperitytSetException {
        if (material instanceof GlassConcrete sandClay){
            return sandClay.getHeight();
        } return 0f;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns width of nest substrate"
    )
    public float getSubstrateWidth() throws NoProperitytSetException {
        if (material instanceof GlassConcrete sandClay){
            return sandClay.getWidth();
        } return 0f;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns substrate of nest"
    )
    public NestInteriorMaterial getMaterial() {
        return material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nest nest = (Nest) o;
        return this.id == nest.id;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "Returns the id of this nest"
    )
    public int getId() {
        return this.id;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Returns a string representation of this nest"
    )
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ID: ").append(id).append("\tMaterial: ").append(material.toString());

        return result.toString();
    }
}
