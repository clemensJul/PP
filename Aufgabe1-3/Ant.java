import java.util.ArrayList;

public class Ant implements Entity {
    private Vector vector;
    private final Grid grid;

    private Vector direction;

    public Ant(Vector vector, Grid grid) {
        this.vector = vector;
        this.direction = Vector.RandomDirection();
        this.grid = grid;
    }

    @Override
    public Vector getPosition() {
        return vector;
    }

    public Vector getDirection() {
        return direction;
    }

    @Override
    public void update() {
        // set vector based on scent and random variable
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
                int deltaX = this.vector.getX() - cell.getPosition().getX();
                int deltaY = this.vector.getY() - cell.getPosition().getY();

                this.direction = new Vector(deltaX, deltaY);
                this.vector = cell.vector;
                break;
            }
        }
    }
}
