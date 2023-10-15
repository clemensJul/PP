import java.awt.*;

// basic tile  - handles coloring logic
public class Tile implements Entity {
    private final Vector position;
    private float currentStink = 0f;

    private static final float stinkDeletionRate = 0.99f;

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

    //updates color according to simple rules
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

    // checks if ant with food is present
    public boolean isFoodPresent() {
        return foodPresent > 0;
    }

    // increases stink of food and food present
    public void increaseFoodPresent() {
        foodPresent++;
        currentStinkOfFood = clamp(currentStinkOfFood + antStink);
    }

    // decreases food present
    public void decreaseFoodPresent() {
        if (foodPresent <= 0) {
            foodPresent = 0;
        } else {
            foodPresent--;
        }
    }

    // increases ants scavenging on tile
    public void increaseAntsScavenge() {
        antsScavenge++;
    }

    // decreases ants scavenging on tile
    public void decreaseAntsScavenge() {
        if (antsScavenge <= 0) {
            antsScavenge = 0;
        } else {
            antsScavenge--;
        }
    }

    // increases ants presents
    public void increaseAntsPresent() {
        antsPresent++;
        currentStink = clamp(currentStink + antStink);
    }

    // created math clamp in java
    private static float clamp(float stink) {
        if (stink < 0.0f) {
            return 0.0f;
        }

        return Math.min(stink, maxStink);
    }

    // decreases ants present
    public void decreaseAntsPresent() {
        if (antsPresent <= 0) {
            antsPresent = 0;
        } else {
            antsPresent--;
        }
    }

    // get current smell of ants
    public float getCurrentStink() {
        return currentStink;
    }

    // get current stink of food
    public float getCurrentStinkOfFood() {
        return currentStinkOfFood;
    }

    // returns color of the tile
    public Color getTileColor() {
        return tileColor;
    }
}
