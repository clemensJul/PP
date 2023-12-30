import java.util.Objects;

public class Position {
    private final MyVector pos1, pos2;

    public Position(MyVector firstPos, MyVector secPos) {
        // head position for ants
        this.pos1 = firstPos;
        // body position for ants
        this.pos2 = secPos;
    }

    public MyVector getPos1() {
        return pos1;
    }

    public MyVector getPos2() {
        return pos2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return pos1.equals(position.pos1) && pos2.equals(position.pos2)
                || pos2.equals(position.pos1) && pos1.equals(position.pos2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos1, pos2);
    }

    public Direction getDirection() {
        int x = pos2.getX() - pos1.getX();
        int y = pos2.getY() - pos1.getY();

        if (y > 0) {
            return Direction.UP;
        }

        if (x > 0) {
            return Direction.RIGHT;
        }

        if (y < 0) {
            return Direction.DOWN;
        }

        return Direction.LEFT;
    }

}
