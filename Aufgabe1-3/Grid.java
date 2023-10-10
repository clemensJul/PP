import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        int nest = 1;
        int food = 3 + (int)(Math.random()*6);


        while (nest > 0 && food > 0){
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    if (Math.random()>0.98f && nest > 0){
                        cells[x][y] = new Nest(new Position(x,y));
                        nest--;
                    } else if ( food > 0) {
                        cells[x][y] = new FoodSource(new Position(x,y));
                        food--;
                    }else {
                        cells[x][y] = new EmptyCell(new Position(x,y));
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
        Position direction = ant.getDirection();
        Position position = ant.getPosition();
        ArrayList<Cell> neighbours = new ArrayList<>();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                int dif = (x+y) + direction.getX()+ direction.getY();
                if (Math.abs(dif)>2 && (x!= 0 && y != 0)){

                }
            }
        }
        return neighbours;
    }
    /*
    * -1,1  0,1  1,1
    * -1,0  0,0  1,0
    * -1-1  0-1  1-1
    *
    *
    * */
}
