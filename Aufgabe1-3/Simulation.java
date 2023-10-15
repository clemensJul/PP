import codedraw.CodeDraw;

import java.awt.*;
import java.util.Queue;

// combines visuals from CodeDraw with the logic found in Grid
public class Simulation {

    private int cellSize;
    private int maxX;
    private int maxY;
    private int numberOfAnts;
    private float[] bias ;

    private int updatesPerCircle;


    private CodeDraw cd;
    private Grid grid;
    private  Queue<Tile> priorityQueue;
    public Simulation(int cellSize, int maxX, int maxY, int numberOfAnts, float[] bias, int updatesPerCircle) {
        this.cellSize = cellSize;
        this.maxX = maxX;
        this.maxY = maxY;
        this.numberOfAnts = numberOfAnts;
        this.bias = bias;
        this.updatesPerCircle = updatesPerCircle;

        //simulation parameters
        cd = new CodeDraw(maxX * cellSize, maxY * cellSize);
        grid = new Grid(maxX, maxY, numberOfAnts,bias);

    }

    public boolean isClosed(){
        return cd.isClosed();
    }

    //runs one circle of the simulation - multiple updates can happen in each circle
    public void run() {

        if (!cd.isClosed()){
            priorityQueue = grid.getUpdateQueue();


            for (int i = 0; i < updatesPerCircle; i++) {
                grid.update();
            }
            cd.setColor(Color.gray);
            cd.fillRectangle(0,0,maxX*cellSize,maxY*cellSize);

            while (!priorityQueue.isEmpty()){
                Tile tile = priorityQueue.poll();
                Color tileColor = tile.getTileColor();
                cd.setColor(tileColor);
                Vector position = tile.getPosition();
                cd.fillRectangle(position.getX()*cellSize, position.getY()*cellSize, cellSize,cellSize);
            }
            cd.show();
        }
    }
}
