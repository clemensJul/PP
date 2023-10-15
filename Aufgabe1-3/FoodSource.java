import java.awt.*;
import java.util.LinkedList;

//extends Tile with custom methodes
public class FoodSource extends Tile {

    public FoodSource(Vector position) {
        super(position);
    }

    public Color getTileColor() {

        return new Color(150, 200, 0);
    }

    @Override
    public boolean update() {
        return true;
    }
}
