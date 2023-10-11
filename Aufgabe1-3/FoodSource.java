import java.util.ArrayList;
import java.util.LinkedList;

public class FoodSource implements Cell {
    private ArrayList<Ant> ants;
    private Position position;

    public FoodSource(Position position) {
        this.position = position;
        this.ants = new ArrayList<>();
    }


    @Override
    public ArrayList<Ant> getAnts() {
        return ants;
    }

    @Override
    public void update(Grid grid) {
        // move ants to another position
        for (Ant ant : ants) {
            // skip ants which are moved into this cell in this step to prevent multiple moves
            if(ant.alreadyUpdated) {
                continue;
            }

            ant.alreadyUpdated = true;
            // todo: we need to set ant mode to futterbringung and set it to the direction it came from
        }
    }

    @Override
    public void beforeUpdate() {

    }

    @Override
    public void afterUpdate() {

    }

    @Override
    public void addAnt(Ant ant) {
        getAnts().add(ant);
    }

    public void removeAnt(Ant ant) {
        getAnts().remove(ant);
    }



    @Override
    public Position getPosition() {
        return position;
    }
}
