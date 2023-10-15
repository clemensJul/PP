import java.awt.*;

public class Tile implements Entity {
    private Vector position;
    private float currentStink = 0f;

    private static float stinkDeletionRate = 0.97f;

    private float antStink = 1f;
    private int foodPresent = 0;
    private int antsPresent = 0;

    private static float maxStink = 1f;

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
        if (foodPresent > 0) {
            tileColor = Color.green;
            return true;
        }
        if (antsPresent > 0) {
            tileColor = Color.black;
            return true;
        } else if (currentStink > 0.005f) {
            tileColor = new Color(1f, 0f, 1f, currentStink);
            return true;
        } else {
            tileColor = Color.gray;
            return false;
        }
    }

    public boolean isFoodPresent() {
        if (foodPresent > 0) return true;
        return false;
    }

    public void increaseFoodPresent() {
        foodPresent++;
        currentStink = clamp(currentStink+antStink);
    }

    public void decreaseFoodPresent() {
        foodPresent--;
    }

    public void increaseAntsPresent() {
        antsPresent++;
        currentStink = clamp(currentStink + antStink);
    }

    private static float clamp(float stink) {
        if (stink < 0.0f) {
            return 0.0f;
        }
        if (stink > maxStink) {
            return 1.0f;
        }
        return stink;
    }

    public void decreaseAntsPresent() {
        antsPresent--;
    }

    public float getCurrentStink() {
        return currentStink;
    }

    public Color getTileColor() {
        return tileColor;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "position=" + position +
                '}';
    }
}
