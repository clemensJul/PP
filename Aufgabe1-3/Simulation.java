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
        float[] bias =  new float[]{0.01f,0.1f,1f,0.1f,0.01f};
        // should not be more than 1
        float exploreBiasChange = 1f;

        // can be more than 1 - currently testing - always takes maximum
        float scavengeBiasChange= 10f;
        float collectBiasChange = 10f;


        CodeDraw cd = new CodeDraw(maxX * cellSize, maxY * cellSize);
        Grid grid = new Grid(maxX, maxY, numberOfAnts,bias, new float[]{exploreBiasChange,scavengeBiasChange,collectBiasChange});
        Queue<Tile> priorityQueue = grid.getUpdateQueue();

        while (!cd.isClosed()) {
            for (int i = 0; i < 1; i++) {
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
