import java.util.ArrayList;

public class CompositeFormicarium extends Formicarium {
    public CompositeFormicarium(ArrayList<FormicariumPart> formicariumParts) {
        super(formicariumParts);
    }

    // elements without same identity are allowed
    public boolean add(FormicariumPart item) throws Exception {
        if(checkRecursiveIdentity(item)) {
            return false;
        }

        compatibility().compatible(item.compatibility());
        // compatibility should throw exception if not compatible
        formicariumParts.add(item);
        return true;
    }

    private boolean checkRecursiveIdentity(FormicariumPart item) {
        return formicariumParts.stream().anyMatch(part -> {
            if(part instanceof Formicarium) {
                return checkRecursiveIdentity(part);
            }

            return part == item;
        });
    }

    public boolean remove(FormicariumPart item) {
        return formicariumParts.remove(item);
    }
}
