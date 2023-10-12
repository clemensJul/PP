import codedraw.CodeDraw;

import java.awt.*;
import java.util.Arrays;
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

        Ant test = new Ant(new Vector(100,100),grid);
        System.out.println(test.getDirection());
        System.out.println(Arrays.toString(grid.availableNeighbours(test)));

        while (!cd.isClosed()) {
            for (int i = 0; i < 10; i++) {
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
