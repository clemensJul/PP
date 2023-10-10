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

        entities = new ArrayList<>(numberOfAnts +sizeX*sizeY);

        int nest = 1;
        int food = 3 + (int)(Math.random()*6);


        while (nest > 0 && food > 0){
            for (int x = 0; x < sizeX; x++) {
                for (int y = 0; y < sizeY; y++) {
                    if (Math.random()>0.98f && nest > 0){
                        entities.add(new Nest(new Position(x,y))) ;
                        nest--;
                    } else if ( food > 0) {
                        entities.add( new FoodSource(new Position(x,y)));
                        food--;
                    }else {
                        entities.add(new EmptyCell(new Position(x,y)));
                    }
                }
            }
        }

    }
    public void update(){
        for (Entity e :
                entities) {
            e.update();
        }
    }
    private Cell[] availableNeighbours(Ant ant){
        return null;
    }
}
