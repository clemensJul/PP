import java.awt.*;

public class Tile implements Entity {
    private final Vector position;
    private float currentStink = 0f;

    private static final float stinkDeletionRate = 0.97f;

    private final float antStink = .2f;
    private int foodPresent = 0;
    private int antsPresent = 0;

    // only for appearance
    private int antsScavenge = 0;

    private float currentStinkOfFood = 0f;

    private static final float maxStink = 1f;

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
        currentStinkOfFood *= stinkDeletionRate;

        if (foodPresent > 0) {
            tileColor = Color.green;
            return true;
        }


        if (antsPresent > 0) {
            tileColor = Color.black;
            return true;
        }

        if (currentStinkOfFood > 0.005f) {
            tileColor = new Color(1f, 1f, 1f, currentStinkOfFood);
            return true;
        }

        if (currentStink > 0.005f) {
            tileColor = new Color(1f, 0f, 1f, currentStink);
            return true;
        }

        tileColor = Color.gray;
        return false;
    }

    public boolean isFoodPresent() {
        return foodPresent > 0;
    }

    public void increaseFoodPresent() {
        foodPresent++;
        currentStinkOfFood = clamp(currentStinkOfFood + antStink);
    }

    public void decreaseFoodPresent() {
        if (foodPresent <= 0) {
            foodPresent = 0;
        } else {
            foodPresent--;
        }
    }

    public void increaseAntsScavenge() {
        antsScavenge++;
    }

    public void decreaseAntsScavenge() {
        if (antsScavenge <= 0) {
            antsScavenge = 0;
        } else {
            antsScavenge--;
        }
    }

    public void increaseAntsPresent() {
        antsPresent++;
        currentStink = clamp(currentStink + antStink);
    }

    private static float clamp(float stink) {
        if (stink < 0.0f) {
            return 0.0f;
        }

        return Math.min(stink, maxStink);
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

    public float getCurrentStinkOfFood() {
        return currentStinkOfFood;
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
