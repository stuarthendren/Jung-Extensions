package net.stuarthendren.jung.cluster.vertex.impl;

import java.util.Collection;

import net.stuarthendren.jung.cluster.vertex.Cluster;
import net.stuarthendren.jung.graph.StaticInducedGraph;
import edu.uci.ics.jung.graph.Graph;

/**
 * Implementation of {@link Cluster} interface that uses a {@link StaticInducedGraph}
 * 
 * Cannot be modified after creation.
 * 
 * @author Stuart Hendren
 * 
 * @param <V>
 *            vertex type
 * @param <E>
 *            edge type
 */
@SuppressWarnings("serial")
public class StaticClusterImpl<V, E> extends AbstractClusterImpl<V, E> {

	private boolean loaded = false;

	public StaticClusterImpl(Graph<V, E> graph, Collection<? extends V> members) {
		super(graph, members);
		loaded = true;
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(V e) {
		if (loaded) {
			throw new UnsupportedOperationException();
		} else {
			return super.add(e);
		}
	}

	@Override
	public boolean addAll(Collection<? extends V> c) {
		if (loaded) {
			throw new UnsupportedOperationException();
		} else {
			return super.addAll(c);
		}
	}

	@Override
	protected Graph<V, E> createInducedGraph(Graph<V, E> graph) {
		return new StaticInducedGraph<V, E>(graph, this);
	}
}
