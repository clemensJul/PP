import codedraw.CodeDraw;

import java.awt.*;
import java.util.List;

public class Draw {
    private static CodeDraw window;
    private static int cellSize;
    private static boolean alreadySet = false;

    public static void setOptions(int cellSize, int width, int height) {
        if (alreadySet) {
            return;
        }
        window = new CodeDraw(width*cellSize,height*cellSize);
        alreadySet = true;
    }

    public static CodeDraw getWindow() {
        return window;
    }

    public static void initializeGraph(Graph graph){
        if (!alreadySet) {
            return;
        }
        window.setColor(Color.gray);
        window.fillRectangle(0, 0, window.getWidth(), window.getHeight());

        graph.getVertices().forEach(vertex ->
                graph.getNeighbors(vertex).forEach(neighbor -> {
                    double dist = Math.floor(graph.getDistance(vertex, neighbor));
                    window.setColor(new Color(0, 255, 0, 30));
                    window.drawLine(vertex.getX() * cellSize, vertex.getY() * cellSize, neighbor.getX() * cellSize, neighbor.getY() * cellSize);

                    window.setColor(Color.BLACK);
                    window.drawText(vertex.getX() * cellSize + (neighbor.getX() - vertex.getX()) * cellSize / 2, vertex.getY() * cellSize + (neighbor.getY() - vertex.getY()) * cellSize / 2, String.valueOf(dist));
                })
        );
        window.show();
    }

    public static void drawResult(List<Vertex> result){
        if (!alreadySet) {
            return;
        }
        window.setColor(Color.RED);
        Vertex prev = result.getFirst();
        for (int i = 1; i < result.size(); i++) {
            Vertex current = result.get(i);
            window.drawLine(prev.getX() * cellSize, prev.getY() * cellSize, current.getX() * cellSize, current.getY() * cellSize);
            prev = current;
        }
        window.drawLine(prev.getX() * cellSize, prev.getY() * cellSize, result.get(0).getX() * cellSize, result.get(0).getY() * cellSize);
        window.show();
    }

    public static void drawIteration(List<Vertex> result) {
        if (!alreadySet) {
            return;
        }
//        cd.setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
        window.setColor(Color.BLACK);
        int cellSize = 5;
        Vertex prev = result.getFirst();
        for (int i = 1; i < result.size(); i++) {
            Vertex current = result.get(i);
            window.drawLine(prev.getX() * cellSize, prev.getY() * cellSize, current.getX() * cellSize, current.getY() * cellSize);
            prev = current;
        }
        window.drawLine(prev.getX() * cellSize, prev.getY() * cellSize, result.getFirst().getX() * cellSize, result.getFirst().getY() * cellSize);
        window.show();
    }
}
