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

        // add some items to sets
        Numeric numeric = new Numeric(12);
        Nest nest = new Nest(23, "BEGINNER");
        Arena arena = new Arena(100, "BEGINNER");
        apq.add(arena);
        apq.addCriterion(nest);
        apq.addCriterion(arena);

        npq.add(nest);
        npq.addCriterion(nest);
        npq.addCriterion(arena);
        npq.addCriterion(new Nest(12, "PRO"));

        paq.add(nest);
        paq.addCriterion(arena);

        aaq.add(arena);
        aaq.addCriterion(new Arena(120, "SEMIPRO"));
        aaq.addCriterion(arena);
        aaq.addCriterion(new Arena(50, "BEGINNER"));

        naq.add(nest);
        naq.addCriterion(arena);

        nnq.add(nest);
        nnq.addCriterion(nest);

        nn.add(new Numeric(0));
        nn.addCriterion(numeric);

        pq.add(arena);
        pq.add(nest);

        aq.add(arena);
        aq.addCriterion(arena);

        nq.add(nest);
        nq.addCriterion(nest);

        StatSet<Part, Nest, Quality> a = pnq;
        StatSet<Part, Part, Quality> b = ppq;
        StatSet<Arena, Nest, Quality> c = anq;

        // add items to c
        c.add(new Arena(129, "SEMIPRO"));
        c.add(new Arena(3, "BEGINNER"));
        c.add(new Arena(129, "SEMIPRO"));
        c.addCriterion(new Nest(3, "PRO"));
        c.addCriterion(new Nest(30, "BEGINNER"));

        for (Iterator<Arena> iterator = c.iterator(); iterator.hasNext(); ) {
            Arena p = iterator.next();
            p.volume();
            a.add(p);
            b.addCriterion(p);
        }

        for (Iterator<Nest> iterator = c.criterions(); iterator.hasNext(); ) {
            Nest p = iterator.next();
            p.antSize();
            a.add(p);
            b.addCriterion(p);
        }

        {
            System.out.println("test adding values to StatSet items");
            Numeric numeric1 = new Numeric(12);
            nnn.add(numeric1);

            int counter = 0;
            for (Numeric ignored : nnn) {
                counter++;
            }

            testValue(counter, 1);

            // should not be added
            nnn.add(numeric1);
            counter = 0;
            for (Numeric ignored : nnn) {
                counter++;
            }
            testValue(counter, 1);

            nnn.add(new Numeric(2));
            nnn.add(new Numeric(6));
            counter = 0;
            for (Numeric ignored : nnn) {
                counter++;
            }
            testValue(counter, 3);

            System.out.println("test adding values to StatSet criteria");
            nnn.addCriterion(numeric1);

            counter = 0;
            for (Iterator<Numeric> it = nnn.criterions(); it.hasNext(); ) {
                Numeric ignored = it.next();
                counter++;
            }

            testValue(counter, 1);

            // should not be added
            nnn.addCriterion(numeric1);
            counter = 0;
            for (Iterator<Numeric> it = nnn.criterions(); it.hasNext(); ) {
                Numeric ignored = it.next();
                counter++;
            }
            testValue(counter, 1);

            nnn.addCriterion(new Numeric(5));
            nnn.addCriterion(new Numeric(8));
            counter = 0;
            for (Numeric ignored : nnn) {
                counter++;
            }
            testValue(counter, 3);

            System.out.println("test StatSet statistics");
            System.out.println(nnn.statistics());

            System.out.println("test StatSet iterator remove method");
            for (Iterator<Numeric> it = nnn.iterator(new Numeric(-2)); it.hasNext(); ) {
                it.next();
                it.remove();
            }

            for (Iterator<Numeric> it = nnn.iterator(); it.hasNext(); ) {
                it.next();
                it.remove();
            }

            counter = 0;
            for (Iterator<Numeric> it = nnn.iterator(); it.hasNext(); ) {
                it.next();
                counter++;
            }
            testValue(counter, 0);

            System.out.println("test StatSet check if removed value can be added again");
            nnn.add(numeric1);
            nnn.add(new Numeric(-23));
            counter = 0;
            for (Iterator<Numeric> it = nnn.iterator(); it.hasNext(); ) {
                it.next();
                counter++;
            }
            testValue(counter, 2);


            System.out.println("test StatSet criterions iterator remove method");
            counter = 3;
            for (Iterator<Numeric> it = nnn.criterions(); it.hasNext(); ) {
                it.next();
                it.remove();
                counter--;
            }

            counter = 0;
            for (Iterator<Numeric> it = nnn.criterions(); it.hasNext(); ) {
                it.next();
                counter++;
            }
            testValue(counter, 0);
        }

        {
            System.out.println("Test numerics");
            // Erstelle einige Numeric-Objekte
            Numeric num1 = new Numeric(5);
            Numeric num2 = new Numeric(10);

            // Teste die Summenbildung
            Numeric sum = num1.sum(num2);
            testValue(sum.getValue(), 15);

            // Teste die Ratioberechnung
            Numeric ratio = num2.ratio(2);
            testValue(ratio.getValue(), 5);

            // Teste die atLeast-Methode
            boolean isAtLeast = num2.atLeast(num1);
            testValue(isAtLeast, true);

            // Teste rated ohne parameter (x sollte quadriert werden)
            num1.setCriterion(x -> x * x);
            try {
                Numeric ratedNum = num1.rated();
                testValue(ratedNum.getValue(), 25);
            } catch (NoCriterionSetException ignored) {
            }

            // Teste rated mit parameter (x nur mehr die hälfte sein)
            testValue(num1.rated(x -> x / 2).getValue(), 2.5);

            // Noch ein lambda ausdruck, der alles unter 10 auf 0 setzt
            DoubleUnaryOperator floorIfSmallerThanTen = x -> {
                if (x < 10) {
                    return 0;
                }
                return x;
            };
            testValue(num1.rated(floorIfSmallerThanTen).getValue(), 0);
            testValue(num2.rated(floorIfSmallerThanTen).getValue(), 10);

            // lambda-ausdruck, der einfach um 1 erhöht
            testValue(num2.rated(x -> x + 1).getValue(), 11);

            // lambda-ausdruck, der das Vorzeichen umkehrt
            testValue(num1.rated(x -> x * (-1)).getValue(), -5);

            // Teste die applyAsDouble-Methode
            double result = num1.applyAsDouble(3);
            testValue(result, 8);
        }
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

    public static void testValue(double given, double expected) {
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


// Arbeitsaufteilung:
// Raphael: Generische Map/Liste, StatSet, CompatibilitySet, Testcases
// Clemens: Nest, Arena, Quality, Exception
// Gemeinsam: Erarbeiten und (Re)Faktorisieren der Generizität