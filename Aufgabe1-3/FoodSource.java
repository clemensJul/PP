import java.util.LinkedList;

public class FoodSource implements Cell{
    private Vector vector;
    public FoodSource(Vector vector) {
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
    public Vector getPosition() {
        return null;
    }

    @Override
    public void update() {

    }
}
