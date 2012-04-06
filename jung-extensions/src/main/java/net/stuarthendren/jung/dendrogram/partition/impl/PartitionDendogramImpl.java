package net.stuarthendren.jung.dendrogram.partition.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.stuarthendren.jung.dendrogram.partition.PartitionDendrogram;
import net.stuarthendren.jung.partition.Partition;

/**
 * An EdgeDemogram is a list of {@link EdgePartition}s that represents a hierarchical clustering of the edges of a
 * graph.
 * 
 * @author Stuart Hendren
 * 
 * @param <V>
 *            the vertex type
 * @param <E>
 *            the edge type
 */
public class PartitionDendogramImpl<T> implements PartitionDendrogram<T> {

	private List<Partition<T>> partitions = new ArrayList<Partition<T>>();

	/**
	 * Add the next EdgePartition to the dendogram, the partition should cover the previous edge partition.
	 * 
	 * @param metric
	 *            the value of the metric used to create the clustering
	 * @param partition
	 *            the corresponding edge partition
	 */
	public void addPartition(Partition<T> partition) {
		partitions.add(partition);
	}

	public Iterator<Partition<T>> getPartitions() {
		return Collections.unmodifiableList(partitions).iterator();
	}

	public List<Partition<T>> getPartitionsList() {
		return Collections.unmodifiableList(partitions);
	}

	public boolean isValid() {
		Iterator<Partition<T>> iterator = partitions.iterator();
		Partition<T> lastPartition = iterator.next();
		try {
			if (!lastPartition.isComplete()) {
				return false;
			}
			while (iterator.hasNext()) {
				Partition<T> nextPartition = iterator.next();
				if (lastPartition.covers(nextPartition)) {
					return false;
				}
				lastPartition = nextPartition;
			}
		} catch (IllegalStateException e) {
			return false;
		}
		return true;
	}

	public Partition<T> getPartition(int index) {
		return partitions.get(index);
	}

	public Partition<T> insertNewPartition(int indexOf) {
		Partition<T> edgePartition = partitions.get(indexOf - 1);
		Partition<T> copy = edgePartition.copy();
		partitions.add(indexOf, copy);
		return copy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Partition<T> edgePartition : partitions) {
			builder.append(edgePartition.toString());
			builder.append("\n");
		}
		return builder.toString();
	}
}
