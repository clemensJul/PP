import java.util.ArrayList;

public class Grid {
    private int sizeX, sizeY, numberOfAnts;
    private Cell[][] cells;
    private ArrayList<Entity> entities;

    public Grid(int sizeX, int sizeY, int numberOfAnts) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.numberOfAnts = numberOfAnts;
        this.cells = new Cell[sizeX][sizeY];

        entities = new ArrayList<>(numberOfAnts + sizeX*sizeY);

        long nest = 1;
        long food = 3 + Math.round((Math.random()*6));

        while (nest > 0 && food > 0){
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    if (Math.random()>0.98f && nest > 0){
                        cells[x][y] = new Nest(new Vector(x,y));
                        nest--;
                    } else if ( food > 0) {
                        cells[x][y] = new FoodSource(new Vector(x,y));
                        food--;
                    }else {
                        cells[x][y] = new EmptyCell(new Vector(x,y));
                    }
                }
            }
        }

    }
    public void update(){
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                cells[x][y].update();
            }
        }
    }
    //get all neighbours
    public ArrayList<Cell> availableNeighbours(Ant ant){
        Vector direction = ant.getDirection();
        Vector vector = ant.getPosition();
        ArrayList<Cell> neighbours = new ArrayList<>();

        int dirX = direction.getX();
        int dirY = direction.getY();

        // ant is facing direction is diagonally
        if(Math.abs(dirX) == Math.abs(dirY)){
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    int dX = dirX - x;
                }
            }
        }else {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {

                }
            }
        }
        return neighbours;
    }
    private Cell getCell(Vector vector){
        return cells[vector.getX()][vector.getY()];
    }
    /*
    * -1,1  0,1  1,1
    * -1,0  0,0  1,0
    * -1-1  0-1  1-1
    * */
}
