import java.awt.*;

public class Tile implements Entity {
    private Vector position;
    private float currentStink = 0f;

    private float stinkDeletionRate = 0.98f;

    private float antStink = 0.2f;
    private int foodPresent = 0;
    private int antsPresent = 0;

    private float maxStink = 1f;

    private Color tileColor = Color.gray;

    public Tile(Vector position) {
        this.position = position;
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
    public boolean update() {
        currentStink *= stinkDeletionRate;
        if (foodPresent > 0){
            tileColor = Color.green;
            return true;
        }
        if (antsPresent > 0){
            tileColor = Color.black;
            return true;
        }
        else if (currentStink > 0.05d){
            tileColor = new Color(1f,0f,1f, currentStink);
            return true;
        }
        else {
            tileColor = Color.gray;
            return false;
        }
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
    public float getCurrentStink() {
        return currentStink;
    }
    public Color getTileColor(){
        return tileColor;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "position=" + position +
                '}';
    }
}
