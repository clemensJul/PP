@CodedBy("Clemens")
@SignatureAndAssertions(
        historyConstrains = "material muss not be null"
)
public abstract class Nest {

    private static int id_counter = 0;
    private float depth;
    private float height;
    private float width;
    private int id;
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

    //TODO: hab lang rumprobiert, auch mit den multimethoden aber die gehn halt nid wirklich bei verschiedenen return types
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns weight of nest substrate"
    )
    public float getSubstrateWeight(){
        if (material instanceof SandClay sandClay){
            return sandClay.getWeight();
        } return 0f;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns height of nest substrate"
    )
    public float getSubstrateHeight(){
        if (material instanceof GlassConcrete sandClay){
            return sandClay.getHeight();
        } return 0f;
    }
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns width of nest substrate"
    )
    public float getSubstrateWidth(){
        if (material instanceof GlassConcrete sandClay){
            return sandClay.getWidth();
        } return 0f;
    }
}
