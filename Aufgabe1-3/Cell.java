import java.util.ArrayList;
import java.util.LinkedList;

public interface Cell extends Entity {
    double scent = 0;
    ArrayList<Ant> ants = new ArrayList<>();

    ArrayList<Cell> neighboorHood = new ArrayList<>();

    //returns length of ants
    ArrayList<Ant> getAnts();

    void update(Grid grid);

    void beforeUpdate();

    void afterUpdate();

    // indicate which ants are already moved in one update
    default void setAntsToUpdated() {
        for (Ant ant : ants) {
            ant.alreadyUpdated = false;
        }
    }

    default void setAntsToUpdate() {
        for (Ant ant : ants) {
            ant.alreadyUpdated = true;
        }
    }

    // things to do with ant when added to cell
    void addAnt(Ant ant);

    //things to do with ant when removed from cell
    void removeAnt(Ant ant);

    default ArrayList<Cell> getNeighbours(Grid grid) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        int currentX = getPosition().getX();
        int currentY = getPosition().getY();

        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }

                int neighorX = Math.abs((currentX + x) % grid.getSizeX());
                int neighorY = Math.abs((currentY + y) % grid.getSizeY());

                neighbors.add(grid.getCells()[neighorX][neighorY]);
            }
        }

        return neighbors;
    }
}
