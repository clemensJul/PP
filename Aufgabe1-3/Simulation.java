import codedraw.CodeDraw;

public class Simulation {


    public static void main(String[] args) {

        //simulation parameters
        int cellSize = 3;
        int maxX = 250;
        int maxY = 200;
        int numberOfAnts = 100;


        CodeDraw cd = new CodeDraw(maxX * cellSize, maxY * cellSize);
        Grid grid = new Grid(maxX, maxY, 100);

        while (!cd.isClosed()) {
            grid.run();
        }
    }
}
