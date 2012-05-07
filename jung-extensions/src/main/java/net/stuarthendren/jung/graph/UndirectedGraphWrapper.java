package net.stuarthendren.jung.graph;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

/**
 * Wrapper for graphs that are undirected to present the {@link UndirectedGraph} interface
 * 
 * @author Stuart Hendren
 * 
 * @param <V>
 * @param <E>
 */
public class UndirectedGraphWrapper<V, E> extends DelegatingGraph<V, E> implements UndirectedGraph<V, E> {

	/**
	 * Wrap undirected graph
	 * 
	 * @param graph
	 * @param check
	 *            if true, throws {@link IllegalArgumentException} if graph is not undirected
	 */
	public UndirectedGraphWrapper(Graph<V, E> graph, boolean check) {
		super(graph);
		if (check) {
			if (!GraphUtils.isUndirected(graph)) {
				throw new IllegalArgumentException("GRaph must be undirected");
			}
		}
	}

	/**
	 * Wrap undirected graph
	 * 
	 * Don't test to see if undirected
	 * 
	 * @param graph
	 */
	public UndirectedGraphWrapper(Graph<V, E> graph) {
		this(graph, false);
	}

}
