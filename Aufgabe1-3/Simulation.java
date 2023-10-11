import codedraw.CodeDraw;

import java.awt.*;

public class Simulation {


    public static void main(String[] args) {

        //simulation parameters
        int cellSize = 5;
        int maxX = 250;
        int maxY = 200;
        int numberOfAnts = 100;


        CodeDraw cd = new CodeDraw(maxX * cellSize, maxY * cellSize);
        Grid grid = new Grid(maxX, maxY, 100);

        while (!cd.isClosed()) {
            grid.update();
            cd.setColor(Color.gray);
            cd.fillRectangle(0,0,maxX*cellSize,maxY*cellSize);

            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    Color drawColor = grid.getTile(x,y).tileColor();
                    if (drawColor != null){
                        cd.setColor(drawColor);
                        cd.fillRectangle(x*cellSize,y*cellSize,cellSize,cellSize);
                    }
                }
            }
            cd.show();
        }
    }
}
