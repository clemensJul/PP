import java.awt.*;
import java.util.LinkedList;

public class FoodSource extends Tile {


    public FoodSource(Vector position) {
        super(position);
    }
    public Color getTileColor(){

        return new Color(0,150,0);
    }
    @Override
    public boolean update() {
        return true;
    }
}
