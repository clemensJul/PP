class Vertex {
    private final double x;
    private final double y;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
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
