// Modularisierungseinheit: Klasse
// Eine Erweiterung der Tile-Klasse, die ein Untertyp von Entity ist.

import java.awt.*;

// This class extends from Tile
// It describes the FoodSources
public class FoodSource extends Tile {
    private static Color color = Color.GREEN;
    private int foodAmount = 200;

    /**
     * Initializes a FoodSource at the given position.
     *
     * @param position Position of FoodSource
     */
    public FoodSource(Vector position) {
        super(position);
    }

    /**
     * Returns a static stink value.
     *
     * @return 100
     */
    @Override
    public float getCurrentStink(Nest nest) {
        return 100;
    }

    /**
     * Returns the color of a foodsource, where the remaining amount is indicated by the opacity.
     *
     * @return Color
     */
    @Override
    public Color getColor() {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Decreases the amount of left food.
     */
    public boolean decreaseFoodAmount() {
        foodAmount--;
        if (foodAmount == 0) return false;
        return true;
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
        return foodAmount < 0;
    }
}
