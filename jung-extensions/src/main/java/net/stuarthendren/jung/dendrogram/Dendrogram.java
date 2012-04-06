package net.stuarthendren.jung.dendrogram;

import java.util.Iterator;

import net.stuarthendren.jung.partition.Partition;

/**
 * A dendrogram encodes a hierarchical clustering of a set of objects.
 * 
 * Each level of the clustering is a {@link Partition} of the set of objects (of type T). Each level must cover the
 * previous see {@link Partition#covers(Partition)}.
 * 
 * @author Stuart Hendren
 * 
 * @param <T>
 */
public interface Dendrogram<T> {

	/**
	 * @return an iterator of the partitions starting from the partition that contains each object as a single cluster
	 *         and ending with the partition that contains a single cluster.
	 */
	Iterator<Partition<T>> getPartitions();

}
