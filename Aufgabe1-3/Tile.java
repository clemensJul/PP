import java.awt.*;

public class Tile implements Entity {
    private Vector position;
    private double currentStink = 0;

    private double stinkDeletionRate = 0.95;
    private int foodPresent = 0;
    private int antsPresent = 0;

    public Tile(Vector position) {
        this.position = position;
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public void update() {
        currentStink*= stinkDeletionRate;
    }
    public Color tileColor(){
        if (antsPresent > 0) return Color.black;
        return Color.GRAY;
    }

    public boolean isFoodPresent(){
        if (foodPresent > 0 ) return true;
        return false;
    }
    public void increaseFoodPresent(){
        foodPresent++;
    }
    public void decreaseFoodPresent(){
        foodPresent--;
    }
    public void increaseAntsPresent(){
        antsPresent++;
    }
    public void decreaseAntsPresent(){
        antsPresent--;
    }
    public double getCurrentStink() {
        return currentStink;
    }

    public void addStink(double stink ) {
        currentStink += stink;
    }
}
