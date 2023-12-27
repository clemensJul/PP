public class Position {
    private final MyVector firstVector;
    private final MyVector secondVector;

    public Position(MyVector firstPos, MyVector secPos) {
        this.firstVector = firstPos;
        this.secondVector = secPos;
    }

    public MyVector getFirstVector() {
        return this.firstVector;
    }

    public MyVector getSecondVector(){
        return secondVector;
    }

    public MyVector getDirection() {
        return new MyVector(secondVector.getX() - firstVector.getX(), firstVector.getY() - secondVector.getY());
    }
}
