import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

public class Nest extends Tile {
    public Nest(Position position) {
        super(position, false);
    }

    @Override
    public char draw() {
        return 'O';
    }

    private static final List<Leaf> leafs = new LinkedList<>();

    public static void main(String[] args) {
        // Füge einen Shutdown-Hook hinzu, um auf SIGINT (Ctrl+C) zu reagieren
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Code, der beim Empfangen des SIGINT-Signals ausgeführt wird
            StringBuilder output = new StringBuilder();
            leafs.forEach(leaf -> output.append("Leaf: ").append(leaf.getSize()).append("\n"));
            writeToOutputFile(output.toString());
        }));

        try (ObjectInputStream inputStream = new ObjectInputStream(System.in)) {
            // Empfange Daten von ProcessA
            while (true) {
                Object receivedObject = inputStream.readObject();
                if (receivedObject instanceof Leaf receivedLeaf) {
                    leafs.add(receivedLeaf);
                }
            }
        } catch (IOException | ClassNotFoundException | RuntimeException e) {
            e.printStackTrace();
        }
    }

    private static void writeToOutputFile(String string) {
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(string);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
