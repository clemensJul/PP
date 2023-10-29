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
    private int currentLifetime;
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

        this.stinkBias = 10;
        this.directionBias = 10;
        this.targetBias = 10;
        this.lifetime = 30+(int)(Math.random()*300);
        this.currentLifetime = this.lifetime;
    }

    @Override
    public boolean update() {

        // new neighbours are found
        updateAvailableNeighbours();
        doThing();
        makeMove();


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
    private void makeMove() {

        int bestDirection = 2;
        int maxBias = 0;
        randomizeBias();
        Vector normalizedTarget = target != null ? target.normalizedVector(position) : null;
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

            if (currentTile.totalOtherSmell(this.nest)>0f) stinkDirectionBias = -1000;
            else if (currentTile instanceof Obstacle) stinkDirectionBias = -1000;


            int totalDirectionBias = stinkDirectionBias + directionBias + targetDirectionBias;
            // Update the best direction if this direction has a higher bias.
            if (totalDirectionBias > maxBias) {
                bestDirection = i;
                maxBias = totalDirectionBias;
            }
        }
        // Return the tile in the best direction.
        lookDirection[2] = lookDirection[bestDirection];
        position = availableNeighbours[bestDirection].getPosition();

    }
    private void doThing(){
        Tile current = grid.getTile(this.position);
        current.addStink(nest,state == State.COLLECT);
        if (current instanceof Nest){
            currentLifetime = lifetime;
            //nest.updateKnownLocations(knownLocations);
            //knownLocations.addAll(nest.getKnownLocations());
            if (state == State.COLLECT){
                state = State.SCAVENGE;
                setDirection(getDirection().invert());
            }  else state = State.EXPLORE;

        } else if (current instanceof FoodSource) {
            if (!knownLocations.contains(current)) knownLocations.add(current);
            state = State.COLLECT;
            setDirection(getDirection().invert());
        }
        this.currentLifetime = currentLifetime-1;
        if (currentLifetime < 0 && target == null){
            state = State.RETURN;
            target = knownLocations.get(0).getPosition();
            setDirection(getDirection().invert());
        };
    }
    private Vector getFoodSource(){
        int length = knownLocations.size();
        int index = 0;
        while (index<length){
            if (!(knownLocations.get(index) instanceof Nest)) return knownLocations.get(index).getPosition();
            index++;
        }
        return null;
    }

    public Nest getNest() {
        return nest;
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
}
