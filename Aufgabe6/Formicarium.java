@CodedBy("Raphael")
public class Formicarium {
    OurLinkedList nests = new OurLinkedList();
    private final String name;
    private String antSpecies;

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

        return sum / nests.size();
    }

//    public double averageHeatedNestVolume() {
//        double sum = 0;
//        int counter = 0;
//        for (Nest nest : nests) {
//            if(nest instanceof HeatedNest) {
//                counter++;
//                sum += nest.getVolume();
//            }
//        }
//
//        return sum / counter;
//    }
    @CodedBy("Raphael")
    private static class Test {}
}
