import java.util.Arrays;

public class Ant implements Entity {
    Position position;
    Position lastPosition;
    Cell[] availableNeighbours;

    public Ant(Position position) {
    this.position = position;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    public Position getLookDirection(){
        int x = position.getX()+ lastPosition.getX();
        int y = position.getY()+ lastPosition.getY();
        return new Position(x,y);
    }

    public void setAvailableNeighbours(Cell[] availableNeighbours) {
        this.availableNeighbours = availableNeighbours;
    }

    private void setPosition(Cell[] cells) {
        // set position based on scent and random variable
        double scentSum = 0;

        for (int i = 0; i < cells.length; i++) {
            scentSum += cells[i].scent;
        }

        double randomFactor = Math.floor(Math.random() * scentSum);

        // get scent from cells
        double index = 0;

        for (Cell cell : cells) {
            index += cell.scent;

            if (index > randomFactor) {
                this.position = cell.position;
                return;
            }
        }

    }

    //

    @Override
    public void update() {

    }
}
