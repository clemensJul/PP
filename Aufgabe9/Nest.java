import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Nest extends Tile {
    public Nest(Position position) {
        super(position, false);
    }

    @Override
    public char draw() {
        return 'O';
    }

    public static void main(String[] args) {
        List<Leaf> leafs = new ArrayList<>();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> leafs.forEach(leaf -> FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, leaf.toString()))));

        try (ObjectInputStream inputStream = new ObjectInputStream(System.in)) {
            // Empfange Daten von ProcessA
            while (true) {
                try {
                    Object receivedObject = inputStream.readObject();
                    if (receivedObject instanceof Leaf receivedLeaf) {
                        leafs.add(receivedLeaf);
                    }
                } catch (EOFException ignored) {
                } catch (Exception e) {
                    FileWriter.writeToFile(FileWriter.DEBUG_FILEPATH, FileWriter.exceptionToString(e));
                }
            }
        } catch (IOException | RuntimeException e) {
            FileWriter.writeToFile(FileWriter.DEBUG_FILEPATH, FileWriter.exceptionToString(e));
        }
    }
}
