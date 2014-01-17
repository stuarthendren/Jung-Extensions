package net.stuarthendren.jung.cluster.edge;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

import net.stuarthendren.jung.cluster.edge.impl.EdgePair;
import net.stuarthendren.jung.cluster.edge.impl.EdgePartitionDensityTransformer;
import net.stuarthendren.jung.partition.Partition;
import net.stuarthendren.jung.partition.impl.PartitionImpl;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.UndirectedGraph;

/**
 * <p>
 * The EdgeCommunityClustererTransformer calculates a partition of the edges in a graph where each partition defines a
 * community in the graph. Clustering edges allows vertices to be in multiple communities.
 * </p>
 * 
 * <p>
 * Uses the {@link EdgeCommunityClusterer} based on the paper:
 * 
 * <pre>
 * Author = {Yong-Yeol Ahn and James P. Bagrow and Sune Lehmann},
 * Title = {Link communities reveal multiscale complexity in networks},
 * Year = {2009},
 * Eprint = {arXiv:0903.3178},
 * Howpublished = {Nature 466, 761-764 (2010)},
 * Doi = {10.1038/nature09182}
 * </pre>
 * 
 * This class follows the general pattern of clusters in Jung by implementing the {@link Transformer} interface.
 * 
 * @author Stuart Hendren
 * @author WJR Longabaugh
 * 
 */
public class EdgeCommunityClustererTransformer<V, E> implements Transformer<UndirectedGraph<V, E>, Collection<Set<E>>> {

	/**
	 * Returns a partition of the links of the graph. This partition is the cut of the edge similarity dendrogram with
	 * maximal partition density.
	 * 
	 */
	@Override
	public Collection<Set<E>> transform(UndirectedGraph<V, E> graph) {
		if (graph.getVertices().isEmpty()) {
			return new HashSet<Set<E>>();
		}

		SortedMap<Double, Collection<EdgePair<E>>> sortedEdgeSimilarities = EdgeCommunityClusterer
				.getSortedEdgeSimilaritiesMap(graph);

		EdgePartitionDensityTransformer<V, E> transformer = new EdgePartitionDensityTransformer<V, E>(graph);

		Partition<E> basePartition = PartitionImpl.createMaximumPartition(graph.getEdges());

		Iterator<Entry<Double, Collection<EdgePair<E>>>> it = sortedEdgeSimilarities.entrySet().iterator();
		Partition<E> workingPartition = basePartition.copy();
		Partition<E> returnPartition = basePartition.copy();
		Double workingDensity = 0d;
		while (it.hasNext()) {
			Entry<Double, Collection<EdgePair<E>>> similarity = it.next();
			for (EdgePair<E> pair : similarity.getValue()) {
				workingPartition.mergeCluster(pair.getEdge1(), pair.getEdge2());        
			}
			Double localDensity = transformer.transform(workingPartition);
			if (localDensity >= workingDensity) {
				workingDensity = localDensity;
				returnPartition = workingPartition.copy();
			}
		}
		return returnPartition.getPartitioning();
	}

}
