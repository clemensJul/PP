import java.awt.*;
import java.util.*;
// Modularisierungseinheit: Klasse

// handles the entire logic that depends on grid operations
public class Grid {
    private final ArrayList<Ant> ants;
    private final int[] bias;
    private Map<Vector, Tile> map;

    private int sizeX = 200;
    private int sizeY = 250;
    private Vector startPoint = new Vector(0, 0);
    private Vector endPoint = new Vector(200, 250);
    //colors
    private static final Color baseNestColor = new Color(53, 120, 0);
    private static final Color emptyCellColor = new Color(224, 224, 224);

    public Grid(int[] bias) {
        this.bias = bias;
        this.map = new HashMap<>();
        ants = new ArrayList<>();

        //place nest randomly
        int paddingInline = sizeX / 5;
        int paddingBlock = sizeX / 5;
        for (int i = 0; i < 3; i++) {
            int randomX = paddingInline + (int) (Math.random() * (sizeX - 2 * paddingInline));
            int randomY = paddingBlock + (int) (Math.random() * (sizeY - 2 * paddingBlock));

            Tile tile = map.get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            Color nestColor = new Color(baseNestColor.getRed(), baseNestColor.getGreen(), 255 / (i + 1));
            Nest nest = new Nest(new Vector(randomX, randomY), nestColor);
            putTile(nest);
//            // make the nest 3x3
//            for (int x = -1; x < 2; x++) {
//                for (int y = -1; y < 2; y++) {
//                    putTileAt(nest, new Vector(x + nest.getPosition().getX(), y + nest.getPosition().getY()));
//                }
//            }

            // spawn ants for nest
            // we need to define a radius in which ants can be spawned around the nest
            int maxSpawnDistance = 1;
            // spawn ants
            for (int antCounter = 0; antCounter < 10; antCounter++) {
                int x = Math.abs((nest.getPosition().getX() + (int) (Math.random() * maxSpawnDistance * 2) - maxSpawnDistance) % sizeX);
                int y = Math.abs((nest.getPosition().getY() + (int) (Math.random() * maxSpawnDistance * 2) - maxSpawnDistance) % sizeY);

                Vector spawnPos = new Vector(x, y);
                ants.add(new Ant(this, nest, bias, 0, 100, spawnPos));
            }
        }


        int foodCount = (int) (8 + Math.round((Math.random() * 6)));
        //place food tiles randomly
        for (int i = 0; i < foodCount; i++) {
            int randomX = (int) (Math.random() * sizeX);
            int randomY = (int) (Math.random() * sizeY);

            Tile tile = map.get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            tile = new FoodSource(new Vector(randomX, randomY));
            putTile(tile);
        }

        int obstacleCount = (int) (8 + Math.round((Math.random() * 6)));
        //place obstacle tiles randomly
        for (int i = 0; i < obstacleCount; i++) {
            int randomX = (int) (Math.random() * sizeX);
            int randomY = (int) (Math.random() * sizeY);

            Tile tile = map.get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            // make the nest nxm
            int obstacleHeight = Math.min(1 + (int) (Math.random() * 5), sizeX);
            int obstacleWidth = Math.min(1 + (int) (Math.random() * 5), sizeY);
            for (int x = 0; x < obstacleWidth; x++) {
                for (int y = 0; y < obstacleHeight; y++) {
                    Vector position = new Vector(x + randomX, y + randomY);
                    if (map.get(position) != null) {
                        continue;
                    }
                    putTileAt(new Obstacle(new Vector(randomX, randomY)), position);
                }
            }
        }
        System.out.println("created everything");
    }

    public Map<Vector, Tile> getMap() {
        return map;
    }

    /**
     * Handles the update process of the Grid.
     * If a tile needs an update, it gets added to a priority queue to handle visual updates.
     *
     * Takes care of empty foodSources.
     */
    public void update() {
        // update all entities
        // how to know which one need updates??
        ants.forEach(Ant::update);
        map.entrySet().removeIf(entry -> entry.getValue().update());
    }

    public ArrayList<Entity> queue() {
        ArrayList<Entity> entities = new ArrayList<>();

        // first add tiles with scent (scent is always <= 1f)
        map.forEach((key, value) -> {
            if (value.getCurrentStink(null) <= 1f) {
                entities.add(value);
            }
        });

        // ants
        entities.addAll(ants);

        // then obstacles, food, and nest. (scent is fixed to 100)
        map.forEach((key, value) -> {
            if (value.getCurrentStink(null) > 1f) {
                entities.add(value);
            }
        });
        return entities;
    }

    /**
     * Return all ants in grid
     *
     * @return Ants
     */
    public ArrayList<Ant> getAnts() {
        return ants;
    }

    /**
     * Returns a tile at a given position
     *
     * @param position Position
     * @return Tile
     */
    public Tile getTile(Vector position) {
        Tile tile = map.get(position);
        if(tile == null) {
            Tile newTile = new Tile(position);
            putTile(newTile);
            return newTile;
        }
        return tile;
    }

    /**
     * Returns a tile at a given position
     *
     * @param x x position
     * @param y y position
     * @return Tile
     */
    public Tile getTile(int x, int y) {
        return getTile(new Vector(x, y));
    }

    /**
     * Puts a tile into the map.
     * The key is retrieved from the tile.
     *
     * @param tile tile
     * @return Tile
     */
    private Tile putTile(Tile tile) {
        return map.put(tile.getPosition(), tile);
    }

    /**
     * Puts a tile into the map.
     * The key is directly specified.
     *
     * @param tile tile
     * @param position Position
     * @return Tile
     */
    private Tile putTileAt(Tile tile, Vector position) {
        return map.put(position, tile);
    }

    /**
     * Returns the color for a tile.
     * If tile is empty, there is a specific color.
     * Otherwise, the overwritten methods getColor() are getting called.
     *
     * @param tile tile
     * @return Color
     */
    public Color getColor(Tile tile) {
        if (tile == null) {
            return emptyCellColor;
        }
        return tile.getColor();
    }

    /**
     * Returns the biases.
     *
     * @return Bias
     */
    public int[] getBias() {
        return Arrays.copyOf(bias, bias.length);
    }

    // TODO chunk generating
    private void generateNewChunk(Ant ant) {
        int chunkSize = 200;

        // if an ant reaches end of current world
        // create new chunks

        // check which side we need to extend
        // i werd da jetzt nur einen einfachen chunk generator brauchen:
        // heißt also dass wenn die ant z.b. oben ankommt, wird einfach oben das grid um x cells erweitert
        // wenn jetzt mal links z.b. erweitert werden sollte, dann wird der chunk größer sein als der erste, weil die höhe vom ganzen grid sich geändert hat

        // check on which end the ant wants to leave the world
        Vector position = ant.getPosition();
        Vector newStartPoint = new Vector(startPoint.getX(), startPoint.getY());
        Vector newEndPoint = new Vector(endPoint.getX(), endPoint.getY());
        if (position.getX() == startPoint.getX()) {
            // extend left
            newStartPoint = new Vector(startPoint.getX() - chunkSize, startPoint.getY());
        }

        if(position.getX() == endPoint.getX()) {
            // extend right
            newEndPoint = new Vector(endPoint.getX() + chunkSize, endPoint.getY());
        }

        if (position.getY() == startPoint.getY()) {
            // extend bottom
            newStartPoint = new Vector(startPoint.getX(), startPoint.getY()  - chunkSize);
        }

        if(position.getX() == endPoint.getX()) {
            // extend top
            newEndPoint = new Vector(endPoint.getX(), endPoint.getY() + chunkSize);
        }

        // calculate area in which we should create new entities
//        Vector chunkStartingPoint = newStartPoint.subtract()
//        for (int i = 0; i < ; i++) {
//
//        }
    }
}
