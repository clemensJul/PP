import java.awt.*;

public class Tile implements Entity {
    private final Vector position;
    private Color tileColor = Color.gray;


    private float currentStink = 0f;
    private static final float maxStink = 1f;
    private static final float stinkDeletionRate = 0.97f;
    private final float antStink = 1f;


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
        return foodPresent > 0;
    }

    public void increaseFoodPresent() {
        foodPresent++;
    }

    public void decreaseFoodPresent() {
        if (foodPresent <= 0) {
            foodPresent = 0;
        } else {
            foodPresent--;
        }
    }

    public void increaseAntsPresent() {
        antsPresent++;
        currentStink = clamp(currentStink + antStink);
    }

    public void decreaseAntsPresent() {
        if (antsPresent <= 0) {
            antsPresent = 0;
        } else {
            antsPresent--;
        }
    }

    public float getCurrentStink() {
        return currentStink;
    }

    public Color getTileColor() {
        return tileColor;
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
}
