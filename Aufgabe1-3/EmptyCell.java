import java.util.ArrayList;

public class EmptyCell implements Cell {
    private Position position;
    private double currentStink = 0;
    private double stinkDeletionRate = 0.95;

    private double stinkPerAnt = 0.01;
    private ArrayList<Ant> ants;

    public EmptyCell(Position position) {
        this.position = position;
        this.ants = new ArrayList<>();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public ArrayList<Ant> getAnts() {
        return this.ants;
    }

    @Override
    public void update(Grid grid) {
        // move ants to another position
        for (Ant ant : ants) {
            // skip ants which are moved into this cell in this step to prevent multiple moves
            if(ant.alreadyUpdated) {
                continue;
            }

            int directionX = ant.getDirection().getX();
            int directionY = ant.getDirection().getY();

            ArrayList<Cell> neighborCells = getNeighbours(grid);
            ArrayList<Position> possibleMoves = ant.getDirection().getNeighbors();

            Cell nextCell = neighborCells.get((int)Math.floor(Math.random() * neighborCells.size()));
            ant.alreadyUpdated = true;
            nextCell.addAnt(ant);
            removeAnt(ant);
            // todo move ant according to state and randomness
        }
    }

    @Override
    public void beforeUpdate() {
        currentStink *= stinkDeletionRate;
    }

    @Override
    public void afterUpdate() {
        // we now have new ants here

        // TODO: only ants which carry food
        currentStink += stinkPerAnt * ants.size();
    }

    @Override
    public void addAnt(Ant ant) {
        // maybe switch from erkundung to futtersuche
        getAnts().add(ant);
    }

    @Override
    public void removeAnt(Ant ant) {
        getAnts().remove(ant);
    }


    public double getCurrentStink() {
        return currentStink;
    }
}
