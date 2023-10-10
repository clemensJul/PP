public class Vector {
    private int x;
    private int y;

    //ich würd die Ants in der Cell speichern - wir verwenden Vector ja auch für Ants und die brauchen diese INfo nicht.
    //ArrayList<Ant> ants;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
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
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
