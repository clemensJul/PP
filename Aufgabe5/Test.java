import java.util.function.DoubleUnaryOperator;

public class Test {
    public static void main(String[] args) {
        StatSet<Numeric, Numeric, Numeric> nnn = new StatSet<>();
        StatSet<Part, Part, Quality> ppq = new StatSet<>();
        StatSet<Arena, Part, Quality> apq = new StatSet<>();
        StatSet<Nest, Part, Quality> npq = new StatSet<>();
        StatSet<Part, Arena, Quality> paq = new StatSet<>();
        StatSet<Arena, Arena, Quality> aaq = new StatSet<>();
        StatSet<Nest, Arena, Quality> naq = new StatSet<>();
        StatSet<Part, Nest, Quality> pnq = new StatSet<>();
        StatSet<Arena, Nest, Quality> anq = new StatSet<>();
        StatSet<Nest, Nest, Quality> nnq = new StatSet<>();
        CompatibilitySet<Numeric, Numeric> nn = new CompatibilitySet<>();
        CompatibilitySet<Part, Quality> pq = new CompatibilitySet<>();
        CompatibilitySet<Arena, Quality> aq = new CompatibilitySet<>();
        CompatibilitySet<Nest, Quality> nq = new CompatibilitySet<>();

        Quality s = Quality.NOT_USABLE;
    }
}
