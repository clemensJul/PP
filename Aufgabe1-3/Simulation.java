import codedraw.*;

import java.awt.*;
import java.awt.event.MouseEvent;
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
     * @param cellSize         cellSize for Grid
     * @param maxX             width of Grid
     * @param maxY             height of Grid
     * @param bias             Biases for ants on directions
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
        offset = new Vector(0, 0);
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

            updateOffset(input);
            drawWindow();
        }
    }

    private void drawWindow() {
        cd.setColor(Color.gray);
        cd.fillRectangle(0, 0, maxX * cellSize, maxY * cellSize);

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

    private void updateOffSet(EventScanner input){
        if (input.hasEventNow()){

            MouseDownEvent down = input.nextMouseDownEvent();
        }

    }

    //STYLE: 
    private void updateOffset(EventScanner input) {
        int x, y;
        while (input.hasEventNow() && !input.hasMouseDownEvent()) {
            input.nextEvent();
        }

        if (input.hasEventNow() && input.hasMouseDownEvent()) {
            MouseDownEvent down = input.nextMouseDownEvent();
            x = down.getX();
            y = down.getY();
            Vector oldOffset = new Vector(offset.getX(),offset.getY());
            int newX, newY;
            while (input.hasMouseMoveEvent()) {
                MouseMoveEvent move = input.nextMouseMoveEvent();
                if(input.hasMouseUpEvent()) {
                    return;
                }

                newX = move.getX();
                newY = move.getY();
                offset = oldOffset.add(new Vector(newX - x, newY - y));
                drawWindow();
            }

        }

//            int oldX = event.getX();''
//            int oldY = event.getY();
//            return new Vector(oldX,oldY);
    }
        /*
            while (input.hasMouseClickEvent() && input.h) {
                Event e = input.nextMouseMoveEvent();
                e.
                System.out.println(e.getX() + " - " + e.getY());

//            Key key = input.nextKeyDownEvent().getKey();
//            switch (key){
//                case W,UP -> {
//                    y++;
//                }
//                case S,DOWN -> {
//                    y--;
//                }
//                case D,RIGHT -> {
//                    x--;
//                }
//                case A,LEFT -> {
//                    x++;
//                }
//            }
            }
        return new Vector(x * offsetByStep, y * offsetByStep);

         */
}
