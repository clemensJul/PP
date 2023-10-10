import java.util.LinkedList;

public class EmptyCell implements Cell{
    private Position position;
    private double currentStink;
    private double stinkDeletionRate = 0.95d;
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
        currentStink *= stinkDeletionRate;
    }

    public double getCurrentStink() {
        return currentStink;
    }
    public void addStink(double stinkPerAnt){
        currentStink += stinkPerAnt;
    }
}
