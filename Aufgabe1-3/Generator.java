import java.awt.*;

public class Generator {
    private int nestCounter = 3;
    private int foodCounter = 3;
    private int antsPerNest = 100;
    private int obstacleCounter = 100;
    private final int[] antBias;

    private final Grid grid;

    public Generator(Grid grid, int nestCounter, int foodCounter, int antsPerNest, int obstacleCounter, int[] antBias) {
        this.grid = grid;
        this.nestCounter = nestCounter;
        this.foodCounter = foodCounter;
        this.antsPerNest = antsPerNest;
        this.obstacleCounter = obstacleCounter;
        this.antBias = antBias;
    }

    // Methode zur Überprüfung, ob ein Punkt im Dreieck liegt
    private static boolean isInside(Vector point1, Vector point2, Vector point3, int x, int y) {
        double d1 = sign(new Vector(x, y), point1, point2);
        double d2 = sign(new Vector(x, y), point2, point3);
        double d3 = sign(new Vector(x, y), point3, point1);

        boolean hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNeg && hasPos);
    }

    // Methode zur Berechnung der Signatur eines Punkts in Bezug auf eine Kante
    private static double sign(Vector p1, Vector p2, Vector p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

    private void generateObstacles(Vector startPoint, Vector endPoint) {
        for (int i = 0; i < obstacleCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            Vector point1 = new Vector(randomX, randomY);
            Vector point2 = new Vector(generateRandomNumberBetween(randomX - 20, randomX + 20), generateRandomNumberBetween(randomY - 20, randomY + 20));
            Vector point3 = new Vector(generateRandomNumberBetween(randomX - 20, randomX + 20), generateRandomNumberBetween(randomY - 20, randomY + 20));

            Tile tile = grid.getMap().get(new Vector(randomX, randomY));
            // should never be the case because obstacles are generated first
            if (tile != null) {
                i--;
                continue;
            }

            // Finde die Begrenzungen des Rechtecks, das das Dreieck umschließt
            int minX = Math.min(point1.getX(), Math.min(point2.getX(), point3.getX()));
            int maxX = Math.max(point1.getX(), Math.max(point2.getX(), point3.getX()));
            int minY = Math.min(point1.getY(), Math.min(point2.getY(), point3.getY()));
            int maxY = Math.max(point1.getY(), Math.max(point2.getY(), point3.getY()));

            // Iteriere über jedes Gitterpunkt im umschließenden Rechteck
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    Vector position = new Vector(x, y);
                    // Überprüfe, ob der Punkt im Dreieck liegt
                    if (isInside(point1, point2, point3, x, y)) {
                        if (grid.getMap().get(position) != null) {
                            continue;
                        }
                        grid.getMap().put(position, new Obstacle(position));
                    }
                }
            }

//            // make the nest nxm
//            int obstacleHeight = Math.min(1 + (int) (Math.random() * 5), Math.abs(startPoint.getX() - endPoint.getX()));
//            int obstacleWidth = Math.min(1 + (int) (Math.random() * 5), Math.abs(startPoint.getY() - endPoint.getY()));
//            for (int x = 0; x < obstacleWidth; x++) {
//                for (int y = 0; y < obstacleHeight; y++) {
//                    Vector position = new Vector(x + randomX, y + randomY);
//                    if (grid.getMap().get(position) != null) {
//                        continue;
//                    }
//                    grid.getMap().put(position, new Obstacle(position));
//                }
//            }
        }
    }

    private void generateFoodSources(Vector startPoint, Vector endPoint) {
        for (int i = 0; i < foodCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            Tile tile = grid.getMap().get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            grid.getMap().put(new Vector(randomX, randomY), new Obstacle(new Vector(randomX, randomY)));
        }
    }

    private void generateNests(Vector startPoint, Vector endPoint) {
        for (int i = 0; i < nestCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            Tile tile = grid.getMap().get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            Color nestColor = new Color(generateRandomNumberBetween(0, 255), generateRandomNumberBetween(0, 255), generateRandomNumberBetween(0, 255));
            Nest nest = new Nest(new Vector(randomX, randomY), nestColor);

            grid.getMap().put(new Vector(randomX, randomY), nest);

            // spawn ants for nest
            int maxSpawnDistance = 10;
            // spawn ants
            for (int antCounter = 0; antCounter < antsPerNest; antCounter++) {
                int randomXDelta = (int) (Math.random() * maxSpawnDistance * 2) - maxSpawnDistance;
                int randomYDelta = (int) (Math.random() * maxSpawnDistance * 2) - maxSpawnDistance;

                Vector spawnPos = new Vector(nest.getPosition().getX() + randomXDelta, nest.getPosition().getY() + randomYDelta);
                grid.getAnts().add(new Ant(grid, nest, antBias, 0, 100, spawnPos));
            }
        }
    }

    public void generateTilesForChunk(Vector startPoint, Vector endPoint) {
        generateObstacles(startPoint, endPoint);
        generateFoodSources(startPoint, endPoint);
        generateNests(startPoint, endPoint);
    }

    private int generateRandomNumberBetween(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
