package net.stuarthendren.jung.graph;

import java.util.Collection;

import edu.uci.ics.jung.graph.Graph;
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
				if (i > j) {
					graph.addEdge(edges++, i, j);
				}
			}
		}
		return graph;
	}

	public static <V, E> boolean isComplete(Graph<V, E> graph) {
		int vertexCount = graph.getVertexCount();
		if (vertexCount <= 1) {
			return true;
		}
		int edgeCount = graph.getEdgeCount();
		int minNeighbours = vertexCount - 1;
		if (edgeCount < ((vertexCount * minNeighbours) / 2)) {
			return false;
		}
		for (V v : graph.getVertices()) {
			int requiredMinNeighboursSize = minNeighbours;
			Collection<V> neighbors = graph.getNeighbors(v);
			if (neighbors.contains(v)) {
				requiredMinNeighboursSize++;
			}
			if (neighbors.size() < requiredMinNeighboursSize) {
				return false;
			}
		}
		return true;
	}
}
