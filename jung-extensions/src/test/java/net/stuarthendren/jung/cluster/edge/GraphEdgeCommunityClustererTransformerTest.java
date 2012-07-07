package net.stuarthendren.jung.cluster.edge;

import java.util.Collection;
import java.util.Set;

import net.stuarthendren.jung.dendrogram.partition.impl.AbstractEdgeClusterTest;
import net.stuarthendren.jung.graph.CompleteGraph;

import org.apache.commons.collections15.CollectionUtils;
import org.junit.Test;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphEdgeCommunityClustererTransformerTest extends AbstractEdgeClusterTest {

	@Test
	public void checkDisconnectedGraphReturnsEmptySet() {
		UndirectedGraph<Integer, Integer> emptyGraph = new UndirectedSparseGraph<Integer, Integer>();
		GraphEdgeCommunityClustererTransformer<Integer, Integer> edgeCommunityClusterer = new GraphEdgeCommunityClustererTransformer<Integer, Integer>();
		Collection<Set<Integer>> edgePartition = edgeCommunityClusterer.transform(emptyGraph);
		assertEquals(0, edgePartition.size());
	}

	@Test
	public void checkMaxDensityEdgePartitionIsReturnedByTransformer() {
		GraphEdgeCommunityClustererTransformer<Integer, Character> edgeCommunityClusterer = new GraphEdgeCommunityClustererTransformer<Integer, Character>();
		Collection<Set<Character>> edgePartition = edgeCommunityClusterer.transform(graph);
		assertEquals(4, edgePartition.size());
		assertTrue(CollectionUtils.isEqualCollection(edgePartition, maxDensityEdgePartition.getPartitioning()));
	}

	@Test
	public void checkCompleteGraphReturnsBasePartition() {
		UndirectedGraph<Integer, Integer> completeGraph = new CompleteGraph(10);
		GraphEdgeCommunityClustererTransformer<Integer, Integer> edgeCommunityClusterer = new GraphEdgeCommunityClustererTransformer<Integer, Integer>();
		Collection<Set<Integer>> edgePartition = edgeCommunityClusterer.transform(completeGraph);
		assertEquals(1, edgePartition.size());
	}
}
