import java.awt.*;
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

    //lifetime for each ant - maybe i
    private int lifetime;

    private final int stinkBias;

    private final int directionBias;

    private final int targetBias;

    // Current position of ant
    private Vector position;
    // both modifiedBias and neighbours are fixed arrays for each ant to increase performance
    private final int[] modifiedBias;
    //direction vectors
    private final Vector[] lookDirection;
    private final Tile[] availableNeighbours;
    private int currentLifetime;

    private int totalLifetime;
    private Tile target;
    // current state of ant
    private State state;

    private enum State {
        EXPLORE,
        SCAVENGE,
        COLLECT,

        RETURN
    }

    private final Nest nest;

    public Ant(Grid grid, Nest nest, int lifetime, Vector position) {
        this.grid = grid;
        this.nest = nest;
        this.lifetime = lifetime;
        this.position = position;
        this.state = State.EXPLORE;

        //calculate
        Vector direction = Vector.RandomDirection();
        this.lookDirection = new Vector[5];
        this.availableNeighbours = new Tile[5];
        this.modifiedBias = new int[5];
        //this.knownLocations.add(nest);

        Vector left = Vector.orthogonalVector(direction, true);
        Vector leftFront = Vector.sharpVector(direction, left);
        Vector right = Vector.orthogonalVector(direction, false);
        Vector rightFront = Vector.sharpVector(direction, left);
        this.lookDirection[0] = left;
        this.lookDirection[1] = leftFront;
        this.lookDirection[2] = direction;
        this.lookDirection[3] = right;
        this.lookDirection[4] = rightFront;

        this.stinkBias = 10;
        this.directionBias = 15;
        this.targetBias = 5;
        this.lifetime = 100 + (int) (Math.random() * 50);
        this.currentLifetime = this.lifetime;
        this.totalLifetime = currentLifetime*(int)(Math.random()*5+5);
    }

    @Override
    public boolean update() {
        // new neighbours are found
        updateAvailableNeighbours();
        //act on tile
        doThing();
        //find new tile
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

    //determines the next chosen tile
    private void makeMove() {
        boolean allObstacles = Arrays.stream(availableNeighbours).toList().stream().allMatch(neighbor -> neighbor instanceof Obstacle);
        if (allObstacles) {
            setDirection(getDirection().invert());
            return;
        }

        int bestDirection = 2;
        int maxBias = 0;
        randomizeBias();
        Vector normalizedTarget = target != null ? target.getPosition().normalizedVector(position) : null;
        //System.out.print("pos "+position+" dir "+getDirection()+" norm tar "+normalizedTarget+" [");
        for (int i = 0; i < availableNeighbours.length; i++) {

            int directionBias = i == 2 ? this.directionBias * 2 / 3 : modifiedBias[i]; // Penalties for changing direction.

            int targetDirectionBias = 0;
            if (target != null) {
                if (target instanceof Nest) targetDirectionBias = Vector.dotProduct(normalizedTarget, lookDirection[i]) * targetBias*3;
                else targetDirectionBias = Vector.dotProduct(normalizedTarget, lookDirection[i]) * targetBias;
            }

            int stinkDirectionBias = 0;
            Tile currentTile = availableNeighbours[i];
            switch (state) {
                case EXPLORE -> {
                    stinkDirectionBias -= (int) (currentTile.getCurrentStink(this.nest) * stinkBias);
                    if (currentTile instanceof FoodSource) stinkDirectionBias = 1000;
                }
                case SCAVENGE -> {
                    stinkDirectionBias += (int) (currentTile.getCurrentStink(this.nest) * stinkBias);
                    if (currentTile instanceof FoodSource) stinkDirectionBias = 1000;

                }
                case COLLECT, RETURN -> {
                    stinkDirectionBias += (int) (currentTile.getCurrentStink(this.nest) * stinkBias);
                    if (currentTile instanceof Nest) stinkDirectionBias = 1000;

                }
            }

            stinkDirectionBias -= (int) (currentTile.totalOtherSmell(this.nest) * stinkBias);



            int totalDirectionBias = stinkDirectionBias + directionBias + targetDirectionBias;
            if (currentTile instanceof Obstacle) totalDirectionBias = -1000;
            //System.out.print("["+stinkDirectionBias+","+directionBias+","+targetDirectionBias+"]");
            // Update the best direction if this direction has a higher bias.
            if (totalDirectionBias > maxBias) {
                bestDirection = i;
                maxBias = totalDirectionBias;
            }
        }
        //System.out.println("]");
        // Return the tile in the best direction.
        lookDirection[2] = lookDirection[bestDirection];
        position = availableNeighbours[bestDirection].getPosition();
    }

    //acts on the current tile and changes states of the ants
    private void doThing() {
        Tile current = grid.getTile(this.position);
        current.addStink(nest);

        if (current instanceof Nest) {
            // dublicates ant if it has food and is at the nest
            if (state == State.COLLECT) {
                nest.addAnt(this);
                nest.addFood();
                //System.out.println(nest +" ant created");
            }

            currentLifetime = lifetime;
            //nest.updateKnownLocations(knownLocations);
            //knownLocations.addAll(nest.getKnownLocations());
            target = nest.getRandomLocation();
            if (target == null) {
                state = State.EXPLORE;
            } else {
                state = State.SCAVENGE;
                setDirection(getDirection().invert());
            }

        } else if (current instanceof FoodSource) {
            if (!nest.containsLocation(current)) nest.addLocation(current);
            if (!((FoodSource) current).decreaseFoodAmount()) {
                grid.removeTile(current);
                nest.removeLocation(current);
            }
            target = getNest();
            state = State.COLLECT;
            setDirection(getDirection().invert());
        }else if (current.totalOtherSmell(this.nest) > 0.9f) {
            this.nest.killAnt(this);
            //System.out.println(nest + " ant killed");
        }
        this.currentLifetime--;
        this.totalLifetime--;
        if (totalLifetime < 0) {
            nest.killAnt(this);
        }
        if (currentLifetime == 0) {
            /*lifeCycle --;
            if (lifeCycle <0){
                System.out.println("one ant of nest died "+nest.toString());
                grid.getAnts().remove(this);
            }*/
            state = State.RETURN;
            target = getNest();
            setDirection(getDirection().invert());
        }
    }

    public Nest getNest() {
        return nest;
    }

    //randomize biases - make move tries to keep it's path
    private void randomizeBias() {
        for (int i = 0; i < modifiedBias.length; i++) {
            modifiedBias[i] = (int) (Math.random() * directionBias);
        }
    }

    // update neighbours gathered
    private void updateAvailableNeighbours() {
        //generate left hand side vectors
        lookDirection[0] = Vector.orthogonalVector(lookDirection[2], true);
        lookDirection[1] = Vector.sharpVector(lookDirection[2], lookDirection[0]);

        //generate right hand side vectors
        lookDirection[4] = Vector.orthogonalVector(lookDirection[2], false);
        lookDirection[3] = Vector.sharpVector(lookDirection[2], lookDirection[4]);

        this.availableNeighbours[0] = this.grid.getTile(this.position.add(lookDirection[0]));
        this.availableNeighbours[1] = this.grid.getTile(this.position.add(lookDirection[1]));
        this.availableNeighbours[2] = this.grid.getTile(this.position.add(lookDirection[2]));
        this.availableNeighbours[3] = this.grid.getTile(this.position.add(lookDirection[3]));
        this.availableNeighbours[4] = this.grid.getTile(this.position.add(lookDirection[4]));
    }
    public Ant copy(){
        return new Ant(this.grid,this.nest,this.lifetime,this.position);
    }
}
