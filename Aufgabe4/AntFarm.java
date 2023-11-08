public class AntFarm implements FormicariumPart {
    ESubstrat substrat;

    public AntFarm(ESubstrat substrat) {
        this.substrat = substrat;
    }

    @Override
    public boolean compatibleWith(Compatibility compare) {
        return false;
    }
}
