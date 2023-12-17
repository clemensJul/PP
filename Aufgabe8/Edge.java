/**
 * Represents an edge between vertices in a graph.
 */
public class Edge {
    private final double distance; // Distance value of the edge

    /**
     * Constructor to initialize an edge with a specified distance.
     *
     * @param distance The distance value of the edge.
     */
    public Edge(double distance) {
        this.distance = distance;
    }

    /**
     * Retrieves the distance value of the edge, rounded to the nearest integer.
     *
     * @return The rounded distance value of the edge.
     */
    public double getDistance() {
        return Math.round(distance);
    }
}
