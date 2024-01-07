import java.io.File;
import java.io.IOException;

/**
 * Class to test and run multiple simulations of the Arena with different parameters.
 */
public class Test {
    /**
     * Main method to initiate multiple simulations with varying parameters.
     *
     * @param args Command-line arguments (not used in this context).
     */
    public static void main(String[] args) {
        // width - height - numberOfAnts
        int[][] params = {
                {80, 40, 100},
                {40, 20, 10},
                {40, 40, 30},
        };

        // Delete test.out before starting animation
        FileWriter.deleteFileIfExists(new File("test.out"));
        for (int i = 0; i < params.length; i++) {
            FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, "Starting " + (i + 1) + ". simulation:");
            try {
                Process process = Runtime.getRuntime().exec(String.format("java -cp bin Arena %d %d %d", params[i][0], params[i][1], params[i][2]));
                process.waitFor();
                System.out.println((i + 1) + ". Process finished");
                for (int j = 0; j < 5; j++) {
                    if (j % 2 == 0) {
                        FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, "|".repeat(40));
                    } else {
                        FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, "-".repeat(40));
                    }
                }

            } catch (IOException ignored) {
                // Handle IOException if needed
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // Arbeitsteilung:
    // Ants logic and Visualisierung Clemens
    // Interprozesskommunikation Raphael
    // Parallelisierung beide
}
