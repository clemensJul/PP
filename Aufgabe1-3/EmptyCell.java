import java.util.LinkedList;

public class EmptyCell implements Cell{
    private Position position;
    public EmptyCell(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return null;
    }

    @Override
    public int numberOfAntsPresent() {
        return 0;
    }

    @Override
    public LinkedList<Ant> getAnts() {
        return null;
    }

    @Override
    public void update() {

    }
}
