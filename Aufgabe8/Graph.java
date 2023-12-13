import java.util.*;

public class Graph {
    private final Map<Vertex, Map<Vertex, Edge>> graphMap;

    public Graph() {
        this.graphMap = new HashMap<>();
    }

    // Methode zum Hinzufügen eines Vertex zum Graphen
    public void addVertex(Vertex vertex) {
        graphMap.put(vertex, new HashMap<>());

        //add connection to all vertices
        graphMap.keySet().forEach(vertex1 -> addEdge(vertex1, vertex));
    }

    // Methode zum Hinzufügen einer Kante zwischen zwei Vertices
    private void addEdge(Vertex source, Vertex destination) {
        if (source.equals(destination)) {
            return;
        }

        double x1 = source.getX();
        double y1 = source.getY();
        double x2 = destination.getX();
        double y2 = destination.getY();

        double dist = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));

        Edge edge = new Edge(dist);

        graphMap.get(destination).put(source,edge);
        graphMap.get(source).put(destination,edge);
    }

    // Methode zum Anzeigen der Nachbarn eines Vertex
    public Set<Vertex> getNeighbors(Vertex vertex) {
        return graphMap.get(vertex).keySet();
    }

    public double getDistance(Vertex vertex1, Vertex vertex2) {
        return graphMap.get(vertex1).get(vertex2).getDistance();
    }

    public Set<Vertex> getVertices() {
        return graphMap.keySet();
    }
}