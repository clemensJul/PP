public class Position {
    private int x;
    private int y;

    //ich würd die Ants in der Cell speichern - wir verwenden Vector ja auch für Ants und die brauchen diese INfo nicht.
    //ArrayList<Ant> ants;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
