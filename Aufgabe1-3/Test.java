// Modularisierungseinheit: Modul

import java.lang.reflect.Array;
import java.util.Arrays;

//starts the simulation
public class Test {

    /**
     * Initializes some simulations.
     */
    public static void main(String[] args) {
        //simulation parameters
        int numberOfSims = 1;
        int cellSize = 3;
        int maxX = 250;
        int maxY = 200;
        int[] bias = new int[]{1, 3, 15, 3, 1};
        int updatesPerCircle = 1;

        for (int i = 0; i < numberOfSims; i++) {
            try {
                Simulation sim = new Simulation(cellSize, maxX, maxY, bias, updatesPerCircle);
                sim.start();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                System.out.println("closed window");
            }
        }
    }
}
//hw1
// Clemens worked on vectors and the grid
// Raphael did the ant logic

//hw2
//Clemens revamp of ants logic
//Raphael revamp of grid logic
//most of the classes are object-oriented. A notable exception is the Vector class, having many static functions
