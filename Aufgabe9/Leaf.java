import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Leaf implements Serializable  {
    private final float size;
    private final int antId;

    public Leaf(float size, Ant ant) {
        this.size = size;
        this.antId = ant.getId();
    }

    public float getSize() {
        return size;
    }

    public int getAntId() {
        return antId;
    }

    @Override
    public String toString() {
        return "Leaf{" +
                "size=" + BigDecimal.valueOf(size).setScale(3, RoundingMode.HALF_UP) +
                "} brought back by Ant:"+getAntId();
    }
}
