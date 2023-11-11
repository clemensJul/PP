// Modularisierungseinheit: Klasse
// genutzt für die Datenkapselung der Position

import java.util.Objects;

// class that represents vectors - vectors are used for positions and directions
// the programm paradigma is a mix between oop and many static functions acting via functional programming

// STYLE: Klasse ist eine Mischung aus OOP und funktionaler Programmierung
// es gibt Methoden die static sind und auch welche, die auf der Instanz arbeiten.
public class Vector {

    // X-coordinate of Vector
    private final int y;

    // Y-coordinate of Vector
    private final int x;

    /**
     * Returns a Vector object that represents the current position of the ant.
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates orthogonal vectors of a given vector
     *
     * @param vector Vector to calculate the orthogonal from, must be != null.
     * @param isLeft true means the top orthogonal, false is the bottom orthogonal vector
     * @return Orthogonal vector ofthe given Vector
     */
    public static Vector orthogonalVector(Vector vector, boolean isLeft) {
        if (isLeft) {
            return new Vector(-vector.y, vector.x);
        }

        return new Vector(vector.y, -vector.x);
    }

    //ERROR : der orthogonal Vektor sollte nicht übergeben, sondern direkt berechnet werden
    // derzeit könnte jeder beliebige Vektor übergeben werden

    /**
     * Calculates the vector between an orthogonal vector and its main vector
     *
     * @param orthogonal needs to be the orthogonal vector of position and != null
     * @param position   is the origional vector, must be != null
     * @return vector between orthogonal vector and this instance, which is != null
     */
    public static Vector sharpVector(Vector position, Vector orthogonal) {
        int x = position.x + orthogonal.x;
        int y = position.y + orthogonal.y;

        x = Math.max(-1, Math.min(1, x));
        y = Math.max(-1, Math.min(1, y));

        return new Vector(x, y);
    }

    /**
     * Calculates dot-product of two vectors
     *
     * @param a First Vector for dot product calculation, must be != null
     * @param b Second Vector for dot product calculation, must be != null
     * @return Dot-product of the given Vectors
     */
    public static int dotProduct(Vector a, Vector b) {
        return a.x * b.x + a.y * b.y;
    }

    /**
     * Normalizes a Vector.
     *
     * @param position Vector to normalize, must be != null.
     * @return Normalized Vector
     */
    public Vector normalizedVector(Vector position) {
        int dx = this.x - position.x;
        int dy = this.y - position.y;
        double length = Math.sqrt(dx * dx + dy * dx);
        if (length != 0) {
            int normalizedX = (int) Math.round(dx / length);
            int normalizedY = (int) Math.round(dy / length);
            return new Vector(normalizedX, normalizedY);
        } else {
            // Handle the case where the vector has zero length (avoid division by zero).
            return new Vector(0, 0); // Or you can choose another appropriate default value.
        }
    }

    /**
     * Creates a random direction Vector that will always point somewhere
     *
     * @return Random direction Vector, which is != null
     */
    public static Vector RandomDirection() {
        int x = 0;
        int y = 0;
        while (x == 0 && y == 0) {
            x = (int) Math.round(Math.random() * 2 - 1);
            y = (int) Math.round(Math.random() * 2 - 1);
        }
        return new Vector(x, y);
    }

    /**
     * Returns a new Vector with this Vector´s inverted coordinates.
     *
     * @return Inversed Vector, != null
     */
    public Vector invert() {
        return new Vector(-x, -y);
    }

    /**
     * Add a vector to this instance coordinate wise.
     *
     * @param vector Vector to add, must be != null
     * @return Vector with added coordinates.
     */
    public Vector add(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y);
    }

    /**
     * Subtract a vector from this instance coordinate wise.
     *
     * @param vector Vector to substract, must be != null
     * @return Vector with substracted coordinates.
     */
    public Vector subtract(Vector vector) {
        return new Vector(this.x - vector.x, this.y - vector.y);
    }

    /**
     * Multiplies a scalar with this instance coordinate wise.
     *
     * @param scalar Scalar to multiply
     * @return Vector with multiplied coordinates.
     */
    public Vector multiplyWithScalar(int scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    /**
     * Divides a scalar with this instance coordinate wise.
     *
     * @param scalar Scalar to divide
     * @return Vector with adjusted coordinates.
     */
    public Vector divideWithScalar(int scalar) {
        return new Vector(this.x / scalar, this.y / scalar);
    }

    /**
     * X coordinate of vector
     *
     * @return X coordinate of vector
     */
    public int getX() {
        return x;
    }

    /**
     * Y coordinate of vector
     *
     * @return Y coordinate of vector
     */
    public int getY() {
        return y;
    }

    /**
     * Converts Vecot toString
     *
     * @return String repentation of Vector
     */
    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Checks if a given object is equal to this.
     *
     * @return Vectors are equal if each x and each y is equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return x == vector.x && y == vector.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
