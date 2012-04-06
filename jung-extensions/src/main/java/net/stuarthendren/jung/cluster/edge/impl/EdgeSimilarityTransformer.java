package net.stuarthendren.jung.cluster.edge.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

/**
 * Edge similarity transformer.
 * 
 * aka The Jaccard Index
 * 
 * @author Stuart Hendren
 * 
 * @param <E>
 *            Edge type
 */
public class EdgeSimilarityTransformer<V, E> implements
		Transformer<KeyedEdgePair<V, E>, Double> {

	private final Graph<V, E> graph;

	/**
	 * The ratio of the number of common neighbours of vertex i and vertex j and
	 * the number of possible neighbours (not including vertex k).
	 * 
	 * @param graph
	 * @param edge_ik
	 * @param edge_kj
	 * @param vertex_k
	 */
	public EdgeSimilarityTransformer(UndirectedGraph<V, E> graph) {
		this.graph = graph;
	}

	@Override
	public Double transform(KeyedEdgePair<V, E> pair) {
		E edge_ik = pair.getEdge1();
		E edge_kj = pair.getEdge2();
		V vertex_k = pair.getKeyVertex();

		V vertex_i = graph.getOpposite(vertex_k, edge_ik);
		V vertex_j = graph.getOpposite(vertex_k, edge_kj);
		if (vertex_i.equals(vertex_j)) {
			return 1.0;
		} else {
			Set<V> n_i = new HashSet<V>(graph.getNeighbors(vertex_i));
			n_i.add(vertex_i);
			Set<V> n_j = new HashSet<V>(graph.getNeighbors(vertex_j));
			n_j.add(vertex_j);
			int size_n_i = n_i.size();
			int size_n_j = n_j.size();

			// Calculate union, NB not zero as intersection contains k
			Double union = null;
			if (size_n_j < size_n_i) {
				n_i.addAll(n_j);
				union = (double) n_i.size();
			} else {
				n_j.addAll(n_i);
				union = (double) n_j.size();
			}

			// Not zero as intersection contains k
			Double intersection = size_n_i + size_n_j - union;

			return intersection / union;
		}
	}
}
