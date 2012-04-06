package net.stuarthendren.jung.cluster.edge.impl;

public class KeyedEdgePair<V, E> extends EdgePair<E> {

	private final V keyVertex;

	public KeyedEdgePair(V keyVertex, E edge1, E edge2) {
		super(edge1, edge2);
		this.keyVertex = keyVertex;
	}

	public V getKeyVertex() {
		return keyVertex;
	}

	public EdgePair<E> getEdgePair() {
		return new EdgePair<E>(getEdge1(), getEdge2());
	}
}