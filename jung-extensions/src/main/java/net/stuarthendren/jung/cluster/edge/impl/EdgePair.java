package net.stuarthendren.jung.cluster.edge.impl;

public class EdgePair<E> {
	private final E edge1;
	private final E edge2;

	public EdgePair(E edge1, E edge2) {
		super();
		this.edge1 = edge1;
		this.edge2 = edge2;
	}

	public E getEdge1() {
		return edge1;
	}

	public E getEdge2() {
		return edge2;
	}
}