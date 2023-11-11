import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
// Modularisierungseinheit: Klasse
// Daten werden von Ants gekapselt und nur notwendige Daten sind von außen sichtar. (Data-Hiding)
// ist ein Untertyp von Entity

// basic tile - handles coloring logic
// it's heavily oop
public class Tile implements Entity {
    private final Vector position;
    private static final float stinkDeletionRate = 0.99f;
    private final Map<Nest, Float> stinkMap = new HashMap<>();
    private final float antStink = .2f;

    /**
     * Initializes a Tile at the given position.
     *
     * @param position Position of Tile, must be != null
     */
    public Tile(Vector position) {
        this.position = position;
    }

    /**
     * Returns the tile´s position.
     *
     * @return Position of Tile, is != null
     */
    @Override
    public Vector getPosition() {
        return position;
    }

    /**
     * Returns the nest color with the highest scent.
     * If there is no nest in the stinkMap, Color.Black is returned.
     *
     * @return Color, is != null
     */
    @Override
    public Color getColor() {
        if (stinkMap.isEmpty()) {
            return Color.BLACK;
        }
        // should return the foodScent of the nest with the highest scent
        Map.Entry<Nest, Float> pair = Collections.max(stinkMap.entrySet(), Map.Entry.comparingByValue());
        Color base = pair.getKey().getColor().brighter();
        return new Color(base.getRed(), base.getGreen(), base.getBlue(), (int) (pair.getValue() * 255));
    }

    /**
     * Reduces every value in the stinkMap on this tile by stinkDeletionRate.
     *
     * @return true, if one value in the stinkMap is greater than 0.05f.
     */
    @Override
    public boolean update() {
        // decrease stinks
        stinkMap.replaceAll((nest, stink) -> stink * stinkDeletionRate);

        // if there is a stink > 0.05f, we need to remove it from the map.
        return stinkMap.entrySet().stream().noneMatch(entry -> entry.getValue() > 0.05f);
    }

    /**
     * Returns the scent of the specified nest.
     * If nest is null, the scent with of the highest scent nest is returned
     *
     * @param nest Corresponding nest
     * @return stink of the given nest, is >= 0.
     */
    public float getCurrentStink(Nest nest) {
        // we want to return the max stink for checking if there is a stink (needed in grid)
        if (nest == null) {
            if (stinkMap.isEmpty()) {
                return 0f;
            }
            return Collections.max(stinkMap.entrySet(), Map.Entry.comparingByValue()).getValue();
        }

        Float currentStink = stinkMap.get(nest);
        if (currentStink == null) {
            return 0f;
        }
        return currentStink;
    }

    /**
     * Returns the sum of every smell that is not from the nest
     *
     * @param nest Corresponding nest, must be != null
     * @return returns float >=0f of smells from every other ant species
     */
    public float totalOtherSmell(Nest nest) {
        float sum = 0;

        for (Map.Entry<Nest, Float> entry : stinkMap.entrySet()) {
            Nest key = entry.getKey();
            Float value = entry.getValue();

            // Check if the key is not equal to the key to exclude
            if (!key.equals(nest)) {
                // Add the value to the sum
                sum += value;
            }
        }
        return sum;
    }

    /**
     * Adds one antStink to the corresponding nest.
     * The maximal ant stink of a nest can be 1.
     * Add the given nest to the stinkMap if it is not already there.
     *
     * @param nest Corresponding nest, must be != null
     */
    public void addStink(Nest nest) {
        if (nest == null) {
            return;
        }

        Float currentStink = stinkMap.get(nest);
        if (currentStink == null) {
            currentStink = 0f;
        }
        currentStink = Math.min(currentStink + antStink * 3, 1);
        stinkMap.put(nest, currentStink);
    }

    /**
     * Converts Tile toString
     * @return String repentation of Tile
     */
    @Override
    public String toString() {
        return "Tile{" +
                "position=" + position +
                '}';
    }

    /**
     * Checks if a given object is equal to this.
     * A tile is considered equal if they have the same position.
     *
     * @return tiles are equal if they are from the same class and their position is equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return Objects.equals(position, tile.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
