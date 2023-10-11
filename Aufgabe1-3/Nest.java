import java.awt.*;
import java.util.LinkedList;


public class Nest extends Tile {

    public Nest(Vector position) {
        super(position);
    }
    public Color getTileColor(){
        return Color.YELLOW;
    }

    @Override
    public boolean update() {
        return true;
    }
}
