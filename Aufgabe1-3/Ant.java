import java.util.Arrays;
// Modularisierungseinheit: Klasse
// Daten werden von Ants gekapselt und nur notwendige Daten sind von außen sichtbar. (Data-Hiding)
// ist ein Untertyp von Entity. (Design By Contract)

// Ant holds the logic of the different states.
// It handles the process of finding the next tiles.
// It implements the Entity since it needs a position.
public class Ant implements Entity {
    // Current position of ant
    private Vector position;

    // Current looking direction of ant
    private Vector direction;

    // Referencing the grid for selecting neighbors
    private final Grid grid;

    private float[] modifiedBias;
    private final float[] bias;

    private enum State {
        EXPLORE,
        SCAVENGE,
        COLLECT
    }

    // possilbe state of ant
    private State state;

    // Defines the maximal successive steps of bad fields before switching to Explore state
    private final int switchToExploreAfter = 100;

    // Counts the successive steps of bad fields
    private int badScentsCounter = 0;

    // Defines what counts as a badScent tile
    private final float badScent = 0.75f;

    /**
     * Initializes an Ant at the given position
     *
     * @param position Starting position of ant
     * @param grid     Reference to Grid where the Ant lives on
     */
    public Ant(Vector position, Grid grid) {
        this.position = position;
        this.direction = Vector.RandomDirection();
        this.grid = grid;
        state = State.EXPLORE;
        bias = grid.getBias();
        modifiedBias = Arrays.copyOf(bias, bias.length);
    }

    /**
     * Handles the update process of ant.
     * Performs different actions based on the state of the ant.
     * <p>
     * For further details, see {@link #explore(Tile[])}, {@link #scavenge(Tile[])}, and {@link #collect(Tile[])}<br>
     * If there is no foodSource it tries to find a neighbor tile where other ants with food are.
     *
     * @return what does the update return value represent?
     */
    @Override
    public boolean update() {
        //get every possible neighbour and calculate standard bias
        Tile[] neighbours = grid.availableNeighbours(this);

        switch (state) {
            case EXPLORE -> explore(neighbours);
            case SCAVENGE -> scavenge(neighbours);
            case COLLECT -> collect(neighbours);
        }
        return true;
    }

    /**
     * Tries to find a neighbor tile where Nest is located.
     *
     * @param neighbours Represents all neighbors from which we can choose
     * @return successful switch to new tile
     */
    private void collect(Tile[] neighbours) {
        for (Tile neighbour : neighbours) {
            if (neighbour instanceof Nest) {
                changeState(State.SCAVENGE);
                moveTile(neighbour);
                return;
            }
        }
        moveTile(selectTileFromDirectWayToNest(neighbours));
    }

    /**
     * Selects the best neighbor given to the shortest way to the nest.
     *
     * @param neighbors Represents all neighbors from which we can choose
     * @return selected Tile from neighbors
     */
    private Tile selectTileFromDirectWayToNest(Tile[] neighbors) {
        // we need to know where the nest is
        Vector nestPosition = grid.getNest().getPosition();

        int dX = (getPosition().getX() - nestPosition.getX()) % grid.getSizeX();
        int dY = (getPosition().getY() - nestPosition.getY()) % grid.getSizeY();

        int stepsToNest = Math.abs(dX) + Math.abs(dY);
        Vector direction = new Vector(Math.round((float) dX / stepsToNest), Math.round((float) dY / stepsToNest));

        // check if both directions are possible
        for (Tile neighbor : neighbors) {
            if (neighbor.getPosition().getX() != (getPosition().getX() - direction.getX()) % grid.getSizeX()) {
                continue;
            }
            if (neighbor.getPosition().getY() != (getPosition().getY() - direction.getY() % grid.getSizeY())) {
                continue;
            }

            return neighbor;
        }

        // if both directions were not available, try only one coordinate
        for (Tile neighbor : neighbors) {
            if (neighbor.getPosition().getX() != (getPosition().getX() - direction.getX()) % grid.getSizeX()) {
                continue;
            }

            return neighbor;
        }

        for (Tile neighbor : neighbors) {
            if (neighbor.getPosition().getY() != (getPosition().getY() - direction.getY()) % grid.getSizeY()) {
                continue;
            }

            return neighbor;
        }

        // if this might also not work - choose a random neighbor
        return neighbors[(int) Math.floor(Math.random() * (neighbors.length))];
    }

    /**
     * Tries to find a neighbor tile where FoodSource is located.
     * If there is no foodSource it tries to find a neighbor tile where other ants with food are.
     *
     * @param neighbours Represents all neighbors from which we can choose
     * @return successful switch to new tile
     */
    private void scavenge(Tile[] neighbours) {
        boolean foundNeighborWithGoodScent = false;
        for (Tile neighbour : neighbours) {
            if (neighbour instanceof FoodSource) {
                changeState(State.COLLECT);
                moveTile(neighbour);
            }

            float stink = neighbour.getCurrentStink();
            if (stink > badScent) {
                foundNeighborWithGoodScent = true;
            }
        }

        if (foundNeighborWithGoodScent) {
            badScentsCounter = 0;
        } else {
            badScentsCounter++;
        }

        if (badScentsCounter > switchToExploreAfter) {
            changeState(State.EXPLORE);
        }
        moveTile(selectMaxTile(neighbours));
    }

    /**
     * Tries to find a neighbor tile where FoodSource is located. If there is a FoodSource, the ant switches its state to Collect
     * If there is no FoodSource it tries to find a neighbor tile where other ants with food are. If this is successfull, it switches its state to Scavenge.
     *
     * @param neighbours Represents all neighbors from which we can choose
     * @return tile
     */
    private void explore(Tile[] neighbours) {
        for (int i = 0; i < neighbours.length; i++) {
            modifiedBias[i] = bias[i] - bias[i] * neighbours[i].getCurrentStink();
            if (neighbours[i] instanceof FoodSource) {
                changeState(State.COLLECT);
                moveTile(neighbours[i]);
                return;
            } else if (neighbours[i].isFoodPresent()) {
                changeState(State.SCAVENGE);
                moveTile(neighbours[i]);
                return;
            }
        }

        moveTile(selectRandomTile(neighbours));
    }


    /**
     * Select a random tile.
     *
     * @param neighbours Represents all neighbors from which we can choose
     * @return random tile
     */
    private Tile selectRandomTile(Tile[] neighbours) {
        float totalWeight = 0f;
        for (float modifiedBia : modifiedBias) {
            totalWeight += modifiedBia;
        }

        double r = Math.random() * totalWeight;
        double cumulativeWeight = 0.0;
        for (int i = 0; i < modifiedBias.length; i++) {
            cumulativeWeight += modifiedBias[i];
            if (cumulativeWeight >= r) {
                return neighbours[i];
            }
        }

        //Should never be reached
        return null;
    }

    /**
     * Selects the neighbor with the highest stink.
     * If all neighbors have no stink at all - a random neighbor gets picked
     *
     * @param neighbours Represents all neighbors from which we can choose
     *
     * @return selected tile
     */
    private Tile selectMaxTile(Tile[] neighbours) {
        int indexMaxWeight = 0;
        float maxWeight = 0;
        for (int i = 0; i < neighbours.length; i++) {
            float stink;
            if (state == State.COLLECT) {
                stink = neighbours[i].getCurrentStink();
            } else {
                stink = neighbours[i].getCurrentStinkOfFood();
            }
            if (stink > maxWeight) {
                maxWeight = stink;
                indexMaxWeight = i;
            }
        }

        return maxWeight == 0 ? selectRandomTile(neighbours) : neighbours[indexMaxWeight];
    }

    /**
     * Handles the logic of increasing and decreasing the ant counter on the tiles.
     *
     * @param state State which is about to get picked
     */
    private void changeState(State state) {
        Tile currentTile = grid.getTile(position);
        switch (this.state) {
            case EXPLORE -> currentTile.decreaseAntsPresent();
            case SCAVENGE -> currentTile.decreaseAntsScavenge();
            case COLLECT -> currentTile.decreaseFoodPresent();
        }
        switch (state) {
            case EXPLORE -> currentTile.increaseAntsPresent();
            case SCAVENGE -> {
                currentTile.increaseAntsScavenge();
                if (this.state == State.COLLECT) direction = direction.invert();
            }
            case COLLECT -> {
                currentTile.increaseFoodPresent();
                direction = direction.invert();
            }
        }
        this.state = state;
    }

    /**
     * Moves the ant to the specified tile.
     *
     * @param newTile a new tile
     */
    private void moveTile(Tile newTile) {
        Tile currentTile = grid.getTile(position);
        Vector newPos = newTile.getPosition();
        switch (this.state) {
            case EXPLORE -> {
                currentTile.decreaseAntsPresent();
                newTile.increaseAntsPresent();
            }
            case SCAVENGE -> {
                currentTile.decreaseAntsScavenge();
                newTile.increaseAntsScavenge();
            }
            case COLLECT -> {
                currentTile.decreaseFoodPresent();
                newTile.increaseFoodPresent();
            }
        }
        direction = position.calculateDirection(newPos);
        position = newPos;
    }

    /**
     * Returns a Vector object that represents the current position of the ant.
     *
     * @return position of ant
     */
    @Override
    public Vector getPosition() {
        return position;
    }

    /**
     * Returns a Vector object that represents the current direction of the ant.
     *
     * @return direction of ant
     */
    public Vector getDirection() {
        return direction;
    }
}
