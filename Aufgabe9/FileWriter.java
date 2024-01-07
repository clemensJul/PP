import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Utility class to handle file writing operations.
 */
public class FileWriter {
    public final static String OUTPUT_FILEPATH = "test.out"; // Default output file path
    public final static String DEBUG_FILEPATH = "debug.txt"; // Default debug file path

    /**
     * Deletes the specified file if it exists.
     *
     * @param file The file to be deleted.
     */
    public synchronized static void deleteFileIfExists(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Writes a string to a specified file path.
     *
     * @param outputFilePath The path to the file where the string should be written.
     *                       Should be one of the static file paths.
     * @param string         The string to be written to the file.
     */
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

    /**
     * Converts an exception to a string representation that can be written to a file.
     *
     * @param exception The exception that was thrown.
     * @return A string representation of the exception for writing to a file.
     */
    public static String exceptionToString(Exception exception) {
        StringBuilder s = new StringBuilder();
        Arrays.stream(exception.getStackTrace()).forEach(el -> s.append(el.toString()).append("\n"));
        s.append(exception).append("\n");
        return s.toString();
    }
}
