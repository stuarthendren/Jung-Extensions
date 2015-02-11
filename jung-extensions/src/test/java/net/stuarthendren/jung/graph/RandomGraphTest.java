package net.stuarthendren.jung.graph;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;

public class RandomGraphTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeGroups() {
		new RandomGraph(-2, 0.5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeVerticies() {
		new RandomGraph(10, 1.5);
	}

	@Test()
	public void testSizeOfGraph() {
		int size = 10;
		Graph<Integer, Integer> graph = new RandomGraph(size, 0);
		assertEquals(size, graph.getVertexCount());
		assertEquals(0, graph.getEdgeCount());
	}

	@Test()
	public void testProbabilitites() {
		int size = 100;
		double probability = 0.5;
		Graph<Integer, Integer> graph = new RandomGraph(size, probability, 0);
		double maxNumberOfEdges = (size * (size - 1)) / 2;
		assertEquals(probability, graph.getEdgeCount() / maxNumberOfEdges, 0.01);

	}
}
