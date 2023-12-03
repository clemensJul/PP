@CodedBy("Clemens")
@SignatureAndAssertions(
        description = "Formicarium object that can store multiple nests and calculate statistics of them"
)
public class Formicarium {
    private final OurLinkedList nests;
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
            postconditions = "Initializes a new Formicarium"
    )
    public Formicarium(String name, String antSpecies) {
        this.name = name;
        this.antSpecies = antSpecies;
        this.nests = new OurLinkedList();
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            preconditions = "Name must not be null",
            postconditions = "Returns a new Formicarium"
    )
    public void addNest(Nest nest) {
        if (!(nests.contains(nest))) nests.add(nest);
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "if nest is not in Institute return null"
    )
    public Nest getNest(Nest nest) {
        return (Nest) (nests.get(nest));
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "returns Nest if id is contained"
    )
    public Nest getById(int id) {
        for (Object item : nests) {
            Nest nest = (Nest) item;

            if (nest.getId() == id) {
                return nest;
            }
        }
        return null;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            preconditions = "Nest must be != null",
            postconditions = "Removes the nest from the list if it is in here"
    )
    public void removeNest(Nest nest) {
        nests.remove(nest);
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns list of nests"
    )
    public OurLinkedList getNests() {
        return nests;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Return the antSpecies of the Formicarium"
    )
    public String getAntSpecies() {
        return antSpecies;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Return the name of the Formicarium"
    )
    public String getName() {
        return name;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            preconditions = "antSpecies must != null",
            postconditions = "Sets the ant species of the Formicarium"
    )
    public void setAntSpecies(String antSpecies) {
        this.antSpecies = antSpecies;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Sets the ant species to an empty string"
    )
    public void deleteAntSpecies() {
        this.antSpecies = "";
    }

    // statistical calculations
    @CodedBy("Raphael")
    @SignatureAndAssertions(
            preconditions = "specify from what type of nest you need data using the Statistics enum",
            postconditions = "returns the average volume of the all nests - throws an exception if no such element is found"
    )
    public double averageNestVolume(Statistic statistic) throws ArithmeticException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            Nest casted = (Nest) nest;
            switch (statistic) {
                case HEATED -> {
                    if (nest instanceof HeatedNest) {
                        counter++;
                        sum += casted.getVolume();
                    }
                }
                case MOIST -> {
                    if (nest instanceof MoistNest) {
                        counter++;
                        sum += casted.getVolume();
                    }
                }
                case BOTH -> {
                    counter++;
                    sum += casted.getVolume();
                }
            }
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns the average power of the heated nests - throws an exception if no such element is found"
    )
    public int averagePower() throws ArithmeticException {
        int sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if (nest instanceof HeatedNest heatedNest) {
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
    public double averageWatertankVolume() throws ArithmeticException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            if (nest instanceof MoistNest moistNest) {
                counter++;
                sum += moistNest.getWaterTankVolume();
            }
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            preconditions = "specify from what type of nest you need data using the Statistics enum",
            postconditions = "returns the average weight of the sand clay substrates - throws an exception if no such element is found"
    )
    public double averageWeightSandClay(Statistic statistic) throws ArithmeticException, NoPropertytSetException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            Nest casted = (Nest) nest;
            if (!(casted.getMaterial() instanceof SandClay)) {
                continue;
            }
            switch (statistic) {
                case HEATED -> {
                    if (nest instanceof HeatedNest) {
                        counter++;
                        sum += casted.getSubstrateWeight();
                    }
                }
                case MOIST -> {
                    if (nest instanceof MoistNest) {
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
            preconditions = "specify from what type of nest you need data using the Statistics enum",
            postconditions = "returns the average weight of the sand clay substrates - throws an exception if no such element is found"
    )
    public double averageVolumeGlassConcrete(Statistic statistic) throws ArithmeticException, NoPropertytSetException {
        double sum = 0;
        int counter = 0;
        for (Object nest : nests) {
            Nest casted = (Nest) nest;
            if (!(casted.getMaterial() instanceof GlassConcrete)) {
                continue;
            }
            switch (statistic) {
                case HEATED -> {
                    if (nest instanceof HeatedNest) {
                        counter++;
                        sum += casted.getSubstrateHeight() * casted.getSubstrateWidth() * casted.getDepth();
                    }
                }
                case MOIST -> {
                    if (nest instanceof MoistNest) {
                        counter++;
                        sum += casted.getSubstrateHeight() * casted.getSubstrateWidth() * casted.getDepth();
                        ;
                    }
                }
                case BOTH -> {
                    counter++;
                    sum += casted.getSubstrateHeight() * casted.getSubstrateWidth() * casted.getDepth();
                    ;
                }
            }
        }
        if (counter == 0) throw new ArithmeticException("can not divide by zero");
        return sum / counter;
    }

    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "Returns true if the obj to compare is an instance of Formicarium and has the same name."
    )
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formicarium that = (Formicarium) o;
        return this.name.equals(that.name);
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Returns a string representation of this Formicarium"
    )
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(name).append("\n");
        result.append("AntSpecies: ").append(antSpecies).append("\n");

        result.append("\nStatistics:\n");
        try {
            result.append("AverageNestVolume MOIST:\t").append(averageNestVolume(Statistic.MOIST)).append("\n");
        } catch (ArithmeticException ignored) {
        }

        try {
            result.append("AverageNestVolume HEATED:\t").append(averageNestVolume(Statistic.HEATED)).append("\n");
        } catch (ArithmeticException ignored) {
        }

        try {
            result.append("AverageNestVolume BOTH:\t").append(averageNestVolume(Statistic.BOTH)).append("\n");
        } catch (ArithmeticException ignored) {
        }

        try {
            result.append("averageVolumeGlassConcrete MOIST:\t").append(averageVolumeGlassConcrete(Statistic.MOIST)).append("\n");
        } catch (Exception ignored) {
        }

        try {
            result.append("averageVolumeGlassConcrete HEATED:\t").append(averageVolumeGlassConcrete(Statistic.HEATED)).append("\n");
        } catch (Exception ignored) {
        }

        try {
            result.append("averageVolumeGlassConcrete BOTH:\t").append(averageVolumeGlassConcrete(Statistic.BOTH)).append("\n");
        } catch (Exception ignored) {
        }

        try {
            result.append("averageWeightSandClay MOIST:\t").append(averageWeightSandClay(Statistic.MOIST)).append("\n");
        } catch (Exception ignored) {
        }

        try {
            result.append("averageWeightSandClay HEATED:\t").append(averageWeightSandClay(Statistic.HEATED)).append("\n");
        } catch (Exception ignored) {
        }

        try {
            result.append("averageWeightSandClay BOTH:\t").append(averageWeightSandClay(Statistic.BOTH)).append("\n");
        } catch (Exception ignored) {
        }

        try {
            result.append("averagePower:\t").append(averagePower()).append("\n");
        } catch (ArithmeticException ignored) {
        }
        try {
            result.append("averageWatertankVolume:\t").append(averageWatertankVolume()).append("\n");
        } catch (ArithmeticException ignored) {
        }

        result.append("\nNests in Formicarium:\n");
        nests.forEach(nest -> result.append(nest.toString()).append("\n"));

        return result.toString();
    }
}
