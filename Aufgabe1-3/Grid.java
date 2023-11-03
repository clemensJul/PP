import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
// Modularisierungseinheit: Klasse

// handles the entire logic that depends on grid operations
public class Grid {
    private final int[] bias;
    private final Map<Vector, Tile> map;

    private Vector startPoint = new Vector(0, 0);
    private Vector endPoint = new Vector(200, 250);
    //colors
    private static final Color emptyCellColor = new Color(224, 224, 224);

    /**
     * Initializes the Grid.
     * Generates basic entities in the Grid.
     *
     * @param bias Biases for ants on directions
     */
    public Grid(int[] bias) {
        this.bias = bias;
        this.map = new HashMap<>();

        int nestCounter = (int) (Math.random() * 2) + 4;
        int foodCounter = (int) (Math.random() * 20) + 12;
        int antsPerNest = (int) (Math.random() * 100) + 75;
        int obstacleCounter = (int) (Math.random() * 15) + 5;

        // STYLE: objektorientierte Programmierung
        // hohe Objektkopplung. Generator braucht unbedingt eine Grid-Instanz.
        Generator generator = new Generator(this, nestCounter, foodCounter, antsPerNest, obstacleCounter, bias);
        generator.generateTilesForChunk(startPoint, endPoint);
        System.out.println("created everything!");
    }

    /**
     * Returns the entities in a map.
     *
     * @return Entity Map
     */
    public Map<Vector, Tile> getMap() {
        return map;
    }

    /**
     * Handles the update process of the Grid.
     * Tiles which are not needed anymore are removed from the map.
     */
    public void update() {
        // update all entities

        // BAD: ants sind zwar Entities, werden aber durch unserem Design vom Grid extra gespeichert, deshalb müssen wir die Update Methode von denen extra aufrufen
//        ants.forEach(Ant::update);

        // GOOD: Durch die Verwendung von dynamischen Binden werden von allen Entities die update Methoden aufgerufen
        map.entrySet().removeIf(entry -> entry.getValue().update());

        Thread[] threads = getNests().stream().map(Thread::new).toArray(Thread[]::new);

        // start threads
        for (Thread thread : threads) {
            thread.start();
        }

        // wait for threads to finish
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Thread " + i + " wurde unterbrochen.");
            }
        }


        System.out.println("All threads finished");
        generateNewChunks();
    }

    public ArrayList<Nest> getNests() {
        ArrayList<Nest> nests = new ArrayList<>();

        map.forEach((key, value) -> {
            if (value instanceof Nest nest) {
                nests.add(nest);
            }
        });

        return nests;
    }

    public ArrayList<Ant> getAnts() {
        ArrayList<Ant> ants = new ArrayList<>();
        getNests().forEach(nest -> ants.addAll(nest.getAnts()));
        return ants;
    }

    /**
     * Returns all entities which need an update in a List.
     * Map is build in this way:
     * normal tiles,
     * ants,
     * foodSources, obstacles & nests
     *
     * @return Entity List
     */
    public ArrayList<Entity> queue() {
        ArrayList<Entity> entities = new ArrayList<>();

        // first add tiles with scent (scent is always <= 1f)
        map.forEach((key, value) -> {
            if (value.getCurrentStink(null) <= 1f) {
                entities.add(value);
            }
        });

        // ants
        entities.addAll(getAnts());

        // then obstacles, food, and nest. (scent is fixed to 100)
        map.forEach((key, value) -> {
            if (value.getCurrentStink(null) > 1f) {
                entities.add(value);
            }
        });
        return entities;


    }

    /**
     * Returns a tile at a given position
     *
     * @param position Position
     * @return Tile
     */
    public Tile getTile(Vector position) {
        Tile tile = map.get(position);
        if (tile == null) {
            Tile newTile = new Tile(position);
            putTile(newTile);
            return newTile;
        }
        return tile;
    }
    public void removeTile(Tile tile){
//        if(tile instanceof FoodSource) {
//            ants.forEach(ant -> ant.removeLocation(tile));
//        }
        map.remove(tile.getPosition());
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
     * @param tile     tile
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

    /**
     * Checks if there is any ant which want to go to unknown territory.
     * If so, the map increases and new obstacles, nests and foodsources are getting generated.
     */
    private void generateNewChunks() {
        int chunkSize = 200;

        Boolean[] extendSides = new Boolean[4];
        ArrayList<Ant> ants = getAnts();

        extendSides[0] = ants.stream().anyMatch(ant -> ant.getPosition().getX() == startPoint.getX());
        extendSides[1] = ants.stream().anyMatch(ant -> ant.getPosition().getX() == endPoint.getX());
        extendSides[2] = ants.stream().anyMatch(ant -> ant.getPosition().getY() == startPoint.getY());
        extendSides[3] = ants.stream().anyMatch(ant -> ant.getPosition().getY() == endPoint.getY());

        // no need to update chunks
        if (!List.of(extendSides).contains(true)) {
            return;
        }

        Generator generator = new Generator(this, 0, 7, 0, 12, bias);
        Vector newStartPoint, newChunkStartPoint, newEndPoint, newChunkEndPoint;
        if (extendSides[0]) {
            // extend left
            newStartPoint = new Vector(startPoint.getX() - chunkSize, startPoint.getY());
//            newEndPoint = endPoint;

            newChunkStartPoint = newStartPoint;
            newChunkEndPoint = new Vector(startPoint.getX(), endPoint.getY());

            startPoint = newStartPoint;

            generator.generateTilesForChunk(newChunkStartPoint, newChunkEndPoint);
        }

        if (extendSides[1]) {
            // extend right
//            newStartPoint = startPoint;
            newEndPoint = new Vector(endPoint.getX() + chunkSize, endPoint.getY());

            newChunkStartPoint = new Vector(endPoint.getX(), startPoint.getY());
            newChunkEndPoint = endPoint;

            endPoint = newEndPoint;

            generator.generateTilesForChunk(newChunkStartPoint, newChunkEndPoint);
        }

        if (extendSides[2]) {
            // extend bottom
            newStartPoint = new Vector(startPoint.getX(), startPoint.getY() - chunkSize);
//            newEndPoint = endPoint;

            newChunkStartPoint = newStartPoint;
            newChunkEndPoint = new Vector(endPoint.getX(), startPoint.getY());

            startPoint = newStartPoint;

            generator.generateTilesForChunk(newChunkStartPoint, newChunkEndPoint);
        }

        if (extendSides[3]) {
            // extend top
//            newStartPoint = startPoint;
            newEndPoint = new Vector(endPoint.getX(), endPoint.getY() + chunkSize);

            newChunkStartPoint = new Vector(startPoint.getX(), endPoint.getY());
            newChunkEndPoint = newEndPoint;

            endPoint = newEndPoint;

            generator.generateTilesForChunk(newChunkStartPoint, newChunkEndPoint);
        }
    }
}
