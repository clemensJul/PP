import java.awt.*;

public class Tile implements Entity {
    private Vector position;
    private float currentStink = 0f;

    private float stinkDeletionRate = 0.98f;

    private float antStink = 0.2f;
    private int foodPresent = 0;
    private int antsPresent = 0;

    private float maxStink = 1f;

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
        if (currentStink > 0.05d){
            return new Color(1f,0f,1f, currentStink);
        }
        return null;
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
        currentStink = clamp(currentStink+antStink);
    }
    private static float clamp(float value) {
        if (value < 0.0f) {
            return 0.0f;
        }
        if (value > 1.0f) {
            return 1.0f;
        }
        return value;
    }
    public void decreaseAntsPresent(){
        antsPresent--;
    }
    public double getCurrentStink() {
        return currentStink;
    }
}
