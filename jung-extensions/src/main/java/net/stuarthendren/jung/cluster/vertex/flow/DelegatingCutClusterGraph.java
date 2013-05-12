package net.stuarthendren.jung.cluster.vertex.flow;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import net.stuarthendren.jung.graph.DelegatingGraph;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * Implementation of {@link CutClusterGraph} that delegates through to the
 * original graph where possible.
 * 
 * This will use less memory than the {@link WrappedCutClusterGraph} but will be
 * slower
 * 
 * @param <V>
 * @param <E>
 */
public class DelegatingCutClusterGraph<V, E> extends DelegatingGraph<V, E> implements CutClusterGraph<V, E> {

	private final Map<V, E> additionalEdges = new HashMap<V, E>();

	private final V target;

	public DelegatingCutClusterGraph(DirectedGraph<V, E> delegate, V target, Factory<E> edgeFactory) {
		super(delegate);
		this.target = target;
		for (V v : delegate.getVertices()) {
			additionalEdges.put(v, edgeFactory.create());
		}
	}

	@Override
	public V getTarget() {
		return target;
	}

	private V getAdditionalVertex(E edge) {
		for (Entry<V, E> entry : additionalEdges.entrySet()) {
			if (entry.getValue().equals(edge)) {
				return entry.getKey();
			}
		}
		return null;
	}

	@Override
	public Collection<E> getEdges() {
		Collection<E> edges = new HashSet<E>(super.getEdges());
		edges.addAll(additionalEdges.values());
		return edges;
	}

	@Override
	public Collection<E> getInEdges(V vertex) {
		if (target.equals(vertex)) {
			return additionalEdges.values();
		} else {
			return super.getInEdges(vertex);
		}
	}

	@Override
	public Collection<V> getVertices() {
		Collection<V> verticies = new HashSet<V>(super.getVertices());
		verticies.add(target);
		return verticies;
	}

	@Override
	public Collection<E> getOutEdges(V vertex) {
		if (target.equals(vertex)) {
			return Collections.emptySet();
		} else {
			Collection<E> outEdges = new HashSet<E>(super.getOutEdges(vertex));
			outEdges.add(additionalEdges.get(vertex));
			return outEdges;
		}
	}

	@Override
	public boolean containsVertex(V vertex) {
		if (target.equals(vertex)) {
			return true;
		}
		return super.containsVertex(vertex);
	}

	@Override
	public Collection<V> getPredecessors(V vertex) {
		if (target.equals(vertex)) {
			return super.getVertices();
		}
		return super.getPredecessors(vertex);
	}

	@Override
	public boolean containsEdge(E edge) {
		if (additionalEdges.containsValue(edge)) {
			return true;
		}
		return super.containsEdge(edge);
	}

	@Override
	public int getEdgeCount() {
		return super.getEdgeCount() + additionalEdges.size();
	}

	@Override
	public Collection<V> getSuccessors(V vertex) {
		if (target.equals(vertex)) {
			return Collections.emptySet();
		} else {
			Collection<V> successors = new HashSet<V>(super.getSuccessors(vertex));
			successors.add(target);
			return successors;
		}
	}

	@Override
	public int getVertexCount() {
		return super.getVertexCount() + 1;
	}

	@Override
	public Collection<V> getNeighbors(V vertex) {
		if (target.equals(vertex)) {
			return super.getVertices();
		} else {
			Collection<V> neighbors = new HashSet<V>(super.getNeighbors(vertex));
			neighbors.add(target);
			return neighbors;
		}
	}

	@Override
	public int inDegree(V vertex) {
		return getInEdges(vertex).size();
	}

	@Override
	public Collection<E> getIncidentEdges(V vertex) {
		if (target.equals(vertex)) {
			return additionalEdges.values();
		} else {
			Collection<E> incident = new HashSet<E>(super.getIncidentEdges(vertex));
			incident.add(additionalEdges.get(vertex));
			return incident;
		}
	}

	@Override
	public int outDegree(V vertex) {
		return getOutEdges(vertex).size();
	}

	@Override
	public Collection<V> getIncidentVertices(E edge) {
		if (additionalEdges.containsValue(edge)) {
			return new Pair<V>(getAdditionalVertex(edge), target);
		}
		return super.getIncidentVertices(edge);
	}

	@Override
	public boolean isPredecessor(V v1, V v2) {
		if (target.equals(v2)) {
			return true;
		} else if (target.equals(v1)) {
			return false;
		}
		return super.isPredecessor(v1, v2);
	}

	@Override
	public boolean isSuccessor(V v1, V v2) {
		if (target.equals(v1)) {
			return true;
		} else if (target.equals(v2)) {
			return false;
		}
		return super.isSuccessor(v1, v2);
	}

	@Override
	public E findEdge(V v1, V v2) {
		if (target.equals(v1)) {
			return additionalEdges.get(v2);
		} else if (target.equals(v2)) {
			return additionalEdges.get(v1);
		}
		return super.findEdge(v1, v2);
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
		if (additionalEdges.containsValue(directed_edge)) {
			return getAdditionalVertex(directed_edge);
		}
		return super.getSource(directed_edge);
	}

	@Override
	public Collection<E> findEdgeSet(V v1, V v2) {
		if (target.equals(v1)) {
			return Collections.singleton(additionalEdges.get(v2));
		} else if (target.equals(v2)) {
			return Collections.singleton(additionalEdges.get(v1));
		}
		return super.findEdgeSet(v1, v2);
	}

	@Override
	public V getDest(E directed_edge) {
		if (additionalEdges.containsValue(directed_edge)) {
			return target;
		}
		return super.getDest(directed_edge);
	}

	@Override
	public boolean isSource(V vertex, E edge) {
		if (edge.equals(additionalEdges.get(vertex))) {
			return true;
		}
		return super.isSource(vertex, edge);
	}

	@Override
	public boolean addVertex(V vertex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isDest(V vertex, E edge) {
		if (target.equals(vertex)) {
			return additionalEdges.containsValue(edge);
		}
		return super.isDest(vertex, edge);
	}

	@Override
	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(E e, V v1, V v2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(E edge, Collection<? extends V> vertices, EdgeType edge_type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeVertex(V vertex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Pair<V> getEndpoints(E edge) {
		if (additionalEdges.containsValue(edge)) {
			return new Pair<V>(getAdditionalVertex(edge), target);
		}
		return super.getEndpoints(edge);
	}

	@Override
	public V getOpposite(V vertex, E edge) {
		if (additionalEdges.containsValue(edge)) {
			if (target.equals(vertex)) {
				return getAdditionalVertex(edge);
			} else {
				return target;
			}
		}
		return super.getOpposite(vertex, edge);
	}

	@Override
	public boolean removeEdge(E edge) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNeighbor(V v1, V v2) {
		return getNeighbors(v1).contains(v2);
	}

	@Override
	public boolean isIncident(V vertex, E edge) {
		return getIncidentVertices(edge).contains(vertex);
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
		return 2;
	}

	@Override
	public EdgeType getEdgeType(E edge) {
		return EdgeType.DIRECTED;
	}

	@Override
	public EdgeType getDefaultEdgeType() {
		return EdgeType.DIRECTED;
	}

	@Override
	public Collection<E> getEdges(EdgeType edge_type) {
		if (EdgeType.UNDIRECTED.equals(edge_type)) {
			return Collections.emptySet();
		} else {
			return getEdges();
		}
	}

	@Override
	public int getEdgeCount(EdgeType edge_type) {
		if (EdgeType.UNDIRECTED.equals(edge_type)) {
			return 0;
		} else {
			return super.getEdgeCount() + additionalEdges.size();
		}
	}

}
