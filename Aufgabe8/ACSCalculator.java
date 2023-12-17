import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ACSCalculator {
    private static double INITIAL_PHEROMEN = 1;

    /**
     * @param graph        must not be null
     * @param options      must not be null
     * @return a list of edges that is the last solution of the ACS simulation
     */
    public static List<Vertex> calculate(Graph graph, ACSCalculatorOptions options) {
        HashMap<Edge, Double> pheromones = new HashMap<>();
        ArrayList<Vertex> bestResult = null;
        double bestResultWeight = Double.MAX_VALUE;

        double simpleSolutionForPheromen = calculateSimpleSolution(graph);
        //iterations of algorithm
        for (int i = 0; i < options.iterations(); i++) {
            // in every iteration we need to seed new ants
            ArrayList<Ant> ants = seedAnts(graph, options.antAmount());

            //create a solution for this iteration
            for (int k = 0; k < graph.getVertices().size(); k++) {
                ants.forEach(ant -> {
                    //calculate best vertex
                    Vertex nextVertex = nextCity(ant, graph, options, pheromones);

                    if (nextVertex != null) {
                        // update pheromones
                        Edge edge = graph.getEdge(ant.getCurrentVertex(), nextVertex);
                        double currentPheromone = pheromones.getOrDefault(edge, INITIAL_PHEROMEN);
                        double newPheromone = (1 - options.persistence()) * currentPheromone + options.persistence() * ((double) 1 / simpleSolutionForPheromen);
                        pheromones.put(edge, newPheromone);

                        ant.visitVertex(nextVertex);
                    }
                });
            }

            ArrayList<Vertex> resultFromIteration = getBestResult(graph, ants);
            ArrayList<Edge> edgesFromIteration = getEdgesFromVertices(graph, resultFromIteration);
            double resultWeightFromIteration = getWeightOfEdges(edgesFromIteration);

            // change best solution if current solution is better than the current best
            if (resultWeightFromIteration < bestResultWeight) {
                bestResult = resultFromIteration;
                bestResultWeight = resultWeightFromIteration;
            }

            //get best results
            ArrayList<Edge> edgesInResult = getEdgesFromVertices(graph, bestResult);
            double finalBestResultWeight = bestResultWeight;
            pheromones.replaceAll((edge, currentValue) -> {
                double tau = edgesInResult.contains(edge) ? 1.0 / finalBestResultWeight : 0;
                return (1 - options.persistence()) * currentValue + options.persistence() * tau;
            });
            printIterationProgress(i, resultWeightFromIteration);
        }
        printResult(bestResult, graph);
        printOptions(options);
        return bestResult;
    }

    /**
     * Retrieves a list of edges based on a list of vertices in a graph.
     *
     * @param graph    The graph containing the edges between vertices.
     * @param vertices The list of vertices for which edges are to be retrieved.
     * @return An ArrayList of edges formed by connecting the given vertices in sequence.
     */
    private static ArrayList<Edge> getEdgesFromVertices(Graph graph, List<Vertex> vertices) {
        if (vertices.isEmpty()) {
            return new ArrayList<>(); // Return empty list if no vertices are provided
        }

        // Generate pairs of vertices (circular pairs, including the last-first pair)
        return IntStream.range(0, vertices.size())
                .mapToObj(i -> graph.getEdge(vertices.get(i), vertices.get((i + 1) % vertices.size())))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Calculates the total weight of a list of edges.
     *
     * @param edges The list of edges for which the total weight is being calculated.
     * @return The total weight of the edges in the list.
     */
    private static double getWeightOfEdges(List<Edge> edges) {
        return edges.stream()
                .mapToDouble(Edge::distance)
                .sum();
    }

    /**
     * Determines the next city for the ant based on probabilities and randomness.
     *
     * @param ant The ant for which the next city is being determined.
     * @param graph The graph representing cities and distances between them.
     * @param options The options defining probabilities and preferences.
     * @param pheromones The map storing pheromone levels for edges in the graph.
     * @return The next city for the ant to move to, based on probability calculations.
     */
    private static Vertex nextCity(Ant ant, Graph graph, ACSCalculatorOptions options, HashMap<Edge, Double> pheromones) {
        Random rand = new Random();
        double randomNumber = rand.nextDouble();

        if (randomNumber > (1 - options.probability())) {
            return calculateBestNextCity(ant, graph, pheromones);
        } else {
            return calculateRandomNextCity(ant, graph, options, pheromones);
        }
    }

    /**
     * Calculates a simple solution for the given graph.
     *
     * @param graph The graph representing cities and distances between them.
     * @return The weight of the edges in the calculated simple solution.
     */
    private static double calculateSimpleSolution(Graph graph) {
        ArrayList<Vertex> solution = new ArrayList<>();

        graph.getVertices().forEach(vertex -> {
            int sizeOfNeighbors = graph.getNeighbors(vertex).size();
            int randomNeighbor = (int) (Math.random() * sizeOfNeighbors);

            while (true) {
                if (!solution.contains(vertex)) {
                    solution.add(vertex);
                    break;
                }
                randomNeighbor = (randomNeighbor + 1) % sizeOfNeighbors;
            }
        });

        ArrayList<Edge> solutionEdges = getEdgesFromVertices(graph, solution);
        return getWeightOfEdges(solutionEdges);
    }

    /**
     * Calculates the best next city for the ant to move to based on pheromone levels and distances.
     *
     * @param ant The ant for which the best next city is being calculated.
     * @param graph The graph representing cities and distances between them.
     * @param pheromones The map storing pheromone levels for edges in the graph.
     * @return The best next city for the ant to move to, determined by pheromone levels and distances.
     */
    private static Vertex calculateBestNextCity(Ant ant, Graph graph, HashMap<Edge, Double> pheromones) {
        Vertex currentCity = ant.getCurrentVertex();

        return graph.getNeighbors(currentCity).stream()
                .filter(city -> city != currentCity && !ant.getAlreadyVisitedCities().contains(city))
                .max(Comparator.comparingDouble(city -> {
                    Edge edge = graph.getEdge(currentCity, city);
                    double tij = pheromones.getOrDefault(edge, INITIAL_PHEROMEN);
                    double nij = 1.0 / graph.getDistance(currentCity, city);
                    return tij * nij;
                }))
                .orElse(null);
    }

    /**
     * Calculates the next city for the ant to move to based on probabilities and randomness.
     *
     * @param ant The ant for which the next city is being calculated.
     * @param graph The graph representing cities and distances between them.
     * @param options The options defining alpha and beta values for probability calculation.
     * @param pheromones The map storing pheromone levels for edges in the graph.
     * @return The next city for the ant to move to, determined by probabilities and randomness.
     */
    private static Vertex calculateRandomNextCity(Ant ant, Graph graph, ACSCalculatorOptions options, HashMap<Edge, Double> pheromones) {
        Vertex currentCity = ant.getCurrentVertex();

        return graph.getNeighbors(currentCity).stream()
                .filter(city -> city != currentCity && !ant.getAlreadyVisitedCities().contains(city))
                .max(Comparator.comparingDouble(city -> calculateProbability(ant, graph, city, options, pheromones)*Math.random()))
                .orElse(null);
    }

    /**
     * Calculates the probability of an ant moving to a specific next city based on given parameters and options.
     *
     * @param ant The ant for which the probability is being calculated.
     * @param graph The graph representing cities and distances between them.
     * @param nextCity The next city to consider for movement.
     * @param options The options defining alpha and beta values for probability calculation.
     * @param pheromones The map storing pheromone levels for edges in the graph.
     * @return The calculated probability of the ant moving to the next city.
     */
    private static double calculateProbability(Ant ant, Graph graph, Vertex nextCity, ACSCalculatorOptions options, HashMap<Edge, Double> pheromones) {
        Vertex currentCity = ant.getCurrentVertex();

        double tij = pheromones.getOrDefault(graph.getEdge(currentCity, nextCity), INITIAL_PHEROMEN);
        double nij = 1.0 / graph.getDistance(currentCity, nextCity);

        double numerator = Math.pow(tij, options.alpha()) * Math.pow(nij, options.beta());

        double denominator = graph.getNeighbors(currentCity).stream()
                .filter(city -> city != currentCity && !ant.getAlreadyVisitedCities().contains(city))
                .mapToDouble(city -> {
                    double tik = pheromones.getOrDefault(graph.getEdge(currentCity, city), INITIAL_PHEROMEN);
                    double nik = 1.0 / graph.getDistance(currentCity, city);
                    return Math.pow(tik, options.alpha()) * Math.pow(nik, options.beta());
                })
                .sum();

        return numerator / denominator;
    }

    /**
     * Finds the best result among a list of ants' visited cities based on a given graph.
     * @param graph The graph representing cities and distances between them.
     * @param ants The list of ants, each having a set of visited cities.
     * @return An ArrayList of visited vertices representing the best result found among ants.
     */
    private static ArrayList<Vertex> getBestResult(Graph graph, List<Ant> ants) {
        double bestResultWeight = Double.MAX_VALUE;
        ArrayList<Vertex> visitedVertexes = new ArrayList<>();

        for (Ant ant : ants) {
            ArrayList<Edge> localeResult = getEdgesFromVertices(graph, ant.getAlreadyVisitedCities());

            // check if current result is better than global one
            double localeResultWeight = localeResult.stream()
                    .mapToDouble(Edge::distance)
                    .sum();

            // update if better result found
            if (localeResultWeight < bestResultWeight) {
                bestResultWeight = localeResultWeight;
                visitedVertexes = ant.getAlreadyVisitedCities();
            }
        }

        return visitedVertexes;
    }

    /**
     * Seeds the given number of ants randomly on vertices within the graph.
     * @param graph The graph containing vertices where ants will be seeded.
     * @param numberOfAnts The number of ants to be seeded.
     * @return An ArrayList containing the seeded ants.
     */
    private static ArrayList<Ant> seedAnts(Graph graph, int numberOfAnts) {
        List<Vertex> vertices = graph.getVertices().stream().toList();

        return IntStream.range(0, numberOfAnts)
                .mapToObj(i -> {
                    int randomIndex = (int) (Math.random() * vertices.size());
                    return new Ant(vertices.get(randomIndex));
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Prints the progress of an iteration with the iteration number and the rounded length.
     *
     * @param i      The iteration number.
     * @param length The length value to be printed.
     */
    private static void printIterationProgress(int i, double length) {
        System.out.println("Iteration " + i + ":\t" + Math.round(length) + "m");
    }

    /**
     * Prints the result of the traversal showing the best way and overall distance.
     *
     * @param result The list of vertices representing the best way.
     * @param graph  The graph used to calculate distances between vertices.
     */
    private static void printResult(List<Vertex> result, Graph graph) {
        if (result == null) {
            return; // Exit if the result is null
        }
        StringBuilder output = new StringBuilder();
        output.append("Best way is: \n");

        Vertex oldCity = result.get(0);

        double distance = 0;
        for (int i = 1; i < result.size(); i++) {
            Vertex currentCity = result.get(i);
            double distanceIteration = graph.getDistance(oldCity, currentCity);
            output.append(oldCity).append("\t-\t").append(currentCity).append("\t\tDistance: ").append(distanceIteration).append("m\n");
            oldCity = currentCity;
            distance += distanceIteration;
        }

        double lastDistance = graph.getDistance(oldCity, result.getFirst());
        distance += lastDistance;
        output.append(oldCity).append("\t-\t").append(result.getFirst()).append("\t\tDistance: ").append(lastDistance).append("m\n");
        output.append("with an overall distance of: ").append(distance).append("m");

        System.out.println(output);
    }

    /**
     * Prints the given options related to the Ant Colony System (ACS) algorithm.
     *
     * @param options The options specifying the parameters for the ACS algorithm.
     */
    private static void printOptions(ACSCalculatorOptions options) {
        System.out.println(options);
    }
}
