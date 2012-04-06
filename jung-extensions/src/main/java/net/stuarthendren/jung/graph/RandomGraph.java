package net.stuarthendren.jung.graph;

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
		UndirectedGraph<Integer, Integer> graph = new UndirectedSparseGraph<Integer, Integer>();
		return populateGraph(vertices, probability, graph);
	}

	private static UndirectedGraph<Integer, Integer> populateGraph(int vertices, double probability,
			UndirectedGraph<Integer, Integer> graph) {
		for (int i = 0; i < vertices; i++) {
			graph.addVertex(i);
		}
		int edges = 0;
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				if (Math.random() < probability) {
					graph.addEdge(edges++, i, j);
				}
			}
		}
		return graph;
	}

}
