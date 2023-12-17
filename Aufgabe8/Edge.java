public class Edge {
    private final double distance;


    public Edge(double distance) {
        this.distance = distance;

    }

    public double getDistance() {
        return Math.round(distance);
    }
}
