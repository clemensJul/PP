public class Vector {
    private final int x;
    private final int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector orthogonalVector(Vector vector, boolean isLeft) {
        if (isLeft) {
            return new Vector(-vector.y, vector.x);
        }

        return new Vector(vector.y, -vector.x);
    }

    public Vector sharpVector(Vector orthogonal) {
        int x = this.x + orthogonal.x;
        int y = this.y + orthogonal.y;

        x = Math.max(-1, Math.min(1, x));
        y = Math.max(-1, Math.min(1, y));

        return new Vector(x, y);
    }

    public Vector invert() {
        return new Vector(-this.x, -this.y);
    }

    public Vector calculateDirection(Vector secondPos) {
        int dx2 = secondPos.x - this.x;
        int dy2 = secondPos.y - this.y;

        if (dx2 > 1) dx2 = -1;
        if (dx2 < -1) dx2 = 1;
        if (dy2 > 1) dy2 = -1;
        if (dy2 < -1) dy2 = 1;

        return new Vector(dx2, dy2);
    }

    public static Vector RandomDirection() {
        int x = 0;
        int y = 0;
        while (x == 0 && y == 0) {
            x = (int) Math.round(Math.random() * 2 - 1);
            y = (int) Math.round(Math.random() * 2 - 1);
        }
        return new Vector(x, y);
    }

    public Vector add(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
