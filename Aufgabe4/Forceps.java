import java.util.Objects;

public class Forceps implements Instrument {
    private final int quality;

    public Forceps(int quality) {
        this.quality = quality;
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
        int[] size = new int[2];
        size[0] = quality;
        size[1] = 2 + (quality * 2);

        int[] temperature = new int[2];
        temperature[1] = Integer.MAX_VALUE;

        int[] humidity = new int[2];
        humidity[1] = Integer.MAX_VALUE;
        return new Compatibility(size, temperature, humidity);
    }
}
