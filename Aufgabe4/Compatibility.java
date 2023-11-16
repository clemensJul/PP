import java.util.Arrays;

public class Compatibility {
    private final int[] size;
    private final int[] temperature;
    private final int[] humidity;

    //private final int maxTime;
    //private int time;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compatibility that)) return false;
        return Arrays.equals(size, that.size) && Arrays.equals(temperature, that.temperature) && Arrays.equals(humidity, that.humidity);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(size);
        result = 31 * result + Arrays.hashCode(temperature);
        result = 31 * result + Arrays.hashCode(humidity);
        return result;
    }

    public Compatibility(int[] size, int[] temperature, int[] humidity) {
        this.size = size;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    // hat genau zwei elemente
    public int[] size() {
        return size;
    }

    // hat genau zwei elemente
    public int[] humidity() {
        return humidity;
    }

    // hat genau zwei elemente
    public int[] temperature() {
        return temperature;
    }

    public int time() {
        return 0;
    }

    public int maxTime() {
        return 0;
    }

    // todo throw exception
    public Compatibility compatible(Compatibility compareTo) throws Exception {
        int[] size = clamp(size(), compareTo.size());
        int[] humidity = clamp(humidity(), compareTo.humidity());
        int[] temperature = clamp(temperature(), compareTo.temperature());
        return new Compatibility(size, temperature, humidity);
    }

    private int[] clamp(int[] compare1, int[] compare2) throws Exception{
        if(compare1[0] > compare2[1] || compare1[1] < compare2[0]) {
            throw new Exception("not compatible");
        }

        return new int[]{Math.max(compare1[0], compare2[0]), Math.min(compare1[1], compare2[1])};
    }
}
