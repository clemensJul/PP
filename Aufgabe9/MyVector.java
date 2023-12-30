import java.util.Objects;

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

    public static MyVector substract(MyVector one, MyVector two){
        return new MyVector(one.x-two.x,one.y-two.y);
    }

    public static MyVector orthogonalVector(MyVector vector, boolean clockwise){
        if(clockwise) return new MyVector(vector.y,-vector.x);
        return new MyVector(-vector.y,vector.x);
    }

    public static MyVector getLookingDirection(MyVector vector1, MyVector vector2){
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
}
