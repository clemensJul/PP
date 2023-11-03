import codedraw.CodeDraw;
import codedraw.EventScanner;
import codedraw.Key;

import java.awt.*;
import java.util.Queue;
import java.util.Scanner;

// Modularisierungseinheit: Klasse

// combines visuals from CodeDraw with the logic found in Grid
public class Simulation {
    private final int cellSize;
    private final int maxX;
    private final int maxY;
    private final int updatesPerCircle;
    private final CodeDraw cd;
    private final Grid grid;
    private EventScanner input;
    private Vector offset;

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

        //movement
        input = cd.getEventScanner();
        offset = new Vector(0,0);
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
            // STYLE: prozedulale Programmierung + objektorientierte Programmierung
            // wir arbeiten immer am gleichen Grid Objekt aber verursachen den Programmfortschritt über Seiteneffekte.
            // grid.update() triggert alle Entities und überarbeitet die Entities in der Map.
            for (int i = 0; i < updatesPerCircle; i++) {
                grid.update();
            }

            cd.setColor(Color.gray);
            cd.fillRectangle(0, 0, maxX * cellSize, maxY * cellSize);

            offset = offset.add(updateOffset(input ));

            grid.queue().forEach(entry -> {
                Vector position = entry.getPosition().add(this.offset);
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
    private Vector updateOffset(EventScanner input){
        int x = 0 ,y = 0;
        while (input.hasKeyDownEvent()){
            Key key = input.nextKeyDownEvent().getKey();
            switch (key){
                case W -> {
                    x--;
                }
                case S -> {
                    x++;
                }
                case D -> {
                    y--;
                }
                case A -> {
                    y++;
                }
            }
        }
        return new Vector(x,y);
    }
}
