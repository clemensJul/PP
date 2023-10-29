import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Generator {
    private int nestCounter = 3;
    private int foodCounter = 3;
    private int antsPerNest = 100;
    private int obstacleCounter = 100;

    public Generator(int nestCounter, int foodCounter, int antsPerNest, int obstacleCounter) {
        this.nestCounter = nestCounter;
        this.foodCounter = foodCounter;
        this.antsPerNest = antsPerNest;
        this.obstacleCounter = obstacleCounter;
    }

    private void generateObstacles(Map<Vector, Tile> map, Vector startPoint, Vector endPoint) {
        for (int i = 0; i < obstacleCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            Tile tile = map.get(new Vector(randomX, randomY));
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
                    if (map.get(position) != null) {
                        continue;
                    }
                    map.put(position, new Obstacle(new Vector(randomX, randomY)));
                }
            }
        }
    }

    private void generateFoodSources(Map<Vector, Tile> map, Vector startPoint, Vector endPoint) {
        for (int i = 0; i < foodCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            Tile tile = map.get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            map.put(new Vector(randomX, randomY), new Obstacle(new Vector(randomX, randomY)));
        }
    }
    private void generateNests(Map<Vector, Tile> map, Vector startPoint, Vector endPoint) {
        for (int i = 0; i < nestCounter; i++) {
            int randomX = generateRandomNumberBetween(startPoint.getX(), endPoint.getX());
            int randomY = generateRandomNumberBetween(startPoint.getY(), endPoint.getY());

            Tile tile = map.get(new Vector(randomX, randomY));
            if (tile != null) {
                i--;
                continue;
            }

            Color nestColor = new Color(generateRandomNumberBetween(0, 255), generateRandomNumberBetween(0, 255), generateRandomNumberBetween(0, 255));
            Nest nest = new Nest(new Vector(randomX, randomY), nestColor);

            map.put(new Vector(randomX, randomY), nest);
//
//            // spawn ants for nest
//            // we need to define a radius in which ants can be spawned around the nest
//            int maxSpawnDistance = 1;
//            // spawn ants
//            for (int antCounter = 0; antCounter < 10; antCounter++) {
//                ants.add(new Ant(this, nest, bias, 0, 100, spawnPos));
//            }
        }
    }

    public Map<Vector, Tile> generateTilesForChunk(Vector startPoint, Vector endPoint) {
        HashMap<Vector, Tile> map = new HashMap<>();
        generateObstacles(map, startPoint, endPoint);
        generateFoodSources(map, startPoint, endPoint);
        generateNests(map, startPoint, endPoint);

        return map;
    }

    private int generateRandomNumberBetween(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
