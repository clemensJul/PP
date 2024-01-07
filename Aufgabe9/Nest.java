import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Nest extending the Tile class.
 * The Nest receives Leaf objects and writes their information to a file.
 */
public class Nest extends Tile {

    /**
     * Constructs a Nest object at a specified position.
     *
     * @param position The position of the Nest.
     */
    public Nest(Position position) {
        super(position, false);
    }

    /**
     * Draws the Nest, represented by the character 'O'.
     *
     * @return The character representing the Nest.
     */
    @Override
    public char draw() {
        return 'O';
    }

    /**
     * Main method to receive Leaf objects and write their information to a file.
     *
     * @param args Command-line arguments (not used in this context).
     */
    public static void main(String[] args) {
        List<Leaf> leafs = new ArrayList<>();

        // Write Leaf information to file on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> leafs.forEach(leaf -> FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, leaf.toString()))));

        try (ObjectInputStream inputStream = new ObjectInputStream(System.in)) {
            // Receive data from Arena-Process
            while (true) {
                try {
                    Object receivedObject = inputStream.readObject();
                    if (receivedObject instanceof Leaf receivedLeaf) {
                        leafs.add(receivedLeaf);
                    }
                } catch (EOFException ignored) {
                    // End of input stream reached
                } catch (Exception e) {
                    FileWriter.writeToFile(FileWriter.DEBUG_FILEPATH, FileWriter.exceptionToString(e));
                }
            }
        } catch (IOException | RuntimeException e) {
            FileWriter.writeToFile(FileWriter.DEBUG_FILEPATH, FileWriter.exceptionToString(e));
        }
    }
}
