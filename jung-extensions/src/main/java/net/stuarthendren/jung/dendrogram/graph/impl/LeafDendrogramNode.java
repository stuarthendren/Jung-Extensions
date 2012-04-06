package net.stuarthendren.jung.dendrogram.graph.impl;

import java.util.Collections;
import java.util.Set;

public class LeafDendrogramNode<T> extends DendrogramNodeImpl<T> {

	private final T leaf;

	public LeafDendrogramNode(T leaf) {
		super();
		this.leaf = leaf;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	public T getLeaf() {
		return leaf;
	}

	@Override
	public String toString() {
		return leaf.toString();
	}

	@Override
	public Set<T> getContents() {
		return Collections.singleton(leaf);
	}

}
