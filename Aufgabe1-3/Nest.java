import java.util.LinkedList;

public class Nest implements Cell{
    private Vector vector;

    @Override
    public Vector getPosition() {
        return null;
    }

    public Nest(Vector vector) {
        this.vector = vector;
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
