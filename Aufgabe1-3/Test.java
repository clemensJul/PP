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
        int[] bias = new int[]{1,3,15,3,1};
        int updatesPerCircle = 1;

        boolean areNotClosed = true;

        Simulation[] sims = new Simulation[numberOfSims];
        for (int i = 0; i < sims.length; i++) {
            sims[i] = new Simulation(cellSize, maxX, maxY, bias, updatesPerCircle);
        }

        while (areNotClosed) {
            boolean isClosed = true;
            for (Simulation sim : sims) {
                if (!sim.isClosed()) isClosed = false;
                sim.run();
            }
            areNotClosed = !isClosed;
        }
    }

}

// Jessica was working on the entire simulation set up
// Clemens worked on vectors and the grid
// Raphael did the ant logic
