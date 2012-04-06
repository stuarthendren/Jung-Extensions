package net.stuarthendren.jung.cluster.edge;

import net.stuarthendren.jung.cluster.edge.impl.EdgeDendogramUtils;
import net.stuarthendren.jung.dendrogram.graph.GraphDendrogram;
import net.stuarthendren.jung.dendrogram.graph.impl.KeyedDendrogram;
import net.stuarthendren.jung.dendrogram.partition.impl.AbstractEdgeClusterTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeyedEdgeCommunityClustererDendogramTest extends AbstractEdgeClusterTest {

	@SuppressWarnings("unchecked")
	@Test
	public void canGetMaximalPartitionDensityEdgePartition() {
		GraphEdgeCommunityClusterer<Integer, Character> clusterer = new GraphEdgeCommunityClusterer<Integer, Character>(
				graph);
		GraphDendrogram<Character> dendrogram = clusterer.computeEdgeComunityDendogram();
		assertEquals(maxDensityEdgePartition.getPartitioning(), EdgeDendogramUtils
				.getMaximalPatition(graph, dendrogram).getPartitioning());
		assertEquals(4, dendrogram.getHeight());
		assertEquals(7, ((KeyedDendrogram<Character, Double>) dendrogram).getNumberOfPartitions());

	}
}
