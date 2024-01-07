import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        // width - height - numberOfAnts
        int[][] params = {
                { 80, 40, 100},
                { 40, 20, 10},
                { 40, 40, 30},
        };

        // delete test.out before starting animation
        FileWriter.deleteFileIfExists(new File("test.out"));
        for (int i = 0; i < params.length; i++) {
            FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, "Starting " + (i+1) + ". simulation:");
            try {
                Process process = Runtime.getRuntime().exec(String.format("java -cp bin Arena %d %d %d", params[i][0], params[i][1], params[i][2]));
                process.waitFor();
                System.out.println((i+1) + ". Process finished");
                for (int j = 0; j < 20; j++) {
                    if(j % 2 == 0) {
                        FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, "|".repeat(40));
                    } else {
                        FileWriter.writeToFile(FileWriter.OUTPUT_FILEPATH, "-".repeat(40));
                    }
                }

            } catch (IOException ignored) {
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
