package net.stuarthendren.jung.dendrogram.graph.impl;


public class KeyedDendrogramNode<T, K> extends DendrogramNodeImpl<T> {

	private final K key;

	public KeyedDendrogramNode(K key) {
		super();
		this.key = key;
	}

	public K getKey() {
		return key;
	}

}
