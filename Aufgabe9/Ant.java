import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class Ant implements Runnable {

    private static Ant kingAnt;
    private static int antIdCounter;
    private final int id;

    private Position position;
    private Leaf leaf;

    private int noMoveCounter;
    private int moveCounter;

    public Ant(Position position) throws RuntimeException {
        if (kingAnt == null) {
            kingAnt = this;
        }
        id = antIdCounter++;
        this.position = position;
        // as this gets called only from one thread and arena will check against each position, this will always aquire the mutexes
        Arena.getMutex(position).forEach(m -> m.tryAcquire());

        // counts how often an ant moved and how often it was stuck
        noMoveCounter = 0;
        moveCounter = 0;
    }

    /**
     * Runs this operation.
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
            if (leaf != null){
                chosenNewPos = lockedPositions.stream().min(Comparator.comparingInt(Arena::distanceToNest)).get();
            }
            // pathfinding to a leaf
            else {
                chosenNewPos = lockedPositions.stream().min(Comparator.comparingInt(Arena::smellOfTiles)).get();
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
                        Arena.nestSemaphore.acquire();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // should be mutex to outputstream
                    if (Arena.objectOutputStream != null) {
                        try {
                            Arena.objectOutputStream.writeObject(leaf);
                            Arena.objectOutputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // release mutex from writing
                    if(Arena.nestSemaphore.availablePermits() == 0) {
                        Arena.nestSemaphore.release();
                    }

                    leaf = null;
                }

                // if currently no leaf carrying but on tile with leaf -> take that leaf
                if (leaf == null && Arena.getTiles(chosenNewPos).stream().anyMatch(Tile::isHasLeaf)) {
                    leaf = new Leaf((float)Math.random(), this);
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
            }else {
                noMoveCounter++;
            }

            // kingAnt time
            if (this == kingAnt) {
                Arena.drawArena();
                System.out.println("times not moved: "+ noMoveCounter);
                System.out.println("times moved"+ moveCounter);
            }

            try {
                Thread.sleep(5 + (long) (Math.random() * 45));
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * @return list of valid positions within cone in front of an ant. all returned positions are valid positions the grid
     */
    private List<Position> getPositions() {
        LinkedList<Position> positions = new LinkedList<>();

        MyVector headPosition = this.position.getPos1();
        MyVector upDirection = MyVector.substract(headPosition, this.position.getPos2());
        MyVector up = MyVector.add(headPosition, upDirection);


        MyVector leftDirection = MyVector.orthogonalVector(upDirection, false);
        MyVector rightDirection = MyVector.orthogonalVector(upDirection, true);


        //90
        MyVector left = MyVector.add(headPosition,leftDirection);
        MyVector hardLeft = MyVector.add(left,leftDirection);
        positions.add(new Position(hardLeft,left));
        //45
        MyVector upLeft = MyVector.add(up, leftDirection);
        positions.add(new Position(upLeft,MyVector.add(headPosition,leftDirection)));

        //0
        positions.add(new Position(MyVector.add(up, upDirection),up));

        //-45
        MyVector right = MyVector.add(headPosition,rightDirection);
        MyVector upRight = MyVector.add(up, rightDirection);
        positions.add(new Position(upRight,right));

        //-90
        MyVector hardRight = MyVector.add(right,rightDirection);
        positions.add(new Position(hardRight,right));

        return positions.stream().filter(Arena::validPosition).toList();
    }

    public Position getPosition() {
        return position;
    }

    public static Ant getKingAnt() {
        return kingAnt;
    }

    public int getId() {
        return id;
    }

    public char drawAntBody() {
        // for testing
        if (this == kingAnt) return 'Q';
        return '+';
    }

    public char drawAntHead() {
        return switch (position.getDirection()) {
            case UP -> 'A';
            case RIGHT -> '>';
            case DOWN -> 'V';
            default -> '<';
        };
    }
}
