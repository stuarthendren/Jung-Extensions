package net.stuarthendren.jung.cluster.vertex;

import net.stuarthendren.jung.cluster.vertex.impl.BackedClusterImpl;

import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import static junit.framework.Assert.assertEquals;

public class BackedClusterImplTest extends AbstractClusterImplTest {

	@Before
	public void createCluster() {
		addVertices();
		cluster = new BackedClusterImpl<Integer, Integer>(graph, vertices);
	}

	@Test
	public void testBacked() {
		Cluster<Integer, Integer> c = new BackedClusterImpl<Integer, Integer>(graph);
		Graph<Integer, Integer> inducedGraph = c.getInducedGraph();
		assertEquals(0, inducedGraph.getEdgeCount());
		assertEquals(0, c.size());
		c.add(0);
		c.add(1);
		assertEquals(2, c.size());
		assertEquals(1, inducedGraph.getEdgeCount());
	}

}
