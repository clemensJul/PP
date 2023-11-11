import java.awt.*;

public class Obstacle extends Tile {
    private static Color color = new Color(74, 44, 18);

    /**
     * Initializes the Nest at the given position
     *
     * @param position Position where Nest is located, must be != null.
     */
    public Obstacle(Vector position) {
        super(position);
    }

    /**
     * Returns 100.
     * Nest is not taken into account.
     * Can be everything > 0.05f
     *
     * @param nest Corresponding nest
     * @return 100
     */
    @Override
    public float getCurrentStink(Nest nest) {
        return 100;
    }

    /**
     * Return obstacleColor.
     *
     * @return Color, is != null
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Obstacle needs no updates because it does not change colors.
     *
     * @return false
     */
    @Override
    public boolean update() {
        return false;
    }
}
