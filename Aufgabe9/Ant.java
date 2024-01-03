import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class Ant implements Runnable {
    private static Ant kingAnt;
    private static int antIdCounter;
    private final int id;

    private Position position;
    private Leaf leaf;

    public Ant(Position position) throws RuntimeException {
        if (kingAnt == null) {
            kingAnt = this;
        }
        id = antIdCounter++;
        this.position = position;
        // as this gets called only from one thread and arena will check against each position, this will always aquire the mutexes
        Arena.getMutex(position).forEach(m -> m.tryAcquire());
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
            Position chosenNewPos = lockedPositions.isEmpty() ? null : lockedPositions.get((int) (Math.random() * lockedPositions.size()));

            // irgendwas passt beim auswahl kriterium von den possible neighbors nicht.. die sind hin und wieder komplett leer und deswegen steht alles
            // man sollt vielleicht wenn sie am rand stehen sie irgendwie umdrehen oder so. also am besten kopf mit körper vertauschen??
            if(chosenNewPos == null) {
                if(availablePositions.isEmpty()) {
                    this.position = new Position(this.position.getPos2(), this.position.getPos1());
                }
                System.out.println();
            }

            if (chosenNewPos != null) {
                // release every other position for other ants
                // but first remove used position from the map
                acquiredVectors.remove(chosenNewPos.getPos1());
                acquiredVectors.remove(chosenNewPos.getPos2());
                acquiredVectors.forEach((key, value) -> value.release());

                List<Tile> newTiles = Arena.getTiles(chosenNewPos);

                if (leaf != null && newTiles.stream().anyMatch(tile -> tile instanceof Nest)) {
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
                if (leaf == null && newTiles.stream().anyMatch(Tile::isHasLeaf)) {
                    leaf = new Leaf((float)Math.random(), this);
                }

                // increase pheromone levels
                if (leaf != null) {
                    newTiles.forEach(Tile::increasePheromoneLevel);
                }

                // update arena
                Position oldPos = this.position;
                this.position = chosenNewPos;
                Arena.updateArena(this, oldPos);


                // also release the ants last positions semaphore
                Arena.getMutex(this.position).stream().filter(semaphore -> semaphore.availablePermits() == 0).forEach(Semaphore::release);
            }

            // kingAnt time
            if (this == kingAnt) {
                Arena.drawArena();
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

        MyVector headPos = this.position.getPos1();
        MyVector lookDirection = MyVector.substract(headPos, this.position.getPos2());
        MyVector inFrontHeadPos = MyVector.add(headPos, lookDirection);


        MyVector orthogonalCounterClockwise = MyVector.orthogonalVector(lookDirection, false);
        MyVector orthogonalClockwise = MyVector.orthogonalVector(lookDirection, true);


        MyVector upLeft = MyVector.add(inFrontHeadPos, orthogonalCounterClockwise);

        positions.add(new Position(upLeft,MyVector.add(headPos, orthogonalCounterClockwise)));

        MyVector hardLeft = MyVector.add(MyVector.add(inFrontHeadPos, orthogonalCounterClockwise), orthogonalCounterClockwise);
        positions.add(new Position(hardLeft,upLeft));

        positions.add(new Position(MyVector.add(inFrontHeadPos, lookDirection),inFrontHeadPos));

        MyVector upRight = MyVector.add(inFrontHeadPos, orthogonalClockwise);
        positions.add(new Position(upRight,MyVector.add(headPos, orthogonalClockwise)));

        MyVector hardRight = MyVector.add(MyVector.add(inFrontHeadPos, orthogonalClockwise), orthogonalCounterClockwise);
        positions.add(new Position(hardRight,upRight));

        if (this == kingAnt) System.out.println(positions.toString());
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
