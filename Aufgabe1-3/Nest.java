import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
// Modularisierungseinheit: Klasse
// Eine Erweiterung der Tile-Klasse, die ein Untertyp von Entity ist.

//extends Tiles with custom methodes
public class Nest extends Tile {

    private final Color color;
    private final LinkedList<Tile> knownLocations;
    private final CopyOnWriteArrayList<Ant> ants = new CopyOnWriteArrayList<>();

    public List<Ant> getAnts() {
        return ants;
    }


    public void killAnt(Ant ant) {
        ants.remove(ant);

    }
    /**
     * Return nestColor.
     *
     * @return Color
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Initializes the Nest at the given position
     *
     * @param position Position where Nest is located.
     */
    public Nest(Vector position, Color nestColor, int antsAmount, Grid grid) {
        super(position);
        this.color = nestColor;
        this.knownLocations = new LinkedList<>();

        for (int i = 0; i < antsAmount; i++) {
            ants.add(new Ant(grid, this, 100, position));
        }
    }

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
     * Nest needs no updates because it does not change colors.
     *
     * @return false
     */
    @Override
    public boolean update() {
        ants.forEach(Ant::update);
        return false;
    }

    public void addLocation(Tile tile) {
        knownLocations.add(tile);
    }

    public Tile getRandomLocation() {
        int length = knownLocations.size();
        if (length == 0) return null;
        int index = (int) (Math.random() * length);
        // if (index == 0 ) return knownLocations.get(1).getPosition();
        return knownLocations.get(index);
    }

    public boolean containsLocation(Tile tile) {
        return knownLocations.contains(tile);
    }

    public boolean removeLocation(Tile tile) {
        return knownLocations.remove(tile);
    }
}
