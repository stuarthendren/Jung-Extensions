package net.stuarthendren.jung.cluster.vertex.impl;

import java.util.Collection;

import net.stuarthendren.jung.cluster.vertex.Cluster;
import net.stuarthendren.jung.graph.BackedInducedGraph;
import edu.uci.ics.jung.graph.Graph;

/**
 * Implementation of {@link Cluster} interface that uses a {@link BackedInducedGraph}.
 * 
 * Additions and removals can be made to the cluster and will be updated in the induced graph
 * 
 * @author Stuart Hendren
 * 
 * @param <V>
 *            vertex type
 * @param <E>
 *            edge type
 */
@SuppressWarnings("serial")
public class BackedClusterImpl<V, E> extends AbstractClusterImpl<V, E> {

	public BackedClusterImpl(Graph<V, E> graph) {
		super(graph);
	}

	public BackedClusterImpl(Graph<V, E> graph, Collection<? extends V> members) {
		super(graph, members);
	}

	@Override
	protected Graph<V, E> createInducedGraph(Graph<V, E> graph) {
		return new BackedInducedGraph<V, E>(graph, this);
	}

}
