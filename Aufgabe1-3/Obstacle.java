import java.awt.*;

public class Obstacle extends Tile {
    /**
     * Initializes the Nest at the given position
     *
     * @param position Position where Nest is located.
     */
    public Obstacle(Vector position) {
        super(position);
    }

    /**
     * Nest needs no updates because it does not change colors.
     *
     * @return true
     */
    @Override
    public void update() {
        return;
    }
}
