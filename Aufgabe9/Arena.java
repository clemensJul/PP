import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Arena {
    private static Tile[][] grid; // Represents the grid of Tiles in the arena.
    private static Character[][] draw; // Represents the visual drawing of the arena based on grid elements.
    private static int height; // Represents the height of the arena grid.
    private static int width; // Represents the width of the arena grid.
    private static int numberOfAnts; // Represents the total number of ants in the simulation.
    private static List<Ant> ants; // Represents the list of ants present in the simulation.
    private static List<Thread> threads; // Represents the list of threads corresponding to each ant.
    private static boolean finishedFlag = false; // Represents the flag indicating whether the simulation has finished.
    private static Process nestProcess; // Represents the process related to the nest in the simulation.
    private static ObjectOutputStream objectOutputStream; // Represents the ObjectOutputStream for sending objects to the nest process.
    private static final Semaphore nestSemaphore = new Semaphore(1); // Represents the Semaphore controlling access to the nest.
    private static Thread mainThread; // Represents the main thread of the simulation.

    /**
     * Main method initiating the simulation of ant behavior within an arena grid.
     * The method initializes the grid, spawns ants, starts their threads, and manages the simulation's flow.
     *
     * @param args Command-line arguments defining the height, width, and number of ants for the simulation.
     *             args[0]: Height of the grid.
     *             args[1]: Width of the grid.
     *             args[2]: Number of ants for the simulation.
     */
    public static void main(String[] args) {
        mainThread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // write ants to file
            ants.forEach(ant -> FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, ant.getPosition().toString() + " - Moves: " + ant.getMoveCounter() + " - NonMoves: " + ant.getNoMoveCounter()));

            // do cleanup things
            nestProcess.destroy();
        }));

        // start Nest process
        try {
            nestProcess = Runtime.getRuntime().exec("java -cp bin Nest");
            objectOutputStream = new ObjectOutputStream(nestProcess.getOutputStream());
        } catch (IOException e) {
            System.out.println();
        }

        height = Integer.parseInt(args[0]);
        width = Integer.parseInt(args[1]);
        numberOfAnts = Integer.parseInt(args[2]);

        // if there are to many ants for this grid, return
        // each ant needs at least two tiles, so for a normal simulation at most one quarter
        // should be occupied
        if (numberOfAnts > (height * width) / 8) {
            System.out.println("Too many ants for this size");
            return;
        }

        grid = new Tile[width][height];
        draw = new Character[width][height];

        Random random = new Random();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                // spawn leafs with an x% chance
                Position position = new Position(new MyVector(x, y), new MyVector(0, 0));
                grid[x][y] = new Tile(position, random.nextDouble() < 0.05);
                draw[x][y] = grid[x][y].draw();
            }
        }

        // overwrite existing tiles with nest in the middle
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                Position position = new Position(new MyVector(x, y), new MyVector(0, 0));
                int xn = x + width / 2 - 1;
                int yn = y + height / 2 - 1;
                grid[xn][yn] = new Nest(position);
                draw[xn][yn] = grid[xn][yn].draw();
            }
        }

        //create ants
        spawnAnts();

        // start thread for each ant
        threads = new LinkedList<>();
        ants.forEach(ant -> threads.add(new Thread(ant)));
        threads.forEach(Thread::start);

        // Warte 3 Sekunden, bevor die Threads gestoppt werden
        try {
            Thread.sleep(3000); // 3 Sekunden in Millisekunden umgerechnet
        } catch (InterruptedException ignored) {
        }

        stopAnts();
    }

    /**
     * Spawns a specified number of ants within the grid, ensuring their random initial placement.
     * Ants are added to the list of ants if their initial positions are valid.
     */
    private static void spawnAnts() {
        ants = new LinkedList<>();

        for (int i = 0; i < numberOfAnts; i++) {
            int x = 2 + (int) (Math.random() * grid.length - 4);
            int y = 2 + (int) (Math.random() * grid[0].length - 4);
            if (x < 1) {
                x = 1;
            }
            if (y < 1) {
                y = 1;
            }

            //create ant
            MyVector body = new MyVector(x, y);
            MyVector head = getRandomNeighbour(body);

            Position pos = new Position(head, body);
            if (checkSelectedPosition(pos)) {
                Ant ant = new Ant(pos);
                ants.add(ant);
            } else {
                i--;
            }
        }
    }

    /**
     * Checks if there are any ants occupying the specified position.
     *
     * @param position The position to be checked for ant presence.
     * @return {@code true} if no ants are present at the specified position, {@code false} otherwise.
     */
    private static boolean checkSelectedPosition(Position position) {
        return getTiles(position).stream().noneMatch(t ->
                t.getMutex().availablePermits() == 0
        );
    }

    /**
     * Retrieves the Tile at the specified position on the grid.
     *
     * @param pos The position (MyVector) to retrieve the Tile from.
     * @return The Tile object at the specified position if it falls within the grid bounds, otherwise returns null.
     */
    private static Tile getTile(MyVector pos) {
        if (pos.getX() >= 0 && pos.getX() < width &&
                pos.getY() >= 0 && pos.getY() < height) {
            return grid[pos.getX()][pos.getY()];
        }
        return null;
    }

    /**
     * Retrieves a random neighboring position relative to the given position.
     *
     * @param pos The position (MyVector) for which a random neighboring position is required.
     * @return A new position (MyVector) that represents a random neighbor of the given position,
     * or null if no valid neighboring position is found.
     */
    private static MyVector getRandomNeighbour(MyVector pos) {
        MyVector newPos = null;

        while (newPos == null) {
            double rand = Math.random();
            // up
            if (rand > 0.75d && pos.getY() < height - 2) {
                newPos = new MyVector(0, 1);
            }
            // right
            else if (rand > 0.5d && pos.getX() < width - 2) {
                newPos = new MyVector(1, 0);
            }
            // down
            else if (rand > 0.25d && pos.getY() > 1) {
                newPos = new MyVector(0, -1);
            }
            //left
            else if (rand > 0d && pos.getX() > 1) {
                newPos = new MyVector(-1, 0);
            }
        }

        return MyVector.add(pos, newPos);
    }

    /**
     * @param position for which the tiles are checked, must be a valid position in the grid
     * @return returns the tiles of this position
     */
    public static List<Tile> getTiles(Position position) {
        List<Tile> tiles = new LinkedList<>();
        MyVector firstVec = position.getPos1();
        MyVector secondVec = position.getPos2();
        tiles.add(grid[firstVec.getX()][firstVec.getY()]);
        tiles.add(grid[secondVec.getX()][secondVec.getY()]);
        return tiles;
    }

    /**
     * Retrieves the Semaphores associated with the positions in the provided Position object.
     *
     * @param position The Position object containing two positions (MyVector) for which Semaphores are required.
     * @return A List containing the Semaphores associated with the positions in the provided Position object.
     */
    public static List<Semaphore> getMutex(Position position) {
        List<Semaphore> mutex = new LinkedList<>();
        MyVector firstVec = position.getPos1();
        MyVector secondVec = position.getPos2();
        mutex.add(grid[firstVec.getX()][firstVec.getY()].getMutex());
        mutex.add(grid[secondVec.getX()][secondVec.getY()].getMutex());
        return mutex;
    }

    /**
     * Checks whether the positions within the provided Position object are within the bounds of the grid.
     *
     * @param position The Position object containing two positions (MyVector) to be validated.
     * @return {@code true} if both positions are within the grid bounds, {@code false} otherwise.
     */
    public static boolean validPosition(Position position) {
        MyVector firstVec = position.getPos1();
        MyVector secondVec = position.getPos2();
        return (firstVec.getX() >= 0 && firstVec.getX() < width &&
                firstVec.getY() >= 0 && firstVec.getY() < height
                && secondVec.getX() >= 0 && secondVec.getX() < width &&
                secondVec.getY() >= 0 && secondVec.getY() < height);
    }


    /**
     * Updates the visual representation of the arena grid to reflect the movement of an ant from its old position to a new position.
     * The method updates the drawing representation of the old and new positions with the corresponding ant symbols.
     *
     * @param ant    The Ant object representing the ant being moved.
     * @param oldPos The previous Position of the ant.
     */
    public static void updateArena(Ant ant, Position oldPos) {
        Position newPos = ant.getPosition();
        MyVector ohead = oldPos.getPos1();
        MyVector obody = oldPos.getPos2();

        MyVector nhead = newPos.getPos1();
        MyVector nbody = newPos.getPos2();

        // Update the drawing representation of the old position to the previous state
        draw[ohead.getX()][ohead.getY()] = getTile(ohead).draw();
        draw[obody.getX()][obody.getY()] = getTile(obody).draw();

        // Update the drawing representation of the new position with the ant's visual representation
        draw[nhead.getX()][nhead.getY()] = ant.drawAntHead();
        draw[nbody.getX()][nbody.getY()] = ant.drawAntBody();
    }

    public static void drawArena() {
        StringBuilder result = new StringBuilder();
        result.append("\n".repeat(2));
        result.append("-".repeat(Math.max(0, height)));
        result.append("\n");
        for (int y = 0; y < draw[0].length; y++) {
            for (Character[] characters : draw) {
                result.append(characters[y]);
            }
            result.append("\n");
        }
        result.append("-".repeat(Math.max(0, height)));
        result.append("\n".repeat(2));

        FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, result.toString());
    }

    /**
     * Checks if either of the positions in the provided Position object contains a Nest.
     *
     * @param position The Position object containing two positions (MyVector) to be checked for a Nest.
     * @return {@code true} if either position contains a Nest, {@code false} otherwise.
     */
    public static Boolean positionHasNest(Position position) {
        return getTile(position.getPos1()) instanceof Nest || getTile(position.getPos2()) instanceof Nest;
    }

    /**
     * Calculates the Manhattan distance between the head position of an ant and the center of the grid considered as the nest.
     *
     * @param position The Position object containing the head position of the ant.
     * @return The Manhattan distance between the ant's head position and the center of the grid (assumed to be the nest).
     */
    public static int distanceToNest(Position position) {
        MyVector headPos = position.getPos1();
        return Math.abs(headPos.getX() - width / 2) + Math.abs(headPos.getY() - height / 2);
    }

    /**
     * Retrieves the total pheromone level from the Tiles at the two positions provided in the Position object.
     *
     * @param position The Position object containing two positions (MyVector) for which the total pheromone level is required. Must be a valid position on the grid.
     * @return The sum of the pheromone levels from the Tiles at the provided positions.
     */
    public static int smellOfTiles(Position position) {
        return getTile(position.getPos1()).getPheromoneLevel() + getTile(position.getPos2()).getPheromoneLevel();
    }

    /**
     * Sends a Leaf object to the output stream of the nestProcess.
     *
     * @param leaf The Leaf object to be sent.
     * @throws IOException If an I/O error occurs while writing the Leaf object to the output stream.
     */
    public static void sendLeaf(Leaf leaf) throws IOException {
        Arena.objectOutputStream.writeObject(leaf);
        Arena.objectOutputStream.flush();
    }


    /**
     * Stops all running ant threads and closes the object output stream.
     * Ensures that threads are interrupted, the object output stream is closed, and flags are set to indicate completion.
     */
    public static synchronized void stopAnts() {
        if (finishedFlag) {
            return;
        }

        // Interrupt all running threads
        threads.forEach(Thread::interrupt);

        try {
            // Close the object output stream
            objectOutputStream.close();
        } catch (IOException ignored) {}

        // Set flags to indicate completion
        finishedFlag = true;
        mainThread.interrupt();
    }

    /**
     * Retrieves the Semaphore associated with the nest.
     *
     * @return The Semaphore object related to the nest.
     */
    public static Semaphore getNestSemaphore() {
        return nestSemaphore;
    }

    /**
     * Retrieves the Process associated with the nest.
     *
     * @return The Process object related to the nest.
     */
    public static Process getNestProcess() {
        return nestProcess;
    }
}
