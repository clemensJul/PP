import java.util.Arrays;

public class Ant implements Entity {
    // Current position of ant
    private Vector position;

    // Current looking direction of ant
    private Vector direction;

    // Referencing the grid for selecting neighbors
    private final Grid grid;

    private float[] bias;

    private enum State {
        EXPLORE,
        SCAVENGE,
        COLLECT
    }

    // possilbe state of ant
    private State state;

    // Defines the maximal successive steps of bad fields before switchting to Explore state
    private final int switchToExploreAfter = 7;


    // Counts the successive steps of bad fields
    private int badScentsCounter = 0;

    // Defines what counts as a badScent tile
    private final float badScent = 0.75f;

    public Ant(Vector position, Grid grid) {
        this.position = position;
        this.direction = Vector.RandomDirection();
        this.grid = grid;
        state = state.EXPLORE;
    }

    // TODO
    /**
     * Handles the update process of ant.
     * Performs different actions based on the state of the ant.
     *
     * For further details, see {@link #explore(Tile[])}, {@link #scavenge(Tile[])}, and {@link #collect(Tile[])}<br>
     * If there is no foodSource it tries to find a neighbor tile where other ants with food are.
     *
     * @return what does the update return value represent?
     */
    @Override
    public boolean update() {
        //get every possible neighbour and calculate standard bias
        Tile[] neighbours = grid.availableNeighbours(this);
        bias = grid.getBias();

        switch (state) {
            case EXPLORE -> {
                if (explore(neighbours)) return true;
            }
            case SCAVENGE -> {
                if (scavenge(neighbours)) return true;
            }
            case COLLECT -> {
                if (collect(neighbours)) return true;
            }

        }
        float totalWeight = 0f;
        for (float bia : bias) {
            totalWeight += bia;
        }
        Tile chosenTile = selectTile(totalWeight, neighbours);
        moveTile(chosenTile);
        return false;
    }

    /**
     * Tries to find a neighbor tile where Nest is located.
     *
     * @param  neighbours Represents all neighbors from which we can choose
     * @return successful switch to new tile
     */
    private boolean collect(Tile[] neighbours) {
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] instanceof Nest) {
                moveTile(neighbours[i]);
                state = State.SCAVENGE;
                grid.getTile(position).decreaseFoodPresent();
                direction = direction.invert();
                return true;
            }
            float stink = neighbours[i].getCurrentStink();
            bias[i] = bias[i] + stink * grid.getStateBias()[2];
        }
        return false;
    }

    /**
     * Tries to find a neighbor tile where FoodSource is located.
     * If there is no foodSource it tries to find a neighbor tile where other ants with food are.
     *
     * @param  neighbours Represents all neighbors from which we can choose
     * @return successful switch to new tile
     */
    private boolean scavenge(Tile[] neighbours) {
        boolean foundNeighborWithGoodScent = false;
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] instanceof FoodSource) {
                state = State.COLLECT;
                moveTile(neighbours[i]);
                direction = direction.invert();
                return true;
            }

            float stink = neighbours[i].getCurrentStink();

            if (stink > badScent) {
                foundNeighborWithGoodScent = true;
            }
            bias[i] = bias[i] + stink * grid.getStateBias()[1];
        }

        if(foundNeighborWithGoodScent) {
            badScentsCounter = 0;
        }
        else {
            badScentsCounter++;
        }

        if (badScentsCounter > switchToExploreAfter) {
            state = State.EXPLORE;
        }

        return false;
    }

    /**
     * Tries to find a neighbor tile where FoodSource is located. If there is a FoodSource, the ant switches its state to Collect
     * If there is no FoodSource it tries to find a neighbor tile where other ants with food are. If this is successfull, it switches its state to Scavenge.
     *
     * @param  neighbours Represents all neighbors from which we can choose
     * @return tile
     */
    private boolean explore(Tile[] neighbours) {
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] instanceof FoodSource) {
                state = State.COLLECT;
                moveTile(neighbours[i]);
                direction = direction.invert();
                return true;
            }

            // TODO: sollt ma nit zuerst alle neighbors anschauen und dann zufällig aus denen auswählen?
            // jetzt wird immer der erste genommen
            if (neighbours[i].isFoodPresent()) {
                state = State.SCAVENGE;
                moveTile(neighbours[i]);
                direction = direction.invert();
                return true;
            }

            float stink = neighbours[i].getCurrentStink();
            bias[i] = bias[i] * (1 - stink * grid.getStateBias()[0]);
        }
        return false;
    }


    /**
     * Select a random tile.
     *
     * @param  totalWeight ??
     * @param  neighbours Represents all neighbors from which we can choose
     * @return random tile
     */
    private Tile selectTile(float totalWeight, Tile[] neighbours) {
        double r = Math.random() * totalWeight;
        double cumulativeWeight = 0.0;
        double maxWeight = 0;
        int maxWeightIndex = 0;
        for (int i = 0; i < bias.length; i++) {
            cumulativeWeight += bias[i];
            if (maxWeight <= bias[i]) {
                maxWeight = bias[i];
                maxWeightIndex = i;
            }
            if (cumulativeWeight >= r && state == State.EXPLORE) {
                return neighbours[i];
            }
        }
        return neighbours[maxWeightIndex];
    }

    /**
     * Moves the ant to the specified tile.
     *
     * @param  newTile a new tile
     */
    private void moveTile(Tile newTile) {
        Tile currentTile = grid.getTile(position);
        Vector newPos = newTile.getPosition();
        currentTile.decreaseAntsPresent();
        newTile.increaseAntsPresent();

        if (state == State.COLLECT) {
            currentTile.decreaseFoodPresent();
            newTile.increaseFoodPresent();
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
