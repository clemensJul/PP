public class Test {
    public static void main(String[] args) {
        // simulation parameters
        int vertexCount = 30;
        int maxX = 200;
        int maxY = 200;
        ACSCalculatorOptions options = new ACSCalculatorOptions(1000, 25, 0.9, 1, 2, 0.1);

        // generate new graph
        Graph graph = new Graph();
        for (int i = 0; i < vertexCount; i++) {
            Vertex v = new Vertex(Math.random() * maxX, Math.random() * maxY);
            graph.addVertex(v);
        }

        long start = System.nanoTime();
        ACSCalculator.calculate(graph, options);
        long end = System.nanoTime();
        System.out.println("calculation took " + ((end - start) / 1_000_000_000) + " seconds");
    }
}
