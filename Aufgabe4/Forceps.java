import java.util.Objects;

public class Forceps implements Instrument {
    private final int quality;
    private final Compatibility compatibility;

    /**
     * Initializes a Forceps.
     *
     * @param quality Quality, should be one of EUsage
     */
    public Forceps(int quality) {
        this.quality = quality;

        int[] size = new int[2];
        size[0] = quality;
        size[1] = 2 + (quality * 2);

        int[] temperature = new int[2];
        temperature[1] = Integer.MAX_VALUE;

        int[] humidity = new int[2];
        humidity[1] = Integer.MAX_VALUE;

        compatibility = new Compatibility(size, temperature, humidity, ETime.UNLIMITED);
    }

    @Override
    public int quality() {
        return quality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forceps forceps)) return false;
        return quality == forceps.quality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quality);
    }

    /**
     * @return
     */
    @Override
    public Compatibility compatibility() {
        return compatibility;
    }
}
