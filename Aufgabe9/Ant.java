import java.util.LinkedList;
import java.util.List;

public class Ant implements Runnable {
    private Position position;
    private Leaf leaf;

    public Ant(Position position) {
        this.position = position;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // try acquiring mutex of all possible neighbors
            List<Tile> neighbors = Arena.getTiles(this.position);
            List<Tile> acquiredNeighbors = new LinkedList<>();
            try {
                for (Tile neighbor : neighbors) {
                    neighbor.getMutex().acquire();
                    acquiredNeighbors.add(neighbor);
                }
                // business logic

                Tile randomNeighbor = neighbors.get((int)(neighbors.size() * Math.random()));

                // increase tile´s pheromone level if carrying leaf
                if(leaf != null) {
                    randomNeighbor.increasePheromoneLevel();
                }

                // collect leaf
                if(leaf == null && randomNeighbor.isHasLeaf()) {
                    leaf = new Leaf(0.04f);
                }

                // bring back leaf
                if(leaf != null && randomNeighbor instanceof Nest) {
                    leaf = null;
                    // send to nest process

                }

                // update position
                // TODO: find correct direction
                this.position = new Position(randomNeighbor.getPosition().getFirstVector(), new MyVector(0,1));


                // release neighbors
                for (Tile neighbor : neighbors) {
                    neighbor.getMutex().release();
                }


                // sleep for 5-50 milsecs
                //(it is not busy waiting)
                Thread.sleep(5L + (long)(Math.random()*45));



            } catch (InterruptedException e) {
                // release all acquired mutexes
                for (Tile neighbor : acquiredNeighbors) {
                    neighbor.getMutex().release();
                }
            }
        }
    }

    public Position getPosition(){
        return position;
    }
}
