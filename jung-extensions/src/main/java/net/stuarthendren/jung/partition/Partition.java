package net.stuarthendren.jung.partition;

import java.util.Set;

/**
 * 
 * A partition is a set of non-intersecting sets, called clusters. The partition is complete when all of the given
 * collection are in a cluster of the partition.
 * 
 * @author Stuart Hendren
 * 
 * @param <T>
 *            the type to be partitioned
 */
public interface Partition<T> {

	/**
	 * Add a cluster of edges to the partition - this cluster must not intersect any clusters already in the partition.
	 * 
	 * @param cluster
	 *            A set of edges of the graph
	 */
	void addCluster(Set<T> cluster);

	/**
	 * Merge the clusters that contain the edges edge1 and edge2 into one cluster of the partition.
	 * 
	 * @param t1
	 * @param t2
	 * @return true if partition changed
	 */
	boolean mergeCluster(T t1, T t2);

	/**
	 * @return true if all edges of the graph are in a cluster of the partition
	 */
	boolean isComplete();

	/**
	 * Add a cluster of edges to the partition - this cluster must not intersect any clusters already in the partition.
	 * 
	 * @param cluster
	 *            An array of edges of the graph
	 */
	void addCluster(T... cluster);

	/**
	 * Create a new EdgePartition containing the same clusters.
	 * 
	 * @return
	 */
	Partition<T> copy();

	/**
	 * @return an unmodifiable set of edge clusters
	 */
	Set<Set<T>> getPartitioning();

	/**
	 * This edge partition, A covers the parameter edgePartition B if and only if every cluster in B is completely
	 * contained in a single cluster of A.
	 * 
	 * @param partition
	 * @return
	 */
	boolean covers(Partition<T> partition);

	boolean contains(T t);

	/**
	 * 
	 * @param t
	 * @return the unique cluster containing the value t
	 */
	Set<T> getCluster(T t);

	/**
	 * @return the set of objects to be partitioned
	 */
	Set<T> getSet();

}