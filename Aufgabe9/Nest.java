public class Nest extends Tile {
    public Nest(Position position) {
        super(position, false);
    }

    @Override
    public char draw(){
        return '0';
    }
}
