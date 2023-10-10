import java.util.ArrayList;

public class Position {
    private int x;
    private int y;

    //ich würd die Ants in der Cell speichern - wir verwenden Position ja auch für Ants und die brauchen diese INfo nicht.
    //ArrayList<Ant> ants;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public static Position RandomDirection(){
        int x = 0;
        int y = 0;
        while (x == 0 && y == 0){
            x = (int)Math.round(Math.random()*2-1);
            y = (int)Math.round(Math.random()*2-1);
        }
        return new Position(x,y);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
