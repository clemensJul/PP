import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Grid {
    private int sizeX, sizeY, numberOfAnts;
    private Tile[][] tiles;
    private ArrayList<Ant> ants;
    private Queue<Tile> updateQueue;

    public Grid(int sizeX, int sizeY, int numberOfAnts) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfAnts = numberOfAnts;
        this.tiles = new Tile[sizeX][sizeY];
        ants = new ArrayList<>();
        updateQueue = new LinkedList<>();
        int foodCount = (int) (3 + Math.round((Math.random() * 6)));

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                tiles[x][y] = new Tile(new Vector(x, y));
            }
        }

        for (int i = 0; i < foodCount; i++) {
            int randomX = (int)(Math.random() * sizeX);
            int randomY = (int)(Math.random() * sizeY);

            if(!(tiles[randomX][randomY] instanceof Tile)) {
                i--;
                continue;
            }

            tiles[randomX][randomY] = new FoodSource(new Vector(randomX, randomY));
        }

        Nest nest = null;
        for (int i = 0; i < 1; i++) {
            int randomX = (int)(Math.random() * sizeX);
            int randomY = (int)(Math.random() * sizeY);

            if(!(tiles[randomX][randomY] instanceof Tile)) {
                i--;
                continue;
            }

            nest = new Nest(new Vector(randomX, randomY));
            tiles[randomX][randomY] = nest;
        }

        // we need to define a radius in which ants can be spawned around the nest
        int maxSpawnDistance = 2;
        // spawn ants
        for (int i = 0; i < numberOfAnts; i++) {
            int x = Math.abs((nest.getPosition().getX() + (int)(Math.random() * maxSpawnDistance*2)-maxSpawnDistance) % sizeX);
            int y = Math.abs((nest.getPosition().getY() + (int)(Math.random() * maxSpawnDistance*2)-maxSpawnDistance) % sizeY);

            Vector spawnPos = new Vector(x, y);
            tiles[x][y].increaseAntsPresent();
            ants.add(new Ant(spawnPos,this));
        }

        System.out.println("created everything");
    }

    public void update() {
        for (Ant ant :
                ants) {
            ant.update();
        }
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (tiles[x][y].update()){
                    updateQueue.add(tiles[x][y]);
                }
            }
        }
    }
    //get all neighbours
    public Tile[] availableNeighbours(Ant ant) {
        Vector direction = ant.getDirection();
        Vector position = ant.getPosition();
        Tile[] neighbours = new Tile[5];

        //generate vectors
        Vector left = Vector.orthogonalVector(direction,true);
        Vector leftFront = direction.add(left);

        Vector right = Vector.orthogonalVector(direction,false);
        Vector rightFront = direction.add(right);

        neighbours[0] = getTile(position.add(left));
        neighbours[1] = getTile(position.add(leftFront));
        neighbours[2] = getTile(position.add(direction));
        neighbours[3] = getTile(position.add(rightFront));
        neighbours[4] = getTile(position.add(right));

        return neighbours;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(Vector position) {
        return tiles[modulo(position.getX(),sizeX)][modulo(position.getY(),sizeY)];
    }
    public Tile getTile(int x, int y) {
        return tiles[modulo(x,sizeX)][modulo(y,sizeY)];
    }
    public Queue<Tile> getUpdateQueue() {
        return updateQueue;
    }

    private static int modulo(int number, int divisor){
        return (number+divisor)%divisor;
    }
    /*
     * -1,1  0,1  1,1
     * -1,0  0,0  1,0
     * -1-1  0-1  1-1
     * */
}
