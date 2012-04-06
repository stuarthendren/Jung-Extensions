package net.stuarthendren.jung.dendrogram.partition;

import java.util.List;

import net.stuarthendren.jung.dendrogram.Dendrogram;
import net.stuarthendren.jung.partition.Partition;

public interface PartitionDendrogram<T> extends Dendrogram<T> {

	/**
	 * @return a list of the partitions
	 */
	List<Partition<T>> getPartitionsList();

	/**
	 * Check to see that the dendrogram is in a valid state
	 * 
	 * ie that every partition covers the previous partition
	 * 
	 * @return
	 */
	boolean isValid();

}