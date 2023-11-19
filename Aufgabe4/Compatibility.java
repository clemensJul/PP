import java.util.Arrays;

public class Compatibility {
    private final int[] size;
    private final int[] temperature;
    private final int[] humidity;

    private final int maxTime;
    private int time;

    /**
     * Sets the time to the specified value.
     *
     * @param size Size range, must have two values >= 0. first value <= second value
     * @param temperature Temperature range, must have two values >= 0. first value <= second value
     * @param humidity Humidity range, must have two values >= 0. first value <= second value
     * @param time Time, must be >= 0 and should be one of {@link ETime}
     */
    public Compatibility(int[] size, int[] temperature, int[] humidity, int time) {
        this.size = size;
        this.temperature = temperature;
        this.humidity = humidity;
        this.maxTime = time;
        this.time = time;
    }

    /**
     * Sets the time to the specified value.
     *
     * @param time newTime to set, must be >= 0 and should be one of {@link ETime}
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Returns the minSize and the maxSize in an array with exactly two values.
     * The first value represents the minSize, the second one the maxSize.
     * all values are >= 0 and the first value is <= the second one
     *
     * @return size range of Compatibility.
     */
    public int[] size() {
        return size;
    }

    /**
     * Returns the minHumidity and the maxHumidity in an array with exactly two values.
     * The first value represents the minHumidity, the second one the maxHumidity.
     * all values are >= 0 and the first value is <= the second one
     *
     * @return humidity range of Compatibility.
     */
    public int[] humidity() {
        return humidity;
    }

    /**
     * Returns the minTemperature and the maxTemperature in an array with exactly two values.
     * The first value represents the minTemperature, the second one the maxTemperature.
     * all values are >= 0 and the first value is <= the second one
     *
     * @return temperature range of Compatibility.
     */
    public int[] temperature() {
        return temperature;
    }

    /**
     * Returns remaining time of the Compatibility object.
     * time is always >= 0
     *
     * @return time of Compatibility.
     */
    public int time() {
        return 0;
    }

    /**
     * Returns remaining time of the Compatibility object.
     * maxTime is always >= 0
     *
     * @return time of Compatibility.
     */
    public int maxTime() {
        return 0;
    }

    /**
     * Returns a new Compatibility object with the new ranges.
     *
     * @throws CompatibilityException, if the ranges of the two objects don´t overlap
     * @param compareTo Compatibility object to compare with, must be != null
     * @return time of Compatibility.
     */
    public Compatibility compatible(Compatibility compareTo) throws CompatibilityException {
        int[] size = clamp(size(), compareTo.size());
        int[] humidity = clamp(humidity(), compareTo.humidity());
        int[] temperature = clamp(temperature(), compareTo.temperature());
        Compatibility compatibility = new Compatibility(size, temperature, humidity, Math.min(this.maxTime, compareTo.maxTime()));
        compatibility.setTime(Math.min(this.time, compareTo.time()));
        return compatibility;
    }

    /**
     * Clamps two ranges to a new range.
     *
     * @throws CompatibilityException if the ranges don´t overlap
     * @param compare1 first range. must be != null and have at least two values
     * @param compare2 second range. must be != null and have at least two values
     *
     * @return new overlapping range.
     */
    private int[] clamp(int[] compare1, int[] compare2) throws CompatibilityException{
        if(compare1[0] > compare2[1] || compare1[1] < compare2[0]) {
            throw new CompatibilityException("not compatible");
        }

        return new int[]{Math.max(compare1[0], compare2[0]), Math.min(compare1[1], compare2[1])};
    }


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
}
