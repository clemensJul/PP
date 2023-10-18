import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
// Modularisierungseinheit: Klasse
// Daten werden von Ants gekapselt und nur notwendige Daten sind von außen sichtbar. (Data-Hiding)
// ist ein Untertyp von Entity. (Design By Contract)

// Ant holds the logic of the different states.
// It handles the process of finding the next tiles.
// It implements the Entity since it needs a position.
public class Ant implements Entity {

    //grid
    private final Grid grid;
    //inherited bias
    private final float[] bias;
    //strength of offsprings mutations
    private final float mutationStrength;

    //lifetime for each ant - maybe i
    private final int lifetime;

    // Current position of ant
    private Vector position;
    // Current looking direction of ant
    private Vector direction;
    // saves learned locations
    private ArrayList<Tile> knownLocations;
    // both modifiedBias and neighbours are fixed arrays for each ant to increase performance
    private float[] modifiedBias;
    private Tile[] availableNeighbours;
    private Vector target;
    // current state of ant
    private State state;
    private enum State {
        EXPLORE,
        SCAVENGE,
        COLLECT
    }




    // Counts the successive steps of bad fields
    private int badScentsCounter = 0;

    // Defines what counts as a badScent tile
    private final float badScent = 0.75f;

    public Ant(Grid grid,Nest nest, float[] bias, float mutationStrength, int lifetime, Vector position) {
        this.grid = grid;
        this.mutationStrength = mutationStrength;
        this.lifetime = lifetime;
        this.position = position;

        //calculate
        this.direction = Vector.RandomDirection();
        this.availableNeighbours = new Tile[5];
        this.bias = Arrays.copyOf(bias,bias.length);
        knownLocations = new ArrayList<>();
        knownLocations.add(nest);
    }

    @Override
    public void update() {
        return;
    }

    /**
     * Returns a Vector object that represents the current position of the ant.
     *
     * @return position of ant
     */
    @Override
    public Vector getPosition() {
        return position;
    }

    /**
     * Returns a Vector object that represents the current direction of the ant.
     *
     * @return direction of ant
     */
    public Vector getDirection() {
        return direction;
    }

    //method should incoperate biases from target(you can use the Vector direction method to calculate what direction should be biased), stink and direction
    private void updateBiases(){

    }
    private void updateAvailableNeighbours(){
        //generate vectors
        Vector left = Vector.orthogonalVector(this.direction, true);
        Vector leftFront = this.direction.sharpVector(left);

        Vector right = Vector.orthogonalVector(this.direction, false);
        Vector rightFront = this.direction.sharpVector(left);

        this.availableNeighbours[0] = this.grid.getTile(this.position.add(left));
        this.availableNeighbours[1] = this.grid.getTile(this.position.add(leftFront));
        this.availableNeighbours[2] = this.grid.getTile(this.position.add(this.direction));
        this.availableNeighbours[3] = this.grid.getTile(this.position.add(rightFront));
        this.availableNeighbours[4] = this.grid.getTile(this.position.add(right));
    }
}
