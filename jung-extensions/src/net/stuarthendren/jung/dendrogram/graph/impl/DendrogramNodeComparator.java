package net.stuarthendren.jung.dendrogram.graph.impl;

import java.util.Comparator;

import net.stuarthendren.jung.dendrogram.graph.DendrogramNode;

public class DendrogramNodeComparator<T, K> implements Comparator<DendrogramNode<T>> {

	private final Comparator<K> comparator;

	private final DendrogramNode<T> rootNode;

	public DendrogramNodeComparator(Comparator<K> comparator, DendrogramNode<T> rootNode) {
		this.comparator = comparator;
		this.rootNode = rootNode;

	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(DendrogramNode<T> node1, DendrogramNode<T> node2) {
		if (node1 == node2) {
			return 0;
		}

		if (node1 == rootNode) {
			return +1;
		}
		if (node2 == rootNode) {
			return -1;
		}
		if (node1.isLeaf() && node2.isLeaf()) {
			return 0;
		}
		if (node1.isLeaf()) {
			return -1;
		}
		if (node2.isLeaf()) {
			return 1;
		}
		return comparator.compare(((KeyedDendrogramNode<T, K>) node1).getKey(),
				((KeyedDendrogramNode<T, K>) node2).getKey());
	}
}
