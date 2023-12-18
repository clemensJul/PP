public class Test {
    public static void main(String[] args) {
        // procedual part
        // simulation parameters
        int vertexCount = 75;
        int maxX = 200;
        int maxY = 200;
        // parameters are given by a record
        ACSCalculatorOptions options = new ACSCalculatorOptions(1000, 25, 0.9, 1, 2, 0.1);

        // generate new graph
        // using objects to represent graphs - can be thought of as containers
        Graph graph = new Graph();
        for (int i = 0; i < vertexCount; i++) {
            Vertex v = new Vertex(Math.random() * maxX, Math.random() * maxY);
            graph.addVertex(v);
        }

        // timer for testing
        long start = System.nanoTime();

        // functional calculation of the shortest path - method can return the optimal path but
        // also prints it into the console
        ACSCalculator.calculate(graph, options);
        long end = System.nanoTime();
        System.out.println("calculation took " + ((end - start) / 1_000_000_000) + " seconds");
    }

    // Aufgabenaufteilung:
    // Clemens: Graph
    // Raphael: Vertex, Edges
    // wir beide haben individuell probiert den Algorithmus für uns selbst zu implementieren und dann die für uns bessere Lösung der beiden ausgewählt.
}
