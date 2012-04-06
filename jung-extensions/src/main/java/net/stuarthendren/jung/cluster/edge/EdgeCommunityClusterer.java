package net.stuarthendren.jung.cluster.edge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import net.stuarthendren.jung.cluster.edge.impl.EdgePair;
import net.stuarthendren.jung.cluster.edge.impl.EdgeSimilarityTransformer;
import net.stuarthendren.jung.cluster.edge.impl.KeyedEdgePair;
import net.stuarthendren.jung.dendrogram.Dendrogram;
import net.stuarthendren.jung.dendrogram.partition.impl.PartitionDendogramImpl;
import net.stuarthendren.jung.partition.Partition;
import net.stuarthendren.jung.partition.impl.PartitionImpl;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

/**
 * <p>
 * The EdgeCommunityClusterer calculates a partition of the edges in a graph where each partition defines a community in
 * the graph. Clustering edges allows vertices to be in multiple communities.
 * </p>
 * 
 * The EdgeCommunityClusterer is based on the paper:
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
 * This implementation attempts to stay close to the original text of the paper. In this class and supporting data
 * structures variable names match those used in the paper. It uses the Partition implementation of the
 * {@link Dendrogram}. (This is faster that the graph implementation but uses more memory).
 * 
 * @author Stuart Hendren
 */
public class EdgeCommunityClusterer<V, E> {

	public static <V, E> Dendrogram<E> computeEdgeComunityDendrogram(UndirectedGraph<V, E> graph) {
		SortedMap<Double, Collection<EdgePair<E>>> similarities = getSortedEdgeSimilaritiesMap(graph);
		return buildDendogram(graph, similarities);
	}

	protected static <V, E> SortedMap<Double, Collection<EdgePair<E>>> getSortedEdgeSimilaritiesMap(
			UndirectedGraph<V, E> graph) {
		SortedMap<Double, Collection<EdgePair<E>>> similarities = new TreeMap<Double, Collection<EdgePair<E>>>(
				new Comparator<Double>() {
					@Override
					public int compare(Double o1, Double o2) {
						return -o1.compareTo(o2);
					}
				});
		populateEdgeSimilaritiesMap(similarities, graph);
		return similarities;
	}

	private static <V, E> Dendrogram<E> buildDendogram(Graph<V, E> graph,
			Map<Double, Collection<EdgePair<E>>> similarities) {

		PartitionDendogramImpl<E> dendogram = new PartitionDendogramImpl<E>();

		Partition<E> basePartition = PartitionImpl.createMaximumPartition(graph.getEdges());

		dendogram.addPartition(basePartition);

		Partition<E> workingPartition = basePartition;
		for (Entry<Double, Collection<EdgePair<E>>> entry : similarities.entrySet()) {
			workingPartition = workingPartition.copy();
			for (EdgePair<E> pair : entry.getValue()) {
				workingPartition.mergeCluster(pair.getEdge1(), pair.getEdge2());
			}
			dendogram.addPartition(workingPartition);
		}

		return dendogram;

	}

	private static <V, E> Map<Double, Collection<EdgePair<E>>> populateEdgeSimilaritiesMap(
			Map<Double, Collection<EdgePair<E>>> map, UndirectedGraph<V, E> graph) {

		EdgeSimilarityTransformer<V, E> transformer = new EdgeSimilarityTransformer<V, E>(graph);

		Map<E, Integer> edgeIndex = new HashMap<E, Integer>(graph.getEdgeCount());
		int index = 0;
		for (E edge : graph.getEdges()) {
			edgeIndex.put(edge, index);
			index++;
		}

		for (V vertex_k : graph.getVertices()) {
			for (E edge_ik : graph.getIncidentEdges(vertex_k)) {
				for (E edge_kj : graph.getIncidentEdges(vertex_k)) {
					int i = edgeIndex.get(edge_ik);
					int j = edgeIndex.get(edge_kj);
					if (i < j) {
						KeyedEdgePair<V, E> pair = new KeyedEdgePair<V, E>(vertex_k, edge_ik, edge_kj);
						putOrAmend(map, transformer.transform(pair), pair.getEdgePair());
					}
				}
			}
		}

		return map;
	}

	private static <E> void putOrAmend(Map<Double, Collection<EdgePair<E>>> map, Double similarity, EdgePair<E> pair) {
		Collection<EdgePair<E>> pairs;
		if (!map.containsKey(similarity)) {
			pairs = new ArrayList<EdgePair<E>>();
			map.put(similarity, pairs);
		} else {
			pairs = map.get(similarity);
		}
		pairs.add(pair);
	}
}
