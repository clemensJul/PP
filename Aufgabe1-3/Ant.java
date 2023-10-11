import java.util.ArrayList;

public class Ant implements Entity {
    private Position position;

    public boolean alreadyUpdated = false;

    private String state;

    private Direction direction;

    public Ant(Position position) {
        this.position = position;
        this.direction = Direction.RandomDirection();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
}
