public class AntFarm implements FormicariumPart {
    ESubstrat substrat;

    public AntFarm(ESubstrat substrat) {
        this.substrat = substrat;
    }

    @Override
    public boolean compatibility(Compatible compare) {
        return false;
    }
}
