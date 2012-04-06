package net.stuarthendren.jung.cluster.edge;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import net.stuarthendren.jung.cluster.edge.impl.EdgeSimilarityTransformer;
import net.stuarthendren.jung.cluster.edge.impl.KeyedEdgePair;
import net.stuarthendren.jung.dendrogram.graph.GraphDendrogram;
import net.stuarthendren.jung.dendrogram.graph.impl.KeyedDendrogram;
import edu.uci.ics.jung.graph.UndirectedGraph;

/**
 * <p>
 * The GraphEdgeCommunityClusterer calculates a partition of the edges in a graph where each partition defines a
 * community in the graph. Clustering edges allows vertices to be in multiple communities.
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
 * This implementation uses the Graph based dendrogram, it is slower than the Partition based version but more memory
 * efficient.
 * 
 * @author Stuart Hendren
 */
public class GraphEdgeCommunityClusterer<V, E> {

	private final KeyedDendrogram<E, Double> dendrogram;

	private final UndirectedGraph<V, E> graph;

	public GraphEdgeCommunityClusterer(UndirectedGraph<V, E> graph) {
		this.graph = graph;
		dendrogram = new KeyedDendrogram<E, Double>(graph.getEdges(), new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				return -o1.compareTo(o2);
			}
		});
	}

	public GraphDendrogram<E> computeEdgeComunityDendogram() {
		buildDendogram();
		return dendrogram;
	}

	private void buildDendogram() {

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
						dendrogram.merge(transformer.transform(pair), edge_ik, edge_kj);
					}
				}
			}
		}
	}
}
