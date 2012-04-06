package net.stuarthendren.jung.dendrogram.graph.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.stuarthendren.jung.dendrogram.graph.DendrogramNode;
import net.stuarthendren.jung.partition.Partition;
import net.stuarthendren.jung.partition.impl.PartitionImpl;

public class KeyedDendogramIterator<T, K> implements Iterator<Partition<T>> {

	private final Map<DendrogramNode<T>, T> tally = new HashMap<DendrogramNode<T>, T>();

	private Partition<T> last;

	private Iterator<KeyedDendrogramNode<T, K>> sortedNodes;

	private DendrogramNode<T> next;

	private DendrogramNode<T> rootNode;

	public KeyedDendogramIterator(KeyedDendrogram<T, K> keyedDendogram) {
		rootNode = keyedDendogram.getRootNode();
		sortedNodes = keyedDendogram.getKeyedIterator();
		List<T> sortedLeafs = keyedDendogram.getSortedLeafs();
		last = PartitionImpl.createMaximumPartition(sortedLeafs);
		for (T t : sortedLeafs) {
			tally.put(keyedDendogram.getLeaf(t), t);
		}
		if (sortedNodes.hasNext()) {
			next = sortedNodes.next();
		}
	}

	@Override
	public boolean hasNext() {
		return last != null;
	}

	@Override
	public Partition<T> next() {
		Partition<T> current = last.copy();
		calculateNext();
		return current;
	}

	@SuppressWarnings("unchecked")
	private void calculateNext() {
		if (next == null) {
			// stop
			last = null;
			return;
		}
		if (next == rootNode) {
			mergeClusters(rootNode);
			next = null;
			return;
		}

		if (sortedNodes.hasNext()) {
			K key = ((KeyedDendrogramNode<T, K>) next).getKey();
			while (key.equals(((KeyedDendrogramNode<T, K>) next).getKey()) && sortedNodes.hasNext()) {
				T previous = mergeClusters(next);
				tally.put(next, previous);
				next = sortedNodes.next();
			}
		} else {
			T previous = mergeClusters(next);
			tally.put(next, previous);
			next = rootNode;
		}
	}

	private T mergeClusters(DendrogramNode<T> node) {
		T previous = null;
		for (DendrogramNode<T> child : node.getChildren()) {
			T removed = tally.remove(child);
			if (previous != null) {
				last.mergeCluster(previous, removed);
			}
			previous = removed;
		}
		return previous;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Cannot remove partition from dendrogram");
	}

}
