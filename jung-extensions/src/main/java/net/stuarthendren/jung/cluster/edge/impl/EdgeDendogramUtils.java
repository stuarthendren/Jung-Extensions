package net.stuarthendren.jung.cluster.edge.impl;

import java.util.Iterator;

import net.stuarthendren.jung.dendrogram.Dendrogram;
import net.stuarthendren.jung.partition.Partition;
import edu.uci.ics.jung.graph.Graph;

/**
 * An EdgeDemogram is a list of {@link EdgePartition}s that represents a hierarchical clustering of the edges of a
 * graph.
 * 
 * @author Stuart Hendren
 * @author WJR Longabaugh
 * 
 * @param <V>
 *            the vertex type
 * @param <E>
 *            the edge type
 * 
 * 
 */
public class EdgeDendogramUtils {

	public static <V, E> Partition<E> getMaximalPatition(Graph<V, E> graph, Dendrogram<E> dendrogram) {
		EdgePartitionDensityTransformer<V, E> transformer = new EdgePartitionDensityTransformer<V, E>(graph);
		Iterator<Partition<E>> partitions = dendrogram.getPartitions();
		Double maxDensity = 0.0;
		Partition<E> last = null;
		while (partitions.hasNext()) {
			Partition<E> next = partitions.next();
			Double partitionDensity = transformer.transform(next);
			if (maxDensity <= partitionDensity) {
				maxDensity = partitionDensity;
				last = next.copy();
			}
		}
		return last;
	}

	// public static <V, E> Partition<E> getPartition(Graph<V, E> graph, Dendrogram<E> dendrogram, Double density) {
	// EdgePartitionDensityTransformer<V, E> transformer = new EdgePartitionDensityTransformer<V, E>(graph);
	// Iterator<Partition<E>> partitions = dendrogram.getPartitions();
	// Double currentDensity = -1.0;
	// Partition<E> last = null;
	// while (partitions.hasNext() && currentDensity < density) {
	// Partition<E> next = partitions.next();
	// Double currentDensity = transformer.transform(next);
	// last = next;
	// }
	//
	// return last;
	// }

}
