import java.util.ArrayList;
import java.util.Iterator;

public class Test {
    public static void main(String[] args) throws CompatibilityException {
        {
            //ArrayList<FormicariumPart> emptyList = new ArrayList<>();

            // compositeFormicarium
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
            FormicariumPart fp_arena = new Arena(ESubstrat.DIRT, EContainerMaterial.PLASTIC,null);
            FormicariumItem fi_arena = new Arena(ESubstrat.GRAVEL, EContainerMaterial.GLAS,new Nest());

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

        //iterator test


        // Test, if an empty formicarium iterator return the formicarium itself
        {
            System.out.println("Test 'FormicariumIterator':");
            Formicarium formicarium = new CompositeFormicarium(new ArrayList<>());
            Iterator<FormicariumPart> iterator_formicarium = formicarium.iterator();
            boolean hasNext = iterator_formicarium.hasNext();
            testValue(hasNext, true);
            FormicariumPart item = iterator_formicarium.next();
            testIdentity(formicarium, item);


            // Test, if a formicarium iterator returns elements
            ArrayList<FormicariumPart> fp_list = new ArrayList<>();
            fp_list.add(new CompositeFormicarium(new ArrayList<>()));
            fp_list.add(new Nest());
            fp_list.add(new AntFarm(ESubstrat.GRAVEL));
            fp_list.add(new Arena(ESubstrat.DIRT, EContainerMaterial.PLASTIC,null));

            Formicarium f_iteratorTest = new CompositeFormicarium(fp_list);
            Iterator<FormicariumPart> f_iterator = f_iteratorTest.iterator();

            int count = 0;
            while (f_iterator.hasNext()) {
                item = f_iterator.next();
                testIdentity(item, fp_list.get(count));
                count++;
            }


            System.out.println("Test 'Add recursive Formicarium items to Formicarium':");

            // create new formicarium
            ArrayList<FormicariumPart> nested_list = new ArrayList<>();
            nested_list.add(new Thermometer(EUsage.PRO));
            nested_list.add(new Nest());
            Formicarium nested_formicarium = new CompositeFormicarium(nested_list);
            fp_list.set(0, nested_formicarium);

            Formicarium parent_formicarium = new CompositeFormicarium(fp_list);

            // first element of iterator should be the thermometer of the sub formicarium
            f_iterator = parent_formicarium.iterator();
            hasNext = f_iterator.hasNext();
            testValue(hasNext, true);
            item = f_iterator.next();
            testIdentity(item, nested_list.get(0));

            // second element of iterator should be the nest of the sub formicarium
            hasNext = f_iterator.hasNext();
            testValue(hasNext, true);
            item = f_iterator.next();
            testIdentity(item, nested_list.get(1));

            // third element of iterator should be the second element of main formicarium
            hasNext = f_iterator.hasNext();
            testValue(hasNext, true);
            item = f_iterator.next();
            testIdentity(item, fp_list.get(1));
        }

        {
            System.out.println("Test 'CompositeFormicarium':");
            // create new CompositeFormicarium
            ArrayList<FormicariumPart> items = new ArrayList<>();
            Nest nest = new Nest();
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(nest);
            CompositeFormicarium compositeFormicarium = new CompositeFormicarium(items);
            int checkIndex = 0;
            try {
                testValue(compositeFormicarium.add(nest), false);
                testValue(compositeFormicarium.add(new Nest()), true);
                checkIndex = 3;
                compositeFormicarium.add(new Thermometer(3000));
            } catch (Exception e) {
                testValue(checkIndex, 3);
            }
        }

        {
            System.out.println("Test 'FormicariumSet iterator':");
            // create new CompositeFormicarium
            ArrayList<FormicariumItem> items = new ArrayList<>();
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Nest());
            items.add(new Nest());
            items.add(new Nest());
            items.add(new Forceps(EUsage.PRO));
            items.add(new Forceps(EUsage.PRO));
            items.add(new Forceps(EUsage.BEGINNER));
            items.add(new Forceps(EUsage.PRO));
            items.add(new AntFarm(ESubstrat.DIRT));
            items.add(new AntFarm(ESubstrat.DIRT));
            items.add(new AntFarm(ESubstrat.DIRT));
            FormicariumSet formicariumSet = new FormicariumSet(items);

            FormicariumSetIterator iterator = (FormicariumSetIterator) formicariumSet.iterator();
            while (iterator.hasNext()) {
                FormicariumItem item = iterator.next();
                System.out.println(item);
            }
        }

        {
            System.out.println("Test 'FormicariumSet counter':");
            // create new CompositeFormicarium
            ArrayList<FormicariumItem> items = new ArrayList<>();
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Nest());
            items.add(new Nest());
            items.add(new Nest());
            items.add(new Forceps(EUsage.PRO));
            items.add(new Forceps(EUsage.PRO));
            items.add(new Forceps(EUsage.BEGINNER));
            items.add(new Forceps(EUsage.PRO));
            items.add(new AntFarm(ESubstrat.DIRT));
            items.add(new AntFarm(ESubstrat.DIRT));
            items.add(new AntFarm(ESubstrat.DIRT));
            FormicariumSet formicariumSet = new FormicariumSet(items);

            FormicariumSetIterator iterator = (FormicariumSetIterator) formicariumSet.iterator();
            while (iterator.hasNext()) {
                FormicariumItem item = iterator.next();
                System.out.println(item + " - " + iterator.count());
            }
        }

        {
            System.out.println("Test 'FormicariumSet remove':");
            // create new CompositeFormicarium
            ArrayList<FormicariumItem> items = new ArrayList<>();
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Nest());
            items.add(new Nest());
            items.add(new Nest());
            items.add(new Forceps(EUsage.BEGINNER));
            items.add(new Forceps(EUsage.PRO));
            items.add(new Forceps(EUsage.PRO));
            items.add(new Forceps(EUsage.PRO));
            items.add(new AntFarm(ESubstrat.DIRT));
            items.add(new AntFarm(ESubstrat.DIRT));
            items.add(new AntFarm(ESubstrat.DIRT));
            FormicariumSet formicariumSet = new FormicariumSet(items);

            FormicariumSetIterator iterator = (FormicariumSetIterator) formicariumSet.iterator();
            while (iterator.hasNext()) {
                FormicariumItem item = iterator.next();
                try {
                   System.out.println(item + " - " + iterator.count() + " elements before remove");
                   iterator.remove();
                   System.out.println(item + " - " + iterator.count() + " elements after remove");
               }
               catch (Exception e) {
                   testEquals(item, new Forceps(EUsage.BEGINNER));
               }
            }
        }

        {
            System.out.println("Test 'FormicariumSet remove n elements':");
            // create new CompositeFormicarium
            ArrayList<FormicariumItem> items = new ArrayList<>();
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Thermometer(EUsage.BEGINNER));
            items.add(new Nest());
            items.add(new Nest());
            items.add(new Nest());
            items.add(new Forceps(EUsage.BEGINNER));
            items.add(new Forceps(EUsage.PRO));
            items.add(new Forceps(EUsage.PRO));
            items.add(new Forceps(EUsage.PRO));
            items.add(new AntFarm(ESubstrat.DIRT));
            items.add(new AntFarm(ESubstrat.DIRT));
            items.add(new AntFarm(ESubstrat.DIRT));
            FormicariumSet formicariumSet = new FormicariumSet(items);

            FormicariumSetIterator iterator = (FormicariumSetIterator) formicariumSet.iterator();
            FormicariumItem item = iterator.next();
            //WIP tests
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
