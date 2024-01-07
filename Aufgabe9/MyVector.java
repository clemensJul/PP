import java.util.Objects;

public class MyVector {
    private final int x, y;

    public MyVector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Adds two vectors and returns a new vector representing their sum.
     *
     * @param one The first vector.
     * @param two The second vector.
     * @return A new vector representing the sum of the input vectors.
     */
    public static MyVector add(MyVector one, MyVector two) {
        return new MyVector(one.x + two.x, one.y + two.y);
    }

    /**
     * Subtracts one vector from another and returns a new vector representing the result.
     *
     * @param one The vector from which to subtract.
     * @param two The vector to subtract.
     * @return A new vector representing the result of subtracting the second vector from the first.
     */
    public static MyVector substract(MyVector one, MyVector two) {
        return new MyVector(one.x - two.x, one.y - two.y);
    }


    /**
     * Generates an orthogonal vector based on the input vector.
     *
     * @param vector    The input vector.
     * @param clockwise If true, generates a clockwise orthogonal vector; otherwise, counterclockwise.
     * @return A new vector orthogonal to the input vector.
     */
    public static MyVector orthogonalVector(MyVector vector, boolean clockwise) {
        if (clockwise) return new MyVector(vector.y, -vector.x);
        return new MyVector(-vector.y, vector.x);
    }

    /**
     * Calculates the direction vector pointing from vector2 to vector1.
     *
     * @param vector1 The starting vector.
     * @param vector2 The ending vector.
     * @return A vector representing the direction from vector2 to vector1.
     */
    public static MyVector getLookingDirection(MyVector vector1, MyVector vector2) {
        return substract(vector1, vector2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyVector myVector = (MyVector) o;
        return x == myVector.x && y == myVector.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "MyVector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
