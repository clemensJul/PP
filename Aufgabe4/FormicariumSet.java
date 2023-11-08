import java.util.ArrayList;
import java.util.List;

public class FormicariumSet {
    private List<FormicariumItem> formicariumItems;

    public FormicariumSet(List<FormicariumItem> formicariumItems) {
        this.formicariumItems = new ArrayList<>();
        this.formicariumItems.addAll(formicariumItems);
    }
}
