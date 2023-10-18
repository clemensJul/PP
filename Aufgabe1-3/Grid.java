import java.awt.*;
import java.util.*;
// Modularisierungseinheit: Klasse

// handles the entire logic that depends on grid operations
public class Grid {

    private final ArrayList<Ant> ants;
    private final float[] bias;
    private Map<Long, Tile> map;

    //colors
    private static final Color obstacleColor = new Color(153,70,0);
    private static final Color foodSourceColor = new Color(0,204,0);
    private static final Color emptyCellColor = new Color(224,224,224);

    public Grid(float[]bias ) {
        ants = new ArrayList<>();
        this.bias = bias;
        this.map = new HashMap<>();

    }

    /**
     * Handles the update process of the Grid.
     * If a tile needs an update, it gets added to a priority queue to handle visual updates.
     */
    public void update() {

    }

    /**
     *
     *
     *
     *
     */


    /**
     * Returns a tile at a given position
     *
     * @return Tile
     */
    public Tile getTile(Vector position) {
        long key = ((long)position.getX() << 32) | (position.getY() & 0xFFFFFFFFL);
        Tile output = map.get(key);
        if (output == null) return new Tile(position);
        return output;
    }
    public Tile getTile(int x, int y) {
        long key = ((long)x<< 32) | (y & 0xFFFFFFFFL);
        Tile output = map.get(key);
        if (output == null) return new Tile(new Vector(x,y));
        return output;
    }
    public Color getColor(Tile tile){
        if (tile == null){
            return emptyCellColor;
        }
        if (tile instanceof Tile) {
            //logic
        }
        if (tile instanceof FoodSource){
            return foodSourceColor;
        }
        if (tile instanceof Obstacle){
            return obstacleColor;
        }
        if (tile instanceof Nest) {
            //logic
        }
        return null;
    }

    public Color getColor(int x, int y){
        long key = ((long)x<< 32) | (y & 0xFFFFFFFFL);
        return getColor(map.get(key));
    }
    /**
     * Returns the biases.
     *
     * @return Bias
     */
    public float[] getBias() {
        return Arrays.copyOf(bias, bias.length);
    }

    /**
     * Returns the nest.
     *
     * @return Nest
     */
}
