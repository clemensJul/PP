import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Direction {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private static HashMap<Direction, ArrayList<Position>> getMap() {
        HashMap<Direction, ArrayList<Position>> map = new HashMap<>();


        Position right = new Position(1, 0);
        Position bottomRight = new Position(1, 1);
        Position bottom = new Position(0, 1);
        Position bottomLeft = new Position(-1, 1);
        Position left = new Position(-1, 0);
        Position topLeft = new Position(-1, -1);
        Position top = new Position(0, -1);
        Position topRight = new Position(1, -1);

        map.put(new Direction(1, 0), new ArrayList<>(Arrays.asList(right, topRight, top, topLeft, left)));

        return map;
    }

    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<Position> getNeighbors() {
        return getMap().get(this);
    }

    public static Direction RandomDirection(){
        int x = 0;
        int y = 0;
        while (x == 0 && y == 0){
            x = (int)Math.round(Math.random()*2-1);
            y = (int)Math.round(Math.random()*2-1);
        }
        return new Direction(x,y);
    }
}
