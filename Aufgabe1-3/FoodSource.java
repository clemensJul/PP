import java.awt.*;
import java.util.LinkedList;

public class FoodSource extends Tile {


    public FoodSource(Vector position) {
        super(position);
    }
    public Color getTileColor(){
        return Color.green;
    }
    @Override
    public boolean update() {
        return true;
    }
}
