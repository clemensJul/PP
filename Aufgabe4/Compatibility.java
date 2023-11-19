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
     * Sets the time to the specified value smaller than maxTime
     *
     * @param time newTime to set, must be >= 0 and should be one of {@link ETime} - if time > maxTime current time will be set to maxTime
     */
    public void setTime(int time) {
        if(time > maxTime) {
            this.time = maxTime;
            return;
        }
        this.time = time;
    }

    /**
     * Returns  the minSize always >= 0 && minSize <= maxSize() in mm
     * @return max size of Compatibility.
     */
    public int minSize() {
        return size[0];
    }

    /**
     * Returns  the maxSize always >= 0 && maxSize >= minSize() in mm
     * @return max size of Compatibility.
     */
    public int maxSize() {
        return size[1];
    }

    /**
     * Returns  the minHumidity always >= 0 && minHumidity <= maxHumidity() in mm
     * @return max size of Compatibility.
     */
    public int minHumidity() {
        return humidity[0];
    }

    /**
     * Returns  the maxHumidity always >= 0 && maxHumidity >= minHumidity() in mm
     * @return max size of Compatibility.
     */
    public int maxHumidity() {
        return humidity[1];
    }

    /**
     * Returns  the minHumidity always >= 0 && minHumidity <= maxHumidity() in mm
     * @return max size of Compatibility.
     */
    public int minTemperature() {
        return temperature[0];
    }

    /**
     * Returns  the maxHumidity always >= 0 && maxHumidity >= minHumidity() in mm
     * @return max size of Compatibility.
     */
    public int maxTemperature() {
        return temperature[1];
    }

    /**
     * Returns remaining time of the Compatibility object.
     * time is always >= 0
     *
     * @return time of Compatibility.
     */
    public int time() {
        return time;
    }

    /**
     * Returns remaining time of the Compatibility object.
     * maxTime is always >= 0
     *
     * @return time of Compatibility.
     */
    public int maxTime() {
        return maxTime;
    }

    /**
     * Returns a new Compatibility object with the new ranges.
     *
     * @throws CompatibilityException, if the ranges of the two objects don´t overlap
     * @param compareTo Compatibility object to compare with, must be != null
     * @return new Compatibility object with new ranges.
     */
    public Compatibility compatible(Compatibility compareTo) throws CompatibilityException {
        int[] size = clamp(this.size, compareTo.size);
        int[] humidity = clamp(this.humidity, compareTo.humidity);
        int[] temperature = clamp(this.temperature, compareTo.temperature);
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
