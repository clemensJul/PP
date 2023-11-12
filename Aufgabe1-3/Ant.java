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

    // reference to grid on which the ant lives
    private final Grid grid;

    // reference to belonging nest
    private final Nest nest;

    // biases for different behaviours of the ant
    private final int stinkBias;
    private final int directionBias;
    private final int targetBias;

    // Current position of ant
    private Vector position;

    // specifies the target ant
    private Tile target;

    // both modifiedBias and neighbours are fixed arrays for each ant to increase performance
    private final int[] modifiedBias;

    //direction vectors
    private final Vector[] lookDirection;
    private final Tile[] availableNeighbours;

    // lifetime for each ant - maybe i
    private int lifetime;
    private int totalLifetime;
    private int currentLifetime; // specifies how long an ant goes on explore mode


    // current state of ant
    private State state;

    // BAD: objektorientierte Programmierung:
    // Dynamisches Binden für verbesserte Wartbarkeit: z.B. in der Methode update wird basierend vom aktuellen Zustand eine andere Aktion ausgeführt
    // Das könnte man durch dynamisches Binden verbessern.
    // Mögliche Verbesserung: Ein eigenes Interface erstellen und für jeden der Zustände eine Klasse, die das Interface implementiert.
    private enum State {
        EXPLORE,
        SCAVENGE,
        COLLECT,
        RETURN
    }

    /**
     * Handles the update process for an Ant.
     * Acts based on biases and on different Ant states.
     *
     * @param grid     Reference to grid. Grid != null
     * @param nest     Reference to nest. Nest != null
     * @param lifetime Lifetime of ant. Must be greather than 0.
     * @param position Position of ant. Position != null
     */
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
        this.totalLifetime = currentLifetime * (int) (Math.random() * 5 + 5);
    }

    /**
     * Handles the update process for an Ant.
     * Acts based on biases and on different Ant states.
     *
     * @return always true, because in each step an Ant makes definitely a move to another tile.
     */
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

    /**
     * Returns the Color of the Ant.
     * Uses a darkened nest color as a basis
     *
     * @return Color of Ant
     */
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

    /**
     * Sets the looking direction of the Ant.
     *
     * @param direction Must be a Vector with x and y coordinates in [-1;1]
     */
    public void setDirection(Vector direction) {
        lookDirection[2] = direction;
    }

    /**
     * Determines the next move of the ant on the grid.
     */
    private void makeMove() {
        // if all possible neighbors are obstacles -> turn around.
        boolean allObstacles = Arrays.stream(availableNeighbours).toList().stream().allMatch(neighbor -> neighbor instanceof Obstacle);
        if (allObstacles) {
            setDirection(getDirection().invert());
            return;
        }

        int bestDirection = 2;
        int maxBias = 0;
        randomizeBias();
        Vector normalizedTarget = target != null ? target.getPosition().normalizedVector(position) : null;
        for (int i = 0; i < availableNeighbours.length; i++) {

            int directionBias = i == 2 ? this.directionBias * 2 / 3 : modifiedBias[i]; // Penalties for changing direction.

            int targetDirectionBias = 0;
            if (target != null) {
                if (target instanceof Nest)
                    targetDirectionBias = Vector.dotProduct(normalizedTarget, lookDirection[i]) * targetBias * 3;
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

    /**
     * Acts on the current tile and change states of the ants.
     * If the ant is going to be on a Nest a has food with it, a new Ant gets born.
     * If the ant is going to be on an enemy Tile (with a scent higher than 0.9f), the Ant gets killed.
     * If the lifetime of the ant is <= 0, the Ant gets also killed.
     */
    // BAD: objektorientierte Programmierung:
    // Schwache Objektkopplung: es gibt verschiedene Verzweigungen, abhängig von der konkreten Tile-Type,
    // und die Ameise reagiert unterschiedlich darauf. DAs führt zu einer schwachen Objektkopplung, da die Ant-Klasse eine hohe Anzahl von
    // Abhängigkeiten von den konkreten Klassen der Tiles aufweist. Man könnte in den Tiles eine Methode implementieren, die festlegt, wie der Move der Ants aussieht.
    private void doThing() {
        Tile current = grid.getTile(this.position);
        current.addStink(nest);

        if (current instanceof Nest) {
            // duplicates ant if it has food and is at the nest
            if (state == State.COLLECT) {
                nest.addAnt(this);
                nest.addFood();
            }

            currentLifetime = lifetime;
            target = nest.getRandomLocation();
            if (target == null) {
                state = State.EXPLORE;
            } else {
                state = State.SCAVENGE;
                setDirection(getDirection().invert());
            }
        }

        if (current instanceof FoodSource) {
            if (!nest.containsLocation(current)) nest.addLocation(current);
            if (!((FoodSource) current).decreaseFoodAmount()) {
                grid.removeTile(current);
                nest.removeLocation(current);
            }
            target = getNest();
            state = State.COLLECT;
            setDirection(getDirection().invert());
        }

        // kill ant if it is on a Tile with a high scent of another nest
        if (current.totalOtherSmell(this.nest) > 0.9f) {
            this.nest.killAnt(this);
        }

        // decrease lifetimes
        this.currentLifetime--;
        this.totalLifetime--;
        if (totalLifetime < 0) {
            nest.killAnt(this);
        }

        if (currentLifetime == 0) {
            state = State.RETURN;
            target = getNest();
            setDirection(getDirection().invert());
        }
    }

    /**
     * @return Nest. Nest != null
     */
    public Nest getNest() {
        return nest;
    }

    /**
     * Randomize biases - make move tries to keep its path
     */
    private void randomizeBias() {
        Arrays.setAll(modifiedBias, i -> (int) (Math.random() * directionBias));
    }

    /**
     * Updates the availableNeighbours array
     */
    // BAD: Starke Objektkopplung zwischen Ant und Grid:
    // Es wird direkt das Grid verwendet um Informationen über die Nachbarn der Ant zu bekommen um die Entscheidung für den nächsten Zug zu treffen.
    // Das führt zu einer starken Kopplung zwischen Ant und Grid.
    //
    // Verbesserung: Um die Kopplung zu reduzieren, könnten man die Logik für die Nachbarn in die Grid-Klasse auslagern.
    // Diese Methode könnte auch Basis der Position und der lookingDirection der Ant alle möglichen Nachbarn zurückgeben.
    // Das würde den Klassenzusammenhalt verbessern und die Objektkopplung lockern.
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

    /**
     * @return a copy of the ant with the same parameters
     */
    public Ant copy() {
        return new Ant(this.grid, this.nest, this.lifetime, this.position);
    }
}
