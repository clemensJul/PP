import codedraw.CodeDraw;

import java.awt.*;
import java.util.Queue;

public class Simulation {


    public static void main(String[] args) {

        //simulation parameters
        int cellSize = 5;
        int maxX = 250;
        int maxY = 200;
        int numberOfAnts = 100;


        CodeDraw cd = new CodeDraw(maxX * cellSize, maxY * cellSize);
        Grid grid = new Grid(maxX, maxY, 100);
        Queue<Tile> priorityQueue = grid.getUpdateQueue();

        while (!cd.isClosed()) {
            cd.setColor(Color.gray);
            cd.fillRectangle(0,0,maxX*cellSize,maxY*cellSize);
            grid.update();
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
