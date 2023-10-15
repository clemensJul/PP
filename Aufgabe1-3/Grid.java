import java.nio.file.attribute.PosixFileAttributes;
import java.util.*;

// handles the entire logic that depends on grid operations
public class Grid {
    private final int sizeX, sizeY;
    private final Tile[][] tiles;
    private Nest nest;
    private final ArrayList<Ant> ants;
    private final Queue<Tile> updateQueue;
    private final float[] bias;

    public Grid(int sizeX, int sizeY, int numberOfAnts, float[] bias) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.tiles = new Tile[sizeX][sizeY];
        this.bias = bias;
        ants = new ArrayList<>();
        updateQueue = new LinkedList<>();
        int foodCount = (int) (3 + Math.round((Math.random() * 6)));

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                tiles[x][y] = new Tile(new Vector(x, y));
            }
        }
        //place food tiles randomly
        for (int i = 0; i < foodCount; i++) {
            int randomX = (int) (Math.random() * sizeX);
            int randomY = (int) (Math.random() * sizeY);

            if (tiles[randomX][randomY] instanceof FoodSource) {
                i--;
                continue;
            }

            tiles[randomX][randomY] = new FoodSource(new Vector(randomX, randomY));
        }

        //place nest semi randomly
        int paddingInline = sizeX / 5;
        int paddingBlock = sizeX / 5;
        for (int i = 0; i < 1; i++) {
            int randomX = paddingInline + (int) (Math.random() * (sizeX - 2 * paddingInline));
            int randomY = paddingBlock + (int) (Math.random() * (sizeY - 2 * paddingBlock));

            if (tiles[randomX][randomY] instanceof FoodSource) {
                i--;
                continue;
            }

            nest = new Nest(new Vector(randomX, randomY));
            tiles[randomX][randomY] = nest;
        }

        // we need to define a radius in which ants can be spawned around the nest
        int maxSpawnDistance = 10;
        // spawn ants
        for (int i = 0; i < numberOfAnts; i++) {
            int x = Math.abs((nest.getPosition().getX() + (int) (Math.random() * maxSpawnDistance * 2) - maxSpawnDistance) % sizeX);
            int y = Math.abs((nest.getPosition().getY() + (int) (Math.random() * maxSpawnDistance * 2) - maxSpawnDistance) % sizeY);

            Vector spawnPos = new Vector(x, y);
            tiles[x][y].increaseAntsPresent();
            ants.add(new Ant(spawnPos, this));
        }

        System.out.println("created everything");
    }

    //updates every entity - if the tile needed an update it gets added to the priority queue that determines which tiles need a visual update in Codedraw
    public void update() {
        for (Ant ant : ants) {
            ant.update();
        }
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (tiles[x][y].update()) {
                    updateQueue.add(tiles[x][y]);
                }
            }
        }
    }

    //get all neighbour tiles that an ant can see
    public Tile[] availableNeighbours(Ant ant) {
        Vector direction = ant.getDirection();
        Vector position = ant.getPosition();
        Tile[] neighbours = new Tile[5];

        //generate vectors
        Vector left = Vector.orthogonalVector(direction, true);
        Vector leftFront = direction.sharpVector(left);

        Vector right = Vector.orthogonalVector(direction, false);
        Vector rightFront = direction.sharpVector(left);

        neighbours[0] = getTile(position.add(left));
        neighbours[1] = getTile(position.add(leftFront));
        neighbours[2] = getTile(position.add(direction));
        neighbours[3] = getTile(position.add(rightFront));
        neighbours[4] = getTile(position.add(right));

        return neighbours;
    }

    //returns tile to a position
    public Tile getTile(Vector position) {
        return tiles[modulo(position.getX(), sizeX)][modulo(position.getY(), sizeY)];
    }

    //returns queue of updated tiles
    public Queue<Tile> getUpdateQueue() {
        return updateQueue;
    }

    //returns an always positive int after a modulo operation
    private static int modulo(int number, int divisor) {
        return (number + divisor) % divisor;
    }

    //returns current biases as set by the simulation parameters
    public float[] getBias() {
        return Arrays.copyOf(bias, bias.length);
    }

    public Nest getNest() {
        return nest;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
