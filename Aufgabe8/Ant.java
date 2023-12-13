import java.util.ArrayList;

public class Ant {
    private final ArrayList<Vertex> alreadyVisitedCities = new ArrayList<>();
    private Vertex currentVertex;

    public Ant(Vertex spawnVertex) {
        this.alreadyVisitedCities.add(spawnVertex);
        this.currentVertex = spawnVertex;
    }

    public Vertex getStartingVertex() {
        return alreadyVisitedCities.get(0);
    }

    public void visitVertex(Vertex vertex){
        alreadyVisitedCities.add(vertex);
        currentVertex = vertex;
    }

    public Vertex getCurrentVertex() {
        return currentVertex;
    }

    public ArrayList<Vertex> getAlreadyVisitedCities() {
        return alreadyVisitedCities;
    }
}
