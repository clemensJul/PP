import java.util.ArrayList;

public class Grid {
    private int sizeX, sizeY, numberOfAnts;
    private Cell[][] cells;

    public Grid(int sizeX, int sizeY, int numberOfAnts) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfAnts = numberOfAnts;
        this.cells = new Cell[sizeX][sizeY];

        int foodCount = (int) (3 + Math.round((Math.random() * 6)));

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                cells[x][y] = new EmptyCell(new Position(x, y));
            }
        }

        for (int i = 0; i < foodCount; i++) {
            int randomX = (int)(Math.random() * sizeX);
            int randomY = (int)(Math.random() * sizeY);

            if(!(cells[randomX][randomY] instanceof EmptyCell)) {
                i--;
                continue;
            }

            cells[randomX][randomY] = new FoodSource(new Position(randomX, randomY));
        }

        Nest nest = null;
        for (int i = 0; i < 1; i++) {
            int randomX = (int)(Math.random() * sizeX);
            int randomY = (int)(Math.random() * sizeY);

            if(!(cells[randomX][randomY] instanceof EmptyCell)) {
                i--;
                continue;
            }

            nest = new Nest(new Position(randomX, randomY));
            cells[randomX][randomY] = nest;
        }

        // we need to define a radius in which ants can be spawned around the nest
        int maxSpawnDistance = 2;
        // spawn ants
        for (int i = 0; i < numberOfAnts; i++) {
            int x = Math.abs((nest.getPosition().getX() + (int)(Math.random() * maxSpawnDistance*2)-maxSpawnDistance) % sizeX);
            int y = Math.abs((nest.getPosition().getY() + (int)(Math.random() * maxSpawnDistance*2)-maxSpawnDistance) % sizeY);

            Ant ant = new Ant(new Position(x, y));
            cells[x][y].getAnts().add(ant);
        }

        // only for testing if all ants are spawned
        int sum = 0;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if(!cells[x][y].getAnts().isEmpty()) {
                    sum += cells[x][y].getAnts().size();
                }
            }
        }

        System.out.println("created everything");
    }

    public void run() {
        beforeUpdate();
        update();
        afterUpdate();
    }

    public void beforeUpdate() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                cells[x][y].setAntsToUpdate();
            }
        }

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                cells[x][y].beforeUpdate();
            }
        }
    }

    public void update() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                cells[x][y].update(this);
            }
        }
    }

    public void afterUpdate() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                cells[x][y].afterUpdate();
            }
        }
    }

    //get all neighbours
    public ArrayList<Cell> availableNeighbours(Ant ant) {
        Direction direction = ant.getDirection();
        Position position = ant.getPosition();
        ArrayList<Cell> neighbours = new ArrayList<>();

        int dirX = direction.getX();
        int dirY = direction.getY();

        // ant is facing direction is diagonally
        if (Math.abs(dirX) == Math.abs(dirY)) {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    int dX = dirX - x;
                }
            }
        } else {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {

                }
            }
        }
        return neighbours;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public Cell[][] getCells() {
        return cells;
    }

    private Cell getCell(Position position) {
        return cells[position.getX()][position.getY()];
    }
    /*
     * -1,1  0,1  1,1
     * -1,0  0,0  1,0
     * -1-1  0-1  1-1
     * */
}
