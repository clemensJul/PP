import java.io.IOException;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class Ant implements Runnable {
    private static Ant kingAnt; // Represents the king ant instance within the simulation.
    private static int antIdCounter; // Tracks the count of created ants for assigning unique identifiers.
    private static final int maxWait = 64; // Defines the maximum count of iterations an ant can remain stationary before the simulation stops.
    private final int id; // Unique identifier for the ant.
    private Position position; // Represents the current position of the ant within the simulation arena.
    private Leaf leaf; // Represents a leaf being carried by the ant (if any).
    private int noMoveCounter; // Counts the number of consecutive iterations the ant remains stationary.
    private int moveCounter; // Counts the number of movements made by the ant.

    /**
     * Constructs an Ant object with a specified position.
     *
     * @param position The position where the Ant will be placed.
     * @throws RuntimeException If unable to initialize the Ant.
     */
    public Ant(Position position) throws RuntimeException {
        if (kingAnt == null) {
            kingAnt = this;
        }
        id = antIdCounter++;
        this.position = position;
        // as this gets called only from one thread and arena will check against each position, this will always acquire the mutexes
        Arena.getMutex(position).forEach(Semaphore::tryAcquire);

        // counts how often an ant moved and how often it was stuck
        noMoveCounter = 0;
        moveCounter = 0;

        Arena.updateArena(this, position);
    }

    /**
     * Runs the ant thread, controlling the movement and behavior of the ant within the simulation environment.
     * The ant continuously explores, moves, carries leaves, and interacts with the arena based on predefined rules.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // try acquiring mutex of all possible neighbors
            List<Position> availablePositions = getPositions();
            // locks only positions that are not already locked by other ants

            // we get some mutexes multiple times due to the fact that the ant needs two tiles on the grid simultaneously
            // so we filter out duplicate mutexes
            Map<MyVector, Semaphore> possibleVectors = new HashMap<>();
            availablePositions.forEach(position -> {
                List<Semaphore> semaphores = Arena.getMutex(position);
                possibleVectors.put(position.getPos1(), semaphores.getFirst());
                possibleVectors.put(position.getPos2(), semaphores.getLast());
            });

            Map<MyVector, Semaphore> acquiredVectors = possibleVectors.entrySet().stream().filter(entry ->
                    entry.getValue().tryAcquire()
            ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            List<Position> lockedPositions = availablePositions.stream().filter(position ->
                    acquiredVectors.containsKey(position.getPos1()) && acquiredVectors.containsKey(position.getPos2())
            ).toList();

            // chose a position of the locked position
            Position chosenNewPos = null;
            // if ant has a leaf try to go home
            if (leaf != null) {
                // nest is occupied
                if (Arena.getNestSemaphore().availablePermits() == 0) {
                    chosenNewPos = lockedPositions.stream().filter(Arena::positionHasNest).min(Comparator.comparingInt(Arena::distanceToNest)).orElse(null);
                }
                chosenNewPos = lockedPositions.stream().min(Comparator.comparingInt(Arena::distanceToNest)).orElse(null);
            }
            // pathfinding to a leaf
            else {
                chosenNewPos = lockedPositions.stream().min(Comparator.comparingInt(Arena::smellOfTiles)).orElse(null);
            }

            if (chosenNewPos != null) {
                // release every other position for other ants
                // but first remove used position from the map
                acquiredVectors.remove(chosenNewPos.getPos1());
                acquiredVectors.remove(chosenNewPos.getPos2());
                acquiredVectors.forEach((key, value) -> value.release());


                if (leaf != null && Arena.positionHasNest(chosenNewPos)) {
                    System.out.println("take back leaf");

                    // send to nest process
                    // reverse ant direction to get somewhere else

                    try {
                        Arena.getNestSemaphore().acquire();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // should be mutex to outputstream
                    if (Arena.getNestProcess().getOutputStream() != null) {
                        try {
                            Arena.sendLeaf(leaf);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // release mutex from writing
                    if (Arena.getNestSemaphore().availablePermits() == 0) {
                        Arena.getNestSemaphore().release();
                    }

                    leaf = null;
                }

                // if currently no leaf carrying but on tile with leaf -> take that leaf
                if (leaf == null && Arena.getTiles(chosenNewPos).stream().anyMatch(Tile::isHasLeaf)) {
                    leaf = new Leaf((float) Math.random(), this);
                }

                // increase pheromone levels
                if (leaf != null) {
                    Arena.getTiles(position).forEach(Tile::increasePheromoneLevel);
                }

                // update arena
                Position oldPos = this.position;
                this.position = chosenNewPos;
                Arena.updateArena(this, oldPos);

                //update move counter
                moveCounter++;

                // also release the ants last positions semaphore
                Arena.getMutex(this.position).stream().filter(semaphore -> semaphore.availablePermits() == 0).forEach(Semaphore::release);
            } else {
                noMoveCounter++;
            }

            // kingAnt time
            if (this == kingAnt) {
                Arena.drawArena();
                System.out.println("times not moved: " + noMoveCounter);
                System.out.println("times moved: " + moveCounter);
            }

            //stop simulation if any ant hits 250 no moves
            if (noMoveCounter >= maxWait) {
                Arena.stopAnts();
            }

            try {
                Thread.sleep(5 + (long) (Math.random() * 45));
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Generates a list of possible positions for the ant to explore based on its current position.
     *
     * @return A list of positions surrounding the ant's current location, considering directional vectors.
     */
    private List<Position> getPositions() {
        LinkedList<Position> positions = new LinkedList<>();

        MyVector headPosition = this.position.getPos1();
        MyVector upDirection = MyVector.substract(headPosition, this.position.getPos2());
        MyVector up = MyVector.add(headPosition, upDirection);


        MyVector leftDirection = MyVector.orthogonalVector(upDirection, false);
        MyVector rightDirection = MyVector.orthogonalVector(upDirection, true);


        //90
        MyVector left = MyVector.add(headPosition, leftDirection);
        MyVector hardLeft = MyVector.add(left, leftDirection);
        positions.add(new Position(hardLeft, left));
        //45
        MyVector upLeft = MyVector.add(up, leftDirection);
        positions.add(new Position(upLeft, MyVector.add(headPosition, leftDirection)));

        //0
        positions.add(new Position(MyVector.add(up, upDirection), up));

        //-45
        MyVector right = MyVector.add(headPosition, rightDirection);
        MyVector upRight = MyVector.add(up, rightDirection);
        positions.add(new Position(upRight, right));

        //-90
        MyVector hardRight = MyVector.add(right, rightDirection);
        positions.add(new Position(hardRight, right));

        return positions.stream().filter(Arena::validPosition).toList();
    }

    /**
     * Retrieves the current position of the ant.
     *
     * @return The position of the ant.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Retrieves the king ant in the simulation (if available).
     *
     * @return The king ant instance.
     */
    public static Ant getKingAnt() {
        return kingAnt;
    }

    /**
     * Retrieves the ID of the ant.
     *
     * @return The ID of the ant.
     */
    public int getId() {
        return id;
    }

    /**
     * Draws the representation of the ant's body.
     *
     * @return '+' for a regular ant body and 'Q' for the body of the leader/king ant.
     */
    public char drawAntBody() {
        // Implementation details for testing purposes
        if (this == kingAnt) {
            return 'Q';
        }
        return '+';
    }

    /**
     * Draws the representation of the ant's head based on its direction.
     *
     * @return A char representing the head of an ant ('A', '>', 'V', or '<') based on its direction.
     */
    public char drawAntHead() {
        return switch (position.getDirection()) {
            case UP -> 'A';
            case RIGHT -> '>';
            case DOWN -> 'V';
            default -> '<';
        };
    }

    /**
     * Retrieves the counter for the number of times the ant remained stationary.
     *
     * @return The counter for the number of times the ant didn't move.
     */
    public int getNoMoveCounter() {
        return noMoveCounter;
    }

    /**
     * Sets the counter for the number of times the ant remained stationary.
     *
     * @param noMoveCounter The value to set for the counter of no moves.
     */
    public void setNoMoveCounter(int noMoveCounter) {
        this.noMoveCounter = noMoveCounter;
    }

    /**
     * Retrieves the counter for the number of times the ant moved.
     *
     * @return The counter for the number of times the ant moved.
     */
    public int getMoveCounter() {
        return moveCounter;
    }

    /**
     * Sets the counter for the number of times the ant moved.
     *
     * @param moveCounter The value to set for the counter of moves.
     */
    public void setMoveCounter(int moveCounter) {
        this.moveCounter = moveCounter;
    }

}
