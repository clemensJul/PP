import java.awt.*;
// Modularisierungseinheit: Klasse
// Daten werden von Ants gekapselt und nur notwendige Daten sind von außen sichtar. (Data-Hiding)
// ist ein Untertyp von Entity

// basic tile - handles coloring logic
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

    /**
     * Initializes a Tile at the given position.
     *
     * @param position Position of Tile
     */
    public Tile(Vector position) {
        this.position = position;
    }

    /**
     * Returns the tile´s position.
     *
     * @return Position of Tile
     */
    @Override
    public Vector getPosition() {
        return position;
    }

    //updates color according to simple rules

    /**
     * Returns the tile´s color based on the ants and the currentStink.
     *
     * @return true if there is a need for an visual update.
     */
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

    /**
     * Checks if there are any ants which carry food on this tile.
     */
    public boolean isFoodPresent() {
        return foodPresent > 0;
    }

    /**
     * Increases the value of present ants which carry food and the corresponding stink.
     */
    public void increaseFoodPresent() {
        foodPresent++;
        currentStinkOfFood = clamp(currentStinkOfFood + antStink);
    }

    /**
     * Decreases the value of present ants which carry food.
     * The minimum of the counter is 0.
     */
    public void decreaseFoodPresent() {
        if (foodPresent <= 0) {
            foodPresent = 0;
        } else {
            foodPresent--;
        }
    }

    /**
     * Increases the value of present ants which are in scavenge state.
     */
    public void increaseAntsScavenge() {
        antsScavenge++;
    }

    /**
     * Decreases the value of present ants which are in scavenge state.
     * The minimum of the counter is 0.
     */
    public void decreaseAntsScavenge() {
        if (antsScavenge <= 0) {
            antsScavenge = 0;
        } else {
            antsScavenge--;
        }
    }

    /**
     * Increases the value of present ants which are in exploring state and the corresponding stink.
     * Only ants in exploring mode are taken into account.
     */
    public void increaseAntsPresent() {
        antsPresent++;
        currentStink = clamp(currentStink + antStink);
    }

    /**
     * Makes sure the stink is always in between 0 and maxStink
     *
     * @param stink stink to clamp
     *
     * @return clamped stink
     */
    private static float clamp(float stink) {
        if (stink < 0.0f) {
            return 0.0f;
        }

        return Math.min(stink, maxStink);
    }

    /**
     * Decreases the value of present ants which are in exploring state.
     * The minimum of the counter is 0.
     */
    public void decreaseAntsPresent() {
        if (antsPresent <= 0) {
            antsPresent = 0;
        } else {
            antsPresent--;
        }
    }

    /**
     * Return the currentStink of ants.
     * This stink only describe ants which are not carrying food.
     *
     * @return currentStink
     */
    public float getCurrentStink() {
        return currentStink;
    }

    /**
     * Return the currentStinkOfFood of ants.
     * This stink only describe ants which are carrying food.
     *
     * @return currentStinkOfFood
     */
    public float getCurrentStinkOfFood() {
        return currentStinkOfFood;
    }

    /**
     * Return the color of Tile.
     *
     * @return Color of Tile
     */
    public Color getTileColor() {
        return tileColor;
    }
}
