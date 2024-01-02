import java.io.*;
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

    private static final List<Leaf> leafs = new LinkedList<>();

    private final static String outputFilePath = "output.txt";
    private final static String debugFilePath = "debug.txt";

    public static void main(String[] args) {
        File file = new File(outputFilePath);
        if (file.exists()) {
            file.delete();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(System.in)) {
            // Empfange Daten von ProcessA
            while (true) {
                try {
                    Object receivedObject = inputStream.readObject();
                    if (receivedObject instanceof Leaf receivedLeaf) {
                        leafs.add(receivedLeaf);
                        writeToFile(outputFilePath, receivedLeaf.toString());
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
