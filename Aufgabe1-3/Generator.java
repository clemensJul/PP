import java.awt.*;

public class Generator {
    private final int nestCounter;
    private final int foodCounter;
    private final int antsPerNest;
    private final int obstacleCounter;

    private final Grid grid;


    // GOOD: Generator hat einen hohen Klassenzusammenhalt mit Grid aber eine schwache Objektkopplung.
    /**
     * Initializes the Generator.
     *
     * @param grid            Grid, Grid != null
     * @param nestCounter     Amount of nests which are getting generated. Must be >= 0
     * @param foodCounter     Amount of foodSources which are getting generated. Must be >= 0
     * @param antsPerNest     Amount of ants which are getting generated per nest. Must be >= 0
     * @param obstacleCounter Amount of ants which are getting generated per nest. Must be >= 0
     */
    public Generator(Grid grid, int nestCounter, int foodCounter, int antsPerNest, int obstacleCounter) {
        this.grid = grid;
        this.nestCounter = nestCounter;
        this.foodCounter = foodCounter;
        this.antsPerNest = antsPerNest;
        this.obstacleCounter = obstacleCounter;
    }

    /**
     * Checks if a given point is in between a triangle.
     *
     * @param point1 Point1, Point1 != null
     * @param point2 Point2, Point2 != null
     * @param point3 Point3, Point3 != null
     * @param x      X value of given Point
     * @param y      Y value of given Point
     * @return isInside of triangle
     */
    private static boolean isInside(Vector point1, Vector point2, Vector point3, int x, int y) {
        double d1 = sign(new Vector(x, y), point1, point2);
        double d2 = sign(new Vector(x, y), point2, point3);
        double d3 = sign(new Vector(x, y), point3, point1);

        boolean hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNeg && hasPos);
    }

    /**
     * Calculates the point's orientation with respect to an edge.
     *
     * @param p1 First edge point, must be != null
     * @param p2 Second edge point, must be != null
     * @param p3 The point to check, must be != null
     * @return The point's orientation.
     */
    private static double sign(Vector p1, Vector p2, Vector p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

    /**
     * Generates obstacles in a given area.
     * Obstacles are generated in a triangle form.
     *
     * @param startPoint Start Point of a rectangle, must be != null
     * @param endPoint   End Point of a rectangle, must be != nulll
     */
    private void generateObstacles(Vector startPoint, Vector endPoint) {
        for (int i = 0; i < obstacleCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            int maxDistance = 30;
            Vector point1 = new Vector(randomX, randomY);
            Vector point2 = new Vector(generateRandomNumberBetween(randomX - maxDistance, randomX + maxDistance), generateRandomNumberBetween(randomY - maxDistance, randomY + maxDistance));
            Vector point3 = new Vector(generateRandomNumberBetween(randomX - maxDistance, randomX + maxDistance), generateRandomNumberBetween(randomY - maxDistance, randomY + maxDistance));

            Tile tile = grid.getMap().get(new Vector(randomX, randomY));
            // should never be the case because obstacles are generated first
            if (tile != null) {
                i--;
                continue;
            }

            // Find the boundaries of the rectangle that encloses the triangle
            int minX = Math.min(point1.getX(), Math.min(point2.getX(), point3.getX()));
            int maxX = Math.max(point1.getX(), Math.max(point2.getX(), point3.getX()));
            int minY = Math.min(point1.getY(), Math.min(point2.getY(), point3.getY()));
            int maxY = Math.max(point1.getY(), Math.max(point2.getY(), point3.getY()));

            // Iterate over each grid point in the enclosing rectangle
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    Vector position = new Vector(x, y);
                    if (isInside(point1, point2, point3, x, y)) {
                        if (grid.getMap().get(position) != null) {
                            continue;
                        }
                        grid.getMap().put(position, new Obstacle(position));
                    }
                }
            }
        }
    }

    /**
     * Generates foodSources in a given area.
     *
     * @param startPoint Start Point of a rectangle, must be != null
     * @param endPoint   End Point of a rectangle, must be != null
     */
    private void generateFoodSources(Vector startPoint, Vector endPoint) {
        for (int i = 0; i < foodCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            Tile tile = grid.getMap().get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            int foodSourceHeight = 1;//(int) (Math.random() * 5) + 1;
            int foodSourceWidth = 1;// (int) (Math.random() * 5) + 1;

            for (int x = 0; x < foodSourceWidth; x++) {
                for (int y = 0; y < foodSourceHeight; y++) {
                    Vector position = new Vector(randomX + x, randomY + y);
                    grid.getMap().put(position, new FoodSource(position));
                }
            }
        }
    }

    /**
     * Generates nests in a given area.
     *
     * @param startPoint Start Point of a rectangle, must be != null
     * @param endPoint   End Point of a rectangle, must be != null
     */
    private void generateNests(Vector startPoint, Vector endPoint) {
        // STYLE: prozeduale Programmierung + objektorientierte Programmierung
        // prozedual: weil es z.B. mit Seiteneffekten am grid arbeitet
        // oo: verwendet Objekte von Colors, Vector, Ants, ..
        for (int i = 0; i < nestCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            Tile tile = grid.getMap().get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            Color nestColor = new Color(generateRandomNumberBetween(0, 255), generateRandomNumberBetween(0, 255), generateRandomNumberBetween(0, 255));
            Nest nest = new Nest(new Vector(randomX, randomY), nestColor, antsPerNest, grid);

            grid.getMap().put(new Vector(randomX, randomY), nest);

            // spawn ants for nest
            int maxSpawnDistance = 10;
            // spawn ants
            for (int antCounter = 0; antCounter < antsPerNest; antCounter++) {
                int randomXDelta = (int) (Math.random() * maxSpawnDistance * 2) - maxSpawnDistance;
                int randomYDelta = (int) (Math.random() * maxSpawnDistance * 2) - maxSpawnDistance;

                Vector spawnPos = new Vector(nest.getPosition().getX() + randomXDelta, nest.getPosition().getY() + randomYDelta);
                nest.getAnts().add(new Ant(grid, nest, 100, spawnPos));
            }
        }
    }

    /**
     * Generates obstacles, nests and FoodSources in a given area.
     *
     * @param startPoint Start Point of a rectangle, must be != null
     * @param endPoint   End Point of a rectangle, must be != null
     */
    public void generateTilesForChunk(Vector startPoint, Vector endPoint) {
        generateObstacles(startPoint, endPoint);
        generateFoodSources(startPoint, endPoint);
        generateNests(startPoint, endPoint);
    }

    /**
     * Returns a random number in a given range.
     * Min value must be equal or lower than max value.
     *
     * @param min Lower bound
     * @param max Lower bound
     */
    // STYLE: funktionale Programmierung
    // basiert nur auf den Eingangsparametern basiert und verändert keinen Zustand und hat keine Seiteneffekte.
    // kann aber in fast allen Paradigmen verwendet werden.
    private static int generateRandomNumberBetween(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
