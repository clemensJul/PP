import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        // start processes
        int NUMBER_OF_SIMS = 3;
        // width - height - numberOfAnts
        int[][] params = {
                { 40, 40, 100},
                { 40, 40, 100},
                { 40, 40, 100},
        };
        for (int i = 0; i < NUMBER_OF_SIMS; i++) {
            try {
                Runtime.getRuntime().exec(String.format("java -cp bin Arena %d %d %d", params[i][0], params[i][1], params[i][2]));
            } catch (IOException ignored) {}
        }
    }
}
