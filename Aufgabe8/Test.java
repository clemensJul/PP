import java.util.List;

public class Test {
    public static void main(String[] args) {
        //simulation parameters
        int cellSize = 5;
        int vertexCount = 60;
        int maxX = 200;
        int maxY = 200;
        ACSCalculatorOptions options = new ACSCalculatorOptions(1000, 25, 0.9, 1, 2, 0.1);
        Draw.setOptions(cellSize,maxX,maxY);

        // generate new graph
        Graph graph = new Graph();
        for (int i = 0; i < vertexCount; i++) {
            Vertex v = new Vertex(Math.random() * maxX, Math.random() * maxY);
            graph.addVertex(v);
        }

        List<Vertex> result = ACSCalculator.calculate(graph, options);
        // print results
        StringBuilder output = new StringBuilder();
        output.append("Best way is: ");

        Vertex oldCity = result.getFirst();
        double distance = 0;
        for (int j = 1; j < result.size(); j++) {
            Vertex currentCity = result.get(j);
            double distanceIteration = graph.getDistance(oldCity, currentCity);
            output.append("from ").append(oldCity).append("\tto ").append(currentCity).append(":").append(distanceIteration).append("m\n");
            oldCity = currentCity;
            distance += distanceIteration;
        }
        output.append("with a overall distance of: ").append(distance).append("m");

        System.out.println(output);

        // print used params
        System.out.println(options);
    }
}
