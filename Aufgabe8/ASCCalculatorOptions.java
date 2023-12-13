public class ASCCalculatorOptions {
    private int antAmount;
    private double probability;
    private double alpha;
    private double beta;
    double persistence;
    int iterations;

    /**
     * @param iterations  number of iteration of the algorithm - must be > 0
     * @param antAmount   number of ants for the calculation - increases accuracy per iteration - must be > 0
     * @param probability probability of ants taking the best vertex - must be >= 0
     * @param alpha       impact of pheromones on ant decision-making - must be >= 0
     * @param beta        impact of distance on ant decision-making - must be >= 0
     * @param persistence factor of pheromone retention - must be 0<=x<=1
     */
    public ASCCalculatorOptions(int iterations, int antAmount, double probability, double alpha, double beta, double persistence) {
        this.iterations = iterations;
        this.antAmount = antAmount;
        this.probability = probability;
        this.alpha = alpha;
        this.beta = beta;
        this.persistence = Math.clamp(persistence, 0, 1);
    }

    public int getAntAmount() {
        return antAmount;
    }

    public void setAntAmount(int antAmount) {
        this.antAmount = antAmount;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getPersistence() {
        return persistence;
    }

    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
