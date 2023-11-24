import java.util.ArrayList;
import java.util.Iterator;

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

//        fillStatSet(nnn);


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

        Quality s = Quality.NOT_USABLE;
    }

    // Generische Methode zur Befüllung eines StatSet mit Einträgen
    public static <A extends Rated<? super B, C>, B, C extends Calc<C>> void fillStatSet(StatSet<A, B, C> set) {
        ArrayList<A> poolForA = new ArrayList<>();
        ArrayList<B> poolForB = new ArrayList<>();
        ArrayList<C> poolForC = new ArrayList<>();

        poolForC.add((C) Quality.NOT_USABLE);
        poolForC.add((C) Quality.BEGINNER);
        poolForC.add((C) Quality.SEMIPRO);
        poolForC.add((C) Quality.PRO);

        for (int i = 0; i < 10; i++) {
            poolForA.add((A) new Numeric(Math.floor(Math.random() * 100)));
            poolForA.add((A) new Arena((float) Math.floor(Math.random() * 100), Quality.getQuality((int) Math.floor(Math.random() * 4)).toString()));
            poolForA.add((A) new Nest((int) Math.floor(Math.random() * 100), Quality.getQuality((int) Math.floor(Math.random() * 4)).toString()));
            poolForB.add((B) new Numeric(Math.floor(Math.random() * 100)));
            poolForB.add((B) new Arena((float) Math.floor(Math.random() * 100), Quality.getQuality((int) Math.floor(Math.random() * 4)).toString()));
            poolForB.add((B) new Nest((int) Math.floor(Math.random() * 100), Quality.getQuality((int) Math.floor(Math.random() * 4)).toString()));
            poolForC.add((C) new Numeric(Math.floor(Math.random() * 100)));
        }

        for (int i = 0; i < 10; i++) {
            set.add(poolForA.get((int) Math.floor(Math.random() * poolForA.size())));
            set.addCriterion(poolForB.get((int) Math.floor(Math.random() * poolForA.size())));
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

    public static void testValue(int given, int expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }
}
