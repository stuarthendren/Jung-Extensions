package net.stuarthendren.jung.cluster.vertex.impl;

import java.util.Collection;
import java.util.HashSet;

import net.stuarthendren.jung.cluster.vertex.Cluster;
import edu.uci.ics.jung.graph.Graph;

@SuppressWarnings("serial")
public abstract class AbstractClusterImpl<V, E> extends HashSet<V> implements Cluster<V, E> {

	private final Graph<V, E> graph;
	private final Graph<V, E> inducedGraph;

	public AbstractClusterImpl(Graph<V, E> graph) {
		super();
		this.graph = graph;
		this.inducedGraph = createInducedGraph(this.graph);
	}

	public AbstractClusterImpl(Graph<V, E> graph, Collection<? extends V> members) {
		super(members);
		this.graph = graph;
		this.inducedGraph = createInducedGraph(this.graph);
	}

	protected abstract Graph<V, E> createInducedGraph(Graph<V, E> graph);

	@Override
	public Graph<V, E> getGraph() {
		return graph;
	}

	@Override
	public double getDensity() {
		return ((double) inducedGraph.getEdgeCount()) / (size() * (size() - 1));
	}

	@Override
	public Graph<V, E> getInducedGraph() {
		return inducedGraph;
	}
}
