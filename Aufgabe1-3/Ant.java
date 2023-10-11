import java.util.ArrayList;

public class Ant implements Entity {
    private Vector position;
    private Vector direction;
    private Grid grid;
    private boolean carriesFood;

    private enum State{
        EXPLORE,
        SCAVENGE,
        COLLECT
    }
    private State state;

    public Ant(Vector position, Grid grid) {
        this.position = position;
        this.direction = Vector.RandomDirection();
        this.grid = grid;
        state = state.EXPLORE;
    }
    @Override
    public void update() {
        Tile[] neighbours = grid.availableNeighbours(this);

        for (int i = 0; i < neighbours.length; i++) {
            switch (state) {
                case EXPLORE -> {
                }
                case SCAVENGE -> {
                }
                case COLLECT -> {
                }
            }
        }

        grid.getTile(position).decreaseAntsPresent();
        position = neighbours[2].getPosition();
        grid.getTile(position).increaseAntsPresent();
    }

    private void move(){

    }

    @Override
    public Vector getPosition() {
        return position;
    }

    public Vector getDirection() {
        return direction;
    }
}
