import codedraw.CodeDraw;

import java.awt.*;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        //simulation parameters
        int cellSize = 5;
        int vertexCount = 7;
        int maxX = 200;
        int maxY = 200;
        CodeDraw cd = new CodeDraw(maxX * cellSize, maxY * cellSize);

        // Beispiel für die Verwendung des Graphen mit eigenen Vertex-Objekten
        Graph graph = new Graph();

        for (int i = 0; i < vertexCount; i++) {
            Vertex v = new Vertex(Math.random()*maxX,Math.random()*maxY);
            graph.addVertex(v);
        }

        cd.setColor(Color.gray);
        cd.fillRectangle(0, 0, cd.getWidth(), cd.getHeight());

        graph.getVertices().forEach(vertex ->
            graph.getNeighbors(vertex).forEach(neighbor -> {
                double dist = Math.floor(graph.getDistance(vertex,neighbor));
                cd.setColor(Color.GREEN);
                cd.drawLine(vertex.getX() * cellSize, vertex.getY() * cellSize, neighbor.getX() * cellSize, neighbor.getY() * cellSize);

                cd.setColor(Color.black);
                cd.drawText(vertex.getX() * cellSize + (neighbor.getX() - vertex.getX())*cellSize/2, vertex.getY() * cellSize+(neighbor.getY() - vertex.getY())*cellSize/2, String.valueOf(dist));
            })
        );

        cd.setColor(Color.black);
        graph.getVertices().forEach(vertex -> {
            cd.fillRectangle(vertex.getX() * cellSize-cellSize/2, vertex.getY() * cellSize-cellSize/2, cellSize, cellSize);
        });

        cd.show();
        cd.setAlwaysOnTop(true);
    }
}
