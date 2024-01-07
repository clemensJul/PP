import java.util.Objects;

/**
 * Represents a position composed of two vectors: pos1 and pos2.
 */
public class Position {

    private final MyVector pos1, pos2;

    /**
     * Constructs a Position object with two vectors.
     *
     * @param firstPos The head position for ants.
     * @param secPos   The body position for ants.
     */
    public Position(MyVector firstPos, MyVector secPos) {
        this.pos1 = firstPos;
        this.pos2 = secPos;
    }

    /**
     * Retrieves the first position vector.
     *
     * @return The first position vector.
     */
    public MyVector getPos1() {
        return pos1;
    }

    /**
     * Retrieves the second position vector.
     *
     * @return The second position vector.
     */
    public MyVector getPos2() {
        return pos2;
    }

    /**
     * Checks if two Position objects are equal by comparing their vectors.
     *
     * @param o The object to compare with this Position.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return pos1.equals(position.pos1) && pos2.equals(position.pos2)
                || pos2.equals(position.pos1) && pos1.equals(position.pos2);
    }

    /**
     * Generates a hash code for the Position object based on its vectors.
     *
     * @return The hash code of the Position object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(pos1, pos2);
    }

    /**
     * Determines the direction between the two vectors (pos1 and pos2).
     *
     * @return The direction based on the vectors.
     */
    public Direction getDirection() {
        int x = pos1.getX() - pos2.getX();
        int y = pos1.getY() - pos2.getY();

        if (y > 0) {
            return Direction.DOWN;
        }

        if (x > 0) {
            return Direction.RIGHT;
        }

        if (y < 0) {
            return Direction.UP;
        }

        return Direction.LEFT;
    }

    /**
     * Provides a string representation of the Position object.
     *
     * @return A string representing the Position object.
     */
    @Override
    public String toString() {
        return "Position{" +
                "pos1=" + pos1 +
                ", pos2=" + pos2 +
                '}';
    }
}
