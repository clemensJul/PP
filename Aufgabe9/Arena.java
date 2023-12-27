import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Arena {
    private static Tile[][] grid;
    private static int height;
    private static int width;
    private static int numberOfAnts;
    private static List<Ant> ants;

    public static void main(String[] args) {
        height = Integer.parseInt(args[0]);
        width = Integer.parseInt(args[1]);
        numberOfAnts = Integer.parseInt(args[2]);
        grid = new Tile[width][height];

        Random random = new Random();
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                // spawn leafs with a 30% chance
                Position position = new Position(new MyVector(x, y), new MyVector(0, 0));
                grid[x][y] = new Tile(position, random.nextDouble() < 0.3);
            }
        }

        // overwrite existing tiles with nest in the middle
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                Position position = new Position(new MyVector(x, y), new MyVector(0, 0));
                grid[x + width / 2 - 1][x + height / 2 - 1] = new Nest(position);
            }
        }

        // create ants
        ants = spawnAntThreads();

        // start thread for each ant
        ants.forEach(ant -> (new Thread(ant)).start());
    }

    private static List<Ant> spawnAntThreads() {
        List<Ant> ants = new LinkedList<>();
        for (int i = 0; i < numberOfAnts; i++) {
            int x = (int) (Math.random() * grid[0].length);
            int y = (int) (Math.random() * grid.length);

            //create ant
            MyVector body = new MyVector(x, y);
            MyVector head = getRandomNeighbour(body);

            Ant ant = new Ant(new Position(body, head));

            // TODO: code for checking if there is an ant already and then if not, spawning one there
            if(checkSelectedPosition(ant.getPosition())) {
                ants.add(ant);
            } else {
                i--;
            }
        }

        return ants;
    }

    private static boolean checkSelectedPosition(Position position) {
        // check if there is any ant on this position
        return ants.stream().noneMatch(ant -> {
            MyVector firstVector = ant.getPosition().getFirstVector();
            MyVector secondVector = ant.getPosition().getSecondVector();

            if(firstVector == position.getFirstVector() || firstVector == position.getSecondVector()) {
                return false;
            }

            return secondVector != position.getFirstVector() && secondVector != position.getSecondVector();
        });
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
                return new MyVector(1, 0);
            }
            // down
            else if (rand > 0.25d && pos.getY() > 1) {
                return new MyVector(0, -1);
            }
            //left
            else if (rand > 0d && pos.getX() > 1) {
                return new MyVector(-1, 0);
            }
        }
        return newPos;
    }


    public static List<Tile> getTiles(Ant ant) {
        return getPositions(ant.getPosition()).stream().map(
                position -> grid[position.getFirstVector().getX()][position.getFirstVector().getY()]
        ).toList();
    }

    /**
     * @param position for which the tiles are checked, must be a valid position in the grid
     * @return returns the tiles of this position
     */
    public static List<Tile> getTiles(Position position) {
        List<Tile> tiles = new LinkedList<>();
        MyVector firstVec = position.getFirstVector();
        MyVector secondVec = position.getSecondVector();
        tiles.add(grid[firstVec.getX()][firstVec.getY()]);
        tiles.add(grid[secondVec.getX()][secondVec.getY()]);
        return tiles;
    }

    /**
     * @param position of ant for whom possible positions are checked
     * @return list of valid positions within cone
     */
    public static List<Position> getPositions(Position position) {
        LinkedList<Position> positions = new LinkedList<>();

        MyVector headPos = position.getSecondVector();
        MyVector lookDirection = position.getDirection();
        MyVector infrontHeadPos = MyVector.add(headPos, lookDirection);


        MyVector orthogonalCounterClockwise = MyVector.orthogonalVector(lookDirection, false);
        MyVector orthogonalClockwise = MyVector.orthogonalVector(lookDirection, true);


        MyVector upLeft = MyVector.add(infrontHeadPos, orthogonalCounterClockwise);
        positions.add(new Position(MyVector.add(headPos, orthogonalCounterClockwise), upLeft));
        positions.add(new Position(upLeft, MyVector.add(MyVector.add(infrontHeadPos, orthogonalCounterClockwise), orthogonalCounterClockwise)));

        positions.add(new Position(infrontHeadPos, MyVector.add(infrontHeadPos, lookDirection)));

        MyVector upRight = MyVector.add(infrontHeadPos, orthogonalClockwise);
        positions.add(new Position(MyVector.add(headPos, orthogonalClockwise), upRight));
        positions.add(new Position(upRight, MyVector.add(MyVector.add(infrontHeadPos, orthogonalClockwise), orthogonalCounterClockwise)));

        return positions.stream().filter(Arena::validPosition).toList();
    }

    private static boolean validPosition(Position position) {
        MyVector firstVec = position.getFirstVector();
        MyVector secondVec = position.getSecondVector();
        return (firstVec.getX() >= 0 && firstVec.getX() < width &&
                firstVec.getY() >= 0 && firstVec.getY() < height
                && secondVec.getX() >= 0 && secondVec.getX() < width &&
                secondVec.getY() >= 0 && secondVec.getY() < height);
    }
}
