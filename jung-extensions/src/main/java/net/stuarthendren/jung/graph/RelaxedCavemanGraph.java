package net.stuarthendren.jung.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.Pair;

public class RelaxedCavemanGraph {

	public static UndirectedGraph<Integer, Integer> generateGraph(double probability, double scalingCoefficient,
			int levels, int minCaveSize, int maxCaveSize) {
		return generateGraph(probability, scalingCoefficient, levels, minCaveSize, maxCaveSize,
				System.currentTimeMillis());
	}

	public static UndirectedGraph<Integer, Integer> generateGraph(double probability, double scalingCoefficient,
			int levels, int minCaveSize, int maxCaveSize, long seed) {
		if (probability < 0.0 || probability > 1.0) {
			throw new IllegalArgumentException("Probability must be between 0 and 1");
		}
		if (scalingCoefficient < 0) {
			throw new IllegalArgumentException("Scaling coefficient of groups must be positive");
		}
		if (levels < 1) {
			throw new IllegalArgumentException("Number of levels must be positive");
		}
		if (minCaveSize < 0) {
			throw new IllegalArgumentException("Minimum cave size must be positive of levels must be positive");
		}
		if (maxCaveSize < minCaveSize) {
			throw new IllegalArgumentException("Max must be greater than min");
		}
		Random rand = new Random(seed);

		// Use determined seed so random properties are different but consistent for the same input seed
		UndirectedGraph<Integer, Integer> graph = new RandomGraph(getRandomSize(rand, minCaveSize, maxCaveSize),
				probability, seed + 1);
		for (int i = 1; i <= levels; i++) {
			addLevel(getProbability(i, probability, scalingCoefficient), graph, minCaveSize, maxCaveSize, rand);
		}
		return graph;
	}

	private static void addLevel(double probability, UndirectedGraph<Integer, Integer> graph, int minCaveSize,
			int maxCaveSize, Random rand) {
		int vertexCount = graph.getVertexCount();
		for (int i = 0; i < vertexCount; i++) {
			UndirectedGraph<Integer, Integer> cave = new RandomGraph(getRandomSize(rand, minCaveSize, maxCaveSize),
					probability);
			replace(graph, i, cave, rand);
		}
	}

	private static void replace(UndirectedGraph<Integer, Integer> graph, int vertex,
			UndirectedGraph<Integer, Integer> cave, Random rand) {
		int baseVerticies = graph.getVertexCount();
		int baseEdges = graph.getEdgeCount();
		int caveSize = cave.getVertexCount();
		// Add new verticies
		for (int i = 1; i < caveSize; i++) {
			graph.addVertex(translate(vertex, baseVerticies, i));
		}
		// re-distribute edges of v
		Collection<Integer> incidentEdges = graph.getIncidentEdges(vertex);
		if (incidentEdges != null) {
			for (Integer edge : new HashSet<Integer>(incidentEdges)) {
				Integer opposite = graph.getOpposite(vertex, edge);
				graph.removeEdge(edge);
				int randomCave = translate(vertex, baseVerticies, getRandomSize(rand, 0, caveSize));
				graph.addEdge(edge, opposite, randomCave);
			}
		}
		// add edges from cave
		for (Integer j : cave.getEdges()) {
			Pair<Integer> endpoints = cave.getEndpoints(j);
			graph.addEdge(translate(baseEdges, baseEdges, j), translate(vertex, baseVerticies, endpoints.getFirst()),
					translate(vertex, baseVerticies, endpoints.getSecond()));
		}

	}

	private static int translate(int main, int base, int cave) {
		if (cave == 0) {
			return main;
		} else {
			return cave + base;
		}
	}

	private static double getProbability(int level, double probability, double scalingCoefficient) {
		return Math.min(1.0, level * scalingCoefficient * probability);
	}

	private static int getRandomSize(Random rand, int min, int max) {
		return min + (rand.nextInt(max - min));
	}

}
