import java.util.ArrayList;

public class CompositeFormicarium extends Formicarium {
    public CompositeFormicarium(ArrayList<FormicariumPart> formicariumParts) {
        super(formicariumParts);
    }

    public boolean add(FormicariumPart item) {
        if (formicariumParts.contains(item)) {
            return false;
        }

        // check for compability otherwise throw exception
        formicariumParts.add(item);
        return true;
    }

    public boolean remove(FormicariumPart item) {
        return formicariumParts.remove(item);
    }
}
