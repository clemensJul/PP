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
    private int totalfarmedFood;
    private int totalAntsCreated;

    /**
     *
     * @returns list of ants
     */
    public List<Ant> getAnts() {
        return ants;
    }

    /**
     *
     * @param ant is killed - needs to be non null and from the nest
     */
    public void killAnt(Ant ant) {
        ants.remove(ant);
        if (ants.size() == 0) System.out.println("nest deleted");

    }

    /**
     *
     * @param ant is added to nest
     */
    public void addAnt(Ant ant){
        ants.add(ant.copy());
        totalAntsCreated++;
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
    public void addFood(){
        totalfarmedFood++;
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
        this.totalAntsCreated = antsAmount;
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
     *
     * @return every food that is brought back to the nest
     */
    public int getTotalfarmedFood() {
        return totalfarmedFood;
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

    /**
     *
     * @return total amount of ants that were in the nest
     */
    public int getTotalAntsCreated() {
        return totalAntsCreated;
    }

    /**
     *adds a tile to the know locations tile != null and tile is a element of grid
     *
     *
     */
    public void addLocation(Tile tile) {
        knownLocations.add(tile);
    }
    /**
     *
     *
     * @return random Tile from knownlocations. if no location is found return null
     */

    public Tile getRandomLocation() {
        int length = knownLocations.size();
        if (length == 0) return null;
        int index = (int) (Math.random() * length);
        // if (index == 0 ) return knownLocations.get(1).getPosition();
        return knownLocations.get(index);
    }
    /**
     *
     *
     * @return boolean if tile is known or not already
     */

    public boolean containsLocation(Tile tile) {
        return knownLocations.contains(tile);
    }

    /**
     * @param tile should not be null
     * removes tile from the list of known locations
     * @return returns boolean of if tile is removed or not
     */
    public boolean removeLocation(Tile tile) {
        return knownLocations.remove(tile);
    }

    @Override
    public String toString() {
        return "Nest{" +
                "color=" + color +
                '}';
    }
}
