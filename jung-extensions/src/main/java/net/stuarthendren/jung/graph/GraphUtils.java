package net.stuarthendren.jung.graph;

import java.util.Collection;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class GraphUtils {

	public static <V, E> boolean isUndirected(Graph<V, E> graph) {
		return 0 == graph.getEdgeCount(EdgeType.DIRECTED);
	}

	/**
	 * A complete (undirected) graph has a link between all pairs of vertices.
	 * 
	 * @param graph
	 * @return true if the graph is complete
	 */
	public static <V, E> boolean isComplete(UndirectedGraph<V, E> graph) {
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
