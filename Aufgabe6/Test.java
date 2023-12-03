import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        {
            Institute inst1 = new Institute("Ants Int");
            Formicarium first = new Formicarium("first","magnae");
            Nest firstN = new MoistNest(100,160,new GlassConcrete(),300);
            Nest secondN = new MoistNest(100,160,new SandClay(),300);
            first.addNest(firstN);
            first.addNest(secondN);

            try{
                firstN.placeProperty(100,160);
                secondN.placeProperty(1000);
            }catch (AlreadySetException a){
                System.out.println("Failed test");
            }

            Formicarium second = new Formicarium("second","magnae");
            Institute inst2 = new Institute("Big Ants corp");
            Institute inst3 = new Institute("Ants ants Ants");

            // add formicariums to institute
            inst1.add(first);
            inst1.add(second);
            inst1.add(new Formicarium("third", "woho"));
            //inst1.remove(first);

            System.out.println("Test: properties set");
            inst1.getByName("first").setAntSpecies("pravus");
            try {
                inst1.getByName("first").averageWeightSandClay(Formicarium.Statistic.BOTH);
                System.out.println("Failed test");
            }catch (NoProperitytSetException e){
                System.out.println("Successful test");
            }
            GlassConcrete glassConcrete = (GlassConcrete)inst1.getByName("first").getById(1).getMaterial();

            System.out.println("Test: try to set properites of glassConcrete two times");
            try {
                glassConcrete.placeDimensions(10,16);
                glassConcrete.placeDimensions(10,16);
                System.out.println("Failed test");
            }catch (AlreadySetException alreadySetException){
                System.out.println("Successful test");
            }

            //test statistics
        }

        {
            System.out.println("Test: Statistic Methods");
            Institute institute = new Institute("Ants Int");
            Formicarium formicarium = new Formicarium("F1", "Rohos");
            Nest moistNest = new MoistNest(2, 2, new GlassConcrete(), 100);
            formicarium.addNest(moistNest);
            institute.add(formicarium);

            try {
                moistNest.placeProperty(12, 10);
                double average = institute.getByName("F1").averageWeightSandClay(Formicarium.Statistic.HEATED);
                System.out.println("Failed test");
            } catch (Exception e) {
                System.out.println("Successful test");
            }

            try {
                double average = institute.getByName("F1").averageVolumeGlassConcrete(Formicarium.Statistic.MOIST);
                testValue(average, 240);
            } catch (Exception e) {
                System.out.println("Failed test");
            }

            // remove nest from institute
            institute.getByName("F1").removeNest(moistNest);

            // same method should now throw an error
            try {
                double average = institute.getByName("F1").averageWeightSandClay(Formicarium.Statistic.HEATED);
                System.out.println("Failed test");
            } catch (Exception e) {
                System.out.println("Successful test");
            }
        }

        {
            Nest nest = new MoistNest(110, 100, new GlassConcrete(), 100);
            // get height/width of nest
            testValue(nest.getHeight(), 110);
            testValue(nest.getWidth(), 100);
        }

        {
            // receive watertank volume of MoistedNest
            Nest nest = new MoistNest(110, 100, new GlassConcrete(), 100);
            MoistNest moistNest = (MoistNest) nest;
            testValue(moistNest.getWaterTankVolume(), 100);
        }
        {
            // receive power of HeatedNest
            Nest nest = new HeatedNest(110, 100, new GlassConcrete(), 100);
            HeatedNest moistNest = (HeatedNest) nest;
            testValue(moistNest.getPower(), 100);
        }

        {
            OurLinkedList institutes = new OurLinkedList();
            // create and fill institutes for some statistics
            for (int i = 0; i < 3; i++) {
                institutes.add(new Institute("F"+(i+1)));

                // add some formicariums to each institute
                for (int j = 0; j < 3; j++) {
                    ((Institute)institutes.get(i)).add(new Formicarium("Formicarium:" +i  + "" + j, "Ant:" +i  + "" + j));

                    for (int k = 0; k < 2; k++) {
                        int randomHeight = (int)(Math.random() * 100);
                        int randomWidth = (int)(Math.random() * 100);
                        int randomTankVolume = (int)(Math.random() * 100);
                        MoistNest nest = new MoistNest(randomHeight, randomWidth, new GlassConcrete(), randomTankVolume);
                        ((Institute)institutes.get(i)).getByName("Formicarium:" +i  + "" + j).addNest(nest);
                        try {
                            nest.placeProperty(23, 4);
                        }
                        catch(AlreadySetException ignored) {
                        }
                    }

                    for (int k = 0; k < 2; k++) {
                        int randomHeight = (int)(Math.random() * 100);
                        int randomWidth = (int)(Math.random() * 100);
                        int randomTankVolume = (int)(Math.random() * 100);
                        MoistNest nest = new MoistNest(randomHeight, randomWidth, new SandClay(), randomTankVolume);
                        ((Institute)institutes.get(i)).getByName("Formicarium:" +i  + "" + j).addNest(nest);
                        try {
                            nest.placeProperty(100);
                        }
                        catch(AlreadySetException ignored) {
                        }
                    }

                    for (int k = 0; k < 2; k++) {
                        int randomHeight = (int)(Math.random() * 100);
                        int randomWidth = (int)(Math.random() * 100);
                        int randomPower = (int)(Math.random() * 100);
                        HeatedNest nest = new HeatedNest(randomHeight, randomWidth, new GlassConcrete(), randomPower);
                        ((Institute)institutes.get(i)).getByName("Formicarium:" +i  + "" + j).addNest(nest);
                        try {
                            nest.placeProperty(23, 4);
                        }
                        catch(AlreadySetException ignored) {
                        }
                    }

                    for (int k = 0; k < 2; k++) {
                        int randomHeight = (int)(Math.random() * 100);
                        int randomWidth = (int)(Math.random() * 100);
                        int randomPower = (int)(Math.random() * 100);
                        HeatedNest nest = new HeatedNest(randomHeight,randomWidth, new SandClay(), randomPower);
                        ((Institute)institutes.get(i)).getByName("Formicarium:" +i  + "" + j).addNest(nest);
                        try {
                            nest.placeProperty(100);
                        }
                        catch(AlreadySetException ignored) {
                        }
                    }
                }
            }

            // print statistics
            for (int i = 0; i < institutes.size(); i++) {
                Institute institute = (Institute) (institutes.get(i));

                if(i == 0) {
                    System.out.println("Print statistics of first Institute:");
                    System.out.println(institute.toString());
                }

                System.out.println("\n\n" + "Test: Check statistics for Institute: F" + (i + 1));
                OurLinkedList list = institute.getFormicariums();
                for (int j = 0; j < list.size(); j++) {
                    Formicarium formicarium = (Formicarium)list.get(j);
                    System.out.println(formicarium.getName());
                    System.out.println("Test: Average volume of both is equal to average volume of moist + heated");
                    double averageVolume = formicarium.averageNestVolume(Formicarium.Statistic.BOTH);
                    double averageVolume2 = (formicarium.averageNestVolume(Formicarium.Statistic.MOIST) + formicarium.averageNestVolume(Formicarium.Statistic.HEATED))/2;
                    testValue(averageVolume,averageVolume2);

                    try {
                        System.out.println("Test: Average volume of both glas concrete nests is equal to average volume of moist + heated");
                        double averageGlasConcreteVolume = formicarium.averageVolumeGlassConcrete(Formicarium.Statistic.BOTH);
                        double averageGlasConcreteVolume2 = formicarium.averageVolumeGlassConcrete(Formicarium.Statistic.MOIST)/2 + formicarium.averageVolumeGlassConcrete(Formicarium.Statistic.HEATED)/2;
                        testValue(averageGlasConcreteVolume,averageGlasConcreteVolume2);
                    } catch (NoProperitytSetException e){
                        System.out.println("Failed test");
                    }
                    try {
                        System.out.println("Test: average weight of both sand clay nests is equal to average weight of moist + heated");
                        double averageWeightSandClay = formicarium.averageWeightSandClay(Formicarium.Statistic.BOTH);
                        double averageWeightSandClay2 = formicarium.averageWeightSandClay(Formicarium.Statistic.MOIST)/2 + formicarium.averageWeightSandClay(Formicarium.Statistic.HEATED)/2;
                        testValue(averageWeightSandClay,averageWeightSandClay2);
                    }catch (NoProperitytSetException e){
                        System.out.println("Failed test");
                    }
                    institute.remove(formicarium);
                }
                Formicarium f = institute.getByName("Formicarium:" +i   + 0);
                Formicarium e = null;
                testEquals(f,e);
            }
        }

        // print statistics from annotations
//        System.out.println(getAnnotationEvaluation());
    }

    private static String getAnnotationEvaluation() {
        StringBuilder result = new StringBuilder();
        String currentDirectory = System.getProperty("user.dir");
        File[] files = new File(currentDirectory).listFiles();

        if(files == null) {
            return "";
        }

        Class <?>[] classes = Arrays.stream(files)
                .filter(f -> f.isFile() && f.getName().endsWith(".java"))
                .map(f -> {
                    try {
                        // Convert file name to class name and load the class
                        String className = f.getName().replace(".java", "");
                        return Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .toArray(Class[]::new);


        HashMap<String, AnnotationObject> infos = new HashMap<>();
        HashMap<String, ClassAnnotationObject> assertionsPerClass = new HashMap<>();

        for (Class<?> aClass : classes) {
            List<AccessibleObject> members = new ArrayList<>();
            members.addAll(Arrays.asList(aClass.getMethods()));
            members.addAll(Arrays.asList(aClass.getConstructors()));

            // read all methods from class
            for (AccessibleObject member : members) {
                // read all annotations from method
                String responsiblePerson = "";
                boolean wroteAssertion = false;
                for (Annotation annotation : member.getAnnotations()) {
                    // check for different cases
                    if (annotation instanceof CodedBy codedBy) {
                        responsiblePerson = codedBy.value();
                        if (aClass.isInterface() || aClass.isAnnotation()) {
                            continue;
                        }

                        AnnotationObject info = infos.computeIfAbsent(responsiblePerson, k -> new AnnotationObject());

                        // if member is constructor, add to constructor stats
                        if (member instanceof Constructor<?>) {
                            HashMap<String, Integer> constructorsInClass = info.getConstructorsInClass();
                            int newValue = constructorsInClass.getOrDefault(aClass.getName(), 0) + 1;
                            constructorsInClass.put(aClass.getName(), newValue);
                        }

                        if (member instanceof Method) {
                            // get the amount of methods per class
                            HashMap<String, Integer> methodsInClass = info.getMethodsInClass();
                            int newValue = methodsInClass.getOrDefault(aClass.getName(), 0) + 1;
                            methodsInClass.put(aClass.getName(), newValue);
                        }
                    }

                    if (annotation instanceof SignatureAndAssertions signatureAndAssertions) {
                        wroteAssertion = true;

                        ClassAnnotationObject classAnnotationObject = assertionsPerClass.computeIfAbsent(aClass.getName(), k -> new ClassAnnotationObject());
                        classAnnotationObject.getAssertionsPerMethod().put(member.toString(), signatureAndAssertions);
                    }
                }

                if (!responsiblePerson.isEmpty() && wroteAssertion) {
                    infos.get(responsiblePerson).increaseAssertions();
                }
            }

            Annotation[] declaredAnnotation = aClass.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotation) {
                if (annotation instanceof CodedBy codedBy) {
                    String name = codedBy.value();
                    AnnotationObject info = infos.get(name);
                    if (info == null) {
                        info = new AnnotationObject();
                        infos.put(name, info);
                    }

                    List<String> list = infos.getOrDefault(name, new AnnotationObject()).getResponsibleEntities();
                    list.add(aClass.getName());
                }
            }
        }

        infos.forEach((key, value) -> {
            result.append("Stats for: ").append(key).append("\n");
            value.getMethodsInClass().forEach((key1, value1) -> {
                result.append(key1).append(":\t").append(value1).append(" method(s)\n");
            });

            if (!value.getMethodsInClass().isEmpty()) {
                result.append("\n");
            }

            value.getConstructorsInClass().forEach((key1, value1) -> {
                result.append(key1).append(":\t").append(value1).append(" constructor(s)\n");
            });

            if (!value.getConstructorsInClass().isEmpty()) {
                result.append("\n");
            }

            result.append("Responsible for ").append(value.getResponsibleEntities().size()).append(" units: ");
            value.getResponsibleEntities().forEach((value1) -> result.append(value1).append(", "));

            result.delete(result.length() - 2, result.length()).append("\n");

            result.append("wrote ").append(value.getAssertions()).append(" assertions");
            result.append("\n\n");
        });

        result.append("Zusicherungen:\n");
        assertionsPerClass.forEach((key, value) -> {
            result.append("---------------------------------------------------------\n");
            result.append(key).append("\n\n");

            value.getAssertionsPerMethod().forEach((key1, value1) -> {
                result.append(key1).append("\n");
                if (!value1.description().isEmpty()) {
                    result.append("Descriptions: ").append(value1.description()).append("\n");
                }

                if (!value1.preconditions().isEmpty()) {
                    result.append("Preconditions: ").append(value1.preconditions()).append("\n");
                }

                if (!value1.postconditions().isEmpty()) {
                    result.append("Postconditions: ").append(value1.postconditions()).append("\n");
                }

                if (!value1.invariants().isEmpty()) {
                    result.append("Invariants: ").append(value1.invariants()).append("\n");
                }

                if (!value1.historyConstrains().isEmpty()) {
                    result.append("History-Constraints: ").append(value1.historyConstrains()).append("\n");
                }

                result.append("\n");
            });
        });

        return result.toString();
    }

    public static void testIdentity(Object given, Object expected) {
        if (given == expected) {
            System.out.println("Successful test");
        } else {
            System.out.println("Test NOT successful! Expected value: " + expected + " / Given value: " + given);
        }
    }

    public static void testEquals(Object given, Object expected) {

        if ((given == null && expected == null)||given.equals(expected) ) {
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
