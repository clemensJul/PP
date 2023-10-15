import java.util.Arrays;

public class Ant implements Entity {
    // Current position of ant
    private Vector position;

    // Current looking direction of ant
    private Vector direction;

    // Referencing the grid for selecting neighbors
    private final Grid grid;

    private float[] bias;
    private float[] modifiedBias;

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
        bias = grid.getBias();
        modifiedBias = Arrays.copyOf(bias,bias.length);
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

        switch (state) {
            case EXPLORE -> {
                explore(neighbours);
            }
            case SCAVENGE -> {
                scavenge(neighbours);
            }
            case COLLECT -> {
                collect(neighbours);
            }

        }
        return true;
    }

    /**
     * Tries to find a neighbor tile where Nest is located.
     *
     * @param  neighbours Represents all neighbors from which we can choose
     * @return successful switch to new tile
     */
    private void collect(Tile[] neighbours) {
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] instanceof Nest) {
                changeState(State.SCAVENGE);
                moveTile(neighbours[i]);
                return;
            }
        }
        moveTile(selectMaxTile(neighbours));
    }

    /**
     * Tries to find a neighbor tile where FoodSource is located.
     * If there is no foodSource it tries to find a neighbor tile where other ants with food are.
     *
     * @param  neighbours Represents all neighbors from which we can choose
     * @return successful switch to new tile
     */
    private void scavenge(Tile[] neighbours) {
        boolean foundNeighborWithGoodScent = false;
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] instanceof FoodSource) {
                changeState(State.COLLECT);
                moveTile(neighbours[i]);
            }

            float stink = neighbours[i].getCurrentStink();
            if (stink > badScent) {
                foundNeighborWithGoodScent = true;
            }
        }

        if(foundNeighborWithGoodScent) {
            badScentsCounter = 0;
        }
        else {
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
     * @param  neighbours Represents all neighbors from which we can choose
     * @return tile
     */
    private void explore(Tile[] neighbours) {


        for (int i = 0; i < neighbours.length; i++) {

            modifiedBias[i] = bias[i] - bias[i]*neighbours[i].getCurrentStink();

            if (neighbours[i] instanceof FoodSource) {
                changeState(State.COLLECT);
                moveTile(neighbours[i]);
                return;
            }else if (neighbours[i].isFoodPresent()) {
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
     * @param  neighbours Represents all neighbors from which we can choose
     * @return random tile
     */
    private Tile selectRandomTile( Tile[] neighbours) {
        float totalWeight = 0f;
        for (int i = 0; i < modifiedBias.length; i++) {
            totalWeight += modifiedBias[i];
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


    private Tile selectMaxTile( Tile[] neighbours){
        int indexMaxWeight = 0;
        float maxWeight = 0;
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i].getCurrentStink() > maxWeight){
                maxWeight = neighbours[i].getCurrentStink();
                indexMaxWeight = i;
            }
        }
        if (maxWeight == 0 )return selectRandomTile(neighbours);
        return neighbours[indexMaxWeight];
    }


    private void changeState(State state){
        Tile currentTile = grid.getTile(position);
        switch (this.state){
            case EXPLORE -> {
                currentTile.decreaseAntsPresent();
            }
            case SCAVENGE -> {
                currentTile.decreaseAntsScavenge();
            }
            case COLLECT -> {
                currentTile.decreaseFoodPresent();
            }
        }
        switch (state){
            case EXPLORE -> {
                currentTile.increaseAntsPresent();
            }
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
     * @param  newTile a new tile
     */
    private void moveTile(Tile newTile) {
        Tile currentTile = grid.getTile(position);
        Vector newPos = newTile.getPosition();
        switch (this.state){
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
