import java.util.Iterator;
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

        {
            // test adding values to StatSet
            Numeric numeric1 = new Numeric(12);
            nnn.add(numeric1);

            int counter = 0;
            for (Numeric value : nnn) {
                counter++;
            }

            testValue(counter, 1);

            // should not be added
            nnn.add(numeric1);
            counter = 0;
            for (Numeric numeric : nnn) {
                counter++;
            }
            testValue(counter, 1);
        }

        Quality s = Quality.NOT_USABLE;
    }

    public static void testIdentity(Object given, Object expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }

    public static void testEquals(Object given, Object expected) {
        if (given.equals(expected)) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected.toString() + " / Given " +
                    "value: " + given.toString());
        }
    }

    public static void testValue(boolean given, boolean expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }

    public static void testValue(int given, int expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }
}
