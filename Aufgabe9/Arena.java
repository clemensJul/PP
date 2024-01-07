import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Arena {
    private static Tile[][] grid;
    private static Character[][] draw;
    private static int height;
    private static int width;
    private static int numberOfAnts;
    private static List<Ant> ants;
    private static List<Thread> threads;
    private static boolean finishedFlag = false;
    private static Process nestProcess;
    private static ObjectOutputStream objectOutputStream;
    private static final Semaphore nestSemaphore = new Semaphore(1);

    private static Thread mainThread;

    public static void main(String[] args) {
        mainThread = Thread.currentThread();
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        // Schedule a task to kill the current process after 10 seconds
//        executor.schedule(() -> {
//            System.out.println("Process will be killed after 10 seconds");
//            System.exit(0); // Terminate the current process
//        }, 30, TimeUnit.SECONDS);
//
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
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
        if(numberOfAnts > (height * width) / 8) {
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

        // Warte 10 Sekunden, bevor die Threads gestoppt werden
        try {
            Thread.sleep(10000); // 10 Sekunden in Millisekunden umgerechnet
        } catch (InterruptedException ignored) {}

        stopAnts();
    }

    private static void spawnAnts() {
        ants = new LinkedList<>();
        int tries = 0;

        for (int i = 0; i < numberOfAnts; i++) {
            int x = 2 + (int) (Math.random() * grid.length - 4);
            int y = 2 + (int) (Math.random() * grid[0].length - 4);
            if (x < 1) x = 1;
            if (y < 1) y = 1;

            //create ant
            MyVector body = new MyVector(x, y);
            MyVector head = getRandomNeighbour(body);

            System.out.println("spawn ant" + i);
            Position pos = new Position(head, body);
            // TODO: code for checking if there is an ant already and then if not, spawning one there
            if (checkSelectedPosition(pos)) {
                Ant ant = new Ant(pos);
                ants.add(ant);
                tries = 0;
            } else {
                i--;
                tries++;
                System.out.println(tries + " tries tried");
            }
        }
    }

    private static boolean checkSelectedPosition(Position position) {
        // check if there is any ant on this position
        return getTiles(position).stream().noneMatch(t ->
             t.getMutex().availablePermits() == 0
        );
    }

    private static Tile getTile(MyVector pos) {
        if (pos.getX() >= 0 && pos.getX() < width &&
                pos.getY() >= 0 && pos.getY() < height) {
            return grid[pos.getX()][pos.getY()];
        }
        return null;
    }

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

    public static List<Semaphore> getMutex(Position position) {
        List<Semaphore> mutex = new LinkedList<>();
        MyVector firstVec = position.getPos1();
        MyVector secondVec = position.getPos2();
        mutex.add(grid[firstVec.getX()][firstVec.getY()].getMutex());
        mutex.add(grid[secondVec.getX()][secondVec.getY()].getMutex());
        return mutex;
    }


    public static boolean validPosition(Position position) {
        MyVector firstVec = position.getPos1();
        MyVector secondVec = position.getPos2();
        return (firstVec.getX() >= 0 && firstVec.getX() < width &&
                firstVec.getY() >= 0 && firstVec.getY() < height
                && secondVec.getX() >= 0 && secondVec.getX() < width &&
                secondVec.getY() >= 0 && secondVec.getY() < height);
    }


    //does not need a lock because two ants can never be on the same position
    public static void updateArena(Ant ant, Position oldPos) {
        Position newPos = ant.getPosition();
        MyVector ohead = oldPos.getPos1();
        MyVector obody = oldPos.getPos2();

        MyVector nhead = newPos.getPos1();
        MyVector nbody = newPos.getPos2();

        //old pos
        draw[ohead.getX()][ohead.getY()] = getTile(ohead).draw();
        draw[obody.getX()][obody.getY()] = getTile(obody).draw();

        // new pos
        draw[nhead.getX()][nhead.getY()] = ant.drawAntHead();
        draw[nbody.getX()][nbody.getY()] = ant.drawAntBody();
    }

    public static void drawArena() {
        StringBuilder result = new StringBuilder();
        result.append("\n".repeat(2));
        result.append("-".repeat(Math.max(0, height)));
        result.append("\n");
        for (int y = 0; y < draw[0].length;y++) {
            for (int x = 0; x < draw.length; x++) {
                result.append(draw[x][y]);
            }
            result.append("\n");
        }
        result.append("-".repeat(Math.max(0, height)));
        result.append("\n".repeat(2));
        System.out.println(result);
    }

    public static Boolean positionHasNest(Position position){
     return getTile(position.getPos1()) instanceof Nest || getTile(position.getPos2()) instanceof Nest;
    }

    public static int distanceToNest (Position position){
        MyVector headPos = position.getPos1();
        return Math.abs(headPos.getX()-width/2)+Math.abs(headPos.getY()-height/2);
    }

    public static int smellOfTiles(Position position){
        return getTile(position.getPos1()).getPheromoneLevel() + getTile(position.getPos2()).getPheromoneLevel();
    }

    public static void sendLeaf(Leaf leaf) throws IOException {
        Arena.objectOutputStream.writeObject(leaf);
        Arena.objectOutputStream.flush();
    }

    public static synchronized void stopAnts(){
        if (finishedFlag) {
            return;
        }

        threads.forEach(Thread::interrupt);

        try {
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finishedFlag = true;
        mainThread.interrupt();
    }

    public static Semaphore getNestSemaphore() {
        return nestSemaphore;
    }

    public static Process getNestProcess() {
        return nestProcess;
    }
}
