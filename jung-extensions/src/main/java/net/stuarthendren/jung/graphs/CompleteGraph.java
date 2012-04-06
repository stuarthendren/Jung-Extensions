package net.stuarthendren.jung.graphs;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * 
 * A graph is complete if every vertex has an edge to every other vertex.
 * 
 * @author Stuart Hendren
 * 
 */
public class CompleteGraph {

	/**
	 * <p>
	 * Create a complete graph with n vertices.
	 * 
	 * 
	 * @param vertices
	 * @return complete graph
	 */
	public static UndirectedGraph<Integer, Integer> generateGraph(int vertices) {
		UndirectedGraph<Integer, Integer> graph = new UndirectedSparseGraph<Integer, Integer>();
		return populateGraph(vertices, graph);
	}

	private static UndirectedGraph<Integer, Integer> populateGraph(int vertices, UndirectedGraph<Integer, Integer> graph) {
		for (int i = 0; i < vertices; i++) {
			graph.addVertex(i);
		}
		int edges = 0;
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				graph.addEdge(edges++, i, j);
			}
		}
		return graph;
	}

}
