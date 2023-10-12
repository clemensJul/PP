import java.util.Arrays;

public class Ant implements Entity {
    private Vector position;
    private Vector direction;
    private Grid grid;

    private float[] bias;
    private float[] stateBias;
    private enum State {
        EXPLORE,
        SCAVENGE,
        COLLECT
    }

    private State state;

    private final double stinkLowerBorder = 0.3;
    private final double stinkUpperBorder = 0.7;

    private final int switchToExploreAfter = 7;

    private int badScentsCounter = 0;

    public Ant(Vector position, Grid grid) {
        this.position = position;
        this.direction = Vector.RandomDirection();
        this.grid = grid;
        this.stateBias = grid.getStateBias();
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
        for (int i = 0; i < bias.length; i++) {
            totalWeight += bias[i];
        }
        Tile chosenTile = selectTile(totalWeight, neighbours);
        moveTile(chosenTile);
        return false;
    }

    private boolean collect(Tile[] neighbours) {
        for (int i = 0; i < neighbours.length; i++) {

            if ( neighbours[i] instanceof Nest){
                moveTile(neighbours[i]);
                state = State.SCAVENGE;
                grid.getTile(position).decreaseFoodPresent();
                direction = direction.invert();
                return true;
            }
            float stink = neighbours[i].getCurrentStink();
            bias[i] = bias[i] +  stink*stateBias[2];
        }
        return false;
    }

    private boolean scavenge(Tile[] neighbours) {
        float totalStink = 0f;
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] instanceof FoodSource) {
                state = State.COLLECT;
                moveTile(neighbours[i]);
                //direction = direction.invert();
                return true;
            }
            float stink = neighbours[i].getCurrentStink();
            bias[i] = bias[i] + stink*stateBias[1];
            totalStink += stink;

            if (totalStink > 0.75f) badScentsCounter++;
            else badScentsCounter = 0;
            if (badScentsCounter>switchToExploreAfter){
                state = State.EXPLORE;
            }
        }
        return false;
    }

    private boolean explore(Tile[] neighbours) {
        for (int i = 0; i < neighbours.length; i++) {

            if (neighbours[i].isFoodPresent()){
                state = State.SCAVENGE;
                moveTile(neighbours[i]);
                direction = direction.invert();
                return true;
            }else if (neighbours[i] instanceof FoodSource){
                state = State.COLLECT;
                moveTile(neighbours[i]);
                direction = direction.invert();
                return true;
            }

            float stink = neighbours[i].getCurrentStink();
            bias[i] = bias[i] * (1 - stink*stateBias[0]);
        }
        return false;
    }

    private Tile selectTile(float totalWeight, Tile[] neighbours) {
        double r = Math.random() * totalWeight;
        double cumulativeWeight = 0.0;
        double maxWeight = 0;
        int maxWeightIndex = 0;
        int randomWeightIndex = 0;
        for (int i = 0; i < bias.length; i++) {
            cumulativeWeight += bias[i];
            if (maxWeight<=bias[i]){
                maxWeight = bias[i];
                maxWeightIndex = i;
            }
            if (cumulativeWeight >= r && state == State.EXPLORE) {
                return neighbours[i];
            }
        }
        return neighbours[maxWeightIndex];
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
