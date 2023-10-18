// Modularisierungseinheit: Modul
// Design by Contract

// An entity is an object that lives on a Grid and needs a position.
public interface Entity {

    /**
     * Returns a Vector object that represents the current position of the Entity.
     *
     * @return position of ant
     */
    Vector getPosition();

    /**
     * Handles the update process of the Entity.
     *
     * @return If there is a need of a visual update.
     */
    void update();
}
