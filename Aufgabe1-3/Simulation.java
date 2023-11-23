import codedraw.CodeDraw;
import codedraw.EventScanner;
import codedraw.Key;

import java.awt.*;

// Modularisierungseinheit: Klasse

// combines visuals from CodeDraw with the logic found in Grid
public class Simulation {
    private double cellSize;
    private final double initialCellSize;
    private final int updatesPerCircle;
    private final CodeDraw cd;
    private final Grid grid;
    private final EventScanner input;
    private Vector offset;
    private int runtime = 400;

    /**
     * Initializes a Simulation.
     *
     * @param cellSize         cellSize for Grid, must be > 0
     * @param maxX             width of Grid, must be > 0
     * @param maxY             height of Grid, must be > 0
     * @param updatesPerCircle how many iterations are made before visual update, must be > 0
     */
    public Simulation(int cellSize, int maxX, int maxY, int updatesPerCircle) {
        this.initialCellSize = cellSize;
        this.cellSize = cellSize;
        this.updatesPerCircle = updatesPerCircle;

        //simulation parameters
        cd = new CodeDraw(maxX * cellSize, maxY * cellSize);
        cd.setAlwaysOnTop(true);
        grid = new Grid(maxX, maxY);

        //movement
        input = cd.getEventScanner();
        offset = new Vector(0, 0);
    }

    /**
     * Runs one circle of the simulation.
     * Multiple updates can happen in each circle
     */
    // GOOD: prozeduale Programmierung: Kontrollfluss leicht erkennbar
    // Hat eine enge Kopplung mit Grid
    private void run() {
        if (!cd.isClosed()) {
            // STYLE: prozedulale Programmierung + objektorientierte Programmierung
            // wir arbeiten immer am gleichen Grid Objekt aber verursachen den Programmfortschritt über Seiteneffekte.
            // grid.update() triggert alle Entities und überarbeitet die Entities in der Map.
            for (int i = 0; i < updatesPerCircle; i++) {
                grid.update();
            }
            handleKeyboardInput(input);
            drawWindow();
        }
        if(runtime-- < 0) cd.close();
        System.out.println(runtime);
    }

    /**
     * Handles the logic of drawing the entities on the window.
     */
    // GOOD: objektorientierte Programmierung:
    // nutzt dynamisches Binden der Entities um die Farben und Positionen zu bekommen.
    private void drawWindow() {
        cd.setColor(Color.gray);
        cd.fillRectangle(0, 0, cd.getWidth(), cd.getHeight());
        Vector actualVector = this.offset.add(new Vector((int) ((cd.getWidth() / cellSize) / 2), (int) ((cd.getHeight() / cellSize) / 2)));
        grid.queue().forEach(entry -> {
            Vector position = entry.getPosition().add(actualVector);
            Color tileColor = entry.getColor();
            cd.setColor(tileColor);
            cd.fillRectangle(position.getX() * cellSize, position.getY() * cellSize, cellSize, cellSize);
        });

        cd.show();
    }

    /**
     * Starts the simulation.
     */
    public void start() {
        while (!cd.isClosed()) {
            run();
        }
    }

    /**
     * Handles the keyboard input.
     * Allows to change the visible part of the window.
     * With the Keys ASWD, 8462, and arrow keys, you can change in top,left,right or bottom directions.
     * With + and -, you can zoom in or zoom out
     * With R, you can reset the window to the initial view
     * With F, you can generate new FoodSources on the Grid
     * With N, you can generate a new Nest on the Grid
     */
    //STYLE: neuer funktionaler Teil.
    // GOOD: prozeduale Programmierung: Kontrollfluss leicht erkennbar
    private void handleKeyboardInput(EventScanner input) {
        int x = 0, y = 0;
        int offsetByStep = 20;
        while (input.hasEventNow() && !input.hasKeyDownEvent()) {
            input.nextEvent();
        }

        while (input.hasEventNow() && input.hasKeyDownEvent()) {
            Key key = input.nextKeyDownEvent().getKey();
            switch (key) {
                case W, UP, NUMPAD8 -> y++;
                case S, DOWN, NUMPAD2 -> y--;
                case D, RIGHT, NUMPAD6 -> x--;
                case A, LEFT, NUMPAD4 -> x++;
                case PLUS, ADD -> cellSize *= (double) 3 / 2;
                case MINUS, SUBTRACT -> cellSize *= (double) 2 / 3;
                case F -> grid.generateFoodSources();
                case N -> grid.generateNests();
                case R -> {
                    offset = new Vector(0, 0);
                    cellSize = initialCellSize;
                }
            }
            offset = offset.add(new Vector(x * offsetByStep, y * offsetByStep));
            drawWindow();
        }
    }
}
