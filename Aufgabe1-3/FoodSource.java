import java.util.LinkedList;

public class FoodSource implements Cell{
    private Position position;
    public FoodSource(Position position) {
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
    public Position getPosition() {
        return null;
    }

    @Override
    public void update() {

    }
}
