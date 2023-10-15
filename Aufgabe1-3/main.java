public class main {

    public static void main(String[] args) {

        //simulation parameters
        int numberOfSims = 1;
        int cellSize = 3;
        int maxX = 250;
        int maxY = 200;
        int numberOfAnts = 100;
        float[] bias =  new float[]{0.01f,0.05f,1f,0.05f,0.01f};
        int updatesPerCircle = 1;

        boolean areNotClosed = true;

        Simulation[] sims = new Simulation[numberOfSims];

        for (int i = 0; i < sims.length; i++) {
            sims[i] = new Simulation(cellSize,maxX,maxY,numberOfAnts,bias,updatesPerCircle);
        }

        while (areNotClosed){
            boolean isClosed = true;
            for (int i = 0; i < sims.length; i++) {
                if (!sims[i].isClosed()) isClosed = false;
                sims[i].run();
            }
            areNotClosed = !isClosed;
        }
    }
}
