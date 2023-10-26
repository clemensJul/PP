// Modularisierungseinheit: Modul

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
        float[] bias = new float[]{0.01f, 0.05f, 1f, 0.05f, 0.01f};
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
