import java.awt.*;
// Modularisierungseinheit: Klasse
// Daten werden von Ants gekapselt und nur notwendige Daten sind von außen sichtar. (Data-Hiding)
// ist ein Untertyp von Entity

// basic tile - handles coloring logic
public class Tile implements Entity {
    private final Vector position;
    private float currentStink = 0f;

    private static final float stinkDeletionRate = 0.99f;

    private final float antStink = .2f;
    /**
     * Initializes a Tile at the given position.
     *
     * @param position Position of Tile
     */
    public Tile(Vector position) {
        this.position = position;
    }

    /**
     * Returns the tile´s position.
     *
     * @return Position of Tile
     */
    @Override
    public Vector getPosition() {
        return position;
    }

    //updates color according to simple rules

    /**
     * Returns the tile´s color based on the ants and the currentStink.
     *
     * @return true if there is a need for an visual update.
     */
    @Override
    public void update() {

    }

}
