import java.util.Arrays;

public class Ant implements Entity {
    Position position;

    Position direction;
    Cell[] availableNeighbours;

    public Ant(Position position) {
    this.position = position;
    this.direction = Position.RandomDirection();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public Position getDirection(){
        return direction;
    }

    public void setAvailableNeighbours(Cell[] availableNeighbours) {
        this.availableNeighbours = availableNeighbours;
    }


    // wenn du dir die nächste position ausrechnest wärs nice wenn du die direction ausrechnest und speicherst
    // das hilft mir beim grid enorm, direction sollt einfach ne position sein, die zb x = 1 und y = 0 hat, wenn die ameise zuvor
    // nach recht gegangen ist
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
