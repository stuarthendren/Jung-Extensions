package net.stuarthendren.jung.dendrogram.graph.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.stuarthendren.jung.dendrogram.graph.DendrogramNode;
import net.stuarthendren.jung.dendrogram.graph.GraphDendrogram;
import net.stuarthendren.jung.partition.Partition;
import net.stuarthendren.jung.partition.impl.PartitionImpl;

/**
 * An EdgeDemogram is a list of {@link PartitionImpl}s that represents a hierarchical clustering of the edges of a
 * graph.
 * 
 * @author Stuart Hendren
 * 
 * @param <K>
 *            the key type
 * @param <T>
 *            the edge type
 */
public class KeyedDendrogram<T, K> implements GraphDendrogram<T> {

	private final Map<T, LeafDendrogramNode<T>> leafs;

	private final DendrogramNodeImpl<T> rootNode = new DendrogramNodeImpl<T>();

	private final DendrogramNodeComparator<T, K> comparator;

	private final Set<KeyedDendrogramNode<T, K>> keyedNodes;

	public KeyedDendrogram(Collection<T> set, final Comparator<K> comparator) {
		this.comparator = new DendrogramNodeComparator<T, K>(comparator, rootNode);
		keyedNodes = new HashSet<KeyedDendrogramNode<T, K>>();
		// keyedNodes.add(rootNode);
		this.leafs = new HashMap<T, LeafDendrogramNode<T>>();
		for (T t : set) {
			LeafDendrogramNode<T> leafNode = new LeafDendrogramNode<T>(t);
			leafs.put(t, leafNode);
			// keyedNodes.add(leafNode);
		}
		for (LeafDendrogramNode<T> leaf : leafs.values()) {
			leaf.setParent(rootNode);
		}
	}

	@Override
	public Iterator<Partition<T>> getPartitions() {
		return new KeyedDendogramIterator<T, K>(this);
	}

	@Override
	public DendrogramNode<T> getLeaf(T t) {
		return leafs.get(t);
	}

	public LeafDendrogramNode<T> getLeafNode(T t) {
		return leafs.get(t);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		writeChildren(rootNode, builder);
		return builder.toString();
	}

	private void writeChildren(DendrogramNode<T> node, StringBuilder builder) {
		builder.append("[");
		for (DendrogramNode<T> child : node.getChildren()) {
			if (child.isLeaf()) {
				builder.append(((LeafDendrogramNode<T>) child).getLeaf());
				builder.append(", ");
			} else {
				writeChildren(child, builder);
			}
		}
		if (", ".equals(builder.substring(builder.length() - 2))) {
			builder.delete(builder.length() - 2, builder.length());
		}
		builder.append("]");

	}

	public String getPartitionString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Partition<T>> partitions = getPartitions();
		while (partitions.hasNext()) {
			builder.append(partitions.next().toString());
			builder.append("\n");
		}
		return builder.toString();
	}

	private void prune(DendrogramNodeImpl<T> potentialPruneNode) {
		if (potentialPruneNode == null) {
			return;
		}
		if (potentialPruneNode.getChildren().size() == 1) {
			if (potentialPruneNode == rootNode) {
				moveChildren(rootNode, potentialPruneNode.children.iterator().next());
			} else {
				DendrogramNodeImpl<T> parent = potentialPruneNode.parent;
				potentialPruneNode.children.iterator().next().setParent(parent);
				prune(remove(potentialPruneNode));
			}
		} else if (potentialPruneNode.getChildren().size() == 0) {
			prune(remove(potentialPruneNode));
		}
	}

	private DendrogramNodeImpl<T> combine(DendrogramNodeImpl<T> node1, DendrogramNodeImpl<T> node2) {
		DendrogramNodeImpl<T> combined = null;
		if (node1.getChildren().size() > node2.getChildren().size()) {
			combined = moveChildren(node1, node2);
		} else {
			combined = moveChildren(node2, node1);
		}
		return combined;
	}

	private DendrogramNodeImpl<T> remove(DendrogramNodeImpl<T> node) {
		if (node == null) {
			return null;
		}
		if (node.getChildren().size() != 0) {
			throw new IllegalArgumentException("Can only remove empty nodes");
		}
		keyedNodes.remove(node);
		return node.setParent(null);
	}

	private DendrogramNodeImpl<T> moveChildren(DendrogramNodeImpl<T> keepNode, DendrogramNodeImpl<T> removeNode) {
		ArrayList<DendrogramNodeImpl<T>> children = new ArrayList<DendrogramNodeImpl<T>>();
		children.addAll(removeNode.children);
		for (DendrogramNodeImpl<T> child : children) {
			child.setParent(keepNode);
		}
		remove(removeNode);
		return keepNode;
	}

	@Override
	public DendrogramNode<T> getRootNode() {
		return rootNode;
	}

	@Override
	public List<T> getSortedLeafs() {
		List<T> sorted = new ArrayList<T>();
		walkChildren(rootNode, sorted);
		return sorted;
	}

	private void walkChildren(DendrogramNode<T> node, List<T> sorted) {
		for (DendrogramNode<T> child : node.getChildren()) {
			if (child.isLeaf()) {
				sorted.add(((LeafDendrogramNode<T>) child).getLeaf());
			} else {
				walkChildren(child, sorted);
			}
		}
	}

	@Override
	public int getDepth(DendrogramNode<T> node) {
		int depth = 0;
		DendrogramNode<T> test = node;
		while (test != rootNode) {
			depth++;
			test = test.getParent();
		}
		return depth;
	}

	@Override
	public int getHeight() {
		int height = 0;
		for (LeafDendrogramNode<T> leaf : leafs.values()) {
			int branchHeight = getDepth(leaf);
			height = Math.max(height, branchHeight);
		}
		return height;
	}

	public int getNumberOfPartitions() {
		Iterator<Partition<T>> partitions = getPartitions();
		int height = 0;
		while (partitions.hasNext()) {
			partitions.next();
			height++;
		}
		return height;
	}

	public void merge(K key, T t1, T t2) {
		LeafDendrogramNode<T> leaf1 = getLeafNode(t1);
		LeafDendrogramNode<T> leaf2 = getLeafNode(t2);
		DendrogramNodeImpl<T> minimalParent1 = getMinimalParentNode(key, leaf1);
		DendrogramNodeImpl<T> minimalParent2 = getMinimalParentNode(key, leaf2);
		if (minimalParent1 == minimalParent2) {
			return;
		}
		DendrogramNodeImpl<T> parent = merge(minimalParent1.parent, minimalParent2.parent);

		DendrogramNodeImpl<T> oldParent1 = null;
		DendrogramNodeImpl<T> oldParent2 = null;

		KeyedDendrogramNode<T, K> newNode = new KeyedDendrogramNode<T, K>(key);
		if (comparator.compare(parent, newNode) > 0) {
			newNode.setParent(parent);
			oldParent1 = minimalParent1.setParent(newNode);
			oldParent2 = minimalParent2.setParent(newNode);
			keyedNodes.add(newNode);
		} else {
			oldParent1 = minimalParent1.setParent(parent);
			oldParent2 = minimalParent2.setParent(parent);
		}

		prune(oldParent1);
		prune(oldParent2);

		for (DendrogramNode<T> node : keyedNodes) {
			if (node.isLeaf()) {
			} else if (node.getChildren().size() == 0) {
				throw new IllegalArgumentException(node.toString());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private DendrogramNodeImpl<T> merge(DendrogramNodeImpl<T> node1, DendrogramNodeImpl<T> node2) {
		if (node1 == null && node2 == null) {
			return rootNode;
		}
		if (node2 != null && (node1 == null || node1 == rootNode)) {
			return node2;
		}
		if (node1 != null && (node2 == null || node2 == rootNode)) {
			return node1;
		}
		if (node1 == node2) {
			return node1;
		}
		int compare = comparator.compare(node1, node2);
		if (compare == 0) {
			return combinedMerge(node1, node2);
		} else {
			DendrogramNodeImpl<T> highest = null;
			DendrogramNodeImpl<T> lowest = null;
			if (compare > 0) {
				highest = node1;
				lowest = node2;
			} else if (compare < 0) {
				highest = node2;
				lowest = node1;
			}
			if (rootNode == highest) {
				return lowest;
			}
			DendrogramNodeImpl<T> minimalParentNode = getMinimalParentNode(
					((KeyedDendrogramNode<T, K>) highest).getKey(), lowest);
			if (minimalParentNode.getParent() == highest) {
				return lowest;
			}
			int compareWithMinimal = comparator.compare(minimalParentNode, highest);
			if (compareWithMinimal == 0) {
				combinedMerge(minimalParentNode, highest);
			} else {
				DendrogramNodeImpl<T> parent = merge(minimalParentNode.parent, highest.parent);
				DendrogramNodeImpl<T> oldParent1 = null;
				DendrogramNodeImpl<T> oldParent2 = null;
				oldParent1 = minimalParentNode.setParent(highest);
				if (comparator.compare(highest, parent) == 0) {
					combinedMerge(highest, parent);
				} else {
					oldParent2 = highest.setParent(parent);
				}
				prune(oldParent1);
				prune(oldParent2);
			}

			return lowest;
		}
	}

	private DendrogramNodeImpl<T> combinedMerge(DendrogramNodeImpl<T> node1, DendrogramNodeImpl<T> node2) {
		DendrogramNodeImpl<T> parent = merge(node1.parent, node2.parent);
		DendrogramNodeImpl<T> combined = combine(node1, node2);
		prune(combined.setParent(parent));
		return combined;
	}

	private DendrogramNodeImpl<T> getMinimalParentNode(K key, DendrogramNodeImpl<T> leaf) {
		DendrogramNodeImpl<T> minimal = leaf;
		DendrogramNodeImpl<T> dummy = new KeyedDendrogramNode<T, K>(key);
		while (minimal != rootNode && comparator.compare(minimal.getParent(), dummy) < 0) {
			minimal = minimal.parent;
		}
		return minimal;

	}

	public Iterator<KeyedDendrogramNode<T, K>> getKeyedIterator() {
		ArrayList<KeyedDendrogramNode<T, K>> list = new ArrayList<KeyedDendrogramNode<T, K>>(keyedNodes);
		Collections.sort(list, comparator);
		return list.iterator();
	}
}
