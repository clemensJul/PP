import java.util.Arrays;

public class Ant implements Entity {
    private Vector position;
    private Vector direction;
    private Grid grid;

    private float[] bias;
    private enum State {
        EXPLORE,
        SCAVENGE,
        COLLECT
    }

    private State state;

    private final int switchToExploreAfter = 7;

    private int badScentsCounter = 0;

    public Ant(Vector position, Grid grid) {
        this.position = position;
        this.direction = Vector.RandomDirection();
        this.grid = grid;
        state = state.EXPLORE;
    }

    @Override
    public boolean update() {

        //get every possible neighbour and calculate standard bias
        Tile[] neighbours = grid.availableNeighbours(this);
//        System.out.println(position);
//        System.out.println(direction);
//        System.out.println(Arrays.toString(neighbours));

        bias = grid.getBias();
        switch (state){
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
        return false;
    }

    private void collect(Tile[] neighbours) {
        for (int i = 0; i < neighbours.length; i++) {

            if ( neighbours[i] instanceof Nest){
                moveTile(neighbours[i]);
                state = State.SCAVENGE;
                grid.getTile(position).decreaseFoodPresent();
                direction = direction.invert();
                return;
            }
        }
        moveTile(selectStrongestSmell(neighbours));
    }

    private void  scavenge(Tile[] neighbours) {
        float totalStink = 0f;
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] instanceof FoodSource) {
                state = State.COLLECT;
                moveTile(neighbours[i]);
                //direction = direction.invert();
                return;
            }
            float stink = neighbours[i].getCurrentStink();
            totalStink += stink;

        }
        if (totalStink > 0.75f) badScentsCounter++;
        else badScentsCounter = 0;
        if (badScentsCounter>switchToExploreAfter){
            state = State.EXPLORE;
        }
        moveTile(selectStrongestSmell(neighbours));
    }

    private void explore(Tile[] neighbours) {
        for (int i = 0; i < neighbours.length; i++) {

            bias[i] = bias[i] - bias[i]*neighbours[i].getCurrentStink();
            if (neighbours[i].isFoodPresent()){
                state = State.SCAVENGE;
                moveTile(neighbours[i]);
                direction = direction.invert();
                return;

            }else if (neighbours[i] instanceof FoodSource){
                state = State.COLLECT;
                moveTile(neighbours[i]);
                direction = direction.invert();
                return;
            }

        }
        moveTile(selectRandomTile(neighbours));
    }

    //selects tile with strongest smell - call selectRandomTile
    private Tile selectStrongestSmell(Tile[] neighbours){
        int indexMaxWeight = 0;
        float maxWeight = 0;
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i].getCurrentStink() > maxWeight){
                maxWeight = neighbours[i].getCurrentStink();
                indexMaxWeight = i;
            }
        }
        if (maxWeight == 0) return selectRandomTile(neighbours);
        return neighbours[indexMaxWeight];
    }

    private Tile selectRandomTile(Tile[] neighbours) {
        float totalWeight = 0f;
        for (int i = 0; i < bias.length; i++) {
            totalWeight += bias[i];
        }
        double r = Math.random() * totalWeight;
        double cumulativeWeight = 0.0;

        for (int i = 0; i < bias.length; i++) {
            cumulativeWeight += bias[i];

            if (cumulativeWeight >= r) {
                return neighbours[i];
            }
        }
        return null;
    }
    private void moveTile(Tile newTile){
        Tile currentTile = grid.getTile(position);
        Vector newPos = newTile.getPosition();

        currentTile.decreaseAntsPresent();
        newTile.increaseAntsPresent();
        if (state == State.COLLECT){
            currentTile.decreaseFoodPresent();
            newTile.increaseFoodPresent();
        }

        direction = position.calculateDirection(newPos);
        position = newPos;
    }


    @Override
    public Vector getPosition() {
        return position;
    }

    public Vector getDirection() {
        return direction;
    }
}
