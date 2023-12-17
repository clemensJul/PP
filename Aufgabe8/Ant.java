import java.util.ArrayList;

/**
 * Represents an Ant that traverses vertices in a graph.
 */
public class Ant {
    private final ArrayList<Vertex> alreadyVisitedCities = new ArrayList<>();
    private Vertex currentVertex;

    /**
     * Constructor to initialize the Ant with a starting vertex.
     *
     * @param spawnVertex The starting vertex for the ant.
     */
    public Ant(Vertex spawnVertex) {
        this.alreadyVisitedCities.add(spawnVertex);
        this.currentVertex = spawnVertex;
    }

    /**
     * Retrieves the starting vertex the ant spawned from.
     *
     * @return The starting vertex of the ant.
     */
    public Vertex getStartingVertex() {
        return alreadyVisitedCities.get(0);
    }

    /**
     * Visits a new vertex and updates the ant's current position.
     *
     * @param vertex The vertex to be visited by the ant.
     */
    public void visitVertex(Vertex vertex){
        alreadyVisitedCities.add(vertex);
        currentVertex = vertex;
    }

    /**
     * Retrieves the current vertex where the ant is positioned.
     *
     * @return The current vertex of the ant.
     */
    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    /**
     * Retrieves the list of vertices already visited by the ant.
     *
     * @return The list of visited vertices by the ant.
     */
    public ArrayList<Vertex> getAlreadyVisitedCities() {
        return alreadyVisitedCities;
    }
}
