public class Compatibility {
    private final int[] size;
    private final int[] temperature;
    private final int[] humidity;

    //private final int maxTime;
    //private int time;


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

    public int time(){
        return 0;
    }

    public int maxTime(){
        return 0;
    }

    // todo throw exception
    public Compatibility compatible(Compatibility compareTo){
        int[] size = clamp(size(), compareTo.size());
        int[] humidity = clamp(size(), compareTo.size());
        int[] temperature = clamp(size(), compareTo.size());
        return new Compatibility(size, humidity, temperature);
    }

    private int[] clamp (int[] compare1, int[] compare2) {
        return new int[] {Math.max(compare1[0], compare2[0]), Math.min(compare1[1], compare2[1])};
    }
}
