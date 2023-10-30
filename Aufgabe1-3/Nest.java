import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
// Modularisierungseinheit: Klasse
// Eine Erweiterung der Tile-Klasse, die ein Untertyp von Entity ist.

//extends Tiles with custom methodes
public class Nest extends Tile {

    private Color color;
    LinkedList<Tile> knownLocations;

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
    public Nest(Vector position, Color nestColor, int antsAmount,Grid grid) {
        super(position);
        this.color = nestColor;
        this.knownLocations = new LinkedList<Tile>();
        knownLocations.add(this);
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

        return false;
    }

    public void updateKnownLocations(List<Tile> list){
        knownLocations.addAll(list);
    }
    public LinkedList<Tile> getKnownLocations(){
        return knownLocations;
    }

}
