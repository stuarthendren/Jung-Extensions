package net.stuarthendren.jung.graph;

import java.util.Collection;

import org.apache.commons.collections15.CollectionUtils;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * Base implementation of induced graph calls come back to the unimplemented getEdges method
 * 
 * @author Stuart Hendren
 * 
 * @param <V>
 * @param <E>
 */
public abstract class AbstractInducedGraph<V, E> implements Graph<V, E> {

	protected final Graph<V, E> graph;
	protected final Collection<V> vertices;

	/**
	 * Create the subgraph of <code>graph</code> induces by the <code>vertices</code> This implementation is backed by
	 * the input collection and changes in the original collection will be reflected by changes in the induced graph
	 * 
	 * @param graph
	 * @param vertices
	 */
	public AbstractInducedGraph(Graph<V, E> graph, Collection<V> vertices) {
		this.graph = graph;
		this.vertices = vertices;
	}

	@Override
	public Collection<V> getVertices() {
		return vertices;
	}

	@Override
	public boolean containsVertex(V vertex) {
		return vertices.contains(vertex);
	}

	@Override
	public boolean containsEdge(E edge) {
		return getEdges().contains(edge);
	}

	@Override
	public int getEdgeCount() {
		return getEdges().size();
	}

	@Override
	public int getVertexCount() {
		return vertices.size();
	}

	@Override
	public Collection<V> getNeighbors(V vertex) {
		return CollectionUtils.intersection(vertices, graph.getNeighbors(vertex));
	}

	@Override
	public Collection<E> getIncidentEdges(V vertex) {
		return CollectionUtils.intersection(graph.getIncidentEdges(vertex), getEdges());
	}

	@Override
	public Collection<V> getIncidentVertices(E edge) {
		if (containsEdge(edge)) {
			return graph.getIncidentVertices(edge);
		}
		return null;
	}

	@Override
	public E findEdge(V v1, V v2) {
		Collection<E> intersection = CollectionUtils.intersection(graph.findEdgeSet(v1, v2), getEdges());
		if (intersection.size() > 0) {
			return intersection.iterator().next();
		}
		return null;
	}

	@Override
	public Collection<E> findEdgeSet(V v1, V v2) {
		return CollectionUtils.intersection(graph.findEdgeSet(v1, v2), getEdges());
	}

	@Override
	public boolean addVertex(V vertex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(E edge, Collection<? extends V> vertices, EdgeType edge_type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeVertex(V vertex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeEdge(E edge) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNeighbor(V v1, V v2) {
		if (containsVertex(v1) && containsVertex(v2)) {
			return graph.isNeighbor(v1, v2);
		}
		return false;
	}

	@Override
	public boolean isIncident(V vertex, E edge) {
		if (containsVertex(vertex) && containsEdge(edge)) {
			return graph.isIncident(vertex, edge);
		}
		return false;
	}

	@Override
	public int degree(V vertex) {
		return getIncidentEdges(vertex).size();
	}

	@Override
	public int getNeighborCount(V vertex) {
		return getNeighbors(vertex).size();
	}

	@Override
	public int getIncidentCount(E edge) {
		Collection<V> incidentVertices = getIncidentVertices(edge);
		if (incidentVertices != null) {
			return incidentVertices.size();
		}
		return 0;
	}

	@Override
	public EdgeType getEdgeType(E edge) {
		return graph.getEdgeType(edge);
	}

	@Override
	public EdgeType getDefaultEdgeType() {
		return graph.getDefaultEdgeType();
	}

	@Override
	public Collection<E> getEdges(EdgeType edge_type) {
		return CollectionUtils.intersection(graph.getEdges(edge_type), getEdges());
	}

	@Override
	public int getEdgeCount(EdgeType edge_type) {
		return getEdges(edge_type).size();
	}

	@Override
	public Collection<E> getInEdges(V vertex) {
		return CollectionUtils.intersection(graph.getInEdges(vertex), getEdges());
	}

	@Override
	public Collection<E> getOutEdges(V vertex) {
		return CollectionUtils.intersection(graph.getOutEdges(vertex), getEdges());
	}

	@Override
	public Collection<V> getPredecessors(V vertex) {
		return CollectionUtils.intersection(graph.getPredecessors(vertex), vertices);
	}

	@Override
	public Collection<V> getSuccessors(V vertex) {
		return CollectionUtils.intersection(graph.getSuccessors(vertex), vertices);
	}

	@Override
	public int inDegree(V vertex) {
		return getInEdges(vertex).size();
	}

	@Override
	public int outDegree(V vertex) {
		return getOutEdges(vertex).size();
	}

	@Override
	public boolean isPredecessor(V v1, V v2) {
		return getPredecessors(v1).contains(v2);
	}

	@Override
	public boolean isSuccessor(V v1, V v2) {
		return getSuccessors(v1).contains(v2);
	}

	@Override
	public int getPredecessorCount(V vertex) {
		return getPredecessors(vertex).size();
	}

	@Override
	public int getSuccessorCount(V vertex) {
		return getSuccessors(vertex).size();
	}

	@Override
	public V getSource(E directed_edge) {
		V v = graph.getSource(directed_edge);
		if (containsVertex(v)) {
			return v;
		}
		return null;
	}

	@Override
	public V getDest(E directed_edge) {
		V v = graph.getDest(directed_edge);
		if (containsVertex(v)) {
			return v;
		}
		return null;
	}

	@Override
	public boolean isSource(V vertex, E edge) {
		return vertex.equals(getSource(edge));
	}

	@Override
	public boolean isDest(V vertex, E edge) {
		return vertex.equals(getDest(edge));
	}

	@Override
	public boolean addEdge(E e, V v1, V v2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Pair<V> getEndpoints(E edge) {
		Pair<V> endpoints = graph.getEndpoints(edge);
		if (vertices.containsAll(endpoints)) {
			return endpoints;
		}
		return null;
	}

	@Override
	public V getOpposite(V vertex, E edge) {
		V v = graph.getOpposite(vertex, edge);
		if (vertices.contains(v)) {
			return v;
		}
		return null;
	}

}
