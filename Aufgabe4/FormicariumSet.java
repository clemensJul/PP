import java.util.*;

public class FormicariumSet implements Iterable<FormicariumItem> {
    private final ArrayList<FormicariumItem> formicariumItems;

    public FormicariumSet(ArrayList<FormicariumItem> formicariumItems) {
        this.formicariumItems = new ArrayList<>();
        this.formicariumItems.addAll(formicariumItems);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormicariumSet that)) return false;
        return Objects.equals(formicariumItems, that.formicariumItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formicariumItems);
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
