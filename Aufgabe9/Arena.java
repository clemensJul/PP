import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Arena {
    private static Tile[][] grid;
    private static Character[][] draw;
    private static int height;
    private static int width;
    private static int numberOfAnts;
    private static List<Ant> ants;

    static Process nestProcess;
    static Semaphore nestSemaphore = new Semaphore(1);

    public static void main(String[] args) {
        // start Nest process
        try {
            nestProcess = Runtime.getRuntime().exec("java Nest");
        } catch (IOException e) {
            System.out.println();
        }


        height = Integer.parseInt(args[0]);
        width = Integer.parseInt(args[1]);
        numberOfAnts = Integer.parseInt(args[2]);
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
        spawnAntThreads();

        // start thread for each ant
        List<Thread> threads = new LinkedList<>();
        ants.forEach(ant -> threads.add(new Thread(ant)));
        threads.forEach(Thread::start);

        // Warte 10 Sekunden, bevor die Threads gestoppt werden
        try {
            Thread.sleep(5000); // 10 Sekunden in Millisekunden umgerechnet
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stoppe die Threads nach 10 Sekunden
        threads.forEach(Thread::interrupt);
        nestProcess.destroy();
    }

    private static void spawnAntThreads() {
        ants = new LinkedList<>();
        for (int i = 0; i < numberOfAnts; i++) {
            int x = 2 + (int) (Math.random() * grid.length - 4);
            int y = 2 + (int) (Math.random() * grid[0].length - 4);
            if (x < 1) x = 1;
            if (y < 1) y = 1;

            System.out.println(x + " " + y);
            //create ant
            MyVector body = new MyVector(x, y);
            MyVector head = getRandomNeighbour(body);

            Ant ant = new Ant(new Position(head, body));

            // TODO: code for checking if there is an ant already and then if not, spawning one there
            if (checkSelectedPosition(ant.getPosition())) {
                ants.add(ant);
            } else {
                i--;
            }
        }
    }

    private static boolean checkSelectedPosition(Position position) {
        // check if there is any ant on this position
        return ants.stream().noneMatch(ant -> position.equals(ant.getPosition()));
    }

    private static Tile getTile(MyVector pos) {
        if (pos.getX() >= 0 && pos.getX() < width &&
                pos.getY() >= 0 && pos.getY() < height) {
            return grid[pos.getX()][pos.getY()];
        }
        return null;
    }

    private static MyVector getRandomNeighbour(MyVector pos) {
        double rand = Math.random();
        MyVector newPos = null;

        while (newPos == null) {
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

    public static void updateArena(Ant ant, Position newPos) {
        Position oldPos = ant.getPosition();
        MyVector v1 = oldPos.getPos1();
        MyVector v2 = oldPos.getPos2();

        MyVector v3 = newPos.getPos1();
        MyVector v4 = newPos.getPos2();

        //old pos
        draw[v1.getX()][v1.getY()] = getTile(v1).draw();
        draw[v2.getX()][v2.getY()] = getTile(v2).draw();

        // new pos
        draw[v3.getX()][v3.getY()] = ant.drawAntHead();
        draw[v4.getX()][v4.getY()] = Ant.drawAntBody();
    }

    public static void drawArena() {
        StringBuilder result = new StringBuilder();
        result.append("\n".repeat(2));
        result.append("-".repeat(Math.max(0, height)));
        result.append("\n");
        for (Character[] row : draw) {
            for (Character cell : row) {
                result.append(cell);
            }
            result.append("\n");
        }
        result.append("-".repeat(Math.max(0, height)));
        result.append("\n".repeat(2));
        System.out.println(result);
    }
}
