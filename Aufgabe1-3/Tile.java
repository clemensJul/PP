import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
// Modularisierungseinheit: Klasse
// Daten werden von Ants gekapselt und nur notwendige Daten sind von außen sichtar. (Data-Hiding)
// ist ein Untertyp von Entity

// basic tile - handles coloring logic
public class Tile implements Entity {
    private final Vector position;

    private static final float stinkDeletionRate = 0.99f;

    private Map<Nest, Float> stinkMap = new HashMap<>();

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

    /**
     * Returns the nest color with the highest scent.
     *
     * @return Color
     */
    @Override
    public Color getColor() {
        // should return the foodScent of the nest with the highest scent
        return Collections.max(stinkMap.entrySet(), Map.Entry.comparingByValue()).getKey().getColor();
    }

    /**
     * Reduces every stink on this tile.
     *
     * @return true.
     */
    @Override
    public boolean update() {
        stinkMap.replaceAll((nest, stink) -> stink * stinkDeletionRate);
        return true;
    }

    /**
     * Returns the scent of the specified nest.
     * If nest is null, the scent with of the highest scent nest is returned
     *
     * @param nest Corresponding nest
     * @return true.
     */
    public float getCurrentStink(Nest nest) {
        // we want to return the max stink for checking if there is a stink (needed in grid)
        if(nest == null) {
            return Collections.max(stinkMap.entrySet(), Map.Entry.comparingByValue()).getValue();
        }

        Float currentStink = stinkMap.get(nest);
        if(currentStink == null) {
            return 0f;
        }
        return currentStink;
    }

    /**
     * Adds a stink to the corresponding scent.
     *
     * @param nest Corresponding nest
     * @return true.
     */
    public void addStink(Nest nest) {
        if(nest == null) {
            return;
        }

        Float currentStink = stinkMap.get(nest);
        if(currentStink == null) {
            currentStink = 0f;
        }
        currentStink = Math.min(currentStink + antStink, 1);
        stinkMap.put(nest, currentStink);
    }
}
