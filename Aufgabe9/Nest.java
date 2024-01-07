import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final static String outputFilePath = "test.out";
    private final static String debugFilePath = "debug.txt";

    private static void deleteFileIfExists(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public static void main(String[] args) {
        List<Leaf> leafs = new ArrayList<>();
        deleteFileIfExists(new File(outputFilePath));
        deleteFileIfExists(new File(debugFilePath));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> leafs.forEach(leaf -> writeToFile(outputFilePath, leaf.toString()))));

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
                    writeToFile(debugFilePath, exceptionToString(e));
                }
            }
        } catch (IOException | RuntimeException e) {
            writeToFile(debugFilePath, exceptionToString(e));
        }
    }

    private static String exceptionToString(Exception exception) {
        StringBuilder s = new StringBuilder();
        Arrays.stream(exception.getStackTrace()).toList().forEach(el -> s.append(el.toString()).append("\n"));
        s.append(exception).append("\n");
        return s.toString();
    }

    private static void writeToFile(String outputFilePath, String string) {
        try {
            FileWriter fileWriter = new FileWriter(outputFilePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(string);
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
