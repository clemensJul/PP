import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
// Modularisierungseinheit: Klasse

// handles the entire logic that depends on grid operations
public class Grid {
    private final Map<Vector, Tile> map;
    private Vector startPoint = new Vector(-100, -125);
    private Vector endPoint = new Vector(100, 125);

    /**
     * Initializes the Grid.
     * Generates basic entities in the Grid.
     */
    public Grid() {
        this.map = new ConcurrentHashMap<>();

        int nestCounter = (int) (Math.random() * 2) + 4;
        int foodCounter = (int) (Math.random() * 20) + 12;
        int antsPerNest = 500;
        int obstacleCounter = (int) (Math.random() * 15) + 5;

        // STYLE: objektorientierte Programmierung
        // hohe Objektkopplung. Generator braucht unbedingt eine Grid-Instanz.
        Generator generator = new Generator(this, nestCounter, foodCounter, antsPerNest, obstacleCounter);
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

        long startTime = System.nanoTime();
        // BAD: ants sind zwar Entities, werden aber durch unserem Design vom Grid extra gespeichert, deshalb müssen wir die Update Methode von denen extra aufrufen

        // GOOD: Durch die Verwendung von dynamischen Binden werden von allen Entities die update Methoden aufgerufen
        List<Vector> removingItems = new CopyOnWriteArrayList<>();
        map.entrySet().parallelStream().forEach(entry -> {
            if (!(entry.getValue() instanceof Nest)) {
                return;
            }
            entry.getValue().update();
        });

        map.entrySet().parallelStream().forEach(entry -> {
            if (entry.getValue() instanceof Nest) {
                return;
            }

            if (entry.getValue().update()) {
                removingItems.add(entry.getKey());
            }
        });

        removingItems.forEach(map::remove);
//        map.entrySet().removeIf(entry -> deletionMap.get(entry.getKey()) != null);
        generateNewChunks();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 1_000_000 + "ms");
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

    public void removeTile(Tile tile) {
        map.remove(tile.getPosition());
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

        Generator generator = new Generator(this, 0, 7, 0, 12);
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
