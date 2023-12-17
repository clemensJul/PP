/**
 * Represents a vertex in a graph with a specific position in 2D space.
 */
public class Vertex {
    private final double x;
    private final double y;

    private static int ID_COUNTER = 0;
    private final int index;

    /**
     * Constructs a vertex with specified x and y coordinates.
     *
     * @param x The x-coordinate of the vertex.
     * @param y The y-coordinate of the vertex.
     */
    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
        this.index = ID_COUNTER++;
    }

    /**
     * Retrieves the x-coordinate of the vertex.
     *
     * @return The x-coordinate of the vertex.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Retrieves the y-coordinate of the vertex.
     *
     * @return The y-coordinate of the vertex.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Provides a string representation of the vertex.
     *
     * @return A string representation of the vertex with its index and position.
     */
    @Override
    public String toString() {
        return "index: " + index + " position:(" + Math.round(x) + ", " + Math.round(y) + ")";
    }
}
