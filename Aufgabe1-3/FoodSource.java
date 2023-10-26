// Modularisierungseinheit: Klasse
// Eine Erweiterung der Tile-Klasse, die ein Untertyp von Entity ist.

import java.awt.*;

// This class extends from Tile
// It describes the FoodSources
public class FoodSource extends Tile {
    private static Color color = Color.GREEN;
    private int foodAmount = 100;

    /**
     * Initializes a FoodSource at the given position.
     *
     * @param position Position of FoodSource
     */
    public FoodSource(Vector position) {
        super(position);
    }

    @Override
    public float getCurrentStink(Nest nest) {
        return 100;
    }

    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Decreases the amount of left food.
     */
    public void decreaseFoodAmount() {
        foodAmount--;
    }

    /**
     * Return left foodAmount.
     */
    public int getFoodAmount() {
        return foodAmount;
    }

    /**
     * There is no need to update a FoodSource
     *
     * @return always true
     */
    @Override
    public boolean update() {
        return false;
    }
}
