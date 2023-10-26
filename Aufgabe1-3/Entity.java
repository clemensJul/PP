// Modularisierungseinheit: Modul
// Design by Contract

import java.awt.*;

// An entity is an object that lives on a Grid and needs a position.
public interface Entity {

    /**
     * Returns a Vector object that represents the current position of the Entity.
     *
     * @return position of ant
     */
    Vector getPosition();

    /**
     * Returns the Color of the Entity.
     *
     * @return Color of entity
     */
    Color getColor();

    /**
     * Handles the update process of the Entity.
     *
     * @return If there is a need of a visual update.
     */
    boolean update();
}
