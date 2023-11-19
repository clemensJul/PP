import java.awt.*;

public class Obstacle extends Tile {
    private static Color color = new Color(74, 44, 18);

    /**
     * Initializes the Nest at the given position
     *
     * @param position Position where Nest is located, must be != null.
     */
    public Obstacle(Vector position) {
        super(position);
    }

    // ERROR: die getCurrentStink-Methode wird eigentlich nur von Tile an sich gebraucht,
    // jedoch nicht von Obtacles, FoodSources oder Nesten. Wir haben die Methode eigentlich so gedacht,
    // dass ein Tile entfernt wird, wenn der stink von dem Tile < 0.05f ist,
    // damit die Map sich mit der Zeit wieder verkleinert, damit weniger Berechnungen pro Update-Vorgang
    // durchgeführt werden müssen. Deswegen wird bei diesen Klassen einfach ein fixer Wert > 0.05f retourniert, damit sie nicht aus der Map gelöscht werden.
    /**
     * Returns 100.
     * Nest is not taken into account.
     * Can be everything > 0.05f
     *
     * @param nest Corresponding nest
     * @return 100
     */
    @Override
    public float getCurrentStink(Nest nest) {
        return 100;
    }

    /**
     * Return obstacleColor.
     *
     * @return Color, is != null
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Obstacle needs no updates because it does not change colors.
     *
     * @return false
     */
    @Override
    public boolean update() {
        return false;
    }
}
