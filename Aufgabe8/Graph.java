import java.util.*;

/**
 * Represents a graph consisting of vertices and edges.
 */
public class Graph {
    private final Map<Vertex, Map<Vertex, Edge>> graphMap;

    /**
     * Constructor to initialize the graph.
     */
    public Graph() {
        this.graphMap = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph along with connections to other vertices.
     *
     * @param vertex The vertex to be added to the graph.
     */
    public void addVertex(Vertex vertex) {
        graphMap.put(vertex, new HashMap<>());

        // Connect the newly added vertex to all existing vertices in the graph
        graphMap.keySet().forEach(vertex1 -> addEdge(vertex1, vertex));
    }

    /**
     * Adds an edge between two vertices in the graph.
     *
     * @param source      The source vertex of the edge.
     * @param destination The destination vertex of the edge.
     */
    private void addEdge(Vertex source, Vertex destination) {
        if (source.equals(destination)) {
            return;// Avoid connecting a vertex to itself
        }

        // Calculate the distance between the two vertices to create the edge
        double x1 = source.getX();
        double y1 = source.getY();
        double x2 = destination.getX();
        double y2 = destination.getY();
        double dist = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));

        Edge edge = new Edge(dist);

        // Add the edge between the source and destination vertices bidirectionally
        graphMap.get(destination).put(source, edge);
        graphMap.get(source).put(destination, edge);
    }

    /**
     * Retrieves the neighbors of a specific vertex in the graph.
     *
     * @param vertex The vertex for which neighbors are to be retrieved.
     * @return A set of neighboring vertices.
     */
    public Set<Vertex> getNeighbors(Vertex vertex) {
        return graphMap.get(vertex).keySet();
    }

    /**
     * Retrieves the distance between two vertices.
     *
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     * @return The distance between the two vertices.
     */
    public double getDistance(Vertex vertex1, Vertex vertex2) {
        return graphMap.get(vertex1).get(vertex2).distance();
    }

    /**
     * Retrieves all vertices in the graph.
     *
     * @return A set of all vertices in the graph.
     */
    public Set<Vertex> getVertices() {
        return graphMap.keySet();
    }

    /**
     * Retrieves the edge between two vertices.
     *
     * @param vertex1 The first vertex.
     * @param vertex2 The second vertex.
     * @return The edge between the two vertices.
     */
    public Edge getEdge(Vertex vertex1, Vertex vertex2) {
        return graphMap.get(vertex1).get(vertex2);
    }
}
