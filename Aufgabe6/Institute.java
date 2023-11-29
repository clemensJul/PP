@CodedBy("Raphael")
public class Institute {
    private final OurLinkedList formicariums;
    private final String name;

    public Institute(String name) {
        this.name = name;
        formicariums = new OurLinkedList();
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            historyConstrains = "only for testing"
    )
    public void add(Formicarium formicarium) {
        formicariums.add(formicarium);
    }

    public void remove(Formicarium formicarium) {
        formicariums.remove(formicarium);
    }

    // TODO string representation of formicariums
    @Override
    public String toString() {
        return "Institute{" +
                "formicariums=" + formicariums +
                '}';
    }
}
