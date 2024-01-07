import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a leaf object that ants can carry.
 */
public class Leaf implements Serializable {
    private final float size; // The size of the leaf.
    private final int antId; // The ID of the ant carrying the leaf.

    /**
     * Constructs a Leaf object with a given size and associated Ant.
     *
     * @param size The size of the leaf.
     * @param ant  The Ant carrying the leaf.
     */
    public Leaf(float size, Ant ant) {
        this.size = size;
        this.antId = ant.getId();
    }

    /**
     * Retrieves the size of the leaf.
     *
     * @return The size of the leaf.
     */
    public float getSize() {
        return size;
    }

    /**
     * Retrieves the ID of the ant carrying the leaf.
     *
     * @return The ID of the ant carrying the leaf.
     */
    public int getAntId() {
        return antId;
    }

    /**
     * Generates a string representation of the Leaf object.
     *
     * @return A string representation of the Leaf object, including its size and the ID of the ant carrying it.
     */
    @Override
    public String toString() {
        return "Leaf{" +
                "size=" + BigDecimal.valueOf(size).setScale(3, RoundingMode.HALF_UP) +
                "} brought back by Ant:" + getAntId();
    }
}
