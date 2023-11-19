import java.util.*;

public class FormicariumSet implements Iterable<FormicariumItem> {
    private final ArrayList<FormicariumItem> formicariumItems;

    public FormicariumSet(ArrayList<FormicariumItem> formicariumItems) {
        this.formicariumItems = new ArrayList<>();
        this.formicariumItems.addAll(formicariumItems);
    }

    public void add(FormicariumItem item) {
        if(checkRecursiveIdentity(item)) {
            return;
        }

        formicariumItems.add(item);
    }

    private boolean checkRecursiveIdentity(FormicariumItem item) {
        return formicariumItems.stream().anyMatch(part -> {
            if(part instanceof Formicarium) {
                return checkRecursiveIdentity(part);
            }

            return part == item;
        });
    }

    @Override
    public Iterator<FormicariumItem> iterator() {
        return new FormicariumSetIterator(formicariumItems);
    }
}
