public class Vector {
    private int x;
    private int y;
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector orthogonalVector(Vector vector,boolean isLeft ){
        if (isLeft) return new Vector(vector.y, vector.x);
        return new Vector(vector.y, -vector.x);
    }
    public static Vector invert(Vector vector){
        return new Vector(-vector.x, -vector.y);
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

}
