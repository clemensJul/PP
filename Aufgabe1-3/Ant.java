import java.util.ArrayList;
import java.util.Arrays;

public class Ant implements Entity {
    private Position position;
    private final Grid grid;

    private Position direction;

    public Ant(Position position, Grid grid) {
        this.position = position;
        this.direction = Position.RandomDirection();
        this.grid = grid;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public Position getDirection() {
        return direction;
    }

    @Override
    public void update() {
        // set position based on scent and random variable
        double scentSum = 0;
        ArrayList<Cell> availableNeighbors = grid.availableNeighbours(this);

        for (Cell availableNeighbour : availableNeighbors) {
            scentSum += availableNeighbour.scent;
        }

        double randomFactor = Math.floor(Math.random() * scentSum);

        // get scent from cells
        double index = 0;
        for (Cell cell : availableNeighbors) {
            index += cell.scent;

            if (index > randomFactor) {
                int deltaX = this.position.getX() - cell.getPosition().getX();
                int deltaY = this.position.getY() - cell.getPosition().getY();

                this.direction = new Position(deltaX, deltaY);
                this.position = cell.position;
                break;
            }
        }
    }
}
