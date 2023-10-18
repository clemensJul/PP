import java.awt.*;
// Modularisierungseinheit: Klasse
// Eine Erweiterung der Tile-Klasse, die ein Untertyp von Entity ist.

// This class extends from Tile
// It describes the FoodSources
public class FoodSource extends Tile {

    /**
     * Initializes a FoodSource at the given position.
     *
     * @param position Position of FoodSource
     */
    public FoodSource(Vector position) {
        super(position);
    }

    /**
     * There is no need to update a FoodSource
     *
     * @return always true
     */
    @Override
    public void update() {
        return;
    }
}
