import codedraw.CodeDraw;
import codedraw.Event;
import codedraw.EventScanner;
import codedraw.Key;

import java.awt.*;
import java.util.Queue;
import java.util.Scanner;

// Modularisierungseinheit: Klasse

// combines visuals from CodeDraw with the logic found in Grid
public class Simulation {
    private int cellSize;
    private final int updatesPerCircle;
    private final CodeDraw cd;
    private final Grid grid;
    private final EventScanner input;
    private Vector offset;

    /**
     * Initializes a Simulation.
     *
     * @param cellSize         cellSize for Grid
     * @param maxX             width of Grid
     * @param maxY             height of Grid
     * @param bias             Biases for ants on directions
     * @param updatesPerCircle how many iterations are made before visual update
     */
    public Simulation(int cellSize, int maxX, int maxY, int[] bias, int updatesPerCircle) {
        this.cellSize = cellSize;
        this.updatesPerCircle = updatesPerCircle;

        //simulation parameters
        cd = new CodeDraw(maxX * cellSize, maxY * cellSize);
        cd.setAlwaysOnTop(true);
        grid = new Grid();

        //movement
        input = cd.getEventScanner();
        offset = new Vector(0, 0);
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
            updateOffset(input);
            drawWindow();
        }
    }

    /**
     * Handles the logic of drawing the entities on the window.
     */
    private void drawWindow() {
        cd.setColor(Color.gray);
        cd.fillRectangle(0, 0, cd.getWidth(), cd.getHeight());

        grid.queue().forEach(entry -> {
            Vector position = entry.getPosition().add(this.offset);
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
     * Redraws the visible part of the map.
     * Mouse-Arrays
     */
    //STYLE: OOP???
    private void updateOffset(EventScanner input) {
        int x = 0, y = 0;
        int offsetByStep = 20;
        while (input.hasEventNow() && !input.hasKeyDownEvent()) {
            input.nextEvent();
        }

        while (input.hasEventNow() && input.hasKeyDownEvent()) {
            Key key = input.nextKeyDownEvent().getKey();
            switch (key) {
                case W, UP -> y++;
                case S, DOWN -> y--;
                case D, RIGHT -> x--;
                case A, LEFT -> x++;
                case PLUS -> cellSize++;
                case MINUS -> {
                    if (cellSize > 1) {
                        cellSize--;
                    }
                }
            }
            offset = offset.add(new Vector(x * offsetByStep, y * offsetByStep));
            drawWindow();
        }
    }
}
