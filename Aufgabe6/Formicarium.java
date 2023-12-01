import java.util.HashMap;

@CodedBy("Raphael")
public class Formicarium {
    OurLinkedList nests = new OurLinkedList();
    private final String name;
    private String antSpecies;

    public enum Statistic {
        MOIST,
        HEATED,
        BOTH
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            preconditions = "Name must not be null",
            postconditions = "Returns a new Formicarium"
    )
    public Formicarium(String name, String antSpecies) {
        this.name = name;
        this.antSpecies = antSpecies;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            preconditions = "Name must not be null",
            postconditions = "Returns a new Formicarium"
    )
    public void addNest(Nest nest) {
        nests.add(nest);
    }

    @CodedBy("Raphael")
    public void removeNest(Nest nest) {
        nests.remove(nest);
    }

    @CodedBy("Raphael")
    public String getAntSpecies() {
        return antSpecies;
    }

    @CodedBy("Raphael")
    public void setAntSpecies(String antSpecies) {
        this.antSpecies = antSpecies;
    }

    @CodedBy("Raphael")
    public void deleteAntSpecies() {
        this.antSpecies = "";
    }

    // statistical calculations
    @CodedBy("Raphael")
    public double averageNestVolume() {
        double sum = 0;
        for (Object nest : nests) {
            sum += ((Nest)nest).getVolume();
        }
        if (sum == 0) return 0;
        return sum / nests.size();
    }

    @SignatureAndAssertions(
            postconditions = "returns the average volume of the moist nests"
    )
    public double averageHeatedNestVolume() {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if(nest instanceof HeatedNest heatedNest) {
                counter++;
                sum += heatedNest.getVolume();
            }
        }
        if (sum == 0) return 0;
        return sum / counter;
    }
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average volume of the moist nests"
    )
    public double averageMoistNestVolume() {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if(nest instanceof MoistNest moistNest) {
                counter++;
                sum += moistNest.getVolume();
            }
        }
        if (sum == 0) return 0;
        return sum / counter;
    }
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average power of the heated nests"
    )
    public int averagePower() {
        int sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if(nest instanceof HeatedNest heatedNest) {
                counter++;
                sum += heatedNest.getPower();
            }
        }
        if (sum == 0) return 0;
        return sum / counter;
    }
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average volume of the moist nests water tanks"
    )
    public double averageWatertankVolumen() {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if(nest instanceof MoistNest moistNest) {
                counter++;
                sum += moistNest.getWaterTankVolume();
            }
        }
        if (sum == 0) return 0;
        return sum / counter;
    }
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average weight of the sand clay substrates"
    )
    //TODO: vllt machen ma statt ner list ne hashmap?
    public double averageWeightSandClay(Statistic statistic) {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {

            //TODO: missing conditional for substrate type - but this methode seems so explicid - is there a better way?
            switch (statistic) {
                case HEATED -> {

                    if (nest instanceof HeatedNest){
                        counter++;
                        sum += ((Nest)nest).getSubstrateWeight();
                    }
                }
                case MOIST -> {
                    if (nest instanceof MoistNest){
                        counter++;
                        sum += ((Nest)nest).getSubstrateWeight();
                    }
                }
                case BOTH -> {
                    counter++;
                    sum += ((Nest)nest).getSubstrateWeight();
                }
            }

        }
        if (sum == 0) return 0;
        return sum / counter;
    }
    @CodedBy("Raphael")
    private static class Test {}
}
