public class Vector {
    private int x;
    private int y;
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector orthogonalVector(Vector vector,boolean isLeft ){
        if (isLeft) return new Vector(-vector.y, vector.x);
        return new Vector(vector.y, -vector.x);
    }
    public Vector invert(){
        return new Vector( -this.x,-this.y);
    }
    public Vector calculateDirection(Vector secondPos) {
        int dx = secondPos.x - this.x;
        int dy = secondPos.y - this.y;

        if (dx > 1) dx =  -1;
        if (dx < -1) dx = 1;
        if (dy > 1) dy =  -1;
        if (dy < -1) dy = 1;

        return new Vector(dx, dy);
    }
    public static Vector RandomDirection(){
        int x = 0;
        int y = 0;
        while (x == 0 && y == 0){
            x = (int)Math.round(Math.random()*2-1);
            y = (int)Math.round(Math.random()*2-1);
        }
        return new Vector(x,y);
    }
    public Vector add(Vector vector){
        return new Vector(this.x+ vector.x,this.y+ vector.y);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
