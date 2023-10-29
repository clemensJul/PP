import codedraw.CodeDraw;
import java.awt.*;
import java.util.Queue;
// Modularisierungseinheit: Klasse

// combines visuals from CodeDraw with the logic found in Grid
public class Simulation {
    private final int cellSize;
    private final int maxX;
    private final int maxY;
    private final int updatesPerCircle;
    private final CodeDraw cd;
    private final Grid grid;

    /**
     * Initializes a Simulation.
     *
     * @param cellSize cellSize for Grid
     * @param maxX width of Grid
     * @param maxY height of Grid
     * @param bias Biases for ants on directions
     * @param updatesPerCircle how many iterations are made before visual update
     */
    public Simulation(int cellSize, int maxX, int maxY, int[] bias, int updatesPerCircle) {
        this.cellSize = cellSize;
        this.maxX = maxX;
        this.maxY = maxY;
        this.updatesPerCircle = updatesPerCircle;

        //simulation parameters
        cd = new CodeDraw(maxX * cellSize, maxY * cellSize);
        cd.setAlwaysOnTop(true);
        grid = new Grid(bias);
    }

    /**
     * Checks if the CodeDraw window is closed.
     *
     * @return if simulation window is closed
     */
    public boolean isClosed() {
        return cd.isClosed();
    }

    /**
     * Runs one circle of the simulation.
     * Multiple updates can happen in each circle
     */
    private void run() {
        if (!cd.isClosed()) {
            for (int i = 0; i < updatesPerCircle; i++) {
                grid.update();
            }

            cd.setColor(Color.gray);
            cd.fillRectangle(0, 0, maxX * cellSize, maxY * cellSize);

            grid.queue().forEach(entry -> {
                Vector position = entry.getPosition();
                Color tileColor = entry.getColor();
                cd.setColor(tileColor);
                cd.fillRectangle(position.getX() * cellSize, position.getY() * cellSize, cellSize, cellSize);
            });

            cd.show();
        }
    }

    /**
     * Starts the simulation.
     */
    public void start() {
        while(!cd.isClosed()) {
            run();
        }
    }
}
