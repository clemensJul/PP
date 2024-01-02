import java.io.Serializable;

public class Leaf implements Serializable  {
    private final float size;

    public Leaf(float size) {
        this.size = size;
    }

    public float getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Leaf{" +
                "size=" + size +
                '}';
    }
}
