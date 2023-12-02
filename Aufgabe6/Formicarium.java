import java.util.HashMap;

@CodedBy("Clemens")
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
    @SignatureAndAssertions(
            preconditions = "Nest must be != null",
            postconditions = "Removes the nest from the list if it is in here"
    )
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
    @SignatureAndAssertions(
            postconditions = "returns the average volume of the all nests - throws an exception if no such element is found"
    )
    public double averageNestVolume() throws ArithmeticException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            sum += ((Nest)nest).getVolume();
            counter++;
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average volume of the moist nests - throws an exception if no such element is found"
    )
    public double averageHeatedNestVolume() throws ArithmeticException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if(nest instanceof HeatedNest heatedNest) {
                counter++;
                sum += heatedNest.getVolume();
            }
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average volume of the moist nests - throws an exception if no such element is found"
    )
    public double averageMoistNestVolume() throws ArithmeticException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if(nest instanceof MoistNest moistNest) {
                counter++;
                sum += moistNest.getVolume();
            }
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average power of the heated nests - throws an exception if no such element is found"
    )
    public int averagePower() throws ArithmeticException{
        int sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if(nest instanceof HeatedNest heatedNest) {
                counter++;
                sum += heatedNest.getPower();
            }
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average volume of the moist nests water tanks - throws an exception if no such element is found"
    )
    public double averageWatertankVolumen() throws ArithmeticException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if(nest instanceof MoistNest moistNest) {
                counter++;
                sum += moistNest.getWaterTankVolume();
            }
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average weight of the sand clay substrates - throws an exception if no such element is found"
    )
    public double averageWeightSandClay(Statistic statistic) throws ArithmeticException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            Nest casted = (Nest)nest;
            if (!(casted.getMaterial() instanceof SandClay)){
                continue;
            }
            switch (statistic) {
                case HEATED -> {

                    if (nest instanceof HeatedNest){
                        counter++;
                        sum += casted.getSubstrateWeight();
                    }
                }
                case MOIST -> {
                    if (nest instanceof MoistNest){
                        counter++;
                        sum += casted.getSubstrateWeight();
                    }
                }
                case BOTH -> {
                    counter++;
                    sum += casted.getSubstrateWeight();
                }
            }
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average weight of the sand clay substrates - throws an exception if no such element is found"
    )
    public double averageVolumeGlassConcrete(Statistic statistic) throws ArithmeticException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            Nest casted = (Nest)nest;
            if (!(casted.getMaterial() instanceof GlassConcrete)){
                continue;
            }
            switch (statistic) {
                case HEATED -> {

                    if (nest instanceof HeatedNest){
                        counter++;
                        sum += casted.getSubstrateHeight()* casted.getSubstrateWidth()* casted.getDepth();
                    }
                }
                case MOIST -> {
                    if (nest instanceof MoistNest){
                        counter++;
                        sum += casted.getSubstrateHeight()* casted.getSubstrateWidth()* casted.getDepth();;
                    }
                }
                case BOTH -> {
                    counter++;
                    sum += casted.getSubstrateHeight()* casted.getSubstrateWidth()* casted.getDepth();;
                }
            }
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }
}
