public class Nest implements FormicariumPart {
    // nest needs an arena
    // for a specified amount of time, it can last without a arena
    Arena arena;


    @Override
    public boolean compatibility(Compatible compare) {
        return false;
    }
}
