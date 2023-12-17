class Vertex {
    private final double x;
    private final double y;

    private static int ID_COUNTER = 0;
    private final int index;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
        this.index = ID_COUNTER++;
    }

    public int getIndex() {
        return this.index;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "index:"+index+ " position:(" + x + ", " + y + ")";
    }
}
