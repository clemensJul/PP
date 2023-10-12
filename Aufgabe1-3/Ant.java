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

    private final double stinkLowerBorder = 0.3;
    private final double stinkUpperBorder = 0.7;

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
        Tile[] neighbours = grid.availableNeighbours(this);
        bias = new float[]{0.0f,0.25f,1.5f,0.25f,0.0f};

        switch (state){
            case EXPLORE -> {
                for (int i = 0; i < neighbours.length; i++) {
                    bias[i] = bias[i] - bias[i]*neighbours[i].getCurrentStink();
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
                }
            }
            case SCAVENGE -> {
                float totalStink = 0f;
                for (int i = 0; i < neighbours.length; i++) {
                    float stink = neighbours[i].getCurrentStink();
                    bias[i] = bias[i]/2 + bias[i]*stink/2;
                    totalStink += stink;

                    if (totalStink > 0.75f) badScentsCounter++;
                    else badScentsCounter = 0;
                    if (badScentsCounter>switchToExploreAfter){
                        state = State.EXPLORE;
                    }

                    if (neighbours[i].isFoodPresent()){
                        state = State.COLLECT;
                        moveTile(neighbours[i]);
                        return true;
                    }
                }
            }
            case COLLECT -> {
                for (int i = 0; i < neighbours.length; i++) {
                    bias[i] = bias[i] * bias[i]*neighbours[i].getCurrentStink()/2;
                    if ( neighbours[i] instanceof Nest){
                        moveTile(neighbours[i]);

                        state = State.SCAVENGE;
                        grid.getTile(position).decreaseFoodPresent();
                        direction = direction.invert();
                        return true;
                    }
                }
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
    private Tile selectTile(float totalWeight, Tile[] neighbours) {
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
