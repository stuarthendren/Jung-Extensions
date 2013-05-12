package net.stuarthendren.jung.cluster.vertex.flow;

import static net.stuarthendren.jung.utils.Wrapper.emptyWraper;
import static net.stuarthendren.jung.utils.Wrapper.wrap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.stuarthendren.jung.utils.Wrapper;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * A {@link CutClusterGraph} that builds a whole new graph, by wrapping each
 * vertex and edge from the original then adding the target and extra edges.
 * 
 * This will be faster than the {@link DelegatingCutClusterGraph} but uses more
 * memory.
 * 
 * @param <V>
 * @param <E>
 */
@SuppressWarnings("serial")
public class WrappedCutClusterGraph<V, E> extends DirectedSparseGraph<Wrapper<V>, Wrapper<E>> implements
		CutClusterGraph<Wrapper<V>, Wrapper<E>> {

	private final Wrapper<V> target = emptyWraper();

	@SuppressWarnings("unchecked")
	public WrappedCutClusterGraph(DirectedGraph<V, E> baseGraph) {

		Map<V, Wrapper<V>> vertexMap = new HashMap<V, Wrapper<V>>();

		for (V v : baseGraph.getVertices()) {
			Wrapper<V> wrapper = wrap(v);
			vertexMap.put(v, wrapper);
			super.addVertex(wrapper);
		}

		for (E e : baseGraph.getEdges()) {
			Pair<V> endpoints = baseGraph.getEndpoints(e);
			super.addEdge(wrap(e), vertexMap.get(endpoints.getFirst()), vertexMap.get(endpoints.getSecond()),
					EdgeType.DIRECTED);
		}

		super.addVertex(target);

		for (Wrapper<V> v : vertexMap.values()) {
			super.addEdge((Wrapper<E>) emptyWraper(), v, target, EdgeType.DIRECTED);
		}
	}

	@Override
	public Wrapper<V> getTarget() {
		return target;
	}

	@Override
	public boolean addVertex(Wrapper<V> vertex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(Wrapper<E> edge, Collection<? extends Wrapper<V>> vertices) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(Wrapper<E> e, Wrapper<V> v1, Wrapper<V> v2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(Wrapper<E> edge, Collection<? extends Wrapper<V>> vertices, EdgeType edge_type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addEdge(Wrapper<E> e, Wrapper<V> v1, Wrapper<V> v2, EdgeType edgeType) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeVertex(Wrapper<V> vertex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeEdge(Wrapper<E> edge) {
		throw new UnsupportedOperationException();
	}
}
