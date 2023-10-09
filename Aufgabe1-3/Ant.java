import java.util.Arrays;

public class Ant {
    Position position;
    Position lastPosition;

    public Ant() {

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
    private Cell[] possibleCells() {

    }
}
