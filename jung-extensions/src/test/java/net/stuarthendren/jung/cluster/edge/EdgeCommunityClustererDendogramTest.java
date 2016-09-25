package net.stuarthendren.jung.cluster.edge;

import static org.junit.Assert.assertEquals;
import net.stuarthendren.jung.cluster.edge.impl.EdgeDendogramUtils;
import net.stuarthendren.jung.dendrogram.Dendrogram;
import net.stuarthendren.jung.dendrogram.partition.impl.AbstractEdgeClusterTest;
import net.stuarthendren.jung.partition.Partition;

import org.junit.Test;

public class EdgeCommunityClustererDendogramTest extends AbstractEdgeClusterTest {

	@Test
	public void canGetMaximalPartitionDensityEdgePartition() {
		Dendrogram<Character> dendrogram = EdgeCommunityClusterer.computeEdgeComunityDendrogram(graph);

		Partition<Character> maximalPatition = EdgeDendogramUtils.getMaximalPatition(graph, dendrogram);
		assertEquals(maxDensityEdgePartition, maximalPatition);
	}

}
