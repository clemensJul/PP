import java.util.ArrayList;

public class CompositeFormicarium extends Formicarium {
    public CompositeFormicarium(ArrayList<FormicariumPart> formicariumParts) {
        super(formicariumParts);
    }

    public boolean add(FormicariumPart item) throws CompatibilityException{
        try {
            // check if item with same identity is already in List
            for (FormicariumPart part : formicariumParts) {
                if(part == item) {
                    return false;
                }
            }

            // check for compatibility otherwise throw exception
            this.compatibility().compatible(item.compatibility());
            formicariumParts.add(item);
            return true;
        }catch (CompatibilityException e){
            throw e;
        }
    }

    public boolean remove(FormicariumPart item) {
        return formicariumParts.remove(item);
    }
}
