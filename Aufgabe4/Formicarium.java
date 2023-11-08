import java.util.ArrayList;

public class Formicarium implements FormicariumPart {
    ArrayList<FormicariumPart> formicariumParts;

    public Formicarium(ArrayList<FormicariumPart> formicariumParts) {
        this.formicariumParts = new ArrayList<>();
        this.formicariumParts.addAll(formicariumParts);
    }

    @Override
    public boolean compatibility(Compatible compare) {
        return false;
    }
}
