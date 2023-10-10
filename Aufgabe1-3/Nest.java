import java.util.LinkedList;

public class Nest implements Cell{
    private Position position;

    @Override
    public Position getPosition() {
        return null;
    }

    public Nest(Position position) {
        this.position = position;
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
