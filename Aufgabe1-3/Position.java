import java.util.ArrayList;

public class Position {
    int x;
    int y;

    ArrayList<Ant> ants;

    public Position(int x, int y, ArrayList<Ant> ants) {
        this.x = x;
        this.y = y;
        this.ants = ants;
    }
}
