package net.stuarthendren.jung.cluster.vertex;

import java.util.Set;

import edu.uci.ics.jung.graph.Graph;

/**
 * A cluster of a graph G(V, E) is a sub-set of V
 * 
 * @author Stuart Hendren
 * 
 * @param <V>
 * @param <E>
 */
public interface Cluster<V, E> extends Set<V> {

	/**
	 * 
	 * @return the graph that this is a cluster of
	 */
	Graph<V, E> getGraph();

	/**
	 * The (internal) density is the number of edges in the induced graph divided by the maximum possible number of
	 * edges between the vertices of the cluster
	 * 
	 * @return the density of the cluster
	 */
	double getDensity();

	/**
	 * The induced graph is the sub graph of G containing all the vertices in the cluster and all the edges of G between
	 * the vertices of the cluster
	 * 
	 * @return the induced graph
	 */
	Graph<V, E> getInducedGraph();

}
