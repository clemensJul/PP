public class Edge {
    private final double distance;
    private double pheromone;

    public Edge(double distance) {
        this.distance = distance;
        this.pheromone = 0;
    }

    public double getDistance() {
        return distance;
    }

    public double getPheromone() {
        return pheromone;
    }

    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }
}
