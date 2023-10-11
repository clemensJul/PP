import java.util.ArrayList;
import java.util.LinkedList;

public class Nest implements Cell {
    private Position position;
    private ArrayList<Ant> ants;

    public Nest(Position position) {
        this.position = position;
        this.ants = new ArrayList<>();
    }

    @Override
    public ArrayList<Ant> getAnts() {
        return ants;
    }

    @Override
    public void update(Grid grid) {

    }

    @Override
    public void beforeUpdate() {

    }

    @Override
    public void afterUpdate() {

    }

    @Override
    public void addAnt(Ant ant) {

    }

    @Override
    public void removeAnt(Ant ant) {

    }


    @Override
    public Position getPosition() {
        return position;
    }
}
