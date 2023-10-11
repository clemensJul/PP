import javax.swing.text.Position;
import java.util.ArrayList;
import java.util.Arrays;

public class Ant implements Entity {
    private Vector position;
    private Vector direction;
    private Grid grid;

    private enum State {
        EXPLORE,
        SCAVENGE,
        COLLECT
    }

    private State state;

    private final double stinkLowerBorder = 0.3;
    private final double stinkUpperBorder = 0.7;

    private final int switchToExploreAfter = 7;

    private int badScentsCounter = 0;

    public Ant(Vector position, Grid grid) {
        this.position = position;
        this.direction = Vector.RandomDirection();
        this.grid = grid;
        state = state.EXPLORE;
    }

    @Override
    public void update() {
        Tile[] neighbours = grid.availableNeighbours(this);

        Tile newTile = null;
        switch (state) {
            case EXPLORE -> {
                // check for food source
                Tile foodSource = checkNeighborsForFoodSource(new ArrayList<>(Arrays.asList(neighbours)));
                if (foodSource != null) {
                    newTile = foodSource;
                    this.state = State.COLLECT;
                    break;
                }

                // prefers tiles with low scents

                // if a tile has a good scent we switch to scavenge
                // check for good scents
                ArrayList<Tile> goodScents = new ArrayList<>();
                for (Tile neighbour : neighbours) {
                    if (neighbour.getCurrentStink() > stinkUpperBorder) {
                        goodScents.add(neighbour);
                    }
                }

                if (!goodScents.isEmpty()) {
                    state = State.SCAVENGE;
                    newTile = selectRandomTile(goodScents);
                    break;
                }

                // check for bad scents
                ArrayList<Tile> badScents = new ArrayList<>();
                for (Tile neighbour : neighbours) {
                    if (neighbour.getCurrentStink() < stinkLowerBorder) {
                        badScents.add(neighbour);
                    }
                }

                if (!badScents.isEmpty()) {
                    newTile = selectRandomTile(badScents);
                    break;
                }

                // no good and no bad scents found --> selection out of all neighbors
                newTile = selectRandomTile(new ArrayList<>(Arrays.asList(neighbours)));
            }
            case SCAVENGE -> {
                // check for food source
                Tile foodSource = checkNeighborsForFoodSource(new ArrayList<>(Arrays.asList(neighbours)));
                if (foodSource != null) {
                    newTile = foodSource;
                    this.state = State.COLLECT;
                    break;
                }

                // if a tile has a good scent we switch to scavenge
                // check for good scents
                ArrayList<Tile> goodScents = new ArrayList<>();
                for (Tile neighbour : neighbours) {
                    if (neighbour.getCurrentStink() > stinkUpperBorder) {
                        goodScents.add(neighbour);
                    }
                }

                // select neihgbor with good scent
                if (!goodScents.isEmpty()) {
                    newTile = selectRandomTile(goodScents);
                    // reset counter because there was a good scent in neighborhood
                    badScentsCounter = switchToExploreAfter;
                    break;
                }

                // increase counter because there is no good scent
                badScentsCounter++;
                if (badScentsCounter > switchToExploreAfter) {
                    this.state = State.EXPLORE;
                }

                // select random tile from neighbors
                newTile = selectRandomTile(new ArrayList<>(Arrays.asList(neighbours)));
            }
            case COLLECT -> {
                // we need to check if it just came on the food source field
                if (grid.getTile(position) instanceof FoodSource) {
                    // do 180 degree
                    Vector invertedDirection = Vector.invert(direction);
                    newTile = grid.getTile(position.getX() + invertedDirection.getX(), position.getY() + invertedDirection.getY());
                    break;
                }

                Tile nest = checkNeighborsForNest(new ArrayList<>(Arrays.asList(neighbours)));
                if (nest != null) {
                    newTile = nest;
                    this.state = State.EXPLORE;
                    direction = Vector.invert(direction);
                    break;
                }

                // select random tile from neighbors
                newTile = selectRandomTile(new ArrayList<>(Arrays.asList(neighbours)));
            }
        }
        grid.getTile(position).decreaseAntsPresent();
        if (state == State.COLLECT) {
            grid.getTile(position).decreaseFoodPresent();
        }
        position = newTile.getPosition();
        grid.getTile(position).increaseAntsPresent();
        if (state == State.COLLECT) {
            grid.getTile(position).increaseFoodPresent();
        }
    }

    private static Tile checkNeighborsForFoodSource(ArrayList<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile instanceof FoodSource) {
                return tile;
            }
        }
        return null;
    }

    private static Tile checkNeighborsForNest(ArrayList<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile instanceof Nest) {
                return tile;
            }
        }
        return null;
    }

    private static Tile selectRandomTile(ArrayList<Tile> tiles) {
        // set vector based on scent and random variable
        double scentSum = 0;

        return tiles.get((int)Math.floor(Math.random() * tiles.size()));
//
//        for (Tile tile : tiles) {
//            scentSum += tile.getCurrentStink() + 1;
//        }
//
//        double randomFactor = Math.random() * scentSum;
//
//        // get scent from cells
//        double index = 0;
//        for (Tile tile : tiles) {
//            index += tile.getCurrentStink();
//
//            if (index > randomFactor) {
//                return tile;
//            }
//        }
//
//        return tiles.get(tiles.size() - 1);
    }

    private void move() {

    }

    @Override
    public Vector getPosition() {
        return position;
    }

    public Vector getDirection() {
        return direction;
    }
}
