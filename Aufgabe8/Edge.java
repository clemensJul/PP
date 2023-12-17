/**
 * Represents an edge between vertices in a graph.
 *
 * @param distance Distance value of the edge
 */
public record Edge(double distance) {
    /**
     * Constructor to initialize an edge with a specified distance.
     *
     * @param distance The distance value of the edge.
     */
    public Edge {
    }

    /**
     * Retrieves the distance value of the edge, rounded to the nearest integer.
     *
     * @return The rounded distance value of the edge.
     */
    @Override
    public double distance() {
        return Math.round(distance);
    }
}
