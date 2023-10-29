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

    private void generateObstacles(Vector startPoint, Vector endPoint) {
        for (int i = 0; i < obstacleCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            Tile tile = grid.getMap().get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            // make the nest nxm
            int obstacleHeight = Math.min(1 + (int) (Math.random() * 5), Math.abs(startPoint.getX() - endPoint.getX()));
            int obstacleWidth = Math.min(1 + (int) (Math.random() * 5), Math.abs(startPoint.getY() - endPoint.getY()));
            for (int x = 0; x < obstacleWidth; x++) {
                for (int y = 0; y < obstacleHeight; y++) {
                    Vector position = new Vector(x + randomX, y + randomY);
                    if (grid.getMap().get(position) != null) {
                        continue;
                    }
                    grid.getMap().put(position, new Obstacle(new Vector(randomX, randomY)));
                }
            }
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
