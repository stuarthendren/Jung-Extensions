package net.stuarthendren.jung.cluster.edge;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import net.stuarthendren.jung.cluster.edge.impl.EdgeDendogramUtils;

import org.apache.commons.collections15.Transformer;

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
 * This class follows the general pattern of clusters in Jung by implementing the {@link Transformer} interface.
 * 
 * @author Stuart Hendren
 */
public class GraphEdgeCommunityClustererTransformer<V, E> implements
		Transformer<UndirectedGraph<V, E>, Collection<Set<E>>> {

	/**
	 * Returns a partition of the links of the graph. This partition is the cut of the edge similarity dendrogram with
	 * maximal partition density.
	 * 
	 */
	@Override
	public Collection<Set<E>> transform(UndirectedGraph<V, E> graph) {
		if (graph.getEdgeCount() == 0) {
			return Collections.emptySet();
		}
		GraphEdgeCommunityClusterer<V, E> clusterer = new GraphEdgeCommunityClusterer<V, E>(graph);
		return EdgeDendogramUtils.getMaximalPatition(graph, clusterer.computeEdgeComunityDendogram()).getPartitioning();
	}
}
