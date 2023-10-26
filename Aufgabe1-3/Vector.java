// Modularisierungseinheit: Klasse
// genutzt für die Datenkapselung der Position

// class that represents vectors - vectors are used for positions and directions
public class Vector {
    private final int x;
    private final int y;
    private final long cords;

    /**
     * Returns a Vector object that represents the current position of the ant.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
        cords = ((long)x<< 32) | (y & 0xFFFFFFFFL);
    }

    /**
     * Calculates orthogonal vectors of a given vector
     *
     * @param vector Vector to calculate the orthogonal from
     * @param isLeft true means the top orthogonal, false is the bottom orthogonal vector
     *
     * @return Orthogonal vector
     */
    //
    public static Vector orthogonalVector(Vector vector, boolean isLeft) {
        if (isLeft) {
            return new Vector(-vector.y, vector.x);
        }

        return new Vector(vector.y, -vector.x);
    }
    /**
     * Creates a random direction vector that will always point somewhere
     *
     * @return Random direction vector
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
     * Calculates the vector between an orthogonal vector and its main vector
     *
     * @param orthogonal Orthogonal vector from vector
     *
     * @return vector between orthogonal vector and this instance
     */
    public static Vector sharpVector(Vector position,Vector orthogonal) {
        int x = position.x + orthogonal.x;
        int y = position.y + orthogonal.y;

        x = Math.max(-1, Math.min(1, x));
        y = Math.max(-1, Math.min(1, y));

        return new Vector(x, y);
    }
    public static double dotProduct(Vector a, Vector b) {
        boolean aBool = false,bBool = false;
        if (a.x != 0 && a.y != 0) aBool = true;
        if (b.y != 0 && b.y !=0) bBool = true;

        return aBool&&bBool? a.x * b.x + a.y * b.y:aBool?a.x * b.x + a.y * b.y : bBool? a.x * b.x + a.y * b.y:a.x * b.x + a.y * b.y;
    }

    /**
     * Inverts the vector.
     *
     * @return inverted vector
     */
    public Vector invert() {
        return new Vector(-this.x, -this.y);
    }

    /**
     * Calculates a direction even over the border pixels
     *
     * @param secondPos Vector to calculate the direction
     *
     * @return Direction to secondPos vector
     */
    public Vector calculateDirection(Vector secondPos) {
        int dx = secondPos.x - this.x;
        int dy = secondPos.y - this.y;

        int length = dx+dy;

        dx = Math.round(dx/length);
        dy = Math.round(dy/length);

        if (dx > 1) dx = 1;
        if (dx < -1) dx = -1;
        if (dy > 1) dy = 1;
        if (dy < -1) dy = -1;

        return new Vector(dx, dy);
    }

    /**
     * Add a vector to this instance coordinate wise.
     *
     * @param vector Vector to add
     *
     * @return Vector with added coordinates.
     */
    public Vector add(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y);
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
    public long getCords(){
        return cords;
    }
}
