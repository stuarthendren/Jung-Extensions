package net.stuarthendren.jung.cluster.edge.impl;

import java.util.HashSet;
import java.util.Set;

import net.stuarthendren.jung.partition.Partition;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;

/**
 * 
 * An edge partition is a set of non-intersecting sets, called clusters, of edges of a graph. The partition is complete
 * when all edges of the graph are in a cluster of the partition.
 * 
 * @author Stuart Hendren
 * 
 * @param <V>
 *            the vertex type
 * @param <E>
 *            the edge type
 */
public class EdgePartitionDensityTransformer<V, E> implements Transformer<Partition<E>, Double> {

	private final Graph<V, E> graph;

	public EdgePartitionDensityTransformer(Graph<V, E> graph) {
		this.graph = graph;
	}

	/**
	 * 
	 * For a graph with M edges and N vertices, P = {P_1, ..., P_C} is a partition of the edges into C subsets. The
	 * number of edges in subset P_C is m_C. The number of induced vertices, all vertices that those edges touch, is
	 * n_C. The edge (link) density D_C of a community is the number of edges in P_C, normalised by the minimum and
	 * maximum number of edges possible between those vertices, assuming they remain connected. (We assume D_C = 0 if
	 * n_C = 2.) The partition density, D, is the average of D_C, weighted by the fraction of the present edges.
	 * 
	 * (Defined on p9 of http://arxiv.org/abs/0903.3178)
	 * 
	 * @return
	 */
	@Override
	public Double transform(Partition<E> partition) {
		Double M = Double.valueOf(graph.getEdgeCount());
		Double sum = 0d;
		for (Set<E> cluster : partition.getPartitioning()) {
			Double m_c = Double.valueOf(cluster.size());
			Double n_c = getNumberInducedVerticies(graph, cluster);
			if (Double.valueOf(2).equals(n_c) || Double.valueOf(1.0).equals(n_c)) {
				continue;
			}

			sum += m_c * ((m_c - (n_c - 1)) / ((n_c - 2) * (n_c - 1)));
		}

		return (2 / M) * sum;

	}

	private static <V, E> Double getNumberInducedVerticies(Graph<V, E> graph, Set<E> cluster) {
		return Double.valueOf(getIncidentVerticies(graph, cluster).size());
	}

	private static <V, E> Set<V> getIncidentVerticies(Graph<V, E> graph, Set<E> cluster) {
		Set<V> verticies = new HashSet<V>();
		for (E e : cluster) {
			verticies.addAll(graph.getIncidentVertices(e));
		}
		return verticies;
	}

}
