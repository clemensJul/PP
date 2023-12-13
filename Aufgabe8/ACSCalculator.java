import java.util.*;

public class ACSCalculator {

    /**
     *
     * @param graph must not be null
     * @param iterations must not be a negative number
     * @param numberOfAnts must not be a negative number
     * @return a list of edges that is the last solution of the ACS simulation
     */
    public List<Edge> calculate(Graph graph, ASCCalculatorOptions options){
        long timer = System.nanoTime();

        HashMap<Edge,Double> pheromones = new HashMap<>();


        ArrayList<Edge> bestResult = null;
        double bestResultWeight = Double.MAX_VALUE;
        //iterations of algorithm
        for (int i = 0; i < options.getIterations(); i++) {
            // in every iteration we need to seed new ants
            ArrayList<Ant> ants = seedAnts(graph, options.getAntAmount());

            //create a solution
            for (int k = 0; k < graph.getVertices().size(); k++) {
                ants.forEach(ant -> {
                    //calculate best vertex
                    ant.visitVertex();

                });
            }

            ArrayList<Edge> resultFromIteration = getBestResult(graph, ants);

            double resultWeightFromIteration = resultFromIteration.stream()
                    .mapToDouble(Edge::getDistance) // Hier anstelle von ResultType die tatsächliche Klasse verwenden
                    .sum();

            if (resultWeightFromIteration < bestResultWeight) {
                bestResult = resultFromIteration;
                bestResultWeight = resultWeightFromIteration;

            }
            // update pheromones according to best result
        }
        return null;
    }

    private static double newPheromon() {
return 0.0;
    }
    public Vertex calculateBestVertex(){

    }

    private static ArrayList<Edge> getBestResult(Graph graph, List<Ant> ants) {
        ArrayList<Edge> bestResult = null;
        double bestResultWeight = Double.MAX_VALUE;

        for (Ant ant : ants) {
            ArrayList<Edge> localeResult = new ArrayList<>();

            Vertex oldCity = ant.getStartingVertex();
            for (Vertex city : ant.getAlreadyVisitedCities()) {
                localeResult.add(graph.getEdge(oldCity, city));
                oldCity = city;
            }

            // check if current result is better than global one
            double localeResultWeight = localeResult.stream()
                    .mapToDouble(Edge::getDistance) // Hier anstelle von ResultType die tatsächliche Klasse verwenden
                    .sum();

            // update if better result found
            if (localeResultWeight < bestResultWeight) {
                bestResult = localeResult;
                bestResultWeight = localeResultWeight;
            }
        }

        return bestResult;
    }

    private static ArrayList<Ant> seedAnts(Graph graph, int numberOfAnts) {
        List<Vertex> vertices = graph.getVertices().stream().toList();
        ArrayList<Ant> seedAnts = new ArrayList<>();

        for (int i = 0; i < numberOfAnts; i++) {
            int randomIndex = (int)(Math.random() * vertices.size());
            seedAnts.add(new Ant(vertices.get(randomIndex)));
        }

        return seedAnts;
    }
}
