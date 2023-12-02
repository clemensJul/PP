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
        if(!(formicariums.contains(formicarium))) {
            formicariums.add(formicarium);
        }
    }
    @CodedBy("Clemens")
    @SignatureAndAssertions(
            postconditions = "returns every formicarium in the institute - used for testing"
    )
    public OurLinkedList getFormicariums() {
        return formicariums;
    }

    @CodedBy("Raphael")
    @SignatureAndAssertions(
            postconditions = "Removes the formicarium from the list if it is in here."
    )
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
