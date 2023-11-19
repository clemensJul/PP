import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Test {
    public static void main(String[] args) throws CompatibilityException {
        {
            // Create object to check which types are allowed.

            //formicarium
            FormicariumItem fi_compositeFormicarium = new CompositeFormicarium();
            FormicariumPart fp_compositeFormicarium = new CompositeFormicarium();
            Formicarium f_compositeFormicarium = new CompositeFormicarium();

            // nest
            FormicariumPart fp_nest = new Nest();
            FormicariumItem fi_nest = new Nest();

            // antfarm
            FormicariumPart fp_antFarm = new AntFarm(ESubstrat.GRAVEL);
            FormicariumItem fi_antFarm = new AntFarm(ESubstrat.GRAVEL);

            // arena
            FormicariumPart fp_arena = new Arena(ESubstrat.DIRT, EContainerMaterial.PLASTIC, null);
            FormicariumItem fi_arena = new Arena(ESubstrat.GRAVEL, EContainerMaterial.GLAS, new Nest());

            // thermometer
            Thermometer ci_thermometer = new Thermometer(EUsage.SEMI);
            Instrument i_thermometer = new Thermometer(EUsage.PRO);
            FormicariumPart fp_thermometer = new Thermometer(EUsage.BEGINNER);
            FormicariumItem fi_thermometer = new Thermometer(EUsage.SEMI);

            // forceps
            FormicariumItem fi_forceps = new Forceps(EUsage.BEGINNER);
            Instrument i_forceps = new Forceps(EUsage.PRO);

            ArrayList<FormicariumItem> items = new ArrayList<>();
            items.add(fi_compositeFormicarium);
            items.add(fp_compositeFormicarium);
            items.add(f_compositeFormicarium);

            items.add(fp_nest);
            items.add(fi_nest);
            items.add(fi_antFarm);
            items.add(fp_antFarm);
            items.add(fi_arena);
            items.add(fp_arena);
            items.add(ci_thermometer);
            items.add(i_thermometer);
            items.add(fp_thermometer);
            items.add(fi_thermometer);
            items.add(fi_forceps);
            items.add(i_forceps);
        }
        {
            System.out.println("Test if compatibility of composite formicarium takes the values from it's subtypes");
            CompositeFormicarium fi_compositeFormicarium = new CompositeFormicarium();
            CompositeFormicarium fp_compositeFormicarium = new CompositeFormicarium();
            FormicariumPart fp_nest = new Nest();
            FormicariumPart fi_antFarm = new AntFarm(ESubstrat.GRAVEL);
            FormicariumPart ci_thermometer = new Thermometer(EUsage.SEMI);
            fi_compositeFormicarium.add(fp_nest);
            fi_compositeFormicarium.add(fi_antFarm);
            fp_compositeFormicarium.add(ci_thermometer);
            fp_compositeFormicarium.add(fi_compositeFormicarium);
            testValue(fp_compositeFormicarium.compatibility().minSize(), 0);
            testValue(fp_compositeFormicarium.compatibility().maxSize(), 20);
            testValue(fp_compositeFormicarium.compatibility().minTemperature(), 5);
            testValue(fp_compositeFormicarium.compatibility().maxTemperature(), 30);
            testValue(fp_compositeFormicarium.compatibility().minHumidity(), 30);
            testValue(fp_compositeFormicarium.compatibility().maxHumidity(), 70);
        }
        {
            // Test, if an empty Formicarium iterator returns the Formicarium itself
            System.out.println("Test 'FormicariumIterator':");
            Formicarium formicarium = new CompositeFormicarium(new ArrayList<>());
            Iterator<FormicariumPart> iterator_formicarium = formicarium.iterator();
            boolean hasNext = iterator_formicarium.hasNext();
            testValue(hasNext, true);
            FormicariumPart item = iterator_formicarium.next();
            testIdentity(formicarium, item);

            // Test, if a Formicarium iterator returns elements
            ArrayList<FormicariumPart> fp_list = new ArrayList<>();
            fp_list.add(new CompositeFormicarium(new ArrayList<>()));
            fp_list.add(new Nest());
            fp_list.add(new AntFarm(ESubstrat.GRAVEL));
            fp_list.add(new Arena(ESubstrat.DIRT, EContainerMaterial.PLASTIC, null));

            Formicarium f_iteratorTest = new CompositeFormicarium(fp_list);
            Iterator<FormicariumPart> f_iterator = f_iteratorTest.iterator();

            int count = 0;
            while (f_iterator.hasNext()) {
                item = f_iterator.next();
                testIdentity(item, fp_list.get(count));
                count++;
            }
        }
        {
            System.out.println("Test 'Add recursive Formicarium items to Formicarium':");

            Nest duplicatedNest = new Nest();
            ArrayList<FormicariumPart> subItems = new ArrayList<>();
            subItems.add(duplicatedNest);
            subItems.add(new Thermometer(EUsage.SEMI));
            CompositeFormicarium nestedFormicarium = new CompositeFormicarium(subItems);

            CompositeFormicarium formicarium = new CompositeFormicarium();
            testValue(formicarium.add(duplicatedNest), true);
            testValue(formicarium.add(duplicatedNest), false);
            testValue(formicarium.add(new AntFarm(ESubstrat.GRAVEL)), true);
            testValue(formicarium.add(new Arena(ESubstrat.DIRT, EContainerMaterial.PLASTIC, null)), true);
            testValue(formicarium.add(nestedFormicarium), false); // because it has duplicatedNest in it

            // remove duplicatedNest from subItems
            testValue(nestedFormicarium.remove(duplicatedNest), true);
            testValue(formicarium.add(nestedFormicarium), true); // because it has duplicatedNest in it


            // should print 4 items and duplicatedNest should only be there once.
            int counterNest = 0;
            int counter = 0;

            for (FormicariumPart part : formicarium) {
                if (part == duplicatedNest) {
                    counterNest++;
                }
                counter++;
            }

            testValue(counter, 4);
            testValue(counterNest, 1);
        }
        {
            System.out.println("Test 'CompositeFormicarium CompatibilityException':");
            CompositeFormicarium formicarium = new CompositeFormicarium();
            formicarium.add(new Nest());
            formicarium.add(new AntFarm(ESubstrat.GRAVEL));
            formicarium.add(new Arena(ESubstrat.DIRT, EContainerMaterial.PLASTIC, null));

            try {
                formicarium.add(new Thermometer(10000));
                testValue(false, true);
            } catch (CompatibilityException exception) {
                testValue(true, true);
            }
        }
        {
            System.out.println("Test 'FormicariumSet iterator':");
            // create new CompositeFormicarium
            FormicariumSet formicariumSet = new FormicariumSet(new ArrayList<>());

            testValue(formicariumSet.add(new Thermometer(EUsage.BEGINNER)), true);
            testValue(formicariumSet.add(new Thermometer(EUsage.BEGINNER)), true);
            testValue(formicariumSet.add(new Thermometer(EUsage.BEGINNER)), true);
            testValue(formicariumSet.add(new Nest()), true);
            testValue(formicariumSet.add(new Nest()), true);
            testValue(formicariumSet.add(new Nest()), true);
            testValue(formicariumSet.add(new Forceps(EUsage.PRO)), true);
            testValue(formicariumSet.add(new Forceps(EUsage.PRO)), true);
            testValue(formicariumSet.add(new Forceps(EUsage.BEGINNER)), true);
            testValue(formicariumSet.add(new Forceps(EUsage.PRO)), true);
            testValue(formicariumSet.add(new AntFarm(ESubstrat.DIRT)), true);
            testValue(formicariumSet.add(new AntFarm(ESubstrat.DIRT)), true);
            testValue(formicariumSet.add(new AntFarm(ESubstrat.DIRT)), true);

            FormicariumSetIterator iterator = (FormicariumSetIterator) formicariumSet.iterator();
            while (iterator.hasNext()) {
                FormicariumItem item = iterator.next();

                if (item instanceof AntFarm) {
                    testValue(iterator.count(), 3);
                    continue;
                }

                if (item instanceof Nest) {
                    testValue(iterator.count(), 3);
                    continue;
                }

                if (item instanceof Thermometer) {
                    testValue(iterator.count(), 3);
                    continue;
                }

                if (item instanceof Forceps forceps) {
                    if (forceps.quality() == EUsage.BEGINNER) {
                        testValue(iterator.count(), 1);
                    }
                    if (forceps.quality() == EUsage.PRO) {
                        testValue(iterator.count(), 3);
                    }
                }
            }

            iterator = (FormicariumSetIterator) formicariumSet.iterator();
            while (iterator.hasNext()) {
                FormicariumItem item = iterator.next();

                if (item instanceof AntFarm) {
                    iterator.remove();
                    testValue(iterator.count(), 2);
                    continue;
                }

                if (item instanceof Nest) {
                    iterator.remove(2);
                    testValue(iterator.count(), 1);
                    continue;
                }

                if (item instanceof Thermometer) {
                    try {
                        iterator.remove(5);
                        testValue(true, false);
                    } catch (NoSuchElementException exception) {
                        testValue(true, true);
                    }
                }
            }
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

    public static void printValues(FormicariumItem item) {
        Compatibility compatibility = item.compatibility();
        String output = item + " size = " + compatibility.minSize() + ".." + compatibility.maxSize() +
                " temp = " + compatibility.minTemperature() + ".." + compatibility.maxTemperature() +
                " humidity = " + compatibility.minHumidity() + ".." + compatibility.maxHumidity() +
                " maxtime = " + compatibility.maxTime();
        System.out.println(output);
    }

    public static void testValue(int given, int expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }
}

/*
    Untertypbeziehungen:

    Compatibility hat keine weiteren Untertypbeziehungen, weil Compatibility kein konkretes Objekt in der Formicariumhierarchie ist,
    sondern lediglich die Ameisenhaltungseigenschaften jedes Objekts einfängt.

    FormicariumSet hat keine weiteren Untertypbeziehungen mit der Formicariumhierarchie, da es selbst kein konkretes "FormicariumItem" darstellt, sondern eine
    Menge an FormicariumItems ist.

    Die restlichen Untertypbeziehungen lauten wie folgt:

                              FormicariumItem
                              |              |
                    FormicariumPart       Instrument
                    |       |     |          |     |
         Formicarium      Arena   Thermometer       Forceps
         |          |
        Nest        CompositeFormicarium
         |
      Antfarm


      Arbeitsaufwand:

      Raphael: Compatibility; Testfälle, Enums
      Clemens: Iterator, Exceptions

      Klassenstruktur wurde gemeinsam erarbeitet
 */
