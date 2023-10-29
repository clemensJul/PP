import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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
    private final int[] bias;

    //strength of offsprings mutations
    private final float mutationStrength;

    //lifetime for each ant - maybe i
    private int lifetime;

    private final int stinkBias;

    private final int directionBias;

    private final int targetBias;

    // Current position of ant
    private Vector position;
    // saves learned locations
    private ArrayList<Tile> knownLocations;
    // both modifiedBias and neighbours are fixed arrays for each ant to increase performance
    private int[] modifiedBias;
    //direction vectors
    private Vector[] lookDirection;
    private Tile[] availableNeighbours;
    private Vector target;
    // current state of ant
    private State state;
    private enum State {
        EXPLORE,
        SCAVENGE,
        COLLECT,

        RETURN
    }




    // Counts the successive steps of bad fields
    private int badScentsCounter = 0;

    // Defines what counts as a badScent tile
    private final float badScent = 0.75f;
    private final Nest nest;

    public Ant(Grid grid, Nest nest, int[] bias, float mutationStrength, int lifetime, Vector position) {
        this.grid = grid;
        this.nest = nest;
        this.mutationStrength = mutationStrength;
        this.lifetime = lifetime;
        this.position = position;
        this.state = State.EXPLORE;

        //calculate
        Vector direction = Vector.RandomDirection();
        this.lookDirection = new Vector[bias.length];
        this.availableNeighbours = new Tile[bias.length];
        this.modifiedBias = new int[bias.length];
        this.bias = Arrays.copyOf(bias,bias.length);
        this.knownLocations = new ArrayList<>();
        this.knownLocations.add(nest);

        Vector left = Vector.orthogonalVector(direction, true);
        Vector leftFront = direction.sharpVector(direction,left);
        Vector right = Vector.orthogonalVector(direction, false);
        Vector rightFront = direction.sharpVector(direction,left);
        this.lookDirection[0] = left;
        this.lookDirection[1] = leftFront;
        this.lookDirection[2] = direction;
        this.lookDirection[3] = right;
        this.lookDirection[4] = rightFront;

        stinkBias = 1;
        directionBias = 10;
        targetBias = 1;
        lifetime = 100;

    }

    @Override
    public boolean update() {

        // new neighbours are found
        updateAvailableNeighbours();
        grid.getTile(this.position).addStink(nest);

        Tile next = makeMove();
        this.setDirection(this.position.calculateDirection(next.getPosition()));
        this.position = next.getPosition();

        lifetime--;
        if (lifetime < 0 && target == null) switchState(State.RETURN);
        return true;
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

    @Override
    public Color getColor() {
        return nest.getColor().darker();
    }

    /**
     * Returns a Vector object that represents the current direction of the ant.
     *
     * @return direction of ant
     */
    public Vector getDirection() {
        return lookDirection[2];
    }
    public void setDirection(Vector direction) {
         lookDirection[2] = direction;
    }

    //method should incoperate biases from target(you can use the Vector direction method to calculate what direction should be biased), stink and direction
    private Tile makeMove() {

        int bestDirection = 2;
        int maxBias = 0;
        randomizeBias();
        Vector normalizedTarget = target != null ? target.normalizedVector() : null;


        for (int i = 0; i < availableNeighbours.length; i++) {

            int directionBias = i == 2 ? targetBias*2/3 : modifiedBias[i]; // Penalties for changing direction.

            int targetDirectionBias = 0;
            if (target != null) {
                targetDirectionBias = Vector.dotProduct(normalizedTarget, lookDirection[i]) * targetBias;
            }

            int stinkDirectionBias = 0;
            Tile currentTile = availableNeighbours[i];
            switch (state){
                case EXPLORE -> {
                    stinkDirectionBias -= currentTile.getCurrentStink(this.nest)*stinkBias;
                    if (currentTile instanceof FoodSource) stinkDirectionBias = 1000;
                }
                case SCAVENGE -> {
                    stinkDirectionBias += currentTile.getCurrentStink(this.nest)*stinkBias;
                    if (currentTile instanceof FoodSource) stinkDirectionBias = 1000;

                }
                case COLLECT -> {
                    stinkDirectionBias += currentTile.getCurrentStink(this.nest)*stinkBias;
                    if (currentTile instanceof Nest) stinkDirectionBias = 1000;

                }
                case RETURN -> {
                    stinkDirectionBias += currentTile.getCurrentStink(this.nest)*stinkBias;
                    if (currentTile instanceof FoodSource) stinkDirectionBias = 1000;
                }
            }
            stinkDirectionBias -= currentTile.totalOtherSmell(this.nest)*stinkBias*100;

            int totalDirectionBias = stinkDirectionBias + directionBias + targetDirectionBias;

            // Update the best direction if this direction has a higher bias.
            if (totalDirectionBias > maxBias) {
                bestDirection = i;
                maxBias = totalDirectionBias;
            }
        }

        // Return the tile in the best direction.
        lookDirection[2] = lookDirection[bestDirection];
        Tile output = availableNeighbours[bestDirection];
        updateAvailableNeighbours();
        return output;
    }
    private void randomizeBias(){
        for (int i = 0; i < modifiedBias.length; i++) {
            modifiedBias[i] = (int)(Math.random()*directionBias);
        }
    }
    private void updateAvailableNeighbours(){
        //generate left hand side vectors
        lookDirection[0] = Vector.orthogonalVector(lookDirection[2], true);
        lookDirection[1] = lookDirection[2].sharpVector(lookDirection[2],lookDirection[0]);

        //generate right hand side vectors
        lookDirection[4] = Vector.orthogonalVector(lookDirection[2], false);
        lookDirection[3] = lookDirection[2].sharpVector(lookDirection[2],lookDirection[4]);

        this.availableNeighbours[0] = this.grid.getTile(this.position.add(lookDirection[0]));
        this.availableNeighbours[1] = this.grid.getTile(this.position.add(lookDirection[1]));
        this.availableNeighbours[2] = this.grid.getTile(this.position.add(lookDirection[2]));
        this.availableNeighbours[3] = this.grid.getTile(this.position.add(lookDirection[3]));
        this.availableNeighbours[4] = this.grid.getTile(this.position.add(lookDirection[4]));
    }
    private void switchState(State newState){
        switch (state){
            case EXPLORE -> {

            }case SCAVENGE -> {

            }
            case COLLECT -> {

            }case RETURN -> {

            }
        }
        state = newState;

    }

    public Nest getNest() {
        return nest;
    }
}
