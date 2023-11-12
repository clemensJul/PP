import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
// Modularisierungseinheit: Klasse
// Eine Erweiterung der Tile-Klasse, die ein Untertyp von Entity ist.

//extends Tiles with custom methodes
public class Nest extends Tile {
    // color of the nest
    private final Color color;

    // List of all relevant locations
    private final LinkedList<Tile> knownLocations;

    // List of Ants which belong to this nest
    private final CopyOnWriteArrayList<Ant> ants = new CopyOnWriteArrayList<>();

    // counter for retrieved food
    private int totalFarmedFood;

    // counter for ants
    private int totalAntsCreated;

    /**
     * Initializes the Nest.
     *
     * @param position   Position where Nest is located. Must be != null
     * @param nestColor  Color of the nest. Must be != null
     * @param antsAmount Initial amount of ants. Must be > 0
     * @param grid       Reference to Grid where the Nest lives. Must be != null
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
     * @return List of Ants != null
     */
    public List<Ant> getAnts() {
        return ants;
    }

    /**
     * Kills an Ant.
     * It gets removed from the Ants list.
     *
     * @param ant Ant, must be != null
     */
    public void killAnt(Ant ant) {
        ants.remove(ant);
        if (ants.isEmpty()) {
            System.out.println("nest deleted");
        }
    }

    /**
     * Adds an Ant to the Nest.
     *
     * @param ant Ant, must be != null
     */
    public void addAnt(Ant ant) {
        ants.add(ant.copy());
        totalAntsCreated++;
    }

    /**
     * Return nestColor.
     *
     * @return Color, is != null
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Increases the counter for farmed food by one
     */
    public void addFood() {
        totalFarmedFood++;
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
     * @return every food that is brought back to the nest
     */
    public int getTotalFarmedFood() {
        return totalFarmedFood;
    }

    /**
     * Updates all Ants in the Nest.
     *
     * @return true, if there are no Ants in the Nest.
     */
    @Override
    public boolean update() {
        ants.forEach(Ant::update);
        return ants.isEmpty();
    }

    /**
     * @return total amount of ants that were in the nest. Is >= 0.
     */
    public int getTotalAntsCreated() {
        return totalAntsCreated;
    }

    /**
     * Adds a tile to the know locations
     *
     * @param tile Tile to add to locations, must be!= null
     */
    public void addLocation(Tile tile) {
        knownLocations.add(tile);
    }

    /**
     * Returns a random Location from knownLocations.
     *
     * @return random Tile from knownLocations. If there are no knownLocation, null is returned.
     */
    public Tile getRandomLocation() {
        int length = knownLocations.size();
        if (length == 0) {
            return null;
        }
        int index = (int) (Math.random() * length);
        return knownLocations.get(index);
    }

    /**
     * Checks if a given Tile is in knownLocations.
     *
     * @param tile Tile to check, must be != null
     * @return boolean if tile is known or not already
     */
    public boolean containsLocation(Tile tile) {
        return knownLocations.contains(tile);
    }

    /**
     * Removes a given Tile from knownLocations.
     *
     * @param tile Tile to remove, must be != null
     * @return true if the remove was successful
     */
    public boolean removeLocation(Tile tile) {
        return knownLocations.remove(tile);
    }

    /**
     * Converts tile toString
     *
     * @return String repentation of Tile
     */
    @Override
    public String toString() {
        return "Nest{" +
                "color=" + color +
                '}';
    }
}
