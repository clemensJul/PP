import java.util.concurrent.Semaphore;

/**
 * Represents a tile in the arena grid.
 */
public class Tile {
    private final Semaphore mutex = new Semaphore(1); // Semaphore for controlling access to the tile.
    private int pheromoneLevel = 0; // The level of pheromone on the tile.
    private final Position position; // The position of the tile in the grid.
    private final boolean hasLeaf; // Indicates if the tile has a leaf.

    /**
     * Constructs a Tile object with the given position and leaf presence.
     *
     * @param position The position of the tile.
     * @param hasLeaf  Boolean indicating if the tile has a leaf.
     */
    public Tile(Position position, boolean hasLeaf) {
        this.hasLeaf = hasLeaf;
        this.position = position;
    }

    /**
     * Increases the pheromone level of the tile if it's less than 9.
     */
    public synchronized void increasePheromoneLevel() {
        if (pheromoneLevel < 9) {
            pheromoneLevel++;
        }
    }

    /**
     * Retrieves the pheromone level of the tile.
     *
     * @return The pheromone level of the tile.
     */
    public synchronized int getPheromoneLevel() {
        return pheromoneLevel;
    }

    /**
     * Retrieves the Semaphore controlling access to the tile.
     *
     * @return The Semaphore controlling access to the tile.
     */
    public Semaphore getMutex() {
        return mutex;
    }

    /**
     * Checks if the tile has a leaf.
     *
     * @return {@code true} if the tile has a leaf, {@code false} otherwise.
     */
    public boolean isHasLeaf() {
        return hasLeaf;
    }

    /**
     * Retrieves the position of the tile.
     *
     * @return The position of the tile.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Draws the representation of the tile.
     *
     * @return The character representation of the tile.
     */
    public char draw() {
        if (hasLeaf) {
            return 'X';
        }

        if (pheromoneLevel == 0) {
            return ' ';
        }

        return (char) (pheromoneLevel + '0');
    }
}
