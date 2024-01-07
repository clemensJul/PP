import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileWriter {
    public final static String OUTPUT_FILEPATH = "test.out";
    public final static String DEBUG_FILEPATH = "debug.txt";

    public synchronized static void deleteFileIfExists(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public synchronized static void writeToFile(String outputFilePath, String string) {
        try {
            java.io.FileWriter fileWriter = new java.io.FileWriter(outputFilePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(string);
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String exceptionToString(Exception exception) {
        StringBuilder s = new StringBuilder();
        Arrays.stream(exception.getStackTrace()).toList().forEach(el -> s.append(el.toString()).append("\n"));
        s.append(exception).append("\n");
        return s.toString();
    }
}
