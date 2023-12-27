

public class MyVector {
    private final int x,y;
    public MyVector(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public static MyVector add(MyVector one, MyVector two){
        return new MyVector(one.x+two.x,one.y+two.y);
    }
    public static MyVector orthogonalVector(MyVector vector, boolean clockwise){
        if(clockwise) return new MyVector(vector.y,-vector.x);
        return new MyVector(-vector.y,vector.x);
    }
}
