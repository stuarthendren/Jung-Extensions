package net.stuarthendren.jung.graph;

import java.util.Collection;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * DelegateGraph delegates calls to the given graph.
 * 
 * @author Stuart Hendren
 *
 * @param <V>
 * @param <E>
 */
public class DelegatingGraph<V, E> implements Graph<V, E> {
	
	private final Graph<V, E> delegate;

	public Collection<E> getEdges() {
		return delegate.getEdges();
	}

	public Collection<E> getInEdges(V vertex) {
		return delegate.getInEdges(vertex);
	}

	public Collection<V> getVertices() {
		return delegate.getVertices();
	}

	public Collection<E> getOutEdges(V vertex) {
		return delegate.getOutEdges(vertex);
	}

	public boolean containsVertex(V vertex) {
		return delegate.containsVertex(vertex);
	}

	public Collection<V> getPredecessors(V vertex) {
		return delegate.getPredecessors(vertex);
	}

	public boolean containsEdge(E edge) {
		return delegate.containsEdge(edge);
	}

	public int getEdgeCount() {
		return delegate.getEdgeCount();
	}

	public Collection<V> getSuccessors(V vertex) {
		return delegate.getSuccessors(vertex);
	}

	public int getVertexCount() {
		return delegate.getVertexCount();
	}

	public Collection<V> getNeighbors(V vertex) {
		return delegate.getNeighbors(vertex);
	}

	public int inDegree(V vertex) {
		return delegate.inDegree(vertex);
	}

	public Collection<E> getIncidentEdges(V vertex) {
		return delegate.getIncidentEdges(vertex);
	}

	public int outDegree(V vertex) {
		return delegate.outDegree(vertex);
	}

	public Collection<V> getIncidentVertices(E edge) {
		return delegate.getIncidentVertices(edge);
	}

	public boolean isPredecessor(V v1, V v2) {
		return delegate.isPredecessor(v1, v2);
	}

	public boolean isSuccessor(V v1, V v2) {
		return delegate.isSuccessor(v1, v2);
	}

	public E findEdge(V v1, V v2) {
		return delegate.findEdge(v1, v2);
	}

	public int getPredecessorCount(V vertex) {
		return delegate.getPredecessorCount(vertex);
	}

	public int getSuccessorCount(V vertex) {
		return delegate.getSuccessorCount(vertex);
	}

	public V getSource(E directed_edge) {
		return delegate.getSource(directed_edge);
	}

	public Collection<E> findEdgeSet(V v1, V v2) {
		return delegate.findEdgeSet(v1, v2);
	}

	public V getDest(E directed_edge) {
		return delegate.getDest(directed_edge);
	}

	public boolean isSource(V vertex, E edge) {
		return delegate.isSource(vertex, edge);
	}

	public boolean addVertex(V vertex) {
		return delegate.addVertex(vertex);
	}

	public boolean isDest(V vertex, E edge) {
		return delegate.isDest(vertex, edge);
	}

	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		return delegate.addEdge(edge, vertices);
	}

	public boolean addEdge(E e, V v1, V v2) {
		return delegate.addEdge(e, v1, v2);
	}

	public boolean addEdge(E edge, Collection<? extends V> vertices, EdgeType edge_type) {
		return delegate.addEdge(edge, vertices, edge_type);
	}

	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		return delegate.addEdge(e, v1, v2, edgeType);
	}

	public boolean removeVertex(V vertex) {
		return delegate.removeVertex(vertex);
	}

	public Pair<V> getEndpoints(E edge) {
		return delegate.getEndpoints(edge);
	}

	public V getOpposite(V vertex, E edge) {
		return delegate.getOpposite(vertex, edge);
	}

	public boolean removeEdge(E edge) {
		return delegate.removeEdge(edge);
	}

	public boolean isNeighbor(V v1, V v2) {
		return delegate.isNeighbor(v1, v2);
	}

	public boolean isIncident(V vertex, E edge) {
		return delegate.isIncident(vertex, edge);
	}

	public int degree(V vertex) {
		return delegate.degree(vertex);
	}

	public int getNeighborCount(V vertex) {
		return delegate.getNeighborCount(vertex);
	}

	public int getIncidentCount(E edge) {
		return delegate.getIncidentCount(edge);
	}

	public EdgeType getEdgeType(E edge) {
		return delegate.getEdgeType(edge);
	}

	public EdgeType getDefaultEdgeType() {
		return delegate.getDefaultEdgeType();
	}

	public Collection<E> getEdges(EdgeType edge_type) {
		return delegate.getEdges(edge_type);
	}

	public int getEdgeCount(EdgeType edge_type) {
		return delegate.getEdgeCount(edge_type);
	}

	public DelegatingGraph(Graph<V, E> graph){
		this.delegate = graph;
	}

}
