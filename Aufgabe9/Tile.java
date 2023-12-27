import java.util.concurrent.Semaphore;

public class Tile {
    private final Semaphore mutex = new Semaphore(1);
    private int pheromoneLevel = 0;
    private final Position position;
    private final boolean hasLeaf;

    public Tile(Position position, boolean hasLeaf) {
        this.hasLeaf = hasLeaf;
        this.position = position;
    }

    public synchronized void increasePheromoneLevel() {
        if (pheromoneLevel < 9) {
            pheromoneLevel++;
        }
    }

    public synchronized int getPheromoneLevel() {
        return pheromoneLevel;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public boolean isHasLeaf() {
        return hasLeaf;
    }

    public Position getPosition() {
        return position;
    }
}
