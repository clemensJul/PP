import java.util.LinkedList;

public class EmptyCell implements Cell{
    private Vector vector;
    private double currentStink;
    private double stinkDeletionRate = 0.95d;
    public EmptyCell(Vector vector) {
        this.vector = vector;
    }

    @Override
    public Vector getPosition() {
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
