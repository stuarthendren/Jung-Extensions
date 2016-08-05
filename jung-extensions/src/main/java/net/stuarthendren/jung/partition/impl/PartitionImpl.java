package net.stuarthendren.jung.partition.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.stuarthendren.jung.partition.Partition;

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
public class PartitionImpl<T> implements Partition<T> {

	private final Set<T> set;

	private Map<T, Set<T>> index = new HashMap<T, Set<T>>();

	private Set<Set<T>> partitioning = new HashSet<Set<T>>();

	/**
	 * Create a new, empty, edge partition
	 * 
	 * @param set of things to be partitioned
	 */
	public PartitionImpl(Collection<T> set) {
		this.set = new HashSet<T>(set);
	}

	@Override
	public Set<T> getSet() {
		return Collections.unmodifiableSet(set);
	}

	@Override
	public void addCluster(Set<T> cluster) {
		for (T e : cluster) {
			Set<T> previous = index.put(e, cluster);
			if (previous != null) {
				throw new IllegalArgumentException("The edge [" + e + "] is already in a cluster");
			}
		}
		partitioning.add(cluster);
	}

	@Override
	public boolean mergeCluster(T t1, T t2) {
		Set<T> set1 = index.get(t1);
		Set<T> set2 = index.get(t2);
		if (set1 == set2) {
			return false;
		}
		partitioning.remove(set1);
		partitioning.remove(set2);
		if (set1.size() > set2.size()) {
			mergeInternal(set1, set2);
		} else {
			mergeInternal(set2, set1);
		}
		return true;
	}

	/**
	 * Merge the clusters that contain the edges edge1 and edge2 into one cluster of the partition.
	 * 
	 * @param t1
	 * @param t2
	 * @return partition
	 */
	public Partition<T> buildCluster(T t1, T t2) {
		mergeCluster(t1, t2);
		return this;
	}

	private void mergeInternal(Set<T> setA, Set<T> setB) {
		setA.addAll(setB);
		for (T e : setB) {
			index.put(e, setA);
		}
		partitioning.add(setA);
	}

	private int numberOfEdges() {
		return index.size();
	}

	@Override
	public boolean isComplete() {
		return numberOfEdges() == set.size();
	}

	@Override
	public void addCluster(T... cluster) {
		addCluster(new HashSet<T>(Arrays.asList(cluster)));
	}

	@Override
	public String toString() {
		return partitioning.toString();
	}

	/**
	 * Create a new EdgePartition with each edge in its own cluster.
	 * 
	 * @param <V>
	 *            the vertex type
	 * @param <T>
	 *            the edge type
	 * @param set
	 */
	@SuppressWarnings("unchecked")
	public static <T> Partition<T> createMaximumPartition(Collection<T> set) {
		Partition<T> partition = new PartitionImpl<T>(set);
		for (T i : set) {
			partition.addCluster(i);
		}

		return partition;
	}

	@Override
	public Partition<T> copy() {
		Partition<T> copy = new PartitionImpl<T>(set);
		for (Set<T> cluster : partitioning) {
			copy.addCluster(new HashSet<T>(cluster));
		}
		return copy;
	}

	@Override
	public Set<Set<T>> getPartitioning() {
		return Collections.unmodifiableSet(partitioning);
	}

	@Override
	public boolean covers(Partition<T> partition) {
		if (!set.equals(partition.getSet())) {
			return false;
		}
		for (Set<T> cluster : partition.getPartitioning()) {
			T firstElement = cluster.iterator().next();
			Set<T> correspondingCluster = index.get(firstElement);
			for (T t : cluster) {
				if (!correspondingCluster.contains(t)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean contains(T e) {
		return index.containsKey(e);
	}

	@Override
	public Set<T> getCluster(T e) {
		return Collections.unmodifiableSet(index.get(e));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((set == null) ? 0 : set.hashCode());
		result = prime * result + ((partitioning == null) ? 0 : partitioning.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PartitionImpl<?> other = (PartitionImpl<?>) obj;
		if (set == null) {
			if (other.set != null) {
				return false;
			}
		} else if (!set.equals(other.set)) {
			return false;
		}
		if (partitioning == null) {
			if (other.partitioning != null) {
				return false;
			}
		} else if (!partitioning.equals(other.partitioning)) {
			return false;
		}
		return true;
	}
}
