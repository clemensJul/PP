import codedraw.CodeDraw;

import java.awt.*;
import java.util.List;
import java.util.*;

public class ACSCalculator {

    private static double INITIAL_PHEROMEN = 1.0;

    /**
     * @param graph        must not be null
     * @param iterations   must not be a negative number
     * @param numberOfAnts must not be a negative number
     * @return a list of edges that is the last solution of the ACS simulation
     */
    public static List<Vertex> calculate(Graph graph, ASCCalculatorOptions options, CodeDraw cd) {
        long timer = System.nanoTime();

        HashMap<Edge, Double> pheromones = new HashMap<>();
        ArrayList<Vertex> bestResult = null;
        double bestResultWeight = Double.MAX_VALUE;

        double simpleSolutionForPheromen = calculateSimpleSolution(graph);

        //iterations of algorithm
        for (int i = 0; i < options.getIterations(); i++) {
            // in every iteration we need to seed new ants
            ArrayList<Ant> ants = seedAnts(graph, options.getAntAmount());

            //create a solution
            for (int k = 0; k < graph.getVertices().size(); k++) {
                ants.forEach(ant -> {
                    //calculate best vertex
                    Vertex nextVertex = nextCity(ant, graph, options, pheromones);

                    if (nextVertex != null) {
                        // update pheromones
                        Edge edge = graph.getEdge(ant.getCurrentVertex(), nextVertex);
                        double currentPheromone = pheromones.getOrDefault(edge, INITIAL_PHEROMEN);
                        double newPheromone = (1 - options.getPersistence()) * currentPheromone + options.getPersistence() * ((double) 1 / simpleSolutionForPheromen);
                        pheromones.put(edge, newPheromone);

                        ant.visitVertex(nextVertex);
                    }
                });
            }

            ArrayList<Vertex> resultFromIteration = getBestResult(graph, ants);
            ArrayList<Edge> edgesFromIteration = getEdgesFromVertices(graph, resultFromIteration);
            double resultWeightFromIteration = getWeightOfEdges(edgesFromIteration);
            if (resultWeightFromIteration < bestResultWeight) {
                bestResult = resultFromIteration;
                bestResultWeight = resultWeightFromIteration;
                System.out.println(bestResultWeight);
            }

            ArrayList<Edge> edgesInResult = getEdgesFromVertices(graph, bestResult);
            // update pheromones according to best result
            for (Edge edge : pheromones.keySet()) {
                double currentValue = pheromones.getOrDefault(edge, INITIAL_PHEROMEN);
                double tau = 0;
                if (edgesInResult.contains(edge)) {
                    tau = 1 / bestResultWeight;
                }
                double newValue = (1 - options.getPersistence()) * currentValue + options.getPersistence() * tau;
                pheromones.put(edge, newValue);
            }

            // only for testing - draw codedraw
            drawIteration(cd, bestResult);
        }
        System.out.println("done");
        return bestResult;
    }

    private static ArrayList<Edge> getEdgesFromVertices(Graph graph, List<Vertex> vertices) {
        ArrayList<Edge> edges = new ArrayList<>();

        Vertex oldCity = vertices.getFirst();
        for (int j = 1; j < vertices.size(); j++) {
            edges.add(graph.getEdge(oldCity, vertices.get(j)));
            oldCity = vertices.get(j);
        }
        return edges;
    }

    private static double getWeightOfEdges(List<Edge> edges) {
        return edges.stream()
                .mapToDouble(Edge::getDistance) // Hier anstelle von ResultType die tatsächliche Klasse verwenden
                .sum();
    }

    private static void drawIteration(CodeDraw cd, List<Vertex> result) {
//        cd.setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
        cd.setColor(Color.BLACK);
        int cellSize = 5;
        Vertex prev = result.getFirst();
        for (int i = 1; i < result.size(); i++) {
            Vertex current = result.get(i);
            cd.drawLine(prev.getX() * cellSize, prev.getY() * cellSize, current.getX() * cellSize, current.getY() * cellSize);
            prev = current;
        }
        cd.drawLine(prev.getX() * cellSize, prev.getY() * cellSize, result.getFirst().getX() * cellSize, result.getFirst().getY() * cellSize);

        cd.show();
    }

    private static Vertex nextCity(Ant ant, Graph graph, ASCCalculatorOptions options, HashMap<Edge, Double> pheromones) {
        Random rand = new Random();
        double randomNumber = rand.nextDouble();

        if (randomNumber > (1 - options.getProbability())) {
            return calculateBestNextCity(ant, graph, pheromones);
        } else {
            return calculateRandomNextCity(ant, graph, options, pheromones);
        }
    }

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

    private static Vertex calculateRandomNextCity(Ant ant, Graph graph, ASCCalculatorOptions options, HashMap<Edge, Double> pheromones) {
        Vertex currentCity = ant.getCurrentVertex();

        return graph.getNeighbors(currentCity).stream()
                .filter(city -> city != currentCity && !ant.getAlreadyVisitedCities().contains(city))
                .max(Comparator.comparingDouble(city -> calculateProbability(ant, graph, city, options, pheromones)*Math.random()))
                .orElse(null);
    }


    // Eine Methode, die die Wahrscheinlichkeit berechnet, dass eine Ameise von Stadt i nach Stadt j geht
    private static double calculateProbability(Ant ant, Graph graph, Vertex nextCity, ASCCalculatorOptions options, HashMap<Edge, Double> pheromones) {
        Vertex currentCity = ant.getCurrentVertex();

        double tij = pheromones.getOrDefault(graph.getEdge(currentCity, nextCity), INITIAL_PHEROMEN);
        double nij = 1.0 / graph.getDistance(currentCity, nextCity);

        double numerator = Math.pow(tij, options.getAlpha()) * Math.pow(nij, options.getBeta());

        double denominator = graph.getNeighbors(currentCity).stream()
                .filter(city -> city != currentCity && !ant.getAlreadyVisitedCities().contains(city))
                .mapToDouble(city -> {
                    double tik = pheromones.getOrDefault(graph.getEdge(currentCity, city), INITIAL_PHEROMEN);
                    double nik = 1.0 / graph.getDistance(currentCity, city);
                    return Math.pow(tik, options.getAlpha()) * Math.pow(nik, options.getBeta());
                })
                .sum();

        return numerator / denominator;
    }

    private static ArrayList<Vertex> getBestResult(Graph graph, List<Ant> ants) {
        double bestResultWeight = Double.MAX_VALUE;
        ArrayList<Vertex> visitedVertexes = new ArrayList<>();

        for (Ant ant : ants) {
            ArrayList<Edge> localeResult = new ArrayList<>();

            Vertex oldCity = ant.getStartingVertex();
            for (int j = 1; j < ant.getAlreadyVisitedCities().size(); j++) {
                localeResult.add(graph.getEdge(oldCity, ant.getAlreadyVisitedCities().get(j)));
                oldCity = ant.getAlreadyVisitedCities().get(j);
            }
            localeResult.add(graph.getEdge(oldCity, ant.getStartingVertex()));

            // check if current result is better than global one
            double localeResultWeight = localeResult.stream()
                    .mapToDouble(Edge::getDistance) // Hier anstelle von ResultType die tatsächliche Klasse verwenden
                    .sum();

            // update if better result found
            if (localeResultWeight < bestResultWeight) {
                bestResultWeight = localeResultWeight;
                visitedVertexes = ant.getAlreadyVisitedCities();
            }
        }

        return visitedVertexes;
    }

    private static ArrayList<Ant> seedAnts(Graph graph, int numberOfAnts) {
        List<Vertex> vertices = graph.getVertices().stream().toList();
        ArrayList<Ant> seedAnts = new ArrayList<>();

        for (int i = 0; i < numberOfAnts; i++) {
            int randomIndex = (int) (Math.random() * vertices.size());
            seedAnts.add(new Ant(vertices.get(randomIndex)));
        }

        return seedAnts;
    }
}
