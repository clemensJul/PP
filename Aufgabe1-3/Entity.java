public interface Entity {

    Position position = null;

    //updates values needed for next
    void update();
    Position getPosition();
}
