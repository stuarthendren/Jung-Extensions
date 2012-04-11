package net.stuarthendren.jung.graph;

import java.util.Random;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class RandomGraph {

	/**
	 * Create a randomly generated graph with a given number of vertices and probability of a link between any two edges
	 * 
	 * @param vertices
	 * @param probability
	 * @return Undirected randomly generated graph
	 */
	public static UndirectedGraph<Integer, Integer> generateGraph(int vertices, double probability) {
		return generateGraph(vertices, probability, System.currentTimeMillis());
	}

	/**
	 * Create a randomly generated graph with a given number of vertices and probability of a link between any two edges
	 * 
	 * @param vertices
	 * @param probability
	 * @return Undirected randomly generated graph
	 */
	public static UndirectedGraph<Integer, Integer> generateGraph(int vertices, double probability, long seed) {
		if (vertices < 0) {
			throw new IllegalArgumentException("Number of edges must be positive");
		}
		if (probability < 0.0 || probability > 1.0) {
			throw new IllegalArgumentException("Probability must be between 0 and 1");
		}
		UndirectedGraph<Integer, Integer> graph = new UndirectedSparseGraph<Integer, Integer>();
		return populateGraph(vertices, probability, graph, seed);
	}

	private static UndirectedGraph<Integer, Integer> populateGraph(int vertices, double probability,
			UndirectedGraph<Integer, Integer> graph, long seed) {
		Random rand = new Random(seed);
		for (int i = 0; i < vertices; i++) {
			graph.addVertex(i);
		}
		int edges = 0;
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				if (i < j && rand.nextDouble() < probability) {
					graph.addEdge(edges++, i, j);
				}
			}
		}
		return graph;
	}

}
