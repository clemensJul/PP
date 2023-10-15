import java.awt.*;

//extends Tiles with custom methodes
public class Nest extends Tile {
    /**
     * Initializes the Nest at the given position
     *
     * @param position Position where Nest is located.
     */
    public Nest(Vector position) {
        super(position);
    }

    /**
     * Returns the Color of Nest.
     *
     * @return {@link Color#YELLOW}
     */
    public Color getTileColor() {
        return Color.YELLOW;
    }

    /**
     * Nest needs no updates because it does not change colors.
     *
     * @return true
     */
    @Override
    public boolean update() {
        return false;
    }
}
