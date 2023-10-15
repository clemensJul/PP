// An entity which holds

// TODO: welche Moduleinheit??
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
    boolean update();
}
