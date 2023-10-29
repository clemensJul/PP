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
        if(stinkMap.isEmpty()) {
            return null;
        }
        // should return the foodScent of the nest with the highest scent
        Map.Entry<Nest,Float> pair =  Collections.max(stinkMap.entrySet(), Map.Entry.comparingByValue());
        Color base = pair.getKey().getColor().brighter();
        return new Color(base.getRed(),base.getGreen(),base.getBlue(),(int)(pair.getValue()*255));
    }

    /**
     * Reduces every stink on this tile.
     *
     * @return true.
     */
    @Override
    public boolean update() {
        // decrease stinks
        stinkMap.replaceAll((nest, stink) -> stink * stinkDeletionRate);

        // if there is a stink > 0.05f, we need to remove it from the map.
        return !stinkMap.entrySet().stream().anyMatch(entry-> entry.getValue() > 0.05f);
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
            if(stinkMap.isEmpty()) {
                return 0f;
            }
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

    @Override
    public String toString() {
        return "Tile{" +
                "position=" + position +
                '}';
    }
}
